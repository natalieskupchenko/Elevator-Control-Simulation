package ui;

import model.Elevator;
import model.ExcludeFromJacocoGeneratedReport;

import persistence.JsonReader;
import persistence.JsonWriter;
import java.io.IOException;

import java.util.Scanner;

// User interaction for Elevator
@ExcludeFromJacocoGeneratedReport
public class ElevatorApp {

    private Elevator elevator;
    private Scanner scanner;
    private static final String JSON_STORE = "./data/elevator.json";
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    public ElevatorApp() {
        scanner = new Scanner(System.in);
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);

        System.out.println("Welcome to Elevator Simulation.");
        System.out.println("1. Create a new elevator");
        System.out.println("2. Load saved elevator");
        String choice = scanner.nextLine();

        if (choice.equals("1")) {
            int numFloors = askUserForNumberOfFloors();
            elevator = new Elevator(numFloors);
        } else if (choice.equals("2")) {
            loadElevator();
        } else {
            System.out.println("Invalid. Creating new elevator by default.");
            int numFloors = askUserForNumberOfFloors();
            elevator = new Elevator(numFloors);
        }

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

    // EFFECTS: runs the main menu loop for the app
    public void runApp() {
        boolean running = true;
        while (running) {
            displayMenu();
            running = processUserInput();
        }
    }

    // EFFECTS: prints the main menu
    private void displayMenu() {
        System.out.println("\nRequested Floors: " + elevator.getRequestedFloors()
                + ", Current Floor: " + elevator.getCurrentFloor()
                + ", Floors in building: " + elevator.getFloorsInBuilding());

        System.out.println("\nSelect an option:");
        System.out.println("1. Add floor request");
        System.out.println("2. Move elevator step-by-step");
        System.out.println("3. Save elevator state to file");
        System.out.println("4. Load elevator state from file");
        System.out.println("5. View elevator current state");
        System.out.println("6. Quit");
    }

    // MODIFIES: this
    // EFFECTS: processes user selection and returns whether app should continue
    // running
    private boolean processUserInput() {
        String choice = scanner.nextLine();
        handleMenuChoice(choice);
        return !choice.equals("6");
    }

    // MODIFIES: this
    // EFFECTS: executes the command chosen by user
    private void handleMenuChoice(String choice) {
        switch (choice) {
            case "1":
                addFloorRequest();
                break;
            case "2":
                moveElevator();
                break;
            case "3":
                saveElevator();
                break;
            case "4":
                loadElevator();
                break;
            case "5":
                viewState();
                break;
            case "6":
                System.out.println("Exiting elevator simulation.");
                break;
            default:
                System.out.println("Invalid selection. Try again.");
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
        String input = scanner.nextLine().trim();

        try {
            int floor = Integer.parseInt(input);

            // Validate floor input
            if (floor < 1 || floor > elevator.getFloorsInBuilding()) {
                System.out.println("Invalid floor. Please enter a number between 1 and "
                        + elevator.getFloorsInBuilding() + ".");
                return;
            }

            if (floor == elevator.getCurrentFloor()) {
                System.out.println("You're already on that floor.");
                return;
            }

            if (elevator.getRequestedFloors().contains(floor)) {
                System.out.println("That floor is already in the request list.");
                return;
            }

            // Valid input → add to request queue
            elevator.addFloorRequest(floor);
            System.out.println("Floor " + floor + " added to request list.");

        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a whole number.");
        }
    }

    // Move the elevator step-by-step
    private void moveElevator() {
        while (!elevator.getRequestedFloors().isEmpty()) {
            // Display state before move
            System.out.println("[Before Move]");
            displayElevatorState();

            // Store next target before moving
            int next = elevator.getNextRequestedFloor();

            elevator.move(); // dropOff() happens inside here if it arrives

            System.out.println("[After Move]");
            displayElevatorState();

            // If elevator reached target floor
            if (elevator.getCurrentFloor() == next) {
                System.out.println("==> Elevator arrived at floor " + next);
            }

            try {
                Thread.sleep(1000); // Simulate time delay
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt(); // Restore interrupt flag
            }
        }
    }

    // EFFECTS: saves the current elevator state to file
    private void saveElevator() {
        try {
            jsonWriter.open();
            jsonWriter.write(elevator);
            jsonWriter.close();
            System.out.println("Saved current elevator state to " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to save file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads elevator from file
    private void loadElevator() {
        try {
            elevator = jsonReader.read();
            System.out.println("Loaded elevator state from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to load file: " + JSON_STORE);
        }
    }

    // EFFECTS: prints the current elevator state
    private void viewState() {
        System.out.println("\n===== Elevator Status =====");
        System.out.println("Current Floor: " + elevator.getCurrentFloor());
        System.out.println("Direction: " + elevator.getDirection());
        System.out.println("Requested Floors: " + elevator.getRequestedFloors());
        System.out.println("Floors in Building: " + elevator.getFloorsInBuilding());
        System.out.println("\nPress any key then Enter to return to main menu...");

        scanner.nextLine(); // pause
    }

    public static void main(String[] args) {
        new ElevatorApp();
    }
}