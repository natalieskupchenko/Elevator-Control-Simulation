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
1. **Add a floor request**
As a user, I want to press a button for a specific floor so that the elevator will go there. (User story: add multiple Xs to my Y)
2. **Move elevator one floor at a time**</li>
As a user, I want the elevator to move one floor at a time toward the requested floor, updating its direction so I know if it’s going up or down.
3. **Arrive at requested floor and drop off**</li>
As a user, I want the elevator to stop at my requested floor and remove that floor from the list of requested floors.
4. **Track elevator direction, current floor and requested floors**</li>
As a user, I want to see what floor the elevator is on, what floors are requested for the elevator to go to, and whether the elevator is going up, down, or idle. (User story: list all Xs in Y)

### Phase 2

#### User Stories for Phase 2

1. **Option to save entire state of application to file**
As a user, I want the option to be able to save the current state of the elevator to file (current floor, requested floors, direction).
2. **Option to reload state from file and resume exactly where they left off in earlier file**
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

1. As a user, after running the program, I want the first menu I see to have options to either load a previous elevator or create a new elevator.
2. As a user, I want the elevator to sort the requested floors depending on the current direction of the elevator. If there are floors pressed in two opposite directions, then the elevator should go to the first floor pressed, and then continue going to the next floors. 

#### Acknowledgements
This project references and adapts parts of the JsonSerializationDemo from CPSC 210 for JSON persistence (JsonReader, JsonWriter, and Writable patterns).