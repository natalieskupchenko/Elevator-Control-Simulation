# Elevator Control Simulation

A Java-based elevator scheduling system implementing direction-aware request prioritization with floor call functionality, event logging, and JSON persistence.

**Author:** Natalie Skupchenko  
**Course:** CPSC 210 (Software Construction)  
**Term:** September 2024 - December 2024

---

## Overview

This project simulates an elevator control system that efficiently handles floor requests using a direction-aware priority algorithm. The system sorts requests dynamically based on the elevator's current direction (UP, DOWN, or IDLE) to minimize travel time and improve efficiency.

**Why This Project?**
This simulation was built to explore optimization and scheduling problems through software. As a mathematics student interested in systems and algorithms, I used this project to model real-world trade-offs in request prioritization, efficiency, and system state management.


### Key Features

- **Direction-Aware Scheduling**: Requests are automatically prioritized based on elevator direction
  - Ascending order when moving UP or IDLE
  - Descending order when moving DOWN
- **Floor Call Functionality**: Users can call the elevator from any floor using up/down buttons
- **Cabin Request Buttons**: Request specific floors from inside the elevator
- **Interactive GUI**: Visual representation of elevator movement through the building shaft
- **Event Logging**: Comprehensive logging of all system events with timestamps
- **JSON Persistence**: Save and load elevator state to resume simulations

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
- **UI Layer**: Console (`ElevatorApp`) and GUI (`ElevatorAppGui`, `ElevatorPanel`, `ElevatorShaftPanel`)

### Core Algorithm

The elevator uses a direction-aware scheduling strategy:

1. **Request Validation**: When a floor request is added, it's validated (must be within building bounds, not duplicate, not current floor)
2. **Dynamic Sorting**: The request is added to a list that's sorted based on current direction:
   - **IDLE or UP**: Requests sorted in ascending order (bottom to top)
   - **DOWN**: Requests sorted in descending order (top to bottom)
3. **Movement**: The elevator moves one floor at a time toward the next requested floor
4. **Arrival**: Upon arrival, passengers are dropped off and the direction is updated based on remaining requests

This approach minimizes backtracking and provides predictable behavior for users.

---

## Project Structure

```
.
├── model/
│   ├── Elevator.java              # Core elevator logic
│   ├── Direction.java             # Enum: UP, DOWN, IDLE
│   ├── Event.java                 # Event representation
│   ├── EventLog.java              # Singleton event logger
│   └── ExcludeFromJacocoGeneratedReport.java
├── persistence/
│   ├── JsonReader.java            # Load elevator from JSON
│   ├── JsonWriter.java            # Save elevator to JSON
│   └── Writable.java              # JSON serialization interface
├── ui/
│   ├── Main.java                  # Entry point (GUI)
│   ├── ElevatorApp.java           # Console interface
│   ├── ElevatorAppGui.java        # Main GUI window
│   ├── ElevatorPanel.java         # Elevator controls and display
│   ├── ElevatorShaftPanel.java    # Visual shaft rendering
│   ├── EnterFloorsPanel.java      # Building configuration
│   └── IntroPanel.java            # Welcome screen
├── test/
│   └── model/
│       └── TestElevator.java      # JUnit tests
└── data/
    └── elevator.json              # Saved elevator state
```

---

## How to Run

### GUI Version (Recommended)

```bash
javac ui/Main.java
java ui.Main
```

### Console Version

```bash
javac ui/ElevatorApp.java
java ui.ElevatorApp
```

---

## Usage

1. **Create or Load Elevator** - Choose building size (1-200 floors) or load saved state
2. **Request Floors** 
   - Use **floor call buttons** (left panel) to simulate calling from hallway
   - Use **cabin request buttons** (right panel) to request from inside elevator
3. **Start Simulation** - Click "Start" to watch elevator move
4. **Observe Behavior** - Notice how elevator serves requests in direction-aware order
5. **Save State** - Save current configuration for later

---

## Testing

Comprehensive JUnit tests verify elevator behavior:

```bash
# Run tests
java -jar junit-platform-console-standalone.jar --class-path . --scan-class-path
```

**Test Coverage:**
- Elevator initialization and state management
- Direction-aware request sorting
- Floor request validation and duplicate handling
- Movement logic and direction changes
- Drop-off behavior and edge cases
- JSON serialization/deserialization

---

## JSON Persistence

Elevator state is saved to `./data/elevator.json`:

```json
{
    "currentFloor": 7,
    "floorsInBuilding": 12,
    "direction": "UP",
    "requestedFloors": [9, 11]
}
```

Requests are sorted based on direction to maintain efficient travel patterns.

---

## Event Logging

All system events are logged with timestamps:

```
Fri Dec 28 14:23:15 PST 2024
Created new elevator with 12 floors.

Fri Dec 28 14:23:18 PST 2024
Floor 6 requested.

Fri Dec 28 14:23:20 PST 2024
Elevator moved from floor 5 to floor 6.

Fri Dec 28 14:23:21 PST 2024
Elevator arrived at floor 6 and dropped off passengers.

Fri Dec 28 14:23:21 PST 2024
Elevator is moving up.
```

Event log prints to console on program exit.

---

## Learning Outcomes

This project demonstrates:
- **Algorithm implementation**: Direction-aware scheduling with sorted request lists
- **Object-oriented design**: Separation of concerns, encapsulation
- **Data persistence**: JSON serialization/deserialization
- **Event-driven architecture**: Logging and state management
- **GUI development**: Swing components and event handling
- **Testing**: Comprehensive JUnit test coverage

---

## Future Enhancements

- **Multiple Elevators**: Implement multi-elevator system with intelligent request distribution algorithm that assigns calls to the nearest available elevator moving in the appropriate direction

- **Advanced Scheduling Algorithms**: Implement and compare different elevator algorithms:
  - SCAN (elevator sweep): Continue in direction until no requests remain, then reverse
  - LOOK: Similar to SCAN but only travels as far as final request
  - SSTF (Shortest Seek Time First): Serve closest request regardless of direction
  - Compare performance metrics across different algorithms

- **Mathematical Analysis**: Conduct simulation-based analysis to explore optimization questions:
  - What is the maximum number of elevators that can be active simultaneously for a given building size and request load?
  - At what point does adding additional elevators provide diminishing returns?
  - How does elevator starting position (all at ground floor vs. distributed) affect average wait time?
  - What is the relationship between building height, request frequency, and optimal elevator count?
  - Generate performance visualizations and statistical analysis of various configurations

- **Capacity Limits**: Add passenger capacity constraints and weight limits

- **Priority Requests**: Emergency or VIP floor prioritization

- **Performance Metrics**: Track and display average wait time, total distance traveled, idle time percentage, energy efficiency

---

## Acknowledgements

This project references code patterns from:
- **JsonSerializationDemo** (CPSC 210) - JSON persistence implementation
- **AlarmSystem** (CPSC 210) - Event logging system

---

## License

Academic project for CPSC 210 at the University of British Columbia.
