package persistence;

// Code referenced and adapted from CPSC 210 JsonSerializationDemo
// URL: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

import model.Direction;
import model.Elevator;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

// Represents a reader that reads elevator state from JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads elevator from file and returns it;
    // throws IOException if error occurs reading data from file
    public Elevator read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseElevator(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(contentBuilder::append);
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses elevator from JSON object and returns it
    private Elevator parseElevator(JSONObject jsonObject) {
        int floorsInBuilding = jsonObject.getInt("floorsInBuilding");
        int currentFloor = jsonObject.getInt("currentFloor");
        Direction direction = Direction.valueOf(jsonObject.getString("direction"));
        List<Integer> requestedFloors = jsonArrayToList(jsonObject.getJSONArray("requestedFloors"));

        return new Elevator(floorsInBuilding, currentFloor, direction, requestedFloors);
    }

    // EFFECTS: converts JSON array to list of integers
    private List<Integer> jsonArrayToList(JSONArray array) {
        List<Integer> list = new ArrayList<>();
        for (Object obj : array) {
            list.add((Integer) obj);
        }
        return list;
    }
}