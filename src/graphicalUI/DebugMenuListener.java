package graphicalUI;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;

/**
 * Listener used by Debug menu.
 */
public class DebugMenuListener implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getActionCommand().equals("Show Log")) {
			// Display log of program in new window
			JFrame logFrame = new JFrame("Log");
			logFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			logFrame.setBounds(150, 150, 300, 600);
			StringBuilder sb = new StringBuilder();
			ArrayList<String> messages = GraphicalUI.getMessages();

			// Append log messages together
			for (int i = 2; i < messages.size(); i++) {
				sb.append(messages.get(i));
				sb.append("\n");
			}
			logFrame.getContentPane().add(new JTextArea(sb.toString()),
					BorderLayout.CENTER);
			logFrame.pack();
			logFrame.setVisible(true);
			// hide on close
			logFrame.setDefaultCloseOperation(2);
			GraphicalUI.updateMessage("Displayed Log");
		}
	}
}
