package graphicalUI;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import main.Board;
import main.GameEngine;

public class NewMenuListener implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        // load default board
        if (e.getActionCommand().equals("Default (9x9)")) {
            // default 9x9
            GraphicalUI.setGameEngine(new GameEngine(new Board()));
        } else {
            // load specified board
            Object[] sizes = {"9", "13", "19"};
            Component frame = null;
            String s = (String) JOptionPane.showInputDialog(frame,
                    "Select board size...", "New Problem",
                    JOptionPane.PLAIN_MESSAGE, null, sizes, "9");
            if (s != null) {
                // set new board to specified size
                int length = Integer.parseInt(s);
                GraphicalUI.setGameEngine(new GameEngine(new Board(length, length)));
                // set player to back then draw the new board
               
            }
        }
        // set gui settings
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
