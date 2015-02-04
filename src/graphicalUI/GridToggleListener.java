package graphicalUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;
import javax.swing.JToggleButton;

public class GridToggleListener implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        JToggleButton button = (JToggleButton) e.getSource();
        if (button.getText().equals("Show Bounds")) {
            if (!GraphicalUI.getBounds()) {
                GraphicalUI.setBounds(true);
                GraphicalUI.boardJP.repaint();
                GraphicalUI.updateMessage("Bounds shown");
            } else {
                GraphicalUI.setBounds(false);
                GraphicalUI.boardJP.repaint();
                GraphicalUI.updateMessage("Bounds hidden");
            }
        }
        if (button.getText().equals("Show Co-ordinates")) {
            if (!GraphicalUI.getRowNumbers()) {
                GraphicalUI.toggleRowNumbers();
                GraphicalUI.boardJP.repaint();
                GraphicalUI.updateMessage("Co-ordinates shown");
            } else {
                GraphicalUI.toggleRowNumbers();
                GraphicalUI.boardJP.repaint();
                GraphicalUI.updateMessage("Co-ordinates hidden");
            }
        }
        if (button.getText().equals("Competitive Play Mode")) {
        	if (!GraphicalUI.getProblemSettings()) {
        		JOptionPane.showMessageDialog(GraphicalUI.frame,"Please select your bounds and objectives.");
        		ProblemSettingsListener.selectionBox();
        		if (!BoundsObjectiveDialog.cancelled) {
            		// create dialog box to enter competitive mode after player choice
            		PlayerChooseDialog pcd = new PlayerChooseDialog(GraphicalUI.frame);
            		pcd.pack();
            		pcd.setLocationRelativeTo(GraphicalUI.frame);
            		pcd.setVisible(true);
            		if (!PlayerChooseDialog.cancelled) {
            			// Enabled AI vs AI mode
            			if (PlayerChooseDialog.AIvsAI) {
            				BoardJPanel.AIvsAI = true;
            				JOptionPane.showMessageDialog(GraphicalUI.frame,"The AI will now play against itself.\n Feel free to use the pause button.");
            				GraphicalUI.pauseButton.setEnabled(true);
            				BoardJPanel.listeners = false;
            				GraphicalUI.changeMode(true);
            				GraphicalUI.boardJP.GUIAIMove();
            			// Else AI vs human
            			} else {
            				BoardJPanel.AIvsAI = false;
            				GraphicalUI.changeMode(true);
            				GraphicalUI.pauseButton.setEnabled(false);
            			}
            		} else {
            			GraphicalUI.competitiveButton.setSelected(false);
            		}
        		}
        		
        	// When player has already set problem settings
        	} else {
        		// create dialog box to enter competitive mode after player choice
        		PlayerChooseDialog pcd = new PlayerChooseDialog(GraphicalUI.frame);
        		pcd.pack();
        		pcd.setLocationRelativeTo(GraphicalUI.frame);
        		pcd.setVisible(true);
        		// Enabled AI vs AI mode
        		if (!PlayerChooseDialog.cancelled) {
        			if (PlayerChooseDialog.AIvsAI) {
        				BoardJPanel.AIvsAI = true;
        				JOptionPane.showMessageDialog(GraphicalUI.frame,"The AI will now play against itself.\n Feel free to use the pause button.");
        				GraphicalUI.pauseButton.setEnabled(true);
        				BoardJPanel.listeners = false;
        				GraphicalUI.changeMode(true);
        				GraphicalUI.boardJP.GUIAIMove();
        			// Else AI vs human
        			} else {
        				BoardJPanel.AIvsAI = false;
        				GraphicalUI.changeMode(true);
        				GraphicalUI.pauseButton.setEnabled(false);
        			}
        		} else {
        			GraphicalUI.competitiveButton.setSelected(false);
        		}
        	}
        }
        if (button.getText().equals("Problem Creation Mode")) {
			GraphicalUI.changeMode(false);
        }
        if (button.getText().equals("Pause")) {
        	if (!BoardJPanel.pause) {
        		BoardJPanel.pause = true;
        	} else {
        		BoardJPanel.pause = false;
        	}
        }
    }

}
