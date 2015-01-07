package main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HelpMenuListener implements ActionListener {

	// TODO implement helper actions
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("Shout for Help")) {
			// show user help
		}
	}
}