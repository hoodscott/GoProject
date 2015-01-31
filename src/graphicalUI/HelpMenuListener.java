package graphicalUI;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;

import main.FileIO;

public class HelpMenuListener implements ActionListener {

	// TODO implement helper actions
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("Show Keyboard Shortcuts")) {
			String filePath = FileIO.pathOS(FileIO.RELATIVEPATH + "\\info\\GUIShortcuts");
			ArrayList<String> keyboardFile = FileIO
					.readFile(filePath);
			// TODO display shortcuts in a new popup to the user
			JFrame helpFrame = new JFrame("Keyboard Shortcuts");
			helpFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			helpFrame.setBounds(150, 150, 300, 600);
			StringBuilder sb = new StringBuilder();
			for (String t : keyboardFile) {
				sb.append(t);
				sb.append("\n");
			}
			helpFrame.getContentPane().add(new JTextArea(sb.toString()), BorderLayout.CENTER);
			//helpFrame.pack();
			helpFrame.setVisible(true);
			
		}
	}
}
