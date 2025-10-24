package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

//Test for Elevator class
@ExcludeFromJacocoGeneratedReport
class TestElevator {
    private Elevator elevator;

    @BeforeEach
    void setUp() {
        elevator = new Elevator(5); // 5-floor building for testing
    }

    @Test
    void testConstructor() {
        assertEquals(1, elevator.getCurrentFloor());
        assertEquals(5, elevator.getFloorsInBuilding());
        assertEquals(Direction.IDLE, elevator.getDirection());
        assertTrue(elevator.getRequestedFloors().isEmpty());
    }

    @Test
    void testAddFloorRequest() {
        elevator.addFloorRequest(3);

        List<Integer> requested = elevator.getRequestedFloors();
        assertEquals(1, requested.size());
        assertTrue(requested.contains(3));

        // Adding same floor again should not duplicate
        elevator.addFloorRequest(3);
        assertEquals(1, requested.size());

        // Adding another floor
        elevator.addFloorRequest(5);
        assertEquals(2, requested.size());
        assertTrue(requested.contains(3));
        assertTrue(requested.contains(5));
    }

    @Test
    void testMove() {
        // Case 1: No requested floors → should stay IDLE
        elevator.move();
        assertEquals(Direction.IDLE, elevator.getDirection());
        assertEquals(1, elevator.getCurrentFloor());

        // Case 2: Move up
        assertEquals(1, elevator.getCurrentFloor());
        elevator.addFloorRequest(3); // first request
        assertEquals(1, elevator.getRequestedFloors().size());
        elevator.move(); // move from 1 → 2
        assertEquals(2, elevator.getCurrentFloor());
        assertEquals(Direction.UP, elevator.getDirection());

        assertEquals(1, elevator.getRequestedFloors().size());
        elevator.move(); // move from 2 → 3 (arrive, triggers dropOff)
        elevator.dropOff(3);
        assertEquals(3, elevator.getCurrentFloor());
        assertEquals(0, elevator.getRequestedFloors().size());
        assertEquals(Direction.IDLE, elevator.getDirection());

        // Case 3: Move down
        assertEquals(3, elevator.getCurrentFloor());
        elevator.addFloorRequest(1); // request lower than current
        assertEquals(1, elevator.getRequestedFloors().size());
        elevator.move(); // move from 3 → 2
        assertEquals(2, elevator.getCurrentFloor());
        assertEquals(Direction.DOWN, elevator.getDirection());

        elevator.move(); // move from 2 → 1 (arrive, triggers dropOff)
        elevator.dropOff(1);
        assertEquals(0, elevator.getRequestedFloors().size());
        assertEquals(1, elevator.getCurrentFloor());
        assertEquals(Direction.IDLE, elevator.getDirection());
        assertTrue(elevator.getRequestedFloors().isEmpty());

        // Case 4: Multiple requests out of order
        assertEquals(1, elevator.getCurrentFloor());
        elevator.addFloorRequest(5); // first request
        elevator.addFloorRequest(2); // second request, out-of-order
        elevator.move(); // move 1 → 2? No, moves toward first request (5) → 2 → 3
        assertEquals(2, elevator.getCurrentFloor());
        assertEquals(Direction.UP, elevator.getDirection());

        elevator.move(); // 3
        assertEquals(3, elevator.getCurrentFloor());
        assertEquals(Direction.UP, elevator.getDirection());
        elevator.move(); // 4
        assertEquals(4, elevator.getCurrentFloor());
        assertEquals(Direction.UP, elevator.getDirection());
        elevator.move(); // 5 arrives, triggers dropOff
        elevator.dropOff(5);
        assertEquals(5, elevator.getCurrentFloor());
        assertEquals(Direction.DOWN, elevator.getDirection()); // next request is 2
        assertEquals(1, elevator.getRequestedFloors().size());
        assertTrue(elevator.getRequestedFloors().contains(2));

        // Continue toward next floor
        elevator.move(); // 5 → 4
        assertEquals(4, elevator.getCurrentFloor());
        assertEquals(Direction.DOWN, elevator.getDirection());

    }

    @Test
    void testDropOff() {
        assertEquals(1, elevator.getCurrentFloor());

        // Setup multiple requests
        elevator.addFloorRequest(3);
        elevator.addFloorRequest(5);

        // Move to first requested floor
        while (elevator.getCurrentFloor() != 3) {
            elevator.move();
        }

        // Drop off at floor 3
        assertEquals(3, elevator.getCurrentFloor());
        elevator.dropOff(elevator.getCurrentFloor());
        assertEquals(3, elevator.getCurrentFloor()); // should still be at floor 3
        assertFalse(elevator.getRequestedFloors().contains(3)); // floor 3 removed
        assertTrue(elevator.getRequestedFloors().contains(5)); // floor 5 still in requests

        // Move to floor 5 and drop off
        while (elevator.getCurrentFloor() != 5) {
            elevator.move();
        }

        assertEquals(5, elevator.getCurrentFloor());
        elevator.dropOff(elevator.getCurrentFloor());
        assertEquals(5, elevator.getCurrentFloor());
        assertTrue(elevator.getRequestedFloors().isEmpty()); // all requests done
    }

    @Test
    void testGetNextRequestedFloor() {
        assertEquals(1, elevator.getCurrentFloor());
        elevator.addFloorRequest(4);
        elevator.addFloorRequest(2);

        // Next requested floor should always return the first in the list

        elevator.getNextRequestedFloor();
        assertEquals(4, elevator.getNextRequestedFloor());
    }

    @Test
    void testGetCurrentFloor() {
        assertEquals(1, elevator.getCurrentFloor()); // initially floor 1

        // Move elevator up
        elevator.addFloorRequest(3);
        elevator.move();
        assertEquals(2, elevator.getCurrentFloor());
        elevator.move();
        assertEquals(3, elevator.getCurrentFloor());
    }

    @Test
    void testGetRequestedFloors() {
        elevator.addFloorRequest(2);
        elevator.addFloorRequest(5);

        List<Integer> requests = elevator.getRequestedFloors();
        assertEquals(2, requests.size());
        assertTrue(requests.contains(2));
        assertTrue(requests.contains(5));

        elevator.dropOff(2);
        assertFalse(requests.contains(2));
        elevator.dropOff(5);
        assertFalse(requests.contains(5));

    }

    @Test
    void testGetDirection() {
        assertEquals(Direction.IDLE, elevator.getDirection()); // initially idle

        elevator.addFloorRequest(3);
        elevator.move();
        assertEquals(Direction.UP, elevator.getDirection());

        // After reaching the floor and dropping off, should go back to IDLE
        while (!elevator.getRequestedFloors().isEmpty()) {
            elevator.move();
        }
        assertEquals(Direction.IDLE, elevator.getDirection());
    }

    @Test
    void testGetFloorsInBuilding() {
        assertEquals(5, elevator.getFloorsInBuilding()); // building of 5 floors
        assertEquals(6, new Elevator(6).getFloorsInBuilding());
    }

    @Test
    void testAddInvalidFloorTooHigh() {
        Elevator elevator = new Elevator(5); // Building has 5 floors
        elevator.addFloorRequest(10); // Invalid floor
        assertTrue(elevator.getRequestedFloors().isEmpty()); // Should not add
    }

    @Test
    void testAddInvalidFloorZeroOrNegative() {
        Elevator elevator = new Elevator(5);
        elevator.addFloorRequest(0); // Invalid
        elevator.addFloorRequest(-3); // Invalid
        assertTrue(elevator.getRequestedFloors().isEmpty());
    }

    @Test
    void testAddCurrentFloorIgnored() {
        Elevator elevator = new Elevator(5);
        elevator.addFloorRequest(1); // Already on floor 1
        assertTrue(elevator.getRequestedFloors().isEmpty());
    }
}
