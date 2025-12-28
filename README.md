# Elevator Control Simulation

A Java-based elevator scheduling system that implements direction-aware request prioritization with floor call functionality, event logging, and JSON persistence.

**Author:** Natalie Skupchenko  
**Course:** CPSC 210 (Software Construction)  
**Term:** September 2024 - December 2024

---

## Overview

This project simulates an elevator control system that efficiently handles floor requests using a direction-aware priority algorithm. The system sorts requests dynamically based on the elevator's current direction (UP, DOWN, or IDLE) to minimize travel time and improve efficiency.

### Key Features

- **Direction-Aware Scheduling**: Requests are automatically sorted based on elevator direction
  - Ascending order when moving UP or IDLE
  - Descending order when moving DOWN
- **Floor Call Functionality**: Users can call the elevator from any floor using up/down buttons
- **Interactive GUI**: Visual representation of elevator movement through the shaft
- **Event Logging**: Comprehensive logging of all system events (requests, movement, arrivals)
- **JSON Persistence**: Save and load elevator state to resume simulations
- **Multiple Interfaces**: Both console-based and GUI implementations

---

## Motivation

As a mathematics student interested in optimization problems, I became curious about elevator scheduling algorithms. This project explores questions such as:

- What criteria determine how an elevator responds to floor calls?
- How does the number of floors affect optimal scheduling?
- What is the relationship between system size and response efficiency?

The simulation provides a foundation for exploring these optimization questions through computational modeling.

---

## Technical Implementation

### Architecture

The project follows object-oriented design principles with clear separation of concerns:

- **Model Layer**: `Elevator`, `Direction`, `Event`, `EventLog`
- **Persistence Layer**: `JsonReader`, `JsonWriter`, `Writable`
- **UI Layer**: `ElevatorApp` (console), `ElevatorAppGui`, `ElevatorPanel`, `ElevatorShaftPanel` (GUI)

### Core Algorithm

The elevator uses a simple but effective priority algorithm:

1. When a floor request is added, it's validated (must be within building bounds, not duplicate, not current floor)
2. The request is added to a sorted list based on current direction:
   - **IDLE or UP**: Requests sorted in ascending order (bottom to top)
   - **DOWN**: Requests sorted in descending order (top to bottom)
3. The elevator moves one floor at a time toward the next requested floor
4. Upon arrival, passengers are dropped off and the direction is updated based on remaining requests

### JSON Persistence

The system supports saving and loading elevator state to/from JSON files stored in `./data/elevator.json`.

**Example saved state:**
```json
{
    "currentFloor": 3,
    "floorsInBuilding": 10,
    "direction": "UP",
    "requestedFloors": [5, 8]
}
```

Users can save their simulation state and resume later from the exact same configuration.

### Event Logging

All system events are logged with timestamps and printed to the console when the program exits. This provides a complete audit trail of elevator behavior during the simulation.

**Example log output:**
```
Fri Nov 28 16:49:51 PST 2025
Created new elevator with 10 floors.

Fri Nov 28 16:49:52 PST 2025
Floor 2 requested.

Fri Nov 28 16:49:56 PST 2025
Elevator moved from floor 1 to floor 2.

Fri Nov 28 16:49:57 PST 2025
Elevator arrived at floor 2 and dropped off passengers.
```

---

## How to Run

### GUI Version (Recommended)

```bash
# Compile and run the GUI application
javac ui/Main.java
java ui.Main
```

The GUI provides:
- Visual elevator shaft with animated car movement
- Floor request buttons for each floor
- Real-time status display (current floor, direction, pending requests)
- Save/Load functionality
- Control buttons to start/stop simulation

### Console Version

```bash
# Compile and run the console application
javac ui/ElevatorApp.java
java ui.ElevatorApp
```

The console interface offers a text-based menu system with the same core functionality.

---

## Usage Example

1. **Start the application** - Choose to create a new elevator or load a saved one
2. **Set building size** - Specify the number of floors (1-200)
3. **Request floors** - Click floor buttons or use up/down call buttons
4. **Start simulation** - Click "Go" to watch the elevator move
5. **Save state** - Save your current simulation to resume later

---

## Project Structure

```
.
├── model/
│   ├── Elevator.java              # Core elevator logic
│   ├── Direction.java             # Enum for UP/DOWN/IDLE
│   ├── Event.java                 # Event representation
│   └── EventLog.java              # Singleton event logger
├── persistence/
│   ├── JsonReader.java            # Load elevator from JSON
│   ├── JsonWriter.java            # Save elevator to JSON
│   └── Writable.java              # Interface for JSON serialization
├── ui/
│   ├── Main.java                  # Entry point (GUI)
│   ├── ElevatorApp.java           # Console interface
│   ├── ElevatorAppGui.java        # Main GUI window
│   ├── ElevatorPanel.java         # Elevator controls and display
│   ├── ElevatorShaftPanel.java    # Visual shaft rendering
│   ├── EnterFloorsPanel.java      # Floor input screen
│   └── IntroPanel.java            # Welcome screen
└── data/
    └── elevator.json              # Saved elevator state
```

---

## Testing

The project includes comprehensive JUnit tests covering:

- Elevator construction and initialization
- Floor request validation and sorting
- Movement logic (up, down, arrival)
- Drop-off behavior and direction changes
- JSON serialization and deserialization

Run tests with:
```bash
# Using JUnit 5
java -jar junit-platform-console-standalone.jar --class-path . --scan-class-path
```

---

## Future Enhancements

Potential extensions to explore:

- **Multiple elevators**: Implement a scheduling system that assigns calls to the nearest available elevator
- **Optimization analysis**: Compare different scheduling algorithms (FCFS, SCAN, LOOK)
- **Performance metrics**: Track average wait time, total travel distance, idle time
- **Advanced features**: Priority floors, express elevators, capacity limits

---

## Acknowledgements

This project references and adapts code from:

- **JsonSerializationDemo** (CPSC 210) - For JSON persistence patterns (`JsonReader`, `JsonWriter`, `Writable`)
  - URL: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
- **AlarmSystem** (CPSC 210) - For event logging implementation (`Event`, `EventLog`)
  - URL: https://github.students.cs.ubc.ca/CPSC210/AlarmSystem

---

## License

Academic project for CPSC 210 at the University of British Columbia.