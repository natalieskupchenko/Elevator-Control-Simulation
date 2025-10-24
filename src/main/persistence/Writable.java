package persistence;

import org.json.JSONObject;

// Code referenced and adapted from JsonSerializationDemo provided by CPSC 210
// URL: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
// Used under course instruction guidelines.

// Represents data that can be written to JSON
public interface Writable {
    // EFFECTS: returns this as JSON object
    JSONObject toJson();
}