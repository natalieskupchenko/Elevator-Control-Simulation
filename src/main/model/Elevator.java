package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import persistence.Writable;

/**
 * Represents an elevator in a building with direction-aware request scheduling.
 * 
 * The elevator maintains a sorted list of requested floors and moves one floor
 * at a time toward the next destination. Requests are automatically sorted based
 * on the elevator's current direction to minimize travel time.
 */
public class Elevator implements Writable {
    private int currentFloor;          // The floor the elevator is currently on
    private int nextFloor;             // The next floor the elevator will visit
    private int floorsInBuilding;      // Total floors in building (>= 1)
    private List<Integer> requestedFloors;  // Sorted list of requested floors
    private Direction direction;       // Current direction (UP, DOWN, or IDLE)

    // REQUIRES: floorsInBuilding >= 1
    // EFFECTS: creates an Elevator starting at floor 1 with IDLE direction
    //          and logs the creation event
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
    //          and logs the load event
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
    //          and sorts the list based on current direction;
    //          ignores invalid floors (out of range, duplicate, or current floor)
    public void addFloorRequest(int floor) {
        // Validate floor is within building bounds
        if (floor < 1 || floor > floorsInBuilding) {
            return;
        }
        
        // Ignore if already at this floor or already requested
        if (floor == currentFloor || requestedFloors.contains(floor)) {
            return;
        }

        // Add the floor to our request list
        requestedFloors.add(floor);
        EventLog.getInstance().logEvent(new Event("Floor " + floor + " requested."));

        // Sort requests based on current direction for efficient travel
        // UP or IDLE: sort ascending (visit lower floors first, then higher)
        // DOWN: sort descending (visit higher floors first, then lower)
        if (direction == Direction.UP || direction == Direction.IDLE) {
            requestedFloors.sort(Integer::compareTo);
        } else {
            requestedFloors.sort(Collections.reverseOrder());
        }
    }

    // MODIFIES: this
    // EFFECTS: moves elevator one floor toward next requested floor, or stays idle
    //          if no requests; logs movement events; calls dropOff when arriving
    //          at a requested floor
    public void move() {
        if (requestedFloors.isEmpty()) {
            // No requests - remain idle
            direction = Direction.IDLE;
            EventLog.getInstance().logEvent(new Event("Elevator is now idle."));
        } else if (currentFloor < getNextRequestedFloor()) {
            // Need to move up
            int old = currentFloor;
            currentFloor++;
            direction = Direction.UP;
            EventLog.getInstance().logEvent(
                    new Event("Elevator moved from floor "
                            + old + " to floor " + currentFloor + "."));
        } else if (currentFloor > getNextRequestedFloor()) {
            // Need to move down
            int old = currentFloor;
            currentFloor--;
            direction = Direction.DOWN;
            EventLog.getInstance().logEvent(
                    new Event("Elevator moved from floor " 
                    + old + " to floor " + currentFloor + "."));
        } else {
            // Arrived at requested floor - drop off passengers
            dropOff(currentFloor);
        }
    }

    // REQUIRES: requestedFloors contains floor
    // MODIFIES: this
    // EFFECTS: removes floor from requested floors, updates direction based on
    //          remaining requests, and logs the drop-off event
    public void dropOff(int floor) {
        requestedFloors.remove(Integer.valueOf(floor));

        EventLog.getInstance().logEvent(
                new Event("Elevator arrived at floor " + floor + " and dropped off passengers."));

        // Update direction based on remaining requests
        if (requestedFloors.isEmpty()) {
            direction = Direction.IDLE;
            EventLog.getInstance().logEvent(new Event("Elevator is now idle."));
        } else if (floor > getNextRequestedFloor()) {
            // Next request is below us - go down
            direction = Direction.DOWN;
            EventLog.getInstance().logEvent(new Event("Elevator is moving down."));
        } else {
            // Next request is above us - go up
            direction = Direction.UP;
            EventLog.getInstance().logEvent(new Event("Elevator is moving up."));
        }
    }

    // REQUIRES: requestedFloors is not empty
    // EFFECTS: returns next requested floor without removing it from the list
    public int getNextRequestedFloor() {
        nextFloor = getRequestedFloors().get(0);
        return nextFloor;
    }

    // EFFECTS: returns current floor number
    public int getCurrentFloor() {
        return currentFloor;
    }

    // EFFECTS: returns a copy of the requested floors list
    public List<Integer> getRequestedFloors() {
        return requestedFloors;
    }

    // EFFECTS: returns current direction of elevator
    public Direction getDirection() {
        return direction;
    }

    // EFFECTS: returns total number of floors in the building
    public int getFloorsInBuilding() {
        return floorsInBuilding;
    }
}