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

public Elevator (int maxCapacity) {
    this.currentFloor = 1; 
    this.maxCapacity = maxCapacity;
    this.currentLoad = 0;
    this.requestedFloors = new ArrayList<>();
    this.doorsOpen = false;
    this.inService = false;
    this.direction = Direction.IDLE;
}

// REQUIRES:
// MODIFIES:
// EFFECTS: 
public void addFloorRequest(int floor) {

}

// REQUIRES:
// MODIFIES:
// EFFECTS: 
public void moveToNextFloor() {
}

// REQUIRES:
// MODIFIES:
// EFFECTS: 
public void dropOff() {

}

// REQUIRES:
// MODIFIES:
// EFFECTS: 
public void updateLoad(int weightChange) {

}

// REQUIRES:
// MODIFIES:
// EFFECTS: 
public void changeDirection(Direction givenDirection) {

}

// REQUIRES:
// MODIFIES:
// EFFECTS: 
public void openDoors() {
    doorsOpen = true;
}

// REQUIRES:
// MODIFIES:
// EFFECTS: 
public void closeDoors() {
    doorsOpen = false;
}

// REQUIRES:
// MODIFIES:
// EFFECTS: 
public void hasRequests() {

}

// REQUIRES:
// MODIFIES:
// EFFECTS: 
public void removeCurrentRequest() {

}

// REQUIRES:
// MODIFIES:
// EFFECTS: 
public boolean hasCapacityForUser() {
    return false;
}

// Getters and Setters

// REQUIRES:
// MODIFIES:
// EFFECTS: 
public void setInService(boolean inService) {
    this.inService = inService;
}


// REQUIRES:
// MODIFIES:
// EFFECTS: 
public void getNextRequestedFloor() {

}

// REQUIRES:
// MODIFIES:
// EFFECTS: 
public int getCurrentFloor() {
    return currentFloor;
}

// REQUIRES:
// MODIFIES:
// EFFECTS: 
public int getCurrentLoad() {
    return currentLoad;
}

// REQUIRES:
// MODIFIES:
// EFFECTS: 
public boolean isDoorsOpen() {
    return doorsOpen;
}

// REQUIRES:
// MODIFIES:
// EFFECTS: 
public boolean isInService() {
    return inService;
}

// REQUIRES:
// MODIFIES:
// EFFECTS: 
public List<Integer> getRequestedFloors() {
    return requestedFloors;
}

// REQUIRES:
// MODIFIES:
// EFFECTS: 
public Direction getDirection() {
    return direction;
}

// REQUIRES:
// MODIFIES:
// EFFECTS: 
public int getMaxCapacity() {
    return maxCapacity;
}

}
