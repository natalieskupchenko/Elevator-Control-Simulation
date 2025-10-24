package persistence;

import model.Elevator;
import static org.junit.jupiter.api.Assertions.assertEquals;

// Code referenced and adapted from CPSC 210 JsonSerializationDemo
// URL: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

public class JsonTest {
    protected void checkElevator(int floors, int currentFloor, int numRequests, Elevator e) {
        assertEquals(floors, e.getFloorsInBuilding());
        assertEquals(currentFloor, e.getCurrentFloor());
        assertEquals(numRequests, e.getRequestedFloors().size());
    }
}
