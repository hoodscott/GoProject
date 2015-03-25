package graphicalUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JMenuItem;

/**
 * Listener used by Problem Creation menu.
 * Reacts to user selection of options.
 */
public class CreationMenuListener implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {

		if ((e.getActionCommand().equals("Use Black Stones"))) {
			// Set down only black stones
			BoardJPanel.setPlayer("black");
			GraphicalUI.setMixedStones(false);
			GraphicalUI.setDeleteStones(false);
			GraphicalUI.updateMessage("Black stones selected");
		} else if ((e.getActionCommand().equals("Use White Stones"))) {
			// Set down only white stones
			BoardJPanel.setPlayer("white");
			GraphicalUI.setMixedStones(false);
			GraphicalUI.setDeleteStones(false);
			GraphicalUI.updateMessage("White stones selected");
		} else if ((e.getActionCommand().equals("Use Mixed Stones"))) {
			// Use a mixture of stones
			GraphicalUI.setMixedStones(true);
			GraphicalUI.setDeleteStones(false);
			GraphicalUI.updateMessage("Both stones selected");
		} else if ((e.getActionCommand().equals("Delete Stones"))
				|| (e.getActionCommand().equals("Deselect Delete Stones"))) {
			if (GraphicalUI.getDeleteStones()) {
				// Turn off stone deletion
				JMenuItem deleteStones = (JMenuItem) e.getSource();
				deleteStones.setText("Delete Stones");
				GraphicalUI.setMixedStones(true);
				GraphicalUI.setDeleteStones(false);
				GraphicalUI.updateMessage("Delete stones deselected");
			} else {
				// Remove stones user clicks on
				JMenuItem deleteStones = (JMenuItem) e.getSource();
				deleteStones.setText("Deselect Delete Stones");
				GraphicalUI.setMixedStones(false);
				GraphicalUI.setDeleteStones(true);
				GraphicalUI.updateMessage("Delete stones selected");
			}
		}
	}
}
