package graphicalUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

/**
 * Listener for buttons within GUI's grid panel.
 */
public class GridListener implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {

		JButton button = (JButton) e.getSource();

		if (button.getText().equals("Undo")) {
			// Undo last move upon board
			if (GraphicalUI.getGameEngine().undoLastMove()) {
				if (!GraphicalUI.getCompetitive()) {
					// Don't change player in competitive mode
					GraphicalUI.player.setText(BoardJPanel.getPlayer()
							+ " to move");
					GraphicalUI.boardJP.loadBoard(GraphicalUI.getGameEngine());
					GraphicalUI.updateMessage("Undone move");
				} else {
					GraphicalUI.boardJP.changePlayer();
					GraphicalUI.player.setText(BoardJPanel.getPlayer()
							+ " to move");
					GraphicalUI.boardJP.loadBoard(GraphicalUI.getGameEngine());
					GraphicalUI.updateMessage("Undone move");
				}
			} else {
				GraphicalUI.updateMessage("No more moves to undo");
			}
		}
		if (button.getText().equals("Pass")) {
			// Pass current move
			if (GraphicalUI.getCompetitive() && !BoardJPanel.humanVShuman) {
				GraphicalUI.updateMessage(BoardJPanel.getPlayer() + " passes");
				GraphicalUI.boardJP.changePlayer();
				GraphicalUI.boardJP.GUIAIMove();
				GraphicalUI.player
						.setText(BoardJPanel.getPlayer() + " to move");
				GraphicalUI.boardJP.loadBoard(GraphicalUI.getGameEngine());
				System.out.println(BoardJPanel.getPlayer());
			} else {
				GraphicalUI.updateMessage(BoardJPanel.getPlayer() + " passes");
				GraphicalUI.boardJP.changePlayer();
				GraphicalUI.player
						.setText(BoardJPanel.getPlayer() + " to move");
				GraphicalUI.boardJP.loadBoard(GraphicalUI.getGameEngine());
				System.out.println(BoardJPanel.getPlayer());
			}
		}
		if (button.getText().equals("Reset")) {
			// Reset to originally loaded board
			if (GraphicalUI.getGameEngine().restartBoard()) {
				BoardJPanel.listeners = true;
				BoardJPanel.setPlayerInt(1);
				GraphicalUI.player
						.setText(BoardJPanel.getPlayer() + " to move");
				GraphicalUI.boardJP.loadBoard(GraphicalUI.getGameEngine());
				GraphicalUI.updateMessage("Board has been reset");
			} else {
				GraphicalUI.updateMessage("Nothing to reset");
			}
		}
		if (button.getText().equals("Next AI Move")) {
			// Let AI play against itself for AI vs. AI
			GraphicalUI.boardJP.GUIAIMove();
			GraphicalUI.player.setText(BoardJPanel.getPlayer() + " to move");
		}
	}
}
