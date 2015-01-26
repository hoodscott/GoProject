package graphicalUI;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

public class CompetitiveMenuListener implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
		// allow user to use competitive play mode options
        // swap player colours of AI/human
        if (e.getActionCommand().equals("Swap Player Colour")) {
            BoardJPanel.changePlayer();
            // force AI to move first
        } else if (e.getActionCommand().equals("Make AI Move")) {
            BoardJPanel.changePlayer();
            BoardJPanel.GUIAIMove();
        } else {
            Object[] aiValues = {"MiniMax", "AlphaBeta"};
            Component frame = null;
            String s = (String) JOptionPane.showInputDialog(frame,
                    "Select AI type to use...", "AI Type",
                    JOptionPane.PLAIN_MESSAGE, null, aiValues, "miniMax");
            if (s != null) {
                // set ai type to selected value
                GraphicalUI.aiType = s;
            }
        }
    }

}
