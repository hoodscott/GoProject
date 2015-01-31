package graphicalUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;
import javax.swing.JToggleButton;

public class GridToggleListener implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        JToggleButton button = (JToggleButton) e.getSource();
        if (button.getText().equals("Show Bounds")) {
            if (!GraphicalUI.getBounds()) {
                GraphicalUI.setBounds(true);
                GraphicalUI.boardJP.loadBoard(GraphicalUI.getGameEngine());
                GraphicalUI.feedback.setText("Bounds shown");
            } else {
                GraphicalUI.setBounds(false);
                GraphicalUI.boardJP.loadBoard(GraphicalUI.getGameEngine());
                GraphicalUI.feedback.setText("Bounds hidden");
            }
        }
        if (button.getText().equals("Competitive Play Mode")) {
        	if (!GraphicalUI.getProblemSettings()) {
        		JOptionPane.showMessageDialog(GraphicalUI.frame,"Please select your bounds and objectives.");
        		ProblemSettingsListener.selectionBox();
        		// TODO: Add input box to choose human/AI here
        		if (!BoundsObjectiveDialog.cancelled) {
        			GraphicalUI.enterCompetitive();
        		} else {
        			GraphicalUI.competitiveButton.setSelected(false);
        		}
        	} else {
        		// TODO: Add input box to choose human/AI here too
        		GraphicalUI.enterCompetitive();
        	}
        }
        if (button.getText().equals("Problem Creation Mode")) {
            GraphicalUI.enterCreation();
        }
    }

}
