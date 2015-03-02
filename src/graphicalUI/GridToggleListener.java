package graphicalUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;
import javax.swing.JToggleButton;

public class GridToggleListener implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
		JToggleButton button = (JToggleButton) e.getSource();
		if (button.getText().equals("Show Bounds") || button.getText().equals("Hide Bounds")) {
			if (BoardJPanel.boundsSelectionMode) {
				if (GraphicalUI.boardJP.getGE().hasBounds()) {
					BoardJPanel.boundsSelectionMode = false;
			        BoardJPanel.boundsCheck = true;
					GraphicalUI.updateMessage("Bounds updated and shown");
					GraphicalUI.setBounds(true);
					GraphicalUI.boardJP.repaint();
					button.setSelected(true);
					button.setText("Hide Bounds");
					
		            // Tell GUI that user has set bounds/objectives
		            GraphicalUI.setProblemSettings(true);
				} else {
					BoardJPanel.boundsSelectionMode = false;
			        BoardJPanel.boundsCheck = false;
					GraphicalUI.boardJP.repaint();
					GraphicalUI.setBounds(false);
					button.setSelected(false);
					GraphicalUI.updateBoundsButton(false);
		            JOptionPane
					.showMessageDialog(
							GraphicalUI.frame,
							"No bounds were selected. \n");
				}
			} else if (!BoardJPanel.boundsSelectionMode && !GraphicalUI.getBounds()) {
				GraphicalUI.setBounds(true);
				GraphicalUI.boardJP.repaint();
				button.setSelected(true);
				GraphicalUI.updateMessage("Bounds shown");
				button.setText("Hide Bounds");
			} else {
				GraphicalUI.setBounds(false);
				GraphicalUI.boardJP.repaint();
				button.setSelected(false);
				GraphicalUI.updateMessage("Bounds hidden");
				button.setText("Show Bounds");
			}
		}
		if (button.getText().equals("Show Coordinates") || button.getText().equals("Hide Coordinates")) {
			if (!GraphicalUI.getRowNumbers()) {
				GraphicalUI.toggleRowNumbers();
				GraphicalUI.boardJP.repaint();
				GraphicalUI.updateMessage("Coordinates shown");
				button.setText("Hide Coordinates");
			} else {
				GraphicalUI.toggleRowNumbers();
				GraphicalUI.boardJP.repaint();
				GraphicalUI.updateMessage("Coordinates hidden");
				button.setText("Show Coordinates");
			}
		}
		if (button.getText().equals("Competitive Play Mode")) {
			if (!GraphicalUI.getProblemSettings()) {
				JOptionPane.showMessageDialog(GraphicalUI.frame,
						"Please select your bounds and objectives first.");
				ProblemSettingsListener.selectionBox();
				GraphicalUI.competitiveButton.setSelected(false);
			// When player has already set problem settings
			} else {
				// create dialog box to enter competitive mode after player
				// choice
				PlayerChooseDialog pcd = new PlayerChooseDialog(
						GraphicalUI.frame);
				pcd.pack();
				pcd.setLocationRelativeTo(GraphicalUI.frame);
				pcd.setVisible(true);
				if (!PlayerChooseDialog.cancelled) {
					setUp();
				} else {
					GraphicalUI.competitiveButton.setSelected(false);
				}
			}
		}
		if (button.getText().equals("Problem Creation Mode")) {
			GraphicalUI.changeMode(false);
		}
	}

	public void setUp() {
		// Enabled AI vs AI mode
		if (PlayerChooseDialog.AIvsAI) {
			BoardJPanel.boundsSelectionMode = false;
	        BoardJPanel.boundsCheck = true;
			BoardJPanel.AIvsAI = true;
			BoardJPanel.humanVShuman = false;
			JOptionPane
					.showMessageDialog(
							GraphicalUI.frame,
							"Use the 'Next AI Move Button' (ALT + I keys)\n to allow the AI to play against itself. \n");
			GraphicalUI.aiMoveButton.setEnabled(true);
			BoardJPanel.listeners = false;
			GraphicalUI.changeMode(true);
		// Or human vs human
		} else if (PlayerChooseDialog.humanVShuman) {
			BoardJPanel.boundsSelectionMode = false;
	        BoardJPanel.boundsCheck = true;
			BoardJPanel.AIvsAI = false;
			GraphicalUI.changeMode(true);
			GraphicalUI.aiMoveButton.setEnabled(false);
			BoardJPanel.listeners = true;
			BoardJPanel.humanVShuman = true;
		// Else AI vs human
		} else {
			BoardJPanel.boundsSelectionMode = false;
	        BoardJPanel.boundsCheck = true;
			BoardJPanel.AIvsAI = false;
			BoardJPanel.humanVShuman = false;
			GraphicalUI.changeMode(true);
			GraphicalUI.aiMoveButton.setEnabled(false);
			BoardJPanel.listeners = true;
		}
	}

}
