# Elevator Control Simulation 
### By: Natalie Skupchenko

### Introduction
You might be wondering what my motivation is for creating an elevator simulation as a project in Java, and it is a valid question. As a math student, my mind naturally wanders to asking questions in my everyday life like: *"why does this work the way it does?"* or *"how can this be optimized in a creative way to find the most efficient solution?"* 

And even for something trivial like an elevator system, I found myself wondering about the criteria for how an elevator is assigned to a specific floor call. The more I followed this train of thought, I started wondering what would happen as both the number of floors and the number of elevators increased. If set a number of floors, an arbitrary amount of floor calls, and we kept adding elevators to the building, at what point would adding elevators become irrelevant? At some point, there will be enough active elevators in the system that it would be quicker for an active elevator to answer a floor than an inactive elevator. So what are the conditions in the system to have a set of permanently inactive elevators? What is the maximum number of elevators that can be active at once for a given number of floors and floor calls? Does it matter where in the building the elevators start (all at the ground floor or distributed throughout the building?).

And so, my elevator control simulation was born to explore these questions more!

### Program Functionality (to do for me)
1. Fix floor requests so that before move() is called, floor requests are prioritized like this:

IDLE. We are at floor 5. 12 total floors.
Floor req = 9
Floor req = 6
Floor req = 2
Floor req = 4
Floor req = 11
UP. We go to floor 6. Drop off. 
UP. We go to floor 9. Drop off. 
DOWN. We go to floor 4. Drop off. 
DOWN. We go to floor 2. Drop off.
UP. We go to floor 11. Drop off
IDLE.




### JSON Persistence for Saving and Loading Elevator State 

This program uses JSON persistence to store the elevator state. When the user chooses the "Save" option, the current state of the elevator (current floor, requested floors, direction) is saved to: ./data/elevator.json

#### Example of Saved Elevator State:
    "currentFloor": 3, 

    "floorsInBuilding": 10,

    "direction": "UP",

    "requestedFloors": [5, 8]
  

The user can later choose "Load" to restore a previously saved elevator from file and resume the elevator simulation of the selected instance at that exact state. The Elevator class implements a toJson() method so its state can be written to JSON. The JsonWriter class writes the elevator data to a file. The JsonReader class loads the elevator from JSON.


### Event Logging

This program uses event logging to record all events during program execution and prints out the event log in the terminal after the program finishes running. 

#### Example of Printed Event Log

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


### Acknowledgements
This project references and adapts parts of the JsonSerializationDemo from CPSC 210 for JSON persistence (JsonReader, JsonWriter, and Writable patterns).
This project also references and adapts code from CPSC 210 AlarmSystem. URL: https://github.students.cs.ubc.ca/CPSC210/AlarmSystem