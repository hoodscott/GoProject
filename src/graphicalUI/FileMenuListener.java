package graphicalUI;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFileChooser;

import main.BoardFormatException;
import main.FileIO;
import ai.Objective;

import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Listener classes for the File menu. Reacts to user selection of options e.g.
 * load problem, save problem.
 */
public class FileMenuListener implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
		Component pane = null;

		// Retrieve path of saved problem boards and convert to match OS
		String defaultDir = System.getProperty("user.dir")
				+ "\\saveData\\boards";
		defaultDir = FileIO.pathOS(defaultDir);
		if (Files.notExists(Paths.get(defaultDir))) {
			defaultDir = System.getProperty("user.dir")
					+ "\\src\\saveData\\boards";
			defaultDir = FileIO.pathOS(defaultDir);
		}

		if (e.getActionCommand().equals("Load Problem")) {
			// Load specified board
			JFileChooser loadBoard = new JFileChooser(defaultDir);
			int command = loadBoard.showOpenDialog(pane);
			if (command == JFileChooser.APPROVE_OPTION) {
				try {
					// Set save name on title bar
					GraphicalUI.saveName = loadBoard.getSelectedFile()
							.getName();
					GraphicalUI.saveLocation = loadBoard.getSelectedFile()
							.getAbsolutePath();
					System.out.println(GraphicalUI.saveLocation);
					GraphicalUI.setFrameTitle(GraphicalUI.saveName);
					// Load board onto BoardJPanel
					GraphicalUI.setGameEngine(FileIO.readBoard(loadBoard
							.getSelectedFile().getAbsolutePath()));
					GraphicalUI.boardJP.loadBoard(GraphicalUI.getGameEngine());
					Objective localObjective = GraphicalUI.getGameEngine()
							.getObjective();
					if (GraphicalUI.boardJP.getGE().hasBounds()) {
						BoardJPanel.boundsCheck = true;
					}
					// Set initial player
					try {
						BoardJPanel.setPlayerInt(GraphicalUI.getGameEngine()
								.getObjective().getStartingColour());
					} catch (Exception obj) {
						System.out
								.println("No objective specified in opened file.");
					}
					GraphicalUI.player.setText(BoardJPanel.getPlayer()
							+ " to move");
					// Check for set bounds
					if (!BoardJPanel.boundsCheck) {
						GraphicalUI.setProblemSettings(false);
					} else {
						GraphicalUI.setProblemSettings(true);
						GraphicalUI.updateBoundsButton(true);
						GraphicalUI.changeMode(false);
					}
					// Check for set objective
					if (localObjective == null) {
						GraphicalUI.objective.setText("Not specified");
						GraphicalUI.setProblemSettings(false);
					} else {
						GraphicalUI.objective
								.setText(localObjective.toString());
					}
					GraphicalUI.turnOffBounds();
					GraphicalUI.updateMessage("Board loaded");

				} catch (BoardFormatException bfe) {
					GraphicalUI.updateMessage(bfe.getMsg());
				}
			} else {
				GraphicalUI
						.updateMessage("User cancelled load board selection");
			}

		} else if (e.getActionCommand().equals("Save Problem")) {
			// Save current board to location if already saved or prompt user
			if (GraphicalUI.saveName.equals("Untitled")) {
				JFileChooser saveBoard = new JFileChooser(defaultDir);
				int command = saveBoard.showSaveDialog(pane);
				if (command == JFileChooser.APPROVE_OPTION) {
					try {
						// Save board
						FileIO.writeBoard(GraphicalUI.getGameEngine(),
								saveBoard.getSelectedFile().getAbsolutePath());
						GraphicalUI.updateMessage("Problem saved");
						// Update title of window to save name
						GraphicalUI.saveName = saveBoard.getSelectedFile()
								.getName();
						GraphicalUI.setFrameTitle(GraphicalUI.saveName);
					} catch (BoardFormatException bfe) {
						GraphicalUI.updateMessage(bfe.getMsg());
					}
				} else {
					GraphicalUI
							.updateMessage("User cancelled save board selection");
				}
			} else {
				try {
					// Write file to chosen location
					FileIO.writeBoard(GraphicalUI.getGameEngine(),
							GraphicalUI.saveLocation);
					GraphicalUI.updateMessage("Problem saved");
				} catch (BoardFormatException bfe) {
					GraphicalUI.updateMessage(bfe.getMsg());
				}
			}

		} else if (e.getActionCommand().equals("Save Problem As...")) {
			// Save current board to specified location
			JFileChooser saveBoard = new JFileChooser(defaultDir);
			int command = saveBoard.showSaveDialog(pane);
			if (command == JFileChooser.APPROVE_OPTION) {
				try {
					// Write board file to location
					FileIO.writeBoard(GraphicalUI.getGameEngine(), saveBoard
							.getSelectedFile().getAbsolutePath());
					GraphicalUI.updateMessage("Problem saved");
					// Update frame title to save name
					GraphicalUI.saveName = saveBoard.getSelectedFile()
							.getName();
					GraphicalUI.setFrameTitle(GraphicalUI.saveName);
				} catch (BoardFormatException bfe) {
					GraphicalUI.updateMessage(bfe.getMsg());
				}
			} else {
				GraphicalUI
						.updateMessage("User cancelled save board selection");
			}
		} 
		else {
			// Exit option - exit program
			Runtime.getRuntime().halt(0);
		}
	}

}
