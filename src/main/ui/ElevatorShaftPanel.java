package ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JPanel;

import model.Elevator;
import model.ExcludeFromJacocoGeneratedReport;

@ExcludeFromJacocoGeneratedReport
public class ElevatorShaftPanel extends JPanel {

    private final int numFloors;
    private final Elevator elevator;

    private static final int TOP_MARGIN = 10;
    private static final int BOTTOM_MARGIN = 10;

    public ElevatorShaftPanel(Elevator elevator, int numFloors) {
        this.elevator = elevator;
        this.numFloors = numFloors;
        setPreferredSize(new Dimension(250, 600));
        setBackground(new Color(45, 45, 45));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int width = getWidth();
        int height = getHeight();

        drawRails(g, width, height);
        drawFloorLinesAndLabels(g, width, height);
        drawElevatorCar(g, width, height);
    }

// Coordinate Mapping

    private int floorToY(int floor, int height) {
        if (numFloors <= 1) return height - BOTTOM_MARGIN;

        int usableHeight = height - TOP_MARGIN - BOTTOM_MARGIN;
        double ratio = (double) (floor - 1) / (numFloors - 1);

        return TOP_MARGIN + (int) Math.round(usableHeight * (1.0 - ratio));
    }

    private int getUsableHeight(int height) {
        return height - TOP_MARGIN - BOTTOM_MARGIN;
    }

// Drawing Methods 

    private void drawRails(Graphics g, int width, int height) {
        g.setColor(new Color(100, 100, 100));

        int leftRailX = width / 4;
        int rightRailX = width * 3 / 4;

        g.fillRect(leftRailX, 0, 6, height);
        g.fillRect(rightRailX, 0, 6, height);
    }

    private void drawFloorLinesAndLabels(Graphics g, int width, int height) {
        int leftRailX = width / 4;
        int rightRailX = width * 3 / 4;

        int labelInterval = calculateLabelInterval();
        g.setFont(new Font("SansSerif", Font.BOLD, 11));

        for (int floor = 1; floor <= numFloors; floor++) {
            int y = floorToY(floor, height);
            boolean drawLabel = shouldDrawLabel(floor, labelInterval);

            g.setColor(drawLabel
                    ? new Color(150, 150, 150)
                    : new Color(100, 100, 100));

            g.drawLine(leftRailX - 15, y, rightRailX + 15, y);

            if (drawLabel) {
                g.setColor(Color.WHITE);
                g.drawString(String.valueOf(floor), leftRailX - 35, y + 5);
            }
        }
    }

    private void drawElevatorCar(Graphics g, int width, int height) {
        int leftRailX = width / 4;
        int rightRailX = width * 3 / 4;

        int currentFloor = elevator.getCurrentFloor();
        int carBottomY = floorToY(currentFloor, height);

        int carHeight = Math.max(getUsableHeight(height) / (numFloors * 2), 8);
        int carWidth = rightRailX - leftRailX - 12;
        int carX = leftRailX + 6;
        int carY = carBottomY - carHeight;

        drawCarBody(g, carX, carY, carWidth, carHeight);
        drawCarHighlight(g, carX, carY, carWidth, carHeight);
        drawCarLabel(g, carX, carY, carWidth, carHeight, currentFloor);
    }

// Car Rendering

    private void drawCarBody(Graphics g, int x, int y, int width, int height) {
        g.setColor(new Color(70, 130, 180));
        g.fillRect(x, y, width, height);

        g.setColor(new Color(30, 80, 130));
        g.drawRect(x, y, width, height);
    }

    private void drawCarHighlight(Graphics g, int x, int y, int width, int height) {
        if (height <= 15) return;

        g.setColor(new Color(100, 160, 210));
        g.fillRect(x + 2, y + 2, width - 4, height / 4);
    }

    private void drawCarLabel(Graphics g, int x, int y, int width, int height, int floor) {
        g.setColor(Color.WHITE);

        int fontSize = Math.min(14, Math.max(8, height - 4));
        g.setFont(new Font("SansSerif", Font.BOLD, fontSize));

        String text = String.valueOf(floor);
        int textWidth = g.getFontMetrics().stringWidth(text);

        int textX = x + (width - textWidth) / 2;
        int textY = y + (height / 2) + (fontSize / 3);

        g.drawString(text, textX, textY);
    }

// Helpers

    private int calculateLabelInterval() {
        if (numFloors > 80) return 10;
        if (numFloors > 40) return 5;
        if (numFloors > 20) return 2;
        return 1;
    }

    private boolean shouldDrawLabel(int floor, int interval) {
        return floor == 1 || floor == numFloors || floor % interval == 0;
    }
}
