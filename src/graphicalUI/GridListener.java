package graphicalUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

//action listener for buttons in grid on bottom left
public class GridListener implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton button = (JButton) e.getSource();
        if (button.getText().equals("Undo")) {
            if (GraphicalUI.getGameEngine().undoLastMove()) {
                if (!GraphicalUI.getCompetitive()) {

                    GraphicalUI.player.setText(BoardJPanel.getPlayer() + " to move");
                    GraphicalUI.boardJP.loadBoard(GraphicalUI.getGameEngine());
                    GraphicalUI.feedback.setText("Undone move");
                } else {
                    GraphicalUI.boardJP.changePlayer();
                    GraphicalUI.player.setText(BoardJPanel.getPlayer() + " to move");
                    GraphicalUI.boardJP.loadBoard(GraphicalUI.getGameEngine());
                    GraphicalUI.feedback.setText("Undone move");
                }
            } else {
                GraphicalUI.feedback.setText("No more moves to undo");
            }
        }
        if (button.getText().equals("Pass")) {
            // TODO create pass function in gameEngine
            if (true) {
                if (GraphicalUI.getCompetitive()) {
                    GraphicalUI.feedback.setText(BoardJPanel.getPlayer() + " passes");
                    BoardJPanel.changePlayer();
                    BoardJPanel.GUIAIMove();
                    GraphicalUI.player.setText(BoardJPanel.getPlayer() + " to move");
                    GraphicalUI.boardJP.loadBoard(GraphicalUI.getGameEngine());
                    System.out.println(BoardJPanel.getPlayer());
                } else {
                    GraphicalUI.feedback.setText(BoardJPanel.getPlayer() + " passes");
                    GraphicalUI.boardJP.changePlayer();
                    GraphicalUI.player.setText(BoardJPanel.getPlayer() + " to move");
                    GraphicalUI.boardJP.loadBoard(GraphicalUI.getGameEngine());
                    System.out.println(BoardJPanel.getPlayer());
                }

            } else {
                GraphicalUI.feedback.setText(BoardJPanel.getPlayer()
                        + " could not pass");
            }
        }
        if (button.getText().equals("Reset")) {
            if (GraphicalUI.getGameEngine().restartBoard()) {
                BoardJPanel.listeners = true;
                BoardJPanel.setPlayer("black");
                GraphicalUI.player.setText(BoardJPanel.getPlayer() + " to move");
                GraphicalUI.boardJP.loadBoard(GraphicalUI.getGameEngine());
                GraphicalUI.feedback.setText("Board has been reset");
            } else {
                GraphicalUI.feedback.setText("Nothing to reset");
            }
        }
    }
}
