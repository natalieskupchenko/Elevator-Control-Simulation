package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import persistence.Writable;

// Represents an elevator

public class Elevator implements Writable {
    private int currentFloor; // The floor that the elevator is currently on.
    private int nextFloor; // The next floor that the elevator is going to.
    private int floorsInBuilding; // Total floors in building (>= 1).
    private List<Integer> requestedFloors; // The list of requested floors.
    private Direction direction; // The direction the elevator is travelling.

    // REQUIRES: floorsInBuilding >= 1
    // EFFECTS: creates an Elevator starting at floor 1 with IDLE direction
    public Elevator(int floorsInBuilding) {
        this.currentFloor = 1;
        this.floorsInBuilding = floorsInBuilding;
        this.requestedFloors = new ArrayList<>();
        this.direction = Direction.IDLE;

        EventLog.getInstance().logEvent(
                new Event("Created new elevator with " + floorsInBuilding + " floors."));
    }

    // REQUIRES: 1 <= currentFloor <= floorsInBuilding
    // EFFECTS: constructs elevator with full state (used for loading from JSON)
    public Elevator(int floorsInBuilding, int currentFloor, Direction direction, List<Integer> requestedFloors) {
        this.floorsInBuilding = floorsInBuilding;
        this.currentFloor = currentFloor;
        this.direction = direction;
        this.requestedFloors = new ArrayList<>(requestedFloors);

        EventLog.getInstance().logEvent(
                new Event("Loaded elevator from file: current floor "
                        + currentFloor + ", direction " + direction + ", requested floors " + requestedFloors + "."));
    }

    // EFFECTS: returns this elevator as a JSON object
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("currentFloor", currentFloor);
        json.put("floorsInBuilding", floorsInBuilding);
        json.put("direction", direction.toString());
        json.put("requestedFloors", requestedFloorsToJson());
        return json;
    }

    // EFFECTS: returns requestedFloors as JSON array
    private JSONArray requestedFloorsToJson() {
        JSONArray jsonArray = new JSONArray();
        for (Integer floor : requestedFloors) {
            jsonArray.put(floor);
        }
        return jsonArray;
    }

    // MODIFIES: this
    // EFFECTS: if valid floor and not duplicate/current, adds it to requestedFloors
    public void addFloorRequest(int floor) {
        if (floor < 1 || floor > floorsInBuilding) {
            return;
        }
        if (floor == currentFloor || requestedFloors.contains(floor)) {
            return;
        }

        requestedFloors.add(floor);
        EventLog.getInstance().logEvent(new Event("Floor " + floor + " requested."));


        if (direction == Direction.UP || direction == Direction.IDLE) {
            requestedFloors.sort(Integer::compareTo);
        } else {
            requestedFloors.sort(Collections.reverseOrder());
        }
        
    }

    public void move() {
        if (requestedFloors.isEmpty()) {
            direction = Direction.IDLE;
            EventLog.getInstance().logEvent(new Event("Elevator is now idle."));
        } else if (currentFloor < getNextRequestedFloor()) {
            int old = currentFloor;
            currentFloor++;
            direction = Direction.UP;
            EventLog.getInstance().logEvent(
                    new Event("Elevator moved from floor "
                            + old + " to floor " + currentFloor + "."));
        } else if (currentFloor > getNextRequestedFloor()) {
            int old = currentFloor;
            currentFloor--;
            direction = Direction.DOWN;
            EventLog.getInstance().logEvent(
                    new Event("Elevator moved from floor " 
                    + old + " to floor " + currentFloor + "."));
        } else {
            // Arrived at requested floor
            dropOff(currentFloor);
            // remove current floor from requested } }
        }
    }

    // REQUIRES: requestedFloors contains currentFloor
    // MODIFIES: requestedFloors
    // EFFECTS: removes current floor from requested floors
    public void dropOff(int floor) {
        requestedFloors.remove(Integer.valueOf(floor));

        EventLog.getInstance().logEvent(
                new Event("Elevator arrived at floor " + floor + " and dropped off passengers."));

        if (requestedFloors.isEmpty()) {
            direction = Direction.IDLE;
            EventLog.getInstance().logEvent(new Event("Elevator is now idle."));
        } else if (floor > getNextRequestedFloor()) {
            direction = Direction.DOWN;
            EventLog.getInstance().logEvent(new Event("Elevator is moving down."));
        } else {
            direction = Direction.UP;
            EventLog.getInstance().logEvent(new Event("Elevator is moving up."));
        }
    }

    // REQUIRES: requestedFloors is not empty
    // EFFECTS: returns next requested floor without removing it
    public int getNextRequestedFloor() {
        nextFloor = getRequestedFloors().get(0);
        return nextFloor;
    }

    public int getCurrentFloor() {
        return currentFloor;
    }

    public List<Integer> getRequestedFloors() {
        return requestedFloors;
    }

    public Direction getDirection() {
        return direction;
    }

    public int getFloorsInBuilding() {
        return floorsInBuilding;
    }
}
