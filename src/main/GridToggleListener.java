package main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
			GraphicalUI.creationButton.setSelected(false);
			GraphicalUI.setCreation(false);
			if (!GraphicalUI.getCompetitive()) {
				GraphicalUI.setCompetitive(true);
				GraphicalUI.feedback
						.setText("\"Competitive Play Mode\" selected");
			} else {
				GraphicalUI.setCompetitive(false);
				GraphicalUI.feedback
						.setText("\"Competitive Play Mode\" deselected");
			}
		}
		if (button.getText().equals("Problem Creation Mode")) {
			GraphicalUI.competitiveButton.setSelected(false);
			GraphicalUI.setCompetitive(false);
			if (!GraphicalUI.getCreation()) {
				GraphicalUI.setCreation(true);
				GraphicalUI.feedback
						.setText("\"Problem Creation Mode\" selected");
			} else {
				GraphicalUI.setCreation(false);
				GraphicalUI.feedback
						.setText("\"Problem Creation Mode\" deselected");
			}
		}
	}

}