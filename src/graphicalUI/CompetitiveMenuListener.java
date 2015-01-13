package graphicalUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CompetitiveMenuListener implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
		// allow user to use competitive play mode options 
		// swap player colours of AI/human
		if (e.getActionCommand().equals("Swap Player Colours")) {
			BoardJPanel.changePlayer();
		// force AI to move first
		} else {
			BoardJPanel.changePlayer();
			BoardJPanel.GUIAIMove();
		}
	}

}
