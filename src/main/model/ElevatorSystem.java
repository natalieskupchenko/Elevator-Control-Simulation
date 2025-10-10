package model;
// TO DO: ElevatorSystem in Phase 2

// import java.util.ArrayList;
// import java.util.List;

public class ElevatorSystem {
//     private List<Elevator> elevators; // List of elevators in ElevatorSystem
//     private int numFloors; // Number of floors in ElevatorSystem
//     private List<Integer> floorCallsUp; // List of floors where there was a call to go up
//     private List<Integer> floorCallsDown; // List of floors where there was a call to go down


// public ElevatorSystem(int numFloors, int numElevators, int maxCapacity) {
//     this.numFloors = numFloors;
//     this.elevators = new ArrayList<>();
//     this.floorCallsUp = new ArrayList<>();
//     this.floorCallsDown = new ArrayList<>();

//     for (int i = 0; i < numElevators; i++) {
//         elevators.add(new Elevator(maxCapacity));
//     }
// }

// // REQUIRES: floor >= 1 && floor <= numFloors
// // MODIFIES: floorCallsUp, floorCallsDown
// // EFFECTS: adds a call for an elevator at the specified floor and direction
// public void callElevator(int floor, Direction direction) {

// }

// // REQUIRES: floor >= 1 && floor <= numFloors
// // MODIFIES: floorCallsUp, floorCallsDown
// // EFFECTS: removes a call for an elevator at the specified floor and direction
// public void removeFloorCall(int floor, Direction direction) {

// }

// // EFFECTS: returns the list of elevators
// public void getElevators() {

// }

// // EFFECTS: returns the number of floors in the building
// public void getNumFloors() {

// }

// // EFFECTS: returns the list of floors where there was a call to go up
// public void getFloorCallsUp() {

// }

// // EFFECTS: returns the list of floors where there was a call to go down
// public void getFloorCallsDown() {

// }

// // REQUIRES: index >= 0 && index < elevators.size() 
// // MODIFIES: elevators[index]
// // EFFECTS: sets the inService status of the elevator at the given index
// public void setElevatorInService(int index, boolean inService){

// }

// // REQUIRES: floor is between 1 and numFloors, direction is not null
// // EFFECTS: returns an Elevator that can respond to the request (preferably idle); 
// //          if none are idle, returns the first elevator
// public Elevator assignElevator(int floor, Direction direction) {
//     for (Elevator e : elevators) {
//         if (e.isInService() && e.getDirection() == Direction.IDLE) {
//             return e;
//         }
//     }
//     return elevators.get(0); // fallback 
// }

// // EFFECTS: returns a string describing the status of all elevators, 
// ///         including current floor, direction, doors, and load
// public String getStatus() {
//     StringBuilder status = new StringBuilder();
//     for (int i = 0; i < elevators.size(); i++) {
//         Elevator e = elevators.get(i);
//         status.append("Elevator ").append(i + 1)
//               .append(": Floor ").append(e.getCurrentFloor())
//               .append(", Direction ").append(e.getDirection())
//               .append(", Doors ").append(e.isDoorsOpen() ? "Open" : "Closed")
//               .append(", Load ").append(e.getCurrentLoad())
//               .append("/").append(e.getMaxCapacity())
//               .append("\n");
//     }
//     return status.toString();
// }

// // MODIFIES: each elevator elevators
// // EFFECTS: moves each elevator one step towards its next requested floor
// public void step() {
//     for (Elevator e : elevators) {
//         e.moveToNextFloor();
//     }
// }

// // REQUIRES: maxCapacity > 0
// // MODIFIES: this.elevators
// // EFFECTS: adds a new Elevator with the specified capacity to the system
// public void addElevator(int maxCapacity) {
//     elevators.add(new Elevator(maxCapacity));
// }

// // REQUIRES: index is a valid index in elevators
// // MODIFIES: this.elevators
// // EFFECTS: removes the elevator at the specified index from the system
// public void removeElevator(int index) {
//     if (index >= 0 && index < elevators.size()) {
//         elevators.remove(index);
//     }
// }

// // MODIFIES: this.numFloors
// // EFFECTS: increments the number of floors in the system by 1
// public void addFloor() {
//     numFloors++;
// }
}