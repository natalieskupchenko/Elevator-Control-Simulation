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
public class ElevatorPanel extends JPanel {
    private Elevator elevator;
    private JLabel currentFloorLabel;
    private JLabel statusLabel;
    private JLabel requestedFloorsLabel;
    private JPanel elevatorShaft;

    public ElevatorPanel(Elevator elevator, int numFloors) {
        this.elevator = elevator;
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        elevatorShaft = new ElevatorShaftPanel(elevator, numFloors);
        add(elevatorShaft, BorderLayout.CENTER);

        add(createFloorCallPanel(numFloors), BorderLayout.WEST);
        add(createCabinRequestPanel(numFloors), BorderLayout.EAST);
        add(createTopPanel(), BorderLayout.NORTH);
        add(createControlPanel(), BorderLayout.SOUTH);
    }

    private JScrollPane createFloorCallPanel(int numFloors) {
        JPanel callPanel = new JPanel();
        callPanel.setLayout(new BoxLayout(callPanel, BoxLayout.Y_AXIS));
        callPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        for (int i = numFloors; i >= 1; i--) {
            callPanel.add(createFloorCallRow(i, numFloors));
        }

        JScrollPane scrollPane = new JScrollPane(callPanel);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Floor Calls"));
        scrollPane.setPreferredSize(new Dimension(180, 600));
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        return scrollPane;
    }

    private JPanel createFloorCallRow(int floorNumber, int numFloors) {
        JPanel row = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 2));
        
        JLabel label = new JLabel(String.format("F%d:", floorNumber));
        label.setPreferredSize(new Dimension(40, 22));
        label.setFont(new Font("SansSerif", Font.PLAIN, 11));
        row.add(label);

        if (floorNumber < numFloors) {
            JButton upBtn = new JButton("↑");
            upBtn.setPreferredSize(new Dimension(50, 22));
            upBtn.setFont(new Font("SansSerif", Font.BOLD, 12));
            upBtn.addActionListener(e -> handleFloorRequest(floorNumber));
            row.add(upBtn);
        } else {
            row.add(Box.createRigidArea(new Dimension(50, 22)));
        }

        if (floorNumber > 1) {
            JButton downBtn = new JButton("↓");
            downBtn.setPreferredSize(new Dimension(50, 22));
            downBtn.setFont(new Font("SansSerif", Font.BOLD, 12));
            downBtn.addActionListener(e -> handleFloorRequest(floorNumber));
            row.add(downBtn);
        } else {
            row.add(Box.createRigidArea(new Dimension(50, 22)));
        }

        return row;
    }

    private JScrollPane createCabinRequestPanel(int numFloors) {
        JPanel buttonGrid = new JPanel(new GridLayout(0, 2, 8, 8));
        buttonGrid.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        for (int i = numFloors; i >= 1; i--) {
            JButton floorBtn = new JButton("Floor " + i);
            floorBtn.setFont(new Font("SansSerif", Font.PLAIN, 11));
            floorBtn.setPreferredSize(new Dimension(80, 30));
            int floorNumber = i;
            floorBtn.addActionListener(e -> handleFloorRequest(floorNumber));
            buttonGrid.add(floorBtn);
        }

        JScrollPane scrollPane = new JScrollPane(buttonGrid);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Cabin Requests"));
        scrollPane.setPreferredSize(new Dimension(230, 600));
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        return scrollPane;
    }

    private void handleFloorRequest(int floor) {
        elevator.addFloorRequest(floor);
        updateRequestedFloors();
    }

    private JPanel createTopPanel() {
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
        topPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 10, 5));

        currentFloorLabel = new JLabel("Current Floor: " + elevator.getCurrentFloor());
        currentFloorLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        currentFloorLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        statusLabel = new JLabel("Status: " + elevator.getDirection());
        statusLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        statusLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        requestedFloorsLabel = new JLabel("Requested Floors: " + elevator.getRequestedFloors());
        requestedFloorsLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));
        requestedFloorsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        topPanel.add(currentFloorLabel);
        topPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        topPanel.add(statusLabel);
        topPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        topPanel.add(requestedFloorsLabel);

        return topPanel;
    }

    private JPanel createControlPanel() {
        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));

        JButton goButton = new JButton("Start");
        goButton.setFont(new Font("SansSerif", Font.BOLD, 12));
        goButton.setPreferredSize(new Dimension(80, 30));
        goButton.addActionListener(e -> startElevatorSimulation());

        JButton saveButton = new JButton("Save");
        saveButton.setPreferredSize(new Dimension(80, 30));
        saveButton.addActionListener(e -> saveElevator());

        JButton loadButton = new JButton("Load");
        loadButton.setPreferredSize(new Dimension(80, 30));
        loadButton.addActionListener(e -> loadElevator());

        JButton exitButton = new JButton("Exit");
        exitButton.setPreferredSize(new Dimension(80, 30));
        exitButton.addActionListener(e -> exitApplication());

        controlPanel.add(goButton);
        controlPanel.add(saveButton);
        controlPanel.add(loadButton);
        controlPanel.add(exitButton);

        return controlPanel;
    }

    private void saveElevator() {
        try {
            JsonWriter writer = new JsonWriter("./data/elevator.json");
            writer.open();
            writer.write(elevator);
            writer.close();
            JOptionPane.showMessageDialog(this, 
                "Elevator state saved successfully!",
                "Save Complete",
                JOptionPane.INFORMATION_MESSAGE);
            System.out.println("Elevator saved to ./data/elevator.json");
        } catch (IOException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this,
                "Failed to save elevator state.",
                "Save Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }

    public void loadElevator() {
        try {
            JsonReader reader = new JsonReader("./data/elevator.json");
            elevator = reader.read();
            updateElevatorStatus();
            updateRequestedFloors();
            updateElevatorCarPosition();
            JOptionPane.showMessageDialog(this,
                "Elevator state loaded successfully!",
                "Load Complete",
                JOptionPane.INFORMATION_MESSAGE);
            System.out.println("Elevator loaded from ./data/elevator.json");
        } catch (IOException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this,
                "Failed to load elevator state.\nFile may not exist.",
                "Load Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }

    private void startElevatorSimulation() {
        Timer timer = new Timer(1000, e -> {
            if (!elevator.getRequestedFloors().isEmpty()) {
                elevator.move();
                updateElevatorStatus();
                updateRequestedFloors();
                updateElevatorCarPosition();
            } else {
                ((Timer) e.getSource()).stop();
            }
        });
        timer.start();
    }

    private void exitApplication() {
        System.out.println("\n========== Event Log ==========");
        for (Event event : EventLog.getInstance()) {
            System.out.println(event);
        }
        System.out.println("===============================\n");
        System.exit(0);
    }

    private void updateElevatorCarPosition() {
        elevatorShaft.repaint();
    }

    private void updateRequestedFloors() {
        requestedFloorsLabel.setText("Requested Floors: " + elevator.getRequestedFloors());
    }

    private void updateElevatorStatus() {
        currentFloorLabel.setText("Current Floor: " + elevator.getCurrentFloor());
        statusLabel.setText("Status: " + elevator.getDirection());
    }
}