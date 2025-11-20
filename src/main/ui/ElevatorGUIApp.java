package ui;

import java.awt.CardLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import model.Elevator;

public class ElevatorGUIApp extends JFrame {

    private CardLayout cardLayout;
    private JPanel mainContainer;

    public ElevatorGUIApp() {
        setTitle("Elevator Control Simulation");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(1000, 800));

        cardLayout = new CardLayout();
        mainContainer = new JPanel(cardLayout);

        IntroPanel intro = new IntroPanel(this);

        mainContainer.add(intro, "INTRO");

        add(mainContainer);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void showElevatorPanel(int numFloors) {
        Elevator elevator = new Elevator(numFloors);
        ElevatorPanel elevatorPanel = new ElevatorPanel(elevator, numFloors);
        mainContainer.add(elevatorPanel, "ELEVATOR");
        cardLayout.show(mainContainer, "ELEVATOR");
    }

    public void showPanel(String name) {
        cardLayout.show(mainContainer, name);
    }

    public void showEnterFloorsPanel() {
        EnterFloorsPanel enterFloors = new EnterFloorsPanel(this);
        mainContainer.add(enterFloors, "ENTER_FLOORS");
        cardLayout.show(mainContainer, "ENTER_FLOORS");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ElevatorGUIApp());
    }
}
