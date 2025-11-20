package ui;

import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class IntroPanel extends JPanel {
    private ElevatorGUIApp app;

    public IntroPanel(ElevatorGUIApp app) {
        this.app = app;

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));

        JLabel welcomeLabel = new JLabel("Welcome! Would you like to load  previous elevator or create a new one?");
        welcomeLabel.setAlignmentX(CENTER_ALIGNMENT);
        add(welcomeLabel);
        add(Box.createRigidArea(new Dimension(0, 20)));

        JButton loadButton = new JButton("Load Elevator");
        loadButton.setAlignmentX(CENTER_ALIGNMENT);
        JButton newButton = new JButton("Create New Elevator");
        newButton.setAlignmentX(CENTER_ALIGNMENT);

        add(loadButton);
        add(Box.createRigidArea(new Dimension(0, 10)));
        add(newButton);

        loadButton.addActionListener(e -> System.out.println("Load Elevator clicked"));
        newButton.addActionListener(e -> {
            System.out.println("Create New Elevator clicked");
            app.showEnterFloorsPanel();
        });
    }
}
