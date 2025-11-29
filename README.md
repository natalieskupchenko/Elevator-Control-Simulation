# Elevator Simulator: CPSC 210 Term Project

## By: Natalie Skupchenko

### Phase 0 
#### Description of Project for Phase 0
**What will the application do?**</li>
This application will allow the user to simulate the operation of elevators in a building. The user will be able to call an elevator from a floor, select a floor from inside the elevator, hold the door open, close the door, and press a button to call for 911. The user will be able to create a building and specify the number of elevators and floors. The user will also be able to temporarily close an elevator for maintenance, remove an elevator completely, add a new elevator or add a new floor. The user will be able to access the list of calls to the elevator from various floors and will be able to access the list of floors pressed from inside the elevator. The user will also be able to access the current weight inside the elevator and the maximum capacity (in kg).

**Who will use it?**</li>
This application is intended for anyone who wants to explore and understand how elevators operate in a building. It could be used by students learning about simulation, programming, or building systems, as well as by anyone interested in experimenting with elevator scheduling and management in a controlled, simulated environment.

**Why is this project of interest to you?**</li>
I am a mathematics student and I am very interested in optimization/logistics, as well as computer science. I have always been curious about the logic behind how elevators are called and this project allows me to explore this. This project will also be applicable to potential future jobs because it will improve my skills in object oriented programming as well as optimization for logistics and scheduling. 

**Non-Trivial Classes**
1. **Elevator** represents a single elevator. 
1. **ElevatorSystem** represents a collection of elevators in the building.

### Phase 1

#### User Stories for Phase 1 (made in Phase 0)
1. **Add a floor request** </li>
As a user, I want to press a button for a specific floor so that the elevator will go there. (User story: add multiple Xs to my Y)
2. **Move elevator one floor at a time**</li>
As a user, I want the elevator to move one floor at a time toward the requested floor, updating its direction so I know if it’s going up or down.
3. **Arrive at requested floor and drop off**</li>
As a user, I want the elevator to stop at my requested floor and remove that floor from the list of requested floors.
4. **Track elevator direction, current floor and requested floors**</li>
As a user, I want to see what floor the elevator is on, what floors are requested for the elevator to go to, and whether the elevator is going up, down, or idle. (User story: list all Xs in Y)

### Phase 2

#### User Stories for Phase 2

1. **Option to save entire state of application to file** </li>
As a user, I want the option to be able to save the current state of the elevator to file (current floor, requested floors, direction).
2. **Option to reload state from file and resume exactly where they left off in earlier file** </li>
As a user, I want the option to reload a previously saved elevator from file and resume the elevator simulation of the selected instance at that exact state.

This program uses JSON persistence to store the elevator state. When the user chooses the Save option, the elevator is saved to: ./data/elevator.json
Here is an example saved file: 

{

    "currentFloor": 3, 

    "floorsInBuilding": 10,

    "direction": "UP",

    "requestedFloors": [5, 8]
  
}

The user can later choose Load to restore this exact state.
The Elevator class implements a toJson() method so its state can be written to JSON. The JsonWriter class writes the elevator data to a file. The JsonReader class loads the elevator from JSON.

### Phase 3

#### Optional User Stories for Phase 3

1. **Add an opening menu with option to load previous elevator** </li>
As a user, after running the program, I want the first menu I see to have options to either load a previous elevator or create a new elevator.
2. **Sorting floors based on direction** </li>
As a user, I want the elevator to sort the requested floors depending on the current direction of the elevator. If there are floors pressed in two opposite directions, then the elevator should go to the first floor pressed, and then continue going to the next floors. 

### Phase 4

#### Phase 4: Task 2
Printing Event Log:

Fri Nov 28 16:49:51 PST 2025

Created new elevator with 10 floors.

Fri Nov 28 16:49:52 PST 2025

Floor 2 requested.

Fri Nov 28 16:49:53 PST 2025

Floor 3 requested.

Fri Nov 28 16:49:54 PST 2025

Floor 5 requested.

Fri Nov 28 16:49:56 PST 2025

Elevator moved from floor 1 to floor 2.

Fri Nov 28 16:49:57 PST 2025

Elevator arrived at floor 2 and dropped off passengers.

Fri Nov 28 16:49:57 PST 2025

Elevator is moving up.

Fri Nov 28 16:49:58 PST 2025

Elevator moved from floor 2 to floor 3.

Fri Nov 28 16:49:59 PST 2025

Elevator arrived at floor 3 and dropped off passengers.

Fri Nov 28 16:49:59 PST 2025

Elevator is moving up.

Fri Nov 28 16:50:00 PST 2025

Elevator moved from floor 3 to floor 4.

Fri Nov 28 16:50:01 PST 2025

Elevator moved from floor 4 to floor 5.

Fri Nov 28 16:50:02 PST 2025

Elevator arrived at floor 5 and dropped off passengers.

Fri Nov 28 16:50:02 PST 2025

Elevator is now idle.

Fri Nov 28 16:50:04 PST 2025

Floor 8 requested.

Fri Nov 28 16:50:06 PST 2025

Elevator moved from floor 5 to floor 6.

Fri Nov 28 16:50:08 PST 2025

Elevator moved from floor 6 to floor 7.

Fri Nov 28 16:50:09 PST 2025

Elevator moved from floor 7 to floor 8.

Fri Nov 28 16:50:10 PST 2025

Elevator arrived at floor 8 and dropped off passengers.

Fri Nov 28 16:50:10 PST 2025

Elevator is now idle.

#### Phase 4: Task 3
The UML diagram shows how the classes in the project are organized and how they relate to each other. The model classes handle the elevator logic, the persistence classes handle saving and loading, and the UI classes handle what the user sees and interacts with. Right now, the UI directly communicates with the model, which works but mixes the display with the logic. If I had more time, I would add a controller class to separate the UI from the model. The controller would receive input from the UI, tell the elevator what to do, and update the UI based on the elevator’s state. This would make the program easier to maintain, test, and expand in the future.

#### Acknowledgements
This project references and adapts parts of the JsonSerializationDemo from CPSC 210 for JSON persistence (JsonReader, JsonWriter, and Writable patterns).