package ui;

import java.awt.CardLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
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
    }

    public static void main(String [] args) {
        SwingUtilities.invokeLater(() -> new ElevatorGUIApp());
    }
}
