# Elevator Simulator: CPSC 210 Term Project

## By: Natalie Skupchenko

### Phase 0

#### Description of Project 
**What will the application do?**</li>
This application will allow the user to simulate the operation of elevators in a building. The user will be able to call an elevator from a floor, select a floor from inside the elevator, hold the door open, close the door, and press a button to call for 911. The user will be able to create a building and specify the number of elevators and floors. The user will also be able to temporarily close an elevator for maintenance, remove an elevator completely, add a new elevator or add a new floor. The user will be able to access the list of calls to the elevator from various floors and will be able to access the list of floors pressed from inside the elevator. The user will also be able to access the current weight inside the elevator and the maximum capacity (in kg).

**Who will use it?**</li>
This application is intended for anyone who wants to explore and understand how elevators operate in a building. It could be used by students learning about simulation, programming, or building systems, as well as by anyone interested in experimenting with elevator scheduling and management in a controlled, simulated environment.

**Why is this project of interest to you?**</li>
I am a mathematics student and I am very interested in optimization/logistics, as well as computer science. I have always been curious about the logic behind how elevators are called and this project allows me to explore this. This project will also be applicable to potential future jobs because it will improve my skills in object oriented programming as well as optimization for logistics and scheduling. 

**Non-Trivial Classes**
1. **Elevator** represents a single elevator. 
1. **ElevatorSystem** represents a collection of elevators in the building.

#### User Stories for Phase 1

1. **Create Elevator System**
As a user, I want to create an ElevatorSystem with a specific number of floors and elevators so that I can customize the building before using the elevators.
2. **Add a floor request to an elevator**</li>
As a user, I want to add a floor request to an elevator from inside the elevator so I can travel to my desired floor.  
3. **Call an elevator from a floor**</li>
As a user, I want to call an elevator from a floor to go up or down so that the system assigns an appropriate elevator to pick me up.
4. **View the status of all elevators**</li>
As a user, I want to see the status of all elevators (current floor, direction, door state, and current load) so that I can know what each elevator is doing.
