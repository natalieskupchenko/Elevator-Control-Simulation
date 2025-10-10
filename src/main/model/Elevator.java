package model;

import java.util.ArrayList;
import java.util.List;

// Represents an elevator 
public class Elevator {
    private int currentFloor; // The floor that the elevator is currently on.
    private int nextFloor; // The next floor that the elevator is going to.
    private int floorsInBuilding; // Total floors in building, where 1 is the ground floor (floorsInBuiding >= 1).
                                  // Later phases can add basement floors.
    private List<Integer> requestedFloors; // The list of requested floors. Later phases - can sort by order (least to
                                           // greatest or reverse).
    private Direction direction; // The direction the elevator is travelling: "UP", "DOWN", "IDLE"

    // private int maxCapacity; // Add in later phases. The maximum capacity that
    // the elevator can contain in kg.
    // private int currentLoad; // Add in later phases. The current weight inside
    // the elevator in kg.

    // private boolean doorsOpen; // Add in later phases. Returns true if doors are
    // open, false if doors are closed.
    // private boolean inService; // Add in later phases. Returns true if elevator
    // is in service, false if elevator is not in service.

    // REQUIRES: maxCapacity > 0 and floorsInBuilding >= 1
    // EFFECTS: creates an Elevator starting at floor 1 with zero load,
    // doors closed, inService false, IDLE direction, and empty
    // requestedFloors list
    public Elevator(int floorsInBuilding) {
        this.currentFloor = 1;
        // this.maxCapacity = maxCapacity;
        this.floorsInBuilding = floorsInBuilding;
        // this.currentLoad = 0;
        this.requestedFloors = new ArrayList<>();
        // this.doorsOpen = false;
        // this.inService = false;
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
            // Update direction toward next request if any
            // if (requestedFloors.isEmpty()) {
            //     direction = Direction.IDLE;
            // } else {
            //     int newNext = getNextRequestedFloor();
            //     direction = (currentFloor < newNext) ? Direction.UP : Direction.DOWN;
            // }
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
            } else if (floor < getNextRequestedFloor()) {
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

    // TO DO in later phases
    // // MODIFIES: doorsOpen
    // // EFFECTS: sets doorsOpen to true
    // public void openDoors() {
    // doorsOpen = true;
    // }

    // TO DO in later phases
    // // MODIFIES: doorsOpen
    // // EFFECTS: sets doorsOpen to false
    // public void closeDoors() {
    // doorsOpen = false;
    // }

    // TO DO in later phases
    // // REQUIRES: currentLoad + weightChange <= maxCapacity
    // // MODIFIES: currentLoad
    // // EFFECTS: increases or decreases the current load by weightChange
    // public void updateLoad(int weightChange) {
    // }

    // TO DO in later phases
    // // MODIFIES: inService
    // // EFFECTS: sets whether the elevator is in service
    // public void setInService(boolean inService) {
    // this.inService = inService;
    // }

    // TO DO in later phases
    // // EFFECTS: returns current load of the elevator
    // public int getCurrentLoad() {
    // return currentLoad;
    // }

    // TO DO in later phases
    // // EFFECTS: returns true if doors are open, false otherwise
    // public boolean isDoorsOpen() {
    // return doorsOpen;
    // }

    // TO DO in later phases
    // // EFFECTS: returns true if elevator is in service, false otherwise
    // public boolean isInService() {
    // return inService;
    // }

    // TO DO in later phases
    // // EFFECTS: returns maximum capacity of elevator
    // public int getMaxCapacity() {
    // return maxCapacity;
    // }

}
