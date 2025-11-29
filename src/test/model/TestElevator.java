package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

//Test for Elevator class
class TestElevator {
    private Elevator elevator;

    @BeforeEach
    void setUp() {
        elevator = new Elevator(5); // 5-floor building for testing
    }

    // testing constructor
    @Test
    void testConstructor() {
        assertEquals(1, elevator.getCurrentFloor());
        assertEquals(5, elevator.getFloorsInBuilding());
        assertEquals(Direction.IDLE, elevator.getDirection());
        assertTrue(elevator.getRequestedFloors().isEmpty());
    }

    @Test
    void testAlternateConstructor() {
        List<Integer> requests = List.of(2, 5);
        Elevator e = new Elevator(10, 4, Direction.DOWN, requests);

        assertEquals(10, e.getFloorsInBuilding());
        assertEquals(4, e.getCurrentFloor());
        assertEquals(Direction.DOWN, e.getDirection());
        assertEquals(List.of(2, 5), e.getRequestedFloors());
    }

    // testing addFloorRequest
    @Test
    void testAddFloorRequestSortedWithoutDuplicates() {
        elevator.addFloorRequest(4);
        elevator.addFloorRequest(2); // should sort to [2,4]
        elevator.addFloorRequest(4); // no duplicates
        List<Integer> requestList = elevator.getRequestedFloors();
        assertEquals(2, requestList.size());
        assertEquals(List.of(2, 4), requestList);
    }

    @Test
    void testAddFloorRequestWhileAlreadyDownTriggersDescendingSort() {
        // start: go up to 4
        elevator.addFloorRequest(4);
        elevator.move();
        elevator.move();
        elevator.move();
        elevator.move(); // now at 4 and IDLE

        // now request a LOWER floor
        elevator.addFloorRequest(2); // still IDLE, so asc sorting is used but list = [2]
        elevator.move(); // 4 -> 3, now direction becomes DOWN

        // NOW elevator is DOWN — THIS IS THE KEY
        elevator.addFloorRequest(1); // goes into the DOWN branch

        // assert descending order
        assertEquals(List.of(2, 1), elevator.getRequestedFloors());
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

    // testing move
    @Test
    void testMoveUpWithSortedRequests() {
        // Add floors out of order – should sort to [2, 4]
        elevator.addFloorRequest(4);
        elevator.addFloorRequest(2);

        // Move 1: 1 -> 2 (no dropOff yet)
        elevator.move();
        assertEquals(2, elevator.getCurrentFloor());
        assertEquals(Direction.UP, elevator.getDirection());
        assertTrue(elevator.getRequestedFloors().contains(2)); // still there

        // Move 2: at 2 -> dropOff(2) (current stays 2)
        elevator.move();
        assertEquals(2, elevator.getCurrentFloor());
        assertEquals(List.of(4), elevator.getRequestedFloors());

        // Move 3: 2 -> 3
        elevator.move();
        assertEquals(3, elevator.getCurrentFloor());
        assertEquals(Direction.UP, elevator.getDirection());

        // Move 4: 3 -> 4
        elevator.move();
        assertEquals(4, elevator.getCurrentFloor());

        // Move 5: at 4 -> dropOff(4) (current stays 4), now idle
        elevator.move();
        assertTrue(elevator.getRequestedFloors().isEmpty());
        assertEquals(Direction.IDLE, elevator.getDirection());
    }

    @Test
    void testMoveDownAfterGoingUp() {
        elevator.addFloorRequest(4);
        elevator.addFloorRequest(2);
        // Move to top 4 first
        while (!elevator.getRequestedFloors().isEmpty()) {
            elevator.move();
        }
        // Now request down floor
        elevator.addFloorRequest(1);
        assertEquals(Direction.IDLE, elevator.getDirection()); // ready to go DOWN

        elevator.move();
        assertEquals(3, elevator.getCurrentFloor());
        assertEquals(Direction.DOWN, elevator.getDirection());
    }

   
    @Test
    void testMoveSetsIdleWhenNoRequests() {
        assertTrue(elevator.getRequestedFloors().isEmpty());
        elevator.move(); // nothing to do
        assertEquals(Direction.IDLE, elevator.getDirection());
        assertEquals(1, elevator.getCurrentFloor()); // stays put
    }

    @Test
    void testMoveSetsDirectionDownWhenAboveNext() {
        // Get to floor 3 and clear requests
        elevator.addFloorRequest(3);
        while (!elevator.getRequestedFloors().isEmpty()) {
            elevator.move();
        }
        assertEquals(3, elevator.getCurrentFloor());
        assertEquals(Direction.IDLE, elevator.getDirection());

        // Now request a lower floor so next < current
        elevator.addFloorRequest(1);
        elevator.move(); // 3 -> 2, should set DOWN
        assertEquals(2, elevator.getCurrentFloor());
        assertEquals(Direction.DOWN, elevator.getDirection());
    }

    // testing dropOff
    @Test
    void testDropOffRemovesCompletedFloors() {
        elevator.addFloorRequest(3);
        elevator.addFloorRequest(5);

        // Move to 3 (1 -> 2 -> 3)
        elevator.move(); // 1 -> 2
        elevator.move(); // 2 -> 3
        assertTrue(elevator.getRequestedFloors().contains(3)); // not dropped yet

        // Drop off at 3 (stay on 3)
        elevator.move();
        assertFalse(elevator.getRequestedFloors().contains(3)); // now dropped

        // Move to 5 (3 -> 4 -> 5)
        elevator.move();
        elevator.move();
        assertTrue(elevator.getRequestedFloors().contains(5)); // still there

        // Drop off at 5
        elevator.move();
        assertTrue(elevator.getRequestedFloors().isEmpty());
        assertEquals(Direction.IDLE, elevator.getDirection());
    }

    @Test
    void testDropOffSetsDirectionUpWhenNextRequestIsHigher() {
        // Requests sorted ascending: [2, 5]
        elevator.addFloorRequest(2);
        elevator.addFloorRequest(5);

        // Move to 2
        elevator.move(); // 1 -> 2
        elevator.move(); // dropOff(2)

        // Next floor is 5 > 2 so direction should become UP
        assertEquals(Direction.UP, elevator.getDirection());
        assertEquals(List.of(5), elevator.getRequestedFloors());
    }

    @Test
    void testDropOffDirectCallSetsDirectionDown() {
        // Setup elevator already moving UP with multiple requests
        elevator.addFloorRequest(2);
        elevator.addFloorRequest(4); // sorted -> [2, 4]

        // Move to 2 and drop it off
        elevator.move(); // 1 -> 2
        elevator.move(); // dropOff(2), now [4]

        // Now direction = UP. Add lower request so dropOff hits DOWN branch.
        elevator.addFloorRequest(1); // list -> [1,4] but direction still UP
        elevator.dropOff(4); // manually drop off at 4

        // MUST SET direction DOWN because next request (1) is lower
        assertEquals(Direction.DOWN, elevator.getDirection());
    }

    // testing getters and setters

    @Test
    void testGetNextRequestedFloorReturnsSortedFirst() {
        elevator.addFloorRequest(4);
        elevator.addFloorRequest(2); // now sorted to [2,4]
        assertEquals(2, elevator.getNextRequestedFloor());
    }

    @Test
    void testGetCurrentFloorChangesWithMovement() {
        elevator.addFloorRequest(3);
        elevator.move();
        assertEquals(2, elevator.getCurrentFloor());
    }

    @Test
    void testGetDirectionUpdatesCorrectly() {
        elevator.addFloorRequest(3);
        elevator.move();
        assertEquals(Direction.UP, elevator.getDirection());
        while (!elevator.getRequestedFloors().isEmpty()) {
            elevator.move();
        }
        assertEquals(Direction.IDLE, elevator.getDirection());
    }

    @Test
    void testGetFloorsInBuilding() {
        assertEquals(5, elevator.getFloorsInBuilding());
    }

}