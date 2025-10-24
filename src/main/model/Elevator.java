package model;

import java.util.ArrayList;
import java.util.List;

// Represents an elevator 
public class Elevator {
    private int currentFloor; // The floor that the elevator is currently on.
    private int nextFloor; // The next floor that the elevator is going to.
    private int floorsInBuilding; // Total floors in building, where 1 is the ground floor (floorsInBuiding >= 1). 
    private List<Integer> requestedFloors; // The list of requested floors. 
    private Direction direction; // The direction the elevator is travelling: "UP", "DOWN", "IDLE"

    // REQUIRES: floorsInBuilding >= 1
    // EFFECTS: creates an Elevator starting at floor 1 with zero load,
    // IDLE direction, and empty requestedFloors list
    public Elevator(int floorsInBuilding) {
        this.currentFloor = 1;
        this.floorsInBuilding = floorsInBuilding;
        this.requestedFloors = new ArrayList<>();
        this.direction = Direction.IDLE;
    }

    // REQUIRES: 1<= floor and <= number of floors in building
    // MODIFIES: requestedFloors
    // EFFECTS: adds the given floor to the list of requested floors if not already
    // present
    public void addFloorRequest(int floor) {
        if (!requestedFloors.contains(floor)) {
            requestedFloors.add(floor);
        }
    }

    // MODIFIES: currentFloor, direction, requestedFloors
    // EFFECTS: moves the elevator one floor towards next requested floor,
    // updates direction accordingly. If there are no next requested floors,
    // direction is changed to IDLE.
    public void move() {
        if (requestedFloors.isEmpty()) {
            direction = Direction.IDLE;
        } else if (currentFloor < getNextRequestedFloor()) {
            currentFloor++;
            direction = Direction.UP;
        } else if (currentFloor > getNextRequestedFloor()) {
            currentFloor--;
            direction = Direction.DOWN;
        } else { // Arrived at requested floor
            dropOff(currentFloor); // remove current floor from requested

        }
    }

    // REQUIRES: requestedFloors contains currentFloor
    // MODIFIES: requestedFloors
    // EFFECTS: drops off passengers at current floor and removes current floor,
    // removes current floor from requestedFloors
    public void dropOff(int floor) {
        // Remove the current floor from the requested floors
        requestedFloors.remove(Integer.valueOf(floor));
        if (requestedFloors.isEmpty()) {
            direction = Direction.IDLE;
        } else if (floor > getNextRequestedFloor()) {
            direction = Direction.DOWN; 
        } else {
            direction = Direction.UP;
        }
    }
        
    // REQUIRES: requestedFloors is not empty
    // EFFECTS: returns next requested floor without removing it
    public int getNextRequestedFloor() {
        nextFloor = getRequestedFloors().get(0);
        return nextFloor;
    }

    // EFFECTS: returns current floor of the elevator
    public int getCurrentFloor() {
        return currentFloor;
    } 
    
    // EFFECTS: returns list of requested floors
    public List<Integer> getRequestedFloors() {
        return requestedFloors;
    }

    // EFFECTS: returns current direction of elevator
    public Direction getDirection() {
        return direction;
    }

    // EFFECTS: returns floors in building
    public int getFloorsInBuilding() {
        return floorsInBuilding;
    }

}
