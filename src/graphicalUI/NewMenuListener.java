package graphicalUI;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import main.Board;
import main.GameEngine;

/*
 * Listener used by New Problem sub-menu within the File menu 
 * for the loading of new, empty boards.
 */
public class NewMenuListener implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getActionCommand().equals("Default (9x9)")) {
            // Load default board
            GraphicalUI.setGameEngine(new GameEngine(new Board()));
        } else {
            // Load specified board from given sizes
            Object[] sizes = {"9", "13", "19"};
            Component frame = null;
            String s = (String) JOptionPane.showInputDialog(frame,
                    "Select board size...", "New Problem",
                    JOptionPane.PLAIN_MESSAGE, null, sizes, "9");
            if (s != null) {
                // Set new board to specified size
                int length = Integer.parseInt(s);
                GraphicalUI.setGameEngine(new GameEngine(new Board(length, length)));
            }
        }
        // Set GUI settings for an empty problem
        GraphicalUI.turnOffBounds();
        BoardJPanel.setPlayer("black");
        GraphicalUI.boardJP.loadBoard(GraphicalUI.getGameEngine());
        GraphicalUI.updateMessage("New board created");
        GraphicalUI.objective.setText("No set objective");
        GraphicalUI.setProblemSettings(false);
        GraphicalUI.updateBoundsButton(false);
        GraphicalUI.saveName = "Untitled";
        GraphicalUI.setFrameTitle(GraphicalUI.saveName);
    }
}
