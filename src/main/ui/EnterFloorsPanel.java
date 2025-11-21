package ui;

import javax.swing.*;

import model.ExcludeFromJacocoGeneratedReport;

import java.awt.*;

// Provides a screen for the user to enter the number of floors and validates the input before starting the simulation.
@ExcludeFromJacocoGeneratedReport
public class EnterFloorsPanel extends JPanel {

    private JTextField numFloorsField;
    private JButton submitButton;

    // REQUIRES: app != null
    // MODIFIES: this
    // EFFECTS: initializes panel UI components; sets layout, padding, and
    // installs action listener that validates user input and directs
    // app to show elevator panel if input is valid
    public EnterFloorsPanel(ElevatorGuiApp app) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));

        add(createLabel());
        add(createNumFloorsField());
        add(createSubmitButton());

        submitButton.addActionListener(e -> {
            try {
                int numFloors = Integer.parseInt(numFloorsField.getText());

                if (numFloors < 1 || numFloors > 200) {
                    JOptionPane.showMessageDialog(this,
                            "Number of floors must be between 1 and 200.");
                    return;
                }

                System.out.println(numFloors + " floors entered");
                app.showElevatorPanel(numFloors);

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please enter a valid integer.");
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: returns label prompting user to enter number of floors
    private JLabel createLabel() {
        JLabel label = new JLabel("Enter number of floors:");
        label.setAlignmentX(CENTER_ALIGNMENT);
        return label;
    }

    // MODIFIES: this, numFloorsField
    // EFFECTS: creates and returns a text field for entering number of floors;
    // stores text field in numFloorsField field
    private JTextField createNumFloorsField() {
        numFloorsField = new JTextField(5);
        numFloorsField.setMaximumSize(new Dimension(200, 30));
        numFloorsField.setAlignmentX(CENTER_ALIGNMENT);
        return numFloorsField;
    }

    // MODIFIES: this, submitButton
    // EFFECTS: creates submit button, stores it, and returns it
    private JButton createSubmitButton() {
        submitButton = new JButton("Create Elevator");
        submitButton.setAlignmentX(CENTER_ALIGNMENT);
        add(Box.createRigidArea(new Dimension(0, 10)));
        return submitButton;
    }
}
