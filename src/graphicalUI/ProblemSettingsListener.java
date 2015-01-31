package graphicalUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ProblemSettingsListener implements ActionListener {
	@Override
	public void actionPerformed(ActionEvent e) {
		// Display input box for objective and bounds
		BoundsObjectiveDialog bod = new BoundsObjectiveDialog(GraphicalUI.frame);
		bod.pack();
		bod.setLocationRelativeTo(GraphicalUI.frame);
		bod.setVisible(true);
	}
}
