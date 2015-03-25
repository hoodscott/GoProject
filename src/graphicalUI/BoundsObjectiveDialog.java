package graphicalUI;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JDialog;
import javax.swing.JPanel;

import main.Coordinate;
import ai.Objective;

import java.awt.*;
import java.awt.event.*;

/**
 * Input box displayed for user to enable selection of objective through drop down boxes.
 * Immediately followed after is bounds selection mode.
 */
public class BoundsObjectiveDialog extends JDialog implements ActionListener {

	// Swing variables
	private static final long serialVersionUID = 1L;
	private JPanel objectiveChoice, finishButtons;
	@SuppressWarnings("rawtypes")
	private JComboBox actionsBox, coloursBox, coord1, coord2;
	private JButton OKButton, cancelButton;
	public static boolean cancelled;

	// Constructor using GUI frame to show objective box
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public BoundsObjectiveDialog(Frame GUIFrame) {
		super(GUIFrame, true);

		this.setLayout(new GridLayout(0, 1));

		setTitle("Problem Settings");

		// Components for objective selection
		String[] actions = { "Kill", "Defend" };
		actionsBox = new JComboBox(actions);
		String[] colours = { "Black", "White" };
		coloursBox = new JComboBox(colours);

		// Array of suitable co-ordinate positions for selection
		String[] positions = new String[BoardJPanel.getLines()];
		for (int i = 0; i < BoardJPanel.getLines(); i++) {
			positions[i] = i + "";
		}

		// Combo boxes
		coord1 = new JComboBox(positions);
		coord2 = new JComboBox(positions);

		// Buttons for user selection
		OKButton = new JButton("OK");
		cancelButton = new JButton("Cancel");

		// Load values of the current objective if it exists
		Objective obj = GraphicalUI.getGameEngine().getObjective();
		if (obj != null) {
			// Set player colour
			if (obj.getStartingColour() == 1) {
				coloursBox.setSelectedIndex(0);
			} else if (obj.getStartingColour() == 2) {
				coloursBox.setSelectedIndex(1);
			}
			// Set action
			if (obj.getOriginalAction().equals("kill")) {
				actionsBox.setSelectedIndex(0);
			} else if (obj.getOriginalAction().equals("defend")) {
				actionsBox.setSelectedIndex(1);
			}
			System.out.println(obj.getOriginalAction());
			// Set coordinates
			coord1.setSelectedIndex(obj.getPosition().x);
			coord2.setSelectedIndex(obj.getPosition().y);
		}

		// Create panel for objective choice
		objectiveChoice = new JPanel();
		objectiveChoice.add(new JLabel("Objective: "));
		objectiveChoice.add(coloursBox);
		objectiveChoice.add(actionsBox);
		objectiveChoice.add(coord1);
		objectiveChoice.add(coord2);
		this.add(objectiveChoice);

		// Create panel for OK/Cancel
		finishButtons = new JPanel();
		finishButtons.add(OKButton);
		finishButtons.add(cancelButton);
		this.add(finishButtons);

		// Add listener to OK/Cancel buttons
		OKButton.addActionListener(this);
		cancelButton.addActionListener(this);
	}

	@Override
	// React to OK or cancel buttons
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand() == "OK") {
			// Update objective according to user input
			String objAction = ((String) actionsBox.getSelectedItem())
					.toLowerCase();
			String objColour = (String) coloursBox.getSelectedItem();
			int objCoord1 = Integer.parseInt((String) coord1.getSelectedItem());
			int objCoord2 = Integer.parseInt((String) coord2.getSelectedItem());

			Objective setObj = new Objective(objAction,
					BoardJPanel.translatePlayer(objColour), new Coordinate(
							objCoord1, objCoord2));
			BoardJPanel.setObjective(setObj);
			GraphicalUI.updateMessage("Objective updated");
			GraphicalUI.objective.setText(GraphicalUI.getGameEngine()
					.getObjective().toString());
			
			// Check for cancellation of action
			cancelled = false;
			setVisible(false);
		} else {
			cancelled = true;
			setVisible(false);
		}
	}
}
