package main;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

public class ObjectiveMenuListener implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
		// allow user to set objective
		if (e.getActionCommand().equals("Set Objective")) {
			Component frame = null;
			String objective = (String) JOptionPane
					.showInputDialog(
							frame,
							"Specify Objective (Action, Colour, Stone Position):",
							"Set Bounds", JOptionPane.PLAIN_MESSAGE, null,
							null, "");
			if (objective != null) {
				// TODO: Check for valid input
				String[] objParts = objective.split(" ");
				Coordinate pos = new Coordinate(
						Integer.parseInt(objParts[2]),
						Integer.parseInt(objParts[3]));
				Objective obj = new Objective(objParts[0],
						Integer.parseInt(objParts[1]), pos);
				BoardJPanel.setObjective(obj);
				GraphicalUI.feedback.setText("Objective updated");
			} else {
				GraphicalUI.feedback.setText("Invalid objective supplied");
			}
		} else {
			// remove objective from board and label
			BoardJPanel.setObjective(null);
			GraphicalUI.feedback.setText("Objective removed");
			GraphicalUI.objective.setText("None Specified");
		}
	}

}