package graphicalUI;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;

public class DebugMenuListener implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("Toggle Row Numbers")) {
			GraphicalUI.toggleRowNumbers();
			// repaint immediately
			GraphicalUI.boardJP.repaint();
		} else if (e.getActionCommand().equals("Show Log")) {
			JFrame logFrame = new JFrame("Log");
			logFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			logFrame.setBounds(150, 150, 300, 600);
			StringBuilder sb = new StringBuilder();
			ArrayList<String> messages = GraphicalUI.getMessages();
			for (int i = 2; i < messages.size(); i++) {
				sb.append(messages.get(i));
				sb.append("\n");
			}
			logFrame.getContentPane().add(new JTextArea(sb.toString()),
					BorderLayout.CENTER);
			logFrame.pack();
			logFrame.setVisible(true);
			GraphicalUI.updateMessage("Displayed Log");
		} else if (e.getActionCommand().equals("Choose Heuristics")) {
			HeuristicChooseDialog hcd = new HeuristicChooseDialog(
					GraphicalUI.frame);
			hcd.pack();
			hcd.setLocationRelativeTo(GraphicalUI.frame);
			hcd.setVisible(true);
			if (!HeuristicChooseDialog.cancelled) {
				// TODO: Set heuristic options
				try {
					int[] heuristics = hcd.getSelectedHeuristics();
					for (int i: heuristics) System.out.println(i);
				} catch (Exception NullPointer) {
					//
				}
			} else {
				JOptionPane.showMessageDialog(GraphicalUI.frame,
						"No heuristics were chosen.");
			}
		}
	}
}
