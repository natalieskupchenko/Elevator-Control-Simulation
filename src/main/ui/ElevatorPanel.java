package ui;

import javax.swing.*;

import model.Elevator;
import model.Event;
import model.EventLog;
import model.ExcludeFromJacocoGeneratedReport;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.awt.*;
import java.io.IOException;

@ExcludeFromJacocoGeneratedReport
// Shows the main elevator simulation view, including controls, status displays,
// and interactive floor request buttons.
public class ElevatorPanel extends JPanel {
    private Elevator elevator;
    private JLabel currentFloorLabel;
    private JLabel statusLabel;
    private JLabel requestedFloorsLabel;
    private JPanel elevatorShaft;

    // REQUIRES: elevator != null, numFloors > 0
    // MODIFIES: this
    // EFFECTS: initializes GUI components for the elevator panel,
    // including shaft, floor buttons, top display, and control buttons
    public ElevatorPanel(Elevator elevator, int numFloors) {
        this.elevator = elevator;
        setLayout(new BorderLayout());

        elevatorShaft = new ElevatorShaftPanel(elevator, numFloors);
        add(elevatorShaft, BorderLayout.CENTER);

        add(createFloorButtonsPanel(numFloors), BorderLayout.EAST);
        add(createTopPanel(), BorderLayout.NORTH);
        add(createControlPanel(), BorderLayout.SOUTH);
    }

    // REQUIRES: numFloors > 0
    // MODIFIES: this
    // EFFECTS: creates and returns a panel containing buttons for each floor;
    // pressing a button adds a floor request to the elevator
    private JPanel createFloorButtonsPanel(int numFloors) {
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new GridLayout(numFloors / 4, numFloors / 4, 5, 5));

        for (int i = 1; i <= numFloors; i++) {
            int floorNumber = i;
            JButton floorButton = new JButton("" + floorNumber);

            floorButton.addActionListener(e -> {
                elevator.addFloorRequest(floorNumber);
                requestedFloorsLabel.setText("Requested Floors: " + elevator.getRequestedFloors());
            });

            buttonsPanel.add(floorButton);
        }

        return buttonsPanel;
    }

    // REQUIRES: elevator fields accessible
    // MODIFIES: this
    // EFFECTS: creates and returns the top status panel showing
    // current floor, direction, and requested floors
    private JPanel createTopPanel() {
        currentFloorLabel = new JLabel("Current Floor: " + elevator.getCurrentFloor());
        currentFloorLabel.setHorizontalAlignment(SwingConstants.CENTER);

        statusLabel = new JLabel("Status: " + elevator.getDirection());
        statusLabel.setHorizontalAlignment(SwingConstants.CENTER);

        requestedFloorsLabel = new JLabel("Requested Floors: " + elevator.getRequestedFloors());
        requestedFloorsLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel topPanel = new JPanel(new GridLayout(3, 1));
        topPanel.add(currentFloorLabel);
        topPanel.add(statusLabel);
        topPanel.add(requestedFloorsLabel);

        return topPanel;
    }

    // REQUIRES: none
    // MODIFIES: this
    // EFFECTS: creates and returns a panel with Go, Save, Load, and Exit buttons;
    // buttons trigger elevator simulation, saving, loading, or exiting
    private JPanel createControlPanel() {
        JPanel controlPanel = new JPanel(new FlowLayout());

        JButton goButton = new JButton("Go");
        goButton.addActionListener(e -> startElevatorSimulation());

        JButton loadButton = new JButton("Load");
        loadButton.addActionListener(e -> loadElevator());

        JButton exitButton = new JButton("Exit");

        

        exitButton.addActionListener(e -> System.exit(0));
        // When clicked, print event log and exit
        exitButton.addActionListener(e -> {
            System.out.println("Printing Event Log:");
            for (Event event : EventLog.getInstance()) {
                System.out.println(event);
            }
            System.exit(0);
        });

        controlPanel.add(goButton);
        controlPanel.add(addSaveButton());
        controlPanel.add(loadButton);
        controlPanel.add(exitButton);

        return controlPanel;
    }

    // MODIFIES: this
    // EFFECTS: creates and returns a button that saves the current elevator state
    private JButton addSaveButton() {
        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(e -> {
            try {
                JsonWriter writer = new JsonWriter("./data/elevator.json");
                writer.open();
                writer.write(elevator);
                writer.close();
                System.out.println("Elevator saved!");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        return saveButton;
    }

    // REQUIRES: elevator != null
    // MODIFIES: this.elevator, this UI labels, elevatorShaft
    // EFFECTS: starts animated elevator movement using a timer;
    // updates status and stops when no more requests
    private void startElevatorSimulation() {
        Timer timer = new Timer(1000, e -> {
            if (!elevator.getRequestedFloors().isEmpty()) {
                elevator.move();
                updateElevatorStatus();
                updateRequestedFloors();
                updateElevatorCarPosition();
            } else {
                ((Timer) e.getSource()).stop(); // stop when done
            }
        });
        timer.start();
    }

    // REQUIRES: ./data/elevator.json exists and contains valid elevator data
    // MODIFIES: this.elevator, this UI labels
    // EFFECTS: loads elevator state from file and updates UI;
    // shows an error dialog if loading fails
    public void loadElevator() {
        try {
            JsonReader reader = new JsonReader("./data/elevator.json");
            elevator = reader.read(); // update the elevator reference
            updateElevatorStatus();
            updateRequestedFloors();
            System.out.println("Elevator loaded!");
        } catch (IOException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to load elevator state.");
        }
    }

    // MODIFIES: elevatorShaft
    // EFFECTS: repaints elevator shaft to reflect updated elevator position
    private void updateElevatorCarPosition() {
        elevatorShaft.repaint();
    }

    // MODIFIES: requestedFloorsLabel
    // EFFECTS: updates label showing list of requested floors
    private void updateRequestedFloors() {
        requestedFloorsLabel.setText("Requested Floors: " + elevator.getRequestedFloors());
    }

    // MODIFIES: currentFloorLabel, statusLabel
    // EFFECTS: updates UI to show elevator's current floor and direction
    private void updateElevatorStatus() {
        currentFloorLabel.setText("Current Floor: " + elevator.getCurrentFloor());
        statusLabel.setText("Status: " + elevator.getDirection());
    }
}