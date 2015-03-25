package graphicalUI;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

/**
 * Listener used by the Competive Play menu within the GUI.
 * Reacts to user selection of options.
 */
public class CompetitiveMenuListener implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
       
        if (e.getActionCommand().equals("Swap Player Colour")) {
        	// Swap player colours of AI/human
            GraphicalUI.boardJP.changePlayer();
            GraphicalUI.updateMessage("Colour swapped");
        } else if (e.getActionCommand().equals("Make AI Move") && GraphicalUI.getCompetitive()) {
        	// Force AI to move first
        	GraphicalUI.boardJP.GUIAIMove();
        } else if (e.getActionCommand().equals("Select AI Type")) {
        	// Allow user to change AI type
            Object[] aiValues = {"MiniMax", "AlphaBeta","HybridMiniMax"};
            Component frame = null;
            String s = (String) JOptionPane.showInputDialog(frame,
                    "Select AI type to use...", "AI Type",
                    JOptionPane.PLAIN_MESSAGE, null, aiValues, "miniMax");
            if (s != null) {
                // Set AI to selected value
                GraphicalUI.aiType = s;
            }
            GraphicalUI.updateMessage("AI type selected: " + s);
            GraphicalUI.currentAILabel.setText(s);
        }
    }

}
