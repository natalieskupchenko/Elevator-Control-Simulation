package ui;

import javax.swing.*;
import java.awt.*;

public class EnterFloorsPanel extends JPanel {
    public EnterFloorsPanel(ElevatorGUIApp app) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));

        JLabel label = new JLabel("Enter number of floors:");
        label.setAlignmentX(CENTER_ALIGNMENT);
        add(label);

        JTextField numFloorsField = new JTextField(5);
        numFloorsField.setMaximumSize(new Dimension(200, 30));
        numFloorsField.setAlignmentX(CENTER_ALIGNMENT);
        add(numFloorsField);

        JButton submitButton = new JButton("Create Elevator");
        submitButton.setAlignmentX(CENTER_ALIGNMENT);
        add(Box.createRigidArea(new Dimension(0, 10)));
        add(submitButton);

        submitButton.addActionListener(e -> {
            try {
                int numFloors = Integer.parseInt(numFloorsField.getText());
                System.out.println(numFloors + " floors entered"); 
                app.showElevatorPanel(numFloors);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please enter a valid integer.");
            }
        });
    }

}
