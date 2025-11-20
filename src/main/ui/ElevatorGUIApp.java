package ui;

import java.awt.CardLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class ElevatorGUIApp extends JFrame {

    public ElevatorGUIApp() {
        setTitle("Elevator Control Simulation");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(1000,800));
        getContentPane().setLayout(new CardLayout());
        pack();
        setLocationRelativeTo(null);
        setVisible(true);

        CardLayout cardLayout = new CardLayout();
        JPanel mainContainer = new JPanel(cardLayout);
        IntroPanel intro = new IntroPanel();
        mainContainer.add(intro, "INTRO");
        add(mainContainer);
    }

    public static void main(String [] args) {
        SwingUtilities.invokeLater(() -> new ElevatorGUIApp());
    }
}
