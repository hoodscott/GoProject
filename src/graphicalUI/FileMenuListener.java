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
 * Listener classes for menus.
 */
public class FileMenuListener implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        Component pane = null;

        String defaultDir = System.getProperty("user.dir")
                + "\\saveData\\boards";

        // convert path to match system
        defaultDir = FileIO.pathOS(defaultDir);

        // Minor hotfix for Nik's shoddy buildpath
        if (Files.notExists(Paths.get(defaultDir))) {
            defaultDir = System.getProperty("user.dir")
                    + "\\src\\saveData\\boards";
            defaultDir = FileIO.pathOS(defaultDir);
        }
        System.out.println(defaultDir);

        // load specified board
        if (e.getActionCommand().equals("Load Problem")) {

            JFileChooser loadBoard = new JFileChooser(defaultDir);
            int command = loadBoard.showOpenDialog(pane);
            if (command == JFileChooser.APPROVE_OPTION) {
                try {
                    // set save name on title bar
                    GraphicalUI.saveName = loadBoard.getSelectedFile()
                            .getName();
                    GraphicalUI.saveLocation = loadBoard.getSelectedFile().getAbsolutePath();
                    System.out.println(GraphicalUI.saveLocation);
                    GraphicalUI.setFrameTitle(GraphicalUI.saveName);
                    // load board
                    GraphicalUI.setGameEngine(FileIO.readBoard(loadBoard
                            .getSelectedFile().getAbsolutePath()));
                    GraphicalUI.boardJP.loadBoard(GraphicalUI.getGameEngine());
                    Objective localObjective = GraphicalUI.getGameEngine()
                            .getObjective();
                    if (GraphicalUI.boardJP.getGE().hasBounds()) {
                        BoardJPanel.boundsCheck = true;
                    }
                    // set initial player
                    try {
                        BoardJPanel.setPlayerInt(GraphicalUI.getGameEngine()
                                .getObjective().getStartingColour());
                    } catch (Exception obj) {
                        System.out.println("No objective specified in opened file.");
                    }
                    GraphicalUI.player.setText(BoardJPanel.getPlayer()
                            + " to move");
                    // TODO maybe check the objective and bounds are in the
                    // correct form
                    // this assumes the board is saved in the correct format
                    if (!BoardJPanel.boundsCheck) {
                        GraphicalUI.setProblemSettings(false);
                    } else {
                        GraphicalUI.setProblemSettings(true);
                        GraphicalUI.updateBoundsButton(true);
                        GraphicalUI.changeMode(false);
                    }
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

            // save current board to default location or ask user for one
        } else if (e.getActionCommand().equals("Save Problem")) {
            if (GraphicalUI.saveName.equals("Untitled")) {
                JFileChooser saveBoard = new JFileChooser(defaultDir);
                int command = saveBoard.showSaveDialog(pane);
                if (command == JFileChooser.APPROVE_OPTION) {
                    try {
                        // save board
                        FileIO.writeBoard(GraphicalUI.getGameEngine(),
                                saveBoard.getSelectedFile().getAbsolutePath());
                        GraphicalUI.updateMessage("Problem saved");
                        // update title save name
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
                    FileIO.writeBoard(GraphicalUI.getGameEngine(), GraphicalUI.saveLocation);
                    GraphicalUI.updateMessage("Problem saved");
                } catch (BoardFormatException bfe) {
                    GraphicalUI.updateMessage(bfe.getMsg());
                }
            }

            // save current board to specified location
        } else if (e.getActionCommand().equals("Save Problem As...")) {
            JFileChooser saveBoard = new JFileChooser(defaultDir);
            int command = saveBoard.showSaveDialog(pane);
            if (command == JFileChooser.APPROVE_OPTION) {
                try {
                    // save board
                    FileIO.writeBoard(GraphicalUI.getGameEngine(), saveBoard
                            .getSelectedFile().getAbsolutePath());
                    GraphicalUI.updateMessage("Problem saved");
                    // update title save name
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
        } // exit program
        else {
            Runtime.getRuntime().halt(0);
        }
    }

}
