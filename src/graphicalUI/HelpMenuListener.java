package graphicalUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import main.FileIO;

public class HelpMenuListener implements ActionListener {

	// TODO implement helper actions
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO fix this
		if (e.getActionCommand().equals("Shout for Help")) {
			String filePath = FileIO.pathOS(FileIO.RELATIVEPATH + "\\info\\GUIShortcuts");
			ArrayList<String> keyboardFile = FileIO
					.readFile(filePath);
			// TODO display shortcuts in a new popup to the user
			for (String t : keyboardFile) {
				System.out.println(t);
			}
		}
	}
}
