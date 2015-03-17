package graphicalUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

public class ProblemSettingsListener implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Objective & Bounds")) {
            selectionBox();
        } else if (e.getActionCommand().equals("Alter Bounds")) {
            boundsBox();
        } else if (e.getActionCommand().equals("Choose Heuristics")) {
            heuristicsBox();
        }
    }

    // Method to allow bounds/objective box call anywhere
    public static void selectionBox() {
        // Display input box for objective and bounds
        BoundsObjectiveDialog bod = new BoundsObjectiveDialog(GraphicalUI.frame);
        bod.pack();
        bod.setLocationRelativeTo(GraphicalUI.frame);
        bod.setVisible(true);

        if (!BoundsObjectiveDialog.cancelled) {
            boundsBox();
        }
    }

    // Method to change the bounds
    public static void boundsBox() {
        JOptionPane.showMessageDialog(GraphicalUI.frame, "Click on the board to select your bounds.\n"
                + "Press 'Show Bounds' once finished.");
        BoardJPanel.boundsSelectionMode = true;
        GraphicalUI.updateBoundsButton(true);
        GraphicalUI.setBounds(true);
    }

    // Method to choose the problems heuristics
    public static void heuristicsBox() {
        HeuristicChooseDialog hcd = new HeuristicChooseDialog(
                GraphicalUI.frame);
        hcd.pack();
        hcd.setLocationRelativeTo(GraphicalUI.frame);
        hcd.setVisible(true);
        if (!HeuristicChooseDialog.cancelled) {
            try {
                // Input specified heuristics
                GraphicalUI.heuristics = hcd.getSelectedHeuristics();
            } catch (Exception NullPointer) {
                //
            }
        } else {
            JOptionPane.showMessageDialog(GraphicalUI.frame,
                    "No heuristics were chosen.");
        }
    }
}
