package ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;
import model.Elevator;
import model.ExcludeFromJacocoGeneratedReport;

// Visually renders the elevator shaft, floors, rails, and animated elevator car based on the model’s current state.
@ExcludeFromJacocoGeneratedReport
public class ElevatorShaftPanel extends JPanel {
    private int numFloors;
    private int carHeight = 50;
    private Elevator elevator;

    // REQUIRES: elevator != null; numFloors > 0
    // MODIFIES: this
    // EFFECTS: initializes the shaft panel with given elevator and number of
    // floors;
    // sets preferred size and background color
    public ElevatorShaftPanel(Elevator elevator, int numFloors) {
        this.elevator = elevator;
        this.numFloors = numFloors;
        setPreferredSize(new Dimension(200, 400));
        setBackground(Color.DARK_GRAY);
    }

    // REQUIRES: elevator != null; numFloors > 0; g != null
    // MODIFIES: g
    // EFFECTS: draws elevator rails, floor lines, floor numbers,
    // and the elevator car at its current floor
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int width = getWidth();
        int height = getHeight();

        // Draw elevator rails
        g.setColor(Color.GRAY);
        g.fillRect(width / 4, 0, 5, height); // left rail
        g.fillRect(width * 3 / 4, 0, 5, height); // right rail

        // Draw floor lines and numbers
        g.setColor(Color.LIGHT_GRAY);
        int spacing = height / numFloors;
        g.setColor(Color.WHITE); // numbers in white for contrast
        for (int i = 0; i < numFloors; i++) {
            int y = height - (i + 1) * spacing;
            g.setColor(Color.LIGHT_GRAY);
            g.drawLine(width / 4 - 10, y, width * 3 / 4 + 10, y); // horizontal line
            g.setColor(Color.WHITE);
            g.drawString(Integer.toString(i + 1), width / 4 - 30, y + 5); // label on left
        }

        // Draw elevator car
        int carY = height - elevator.getCurrentFloor() * spacing;
        g.setColor(Color.RED);
        g.fillRect(width / 4, carY - carHeight + 5, width / 2, carHeight);
    }
}
