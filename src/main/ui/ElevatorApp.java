package ui;

import model.Elevator;
import model.ExcludeFromJacocoGeneratedReport;

import java.util.Scanner;

// User interaction for Elevator
@ExcludeFromJacocoGeneratedReport
public class ElevatorApp {

    private Elevator elevator;
    private Scanner scanner;

    public ElevatorApp() {
        scanner = new Scanner(System.in);
        int numFloors = askUserForNumberOfFloors();
        elevator = new Elevator(numFloors);
        runApp();
    }

    // Prompt user to set number of floors
    private int askUserForNumberOfFloors() {
        int floors = 0;
        while (floors < 1) {
            System.out.print("Enter number of floors in the building (min 1): ");
            try {
                floors = Integer.parseInt(scanner.nextLine());
                if (floors < 1) {
                    System.out.println("Number of floors must be at least 1.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }
        return floors;
    }

    // Main loop
    public void runApp() {
        boolean running = true;
        while (running) {
            System.out.println("Requested Floors: " + elevator.getRequestedFloors() 
                    + ", Current Floor: " + elevator.getCurrentFloor() 
                    + ", Floors in building: " + elevator.getFloorsInBuilding());
            System.out.println("\nSelect an option:");
            System.out.println("1. Add floor request");
            System.out.println("2. Move elevator (step-by-step)");
            System.out.println("3. Quit");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    addFloorRequest();
                    break;
                case "2":
                    moveElevator();
                    break;
                case "3":
                    System.out.println("Exiting elevator simulation.");
                    running = false;
                    break;
            }
        }
    }

    // Display the current state
    private void displayElevatorState() {
        System.out.println("\n----- Elevator State -----");
        System.out.println("Current Floor: " + elevator.getCurrentFloor());
        System.out.println("Direction: " + elevator.getDirection());
        System.out.println("Requested Floors: " + elevator.getRequestedFloors());
        System.out.println("Current Floor: " + elevator.getCurrentFloor());
    }

    // Add a floor request
    private void addFloorRequest() {
        System.out.print("Enter floor number to request (1 to " + elevator.getFloorsInBuilding() + "): ");
        int floor = Integer.parseInt(scanner.nextLine());
        elevator.addFloorRequest(floor);
        System.out.println("Floor " + floor + " requested.");
    }

    // Move the elevator step-by-step
    private void moveElevator() {
        while (!elevator.getRequestedFloors().isEmpty()) {
            // Display state before move
            System.out.println("[Before Move]");
            displayElevatorState();

            // Move one floor
            elevator.move();

            // Display state after move
            System.out.println("[After Move]");
            displayElevatorState();

            // Drop off if at requested floor
            if (elevator.getRequestedFloors().contains(elevator.getCurrentFloor())) {
                System.out.println("==> Elevator arrived at floor " + elevator.getCurrentFloor());
                elevator.dropOff(elevator.getCurrentFloor());
            }

            try {
                Thread.sleep(1000); // Simulate time delay
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt(); // Restore interrupt flag
            }
        }
    }

    public static void main(String[] args) {
        new ElevatorApp();
    }
}