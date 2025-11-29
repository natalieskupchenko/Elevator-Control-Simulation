package ui;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import model.Elevator;

import model.ExcludeFromJacocoGeneratedReport;
import persistence.JsonReader;

// Represents the main GUI application window that manages switching between UI
// panels.
@ExcludeFromJacocoGeneratedReport
public class ElevatorAppGui extends JFrame {

    private CardLayout cardLayout;
    private JPanel mainContainer;

    // MODIFIES: this
    // EFFECTS: constructs the main GUI window, initializes layout and intro panel,
    // makes window visible
    public ElevatorAppGui() {
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


    // REQUIRES: numFloors > 0
    // MODIFIES: this.mainContainer
    // EFFECTS: creates a new Elevator and ElevatorPanel and switches the GUI to the
    // elevator view
    public void showElevatorPanel(int numFloors) {
        Elevator elevator = new Elevator(numFloors);
        ElevatorPanel elevatorPanel = new ElevatorPanel(elevator, numFloors);
        mainContainer.add(elevatorPanel, "ELEVATOR");
        cardLayout.show(mainContainer, "ELEVATOR");
    }

    // REQUIRES: name matches a panel previously added to mainContainer
    // MODIFIES: this.cardLayout
    // EFFECTS: switches the visible panel to the one with the given name
    public void showPanel(String name) {
        cardLayout.show(mainContainer, name);
    }

    // REQUIRES: ./data/elevator.json exists and contains valid elevator data
    // MODIFIES: this.mainContainer
    // EFFECTS: loads an Elevator from file, creates a panel for it, and shows it;
    // displays an error dialog if loading fails
    public void loadElevatorFromFile() {
        try {
            JsonReader reader = new JsonReader("./data/elevator.json");
            Elevator loadedElevator = reader.read(); // read saved elevator
            ElevatorPanel elevatorPanel = new ElevatorPanel(loadedElevator, loadedElevator.getFloorsInBuilding());
            mainContainer.add(elevatorPanel, "ELEVATOR");
            cardLayout.show(mainContainer, "ELEVATOR");
        } catch (IOException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to load elevator state.");
        }
    }

    // MODIFIES: this.mainContainer
    // EFFECTS: creates an EnterFloorsPanel and shows it
    public void showEnterFloorsPanel() {
        EnterFloorsPanel enterFloors = new EnterFloorsPanel(this);
        mainContainer.add(enterFloors, "ENTER_FLOORS");
        cardLayout.show(mainContainer, "ENTER_FLOORS");
    }

    // EFFECTS: launches the ElevatorGuiApp on the Swing event dispatch thread
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ElevatorAppGui());
    }
}
