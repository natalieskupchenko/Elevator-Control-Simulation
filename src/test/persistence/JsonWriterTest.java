package persistence;

import model.Direction;
import model.Elevator;
import model.ExcludeFromJacocoGeneratedReport;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

// Code referenced and adapted from CPSC 210 JsonSerializationDemo
// URL: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

@ExcludeFromJacocoGeneratedReport
public class JsonWriterTest extends JsonTest {

    @Test
    void testWriterInvalidFile() {
        try {
            Elevator e = new Elevator(5);
            e.addFloorRequest(3); //
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException ex) {
            // expected
        }
    }

    @Test
    void testWriterGeneralElevator() {
        try {
            Elevator e = new Elevator(10);
            e.addFloorRequest(2);
            e.addFloorRequest(7);

            JsonWriter writer = new JsonWriter("./data/testWriterElevator.json");
            writer.open();
            writer.write(e);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterElevator.json");
            Elevator loaded = reader.read();

            assertEquals(10, loaded.getFloorsInBuilding());
            assertEquals(1, loaded.getCurrentFloor()); // elevator always starts at 1
            assertEquals(Direction.IDLE, loaded.getDirection());
            assertEquals(2, loaded.getRequestedFloors().size());
            assertTrue(loaded.getRequestedFloors().contains(2));
            assertTrue(loaded.getRequestedFloors().contains(7));
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterEmptyElevator() {
        try {
            Elevator e = new Elevator(5); // no requests added
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyElevator.json");
            writer.open();
            writer.write(e);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyElevator.json");
            Elevator loaded = reader.read();

            assertEquals(5, loaded.getFloorsInBuilding());
            assertEquals(1, loaded.getCurrentFloor()); // default is 1
            assertTrue(loaded.getRequestedFloors().isEmpty());
            assertEquals(Direction.IDLE, loaded.getDirection());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
