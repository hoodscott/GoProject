package main;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

public class BoundsMenuListener implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
		// allow user to set bounds
		if (e.getActionCommand().equals("Set Bounds")) {
			Component frame = null;
			String bounds = (String) JOptionPane.showInputDialog(frame,
					"Specify Bounds Seperated By Spaces:", "Set Bounds",
					JOptionPane.PLAIN_MESSAGE, null, null, "");
			if (bounds != null && bounds.length() == 7) {
				// create array of specified bounds
				int[] selectBounds = { 0, 0, 0, 0 };
				int j = 0;
				for (int i = 0; i < bounds.length(); i++) {
					if (Character.isDigit(bounds.charAt(i))) {
						selectBounds[j] = Integer.parseInt(String
								.valueOf(bounds.charAt(i)));
						j++;
					}
				}
				BoardJPanel.setBounds(selectBounds);
				GraphicalUI.feedback.setText("Bounds updated");
			} else {
				GraphicalUI.feedback.setText("Invalid bounds supplied");
			}
		} else {
			// remove bounds - (make bounds size of board)
			int lines = BoardJPanel.getLines();
			int[] noBounds = { 0, 0, lines, lines };
			BoardJPanel.setBounds(noBounds);
			GraphicalUI.feedback.setText("Bounds removed");
		}
	}

}