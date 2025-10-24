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
public class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        assertThrows(IOException.class, reader::read);
    }

    @Test
    void testReaderGeneralElevator() {
        try {
            Elevator e = new Elevator(6);
            e.addFloorRequest(3);
            e.addFloorRequest(5);

            JsonWriter writer = new JsonWriter("./data/testReaderElevator.json");
            writer.open();
            writer.write(e);
            writer.close();

            JsonReader reader = new JsonReader("./data/testReaderElevator.json");
            Elevator loaded = reader.read();

            assertEquals(6, loaded.getFloorsInBuilding());
            assertEquals(Direction.IDLE, loaded.getDirection());
            assertTrue(loaded.getRequestedFloors().contains(3));
            assertTrue(loaded.getRequestedFloors().contains(5));
            assertEquals(2, loaded.getRequestedFloors().size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
void testReaderEmptyElevator() {
    try {
        // create empty elevator file manually
        Elevator e = new Elevator(4);  // no requests
        JsonWriter writer = new JsonWriter("./data/testReaderEmptyElevator.json");
        writer.open();
        writer.write(e);
        writer.close();

        JsonReader reader = new JsonReader("./data/testReaderEmptyElevator.json");
        Elevator loaded = reader.read();

        assertEquals(4, loaded.getFloorsInBuilding());
        assertEquals(1, loaded.getCurrentFloor());
        assertTrue(loaded.getRequestedFloors().isEmpty());
    } catch (IOException e) {
        fail("Couldn't read from file");
    }
}

}
