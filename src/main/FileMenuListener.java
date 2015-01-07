package main;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFileChooser;

/**
 * Listener classes for menus.
 */
public class FileMenuListener implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
		Component pane = null;
		// load specified board
		if (e.getActionCommand().equals("Load Problem")) {
			JFileChooser loadBoard = new JFileChooser();
			int command = loadBoard.showOpenDialog(pane);
			if (command == JFileChooser.APPROVE_OPTION) {
				try {
					GraphicalUI.setGameEngine(FileIO.readBoard(loadBoard
							.getSelectedFile().getAbsolutePath()));
					GraphicalUI.boardJP.loadBoard(GraphicalUI.getGameEngine());
					GraphicalUI.feedback.setText("Board loaded");
					Objective localObjective = GraphicalUI.getGameEngine().getObjective();
					if (localObjective==null){
						GraphicalUI.objective.setText("Not specified");
					}
					else {
						GraphicalUI.objective.setText(localObjective.toString());
					}
					
				} catch (BoardFormatException bfe) {
					GraphicalUI.feedback.setText(bfe.getMsg());
				}
			} else {
				GraphicalUI.feedback
						.setText("User cancelled load board selection");
			}

			// save current board to default location
		} else if (e.getActionCommand().equals("Save Problem")) {
			try {
				FileIO.writeBoard(GraphicalUI.getGameEngine());
				GraphicalUI.feedback.setText("Problem saved");
			} catch (BoardFormatException bfe) {
				GraphicalUI.feedback.setText(bfe.getMsg());
			}

			// save current board to specified location
		} else if (e.getActionCommand().equals("Save Problem As...")) {
			JFileChooser saveBoard = new JFileChooser();
			int command = saveBoard.showSaveDialog(pane);
			if (command == JFileChooser.APPROVE_OPTION) {
				try {
					FileIO.writeBoard(GraphicalUI.getGameEngine(), saveBoard
							.getSelectedFile().getAbsolutePath());
					GraphicalUI.feedback.setText("Problem saved");
				} catch (BoardFormatException bfe) {
					GraphicalUI.feedback.setText(bfe.getMsg());
				}
			} else {
				GraphicalUI.feedback
						.setText("User cancelled save board selection");
			}
		} // exit program
		else {
			System.exit(0);
		}
	}

}