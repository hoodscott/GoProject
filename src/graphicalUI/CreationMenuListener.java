package graphicalUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
        } else if ((e.getActionCommand().equals("Use Both Stones"))) {
            // use a mixture of stones
            GraphicalUI.setMixedStones(true);
            GraphicalUI.setDeleteStones(false);
            GraphicalUI.updateMessage("Both stones selected");
        } else if ((e.getActionCommand().equals("Delete Stones"))) {
            // remove stones user clicks on
            if (GraphicalUI.getDeleteStones()) {
                GraphicalUI.setMixedStones(true);
                GraphicalUI.setDeleteStones(false);
                GraphicalUI.updateMessage("Delete stones deselected");
            } else {
                GraphicalUI.setMixedStones(false);
                GraphicalUI.setDeleteStones(true);
                GraphicalUI.updateMessage("Delete stones selected");
            }
        }
    }
}
