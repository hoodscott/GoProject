package graphicalUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

public class CreationMenuListener implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        if ((e.getActionCommand().equals("Use Black Stones"))) {
            // set down only black stones
            BoardJPanel.setPlayer("black");
            GraphicalUI.setMixedStones(false);
            GraphicalUI.setDeleteStones(false);
            GraphicalUI.updateMessage("Black stones selected");
        } else if ((e.getActionCommand().equals("Use White Stones"))) {
            // set down only white stones
            BoardJPanel.setPlayer("white");
            GraphicalUI.setMixedStones(false);
            GraphicalUI.setDeleteStones(false);
            GraphicalUI.updateMessage("White stones selected");
        } else if ((e.getActionCommand().equals("Use Mixed Stones"))) {
            // use a mixture of stones
            GraphicalUI.setMixedStones(true);
            GraphicalUI.setDeleteStones(false);
            GraphicalUI.updateMessage("Both stones selected");
        } else if ((e.getActionCommand().equals("Delete Stones")) || (e.getActionCommand().equals("Deselect Delete Stones"))) {
            // remove stones user clicks on
            if (GraphicalUI.getDeleteStones()) {
            	JMenuItem deleteStones = (JMenuItem) e.getSource();
            	deleteStones.setText("Delete Stones");
                GraphicalUI.setMixedStones(true);
                GraphicalUI.setDeleteStones(false);
                GraphicalUI.updateMessage("Delete stones deselected");
            } else {
            	JMenuItem deleteStones = (JMenuItem) e.getSource();
            	deleteStones.setText("Deselect Delete Stones");
                GraphicalUI.setMixedStones(false);
                GraphicalUI.setDeleteStones(true);
                GraphicalUI.updateMessage("Delete stones selected");
            }
        }
    }
}
