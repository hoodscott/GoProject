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
                GraphicalUI.boardJP.repaint();
                GraphicalUI.updateMessage("Bounds shown");
            } else {
                GraphicalUI.setBounds(false);
                GraphicalUI.boardJP.repaint();
                GraphicalUI.updateMessage("Bounds hidden");
            }
        }
        if (button.getText().equals("Show Co-ordinates")) {
            if (!GraphicalUI.getRowNumbers()) {
                GraphicalUI.toggleRowNumbers();
                GraphicalUI.boardJP.repaint();
                GraphicalUI.updateMessage("Co-ordinates shown");
            } else {
                GraphicalUI.toggleRowNumbers();
                GraphicalUI.boardJP.repaint();
                GraphicalUI.updateMessage("Co-ordinates hidden");
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
        if (button.getText().equals("Pause")) {
        	// TODO: Implement pause during AI vs. AI
        }
    }

}
