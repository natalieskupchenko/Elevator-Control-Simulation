package model;

import java.util.ArrayList;
import java.util.List;

public class Elevator {
    private int currentFloor; // The floor that the elevator is currently on.
    private List<Integer> requestedFloors; // The list of requested floors. 
    private Direction direction; // The direction the elevator is travelling: "UP", "DOWN", "IDLE"

    private int maxCapacity; // The maximum capacity that the elevator can contain in kg.
    private int currentLoad; // The current weight inside the elevator in kg.

    private boolean doorsOpen; // Returns true if doors are open, false if doors are closed.
    private boolean inService; // Returns true if elevator is in service, false if elevator is not in service. 


// REQUIRES: maxCapacity > 0
// EFFECTS: creates an Elevator starting at floor 1 with zero load, 
//          doors closed, inService false, IDLE direction, and empty 
//          requestedFloors list    
public Elevator (int maxCapacity) {
    this.currentFloor = 1; 
    this.maxCapacity = maxCapacity;
    this.currentLoad = 0;
    this.requestedFloors = new ArrayList<>();
    this.doorsOpen = false;
    this.inService = false;
    this.direction = Direction.IDLE;
}

// REQUIRES: 1<= floor <= number of floors in building
// MODIFIES: requestedFloors
// EFFECTS: adds the given floor to the list of requested floors if not already present
public void addFloorRequest(int floor) {

}

// REQUIRES: requestedFloors is not empty
// MODIFIES: currentFloor, direction, requestedFloors
// EFFECTS: moves the elevator one floor towards next requested floor,
//          updates direction accordingly
public void moveToNextFloor() {
}

// REQUIRES: requestedFloors contains currentFloor
// MODIFIES: requestedFloors, currentLoad
// EFFECTS: drops off passengers at current floor and removes current floor,
//          removes current floor from requestedFloors, and reduces currentLoad as needed
public void dropOff() {

}

// REQUIRES: currentLoad + weightChange <= maxCapacity
// MODIFIES: currentLoad
// EFFECTS: increases or decreases the current load by weightChange
public void updateLoad(int weightChange) {

}

// REQUIRES: givenDirection != null
// MODIFIES: direction
// EFFECTS: sets the elevator's direction to the given direction
public void changeDirection(Direction givenDirection) {

}

// MODIFIES: doorsOpen
// EFFECTS: sets doorsOpen to true
public void openDoors() {
    doorsOpen = true;
}


// MODIFIES: doorsOpen
// EFFECTS: sets doorsOpen to false
public void closeDoors() {
    doorsOpen = false;
}


// EFFECTS: returns true if there are any requested floors, false otherwise 
public boolean hasRequests() {
    return false;

}

// REQUIRES: requestedFloors is not empty
// MODIFIES: requestedFloors
// EFFECTS: removes the current floor from the requestedFloors list
public void removeCurrentRequest() {
}


// Getters and Setters

// MODIFIES: inService
// EFFECTS: sets whether the elevator is in service
public void setInService(boolean inService) {
    this.inService = inService;
}


// REQUIRES: requestedFloors is not empty
// EFFECTS: returns next requested floor without removing it
public void getNextRequestedFloor() {

}

// EFFECTS: returns current floor of the elevator
public int getCurrentFloor() {
    return currentFloor;
}

// EFFECTS: returns current load of the elevator
public int getCurrentLoad() {
    return currentLoad;
}

// EFFECTS: returns true if doors are open, false otherwise
public boolean isDoorsOpen() {
    return doorsOpen;
}

// EFFECTS: returns true if elevator is in service, false otherwise
public boolean isInService() {
    return inService;
}

// EFFECTS: returns list of requested floors
public List<Integer> getRequestedFloors() {
    return requestedFloors;
}

// EFFECTS: returns current direction of elevator
public Direction getDirection() {
    return direction;
}

// EFFECTS: returns maximum capacity of elevator
public int getMaxCapacity() {
    return maxCapacity;
}

}
