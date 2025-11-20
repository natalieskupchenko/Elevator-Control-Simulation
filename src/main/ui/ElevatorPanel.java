package ui;

import javax.swing.*;

import model.Elevator;

import java.awt.*;

public class ElevatorPanel extends JPanel {
    private Elevator elevator;
    private JLabel currentFloorLabel;

    public ElevatorPanel(Elevator elevator, int numFloors) {
        this.elevator = elevator;

        setLayout(new BorderLayout());

        // placeholder for elevator shaft
        JPanel elevatorShaft = new JPanel();
        elevatorShaft.setPreferredSize(new Dimension(200, 400));
        elevatorShaft.setBackground(Color.LIGHT_GRAY);

        // label showing the current floor
        currentFloorLabel = new JLabel("Current Floor: " + elevator.getCurrentFloor());
        currentFloorLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // floor buttons
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new GridLayout(numFloors, 5, 5, 5));
        for (int i = 1; i <= numFloors; i++) {
            int floorNumber = i;
            JButton floorButton = new JButton("" + floorNumber);
            floorButton.addActionListener(e -> {
                elevator.addFloorRequest(floorNumber);
            });
            buttonsPanel.add(floorButton);
        }
        
        add(currentFloorLabel, BorderLayout.NORTH);
        add(elevatorShaft, BorderLayout.CENTER);
        add(buttonsPanel, BorderLayout.EAST);
    }
}