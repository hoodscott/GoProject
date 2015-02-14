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

public class BoundsObjectiveDialog extends JDialog implements ActionListener {

    private static final long serialVersionUID = 1L;
    private JPanel objectiveChoice, boundsChoice, finishButtons;
    @SuppressWarnings("rawtypes")
    private JComboBox actionsBox, coloursBox, coord1, coord2, bc1, bc2, bc3, bc4;
    private JButton OKButton, cancelButton;
    public static boolean cancelled;

    // Constructor
    @SuppressWarnings({"unchecked", "rawtypes"})
    public BoundsObjectiveDialog(Frame GUIFrame) {
        super(GUIFrame, true);

        this.setLayout(new GridLayout(0, 1));

        setTitle("Problem Settings");

        // Create components for objective selection
        String[] actions = {"Kill", "Defend"};
        actionsBox = new JComboBox(actions);
        String[] colours = {"Black", "White"};
        coloursBox = new JComboBox(colours);

        // Create array of suitable co-ordinate positions for selection
        String[] positions = new String[BoardJPanel.getLines()];
        for (int i = 0; i < BoardJPanel.getLines(); i++) {
            positions[i] = i + "";
        }

        // Create combo boxes
        coord1 = new JComboBox(positions);
        coord2 = new JComboBox(positions);
        bc1 = new JComboBox(positions);
        bc2 = new JComboBox(positions);
        bc3 = new JComboBox(positions);
        bc4 = new JComboBox(positions);

        // Buttons for user selection
        OKButton = new JButton("OK");
        cancelButton = new JButton("Cancel");

        // Load values of the current objective if it exists
        Objective obj = GraphicalUI.getGameEngine().getObjective();
        if (obj != null) {
            // set colour
            if (obj.getStartingColour() == 1) {
                coloursBox.setSelectedIndex(0);
            } else if (obj.getStartingColour() == 2) {
                coloursBox.setSelectedIndex(1);
            }
            // set action
            if (obj.getOriginalAction().equals("kill")) {
                coloursBox.setSelectedIndex(0);
            } else if (obj.getOriginalAction().equals("defend")) {
                coloursBox.setSelectedIndex(1);
            }
            // set coordinates
            coord1.setSelectedIndex(obj.getPosition().x);
            coord2.setSelectedIndex(obj.getPosition().y);
        }

        // Load values of the current bounds, if they exist
        if (GraphicalUI.getBounds()) {
            int[] bounds = GraphicalUI.getGameEngine().getAISearchValues();
            bc1.setSelectedIndex(bounds[0]);
            bc2.setSelectedIndex(bounds[1]);
            bc3.setSelectedIndex(bounds[2]);
            bc4.setSelectedIndex(bounds[3]);
        }

        // Create panel for objective choice
        objectiveChoice = new JPanel();
        objectiveChoice.add(new JLabel("Objective: "));
        objectiveChoice.add(coloursBox);
        objectiveChoice.add(actionsBox);
        objectiveChoice.add(coord1);
        objectiveChoice.add(coord2);
        this.add(objectiveChoice);

        // Create panel for bounds choice
        boundsChoice = new JPanel();
        boundsChoice.add(new JLabel("Bounds: "));
        boundsChoice.add(bc1);
        boundsChoice.add(bc2);
        boundsChoice.add(new JLabel(" to "));
        boundsChoice.add(bc3);
        boundsChoice.add(bc4);
        this.add(boundsChoice);

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
            String objAction = ((String) actionsBox.getSelectedItem()).toLowerCase();
            String objColour = (String) coloursBox.getSelectedItem();
            int objCoord1 = Integer.parseInt((String) coord1.getSelectedItem());
            int objCoord2 = Integer.parseInt((String) coord2.getSelectedItem());

            Objective setObj = new Objective(objAction, BoardJPanel.translatePlayer(objColour), new Coordinate(objCoord1, objCoord2));
            BoardJPanel.setObjective(setObj);
            GraphicalUI.updateMessage("Objective updated");
            GraphicalUI.objective.setText(GraphicalUI.getGameEngine()
                    .getObjective().toString());

            // Update boundaries according to user input
            int bCoord1 = Integer.parseInt((String) bc1.getSelectedItem());
            int bCoord2 = Integer.parseInt((String) bc2.getSelectedItem());
            int bCoord3 = Integer.parseInt((String) bc3.getSelectedItem());
            int bCoord4 = Integer.parseInt((String) bc4.getSelectedItem());

            int[] newBounds = {bCoord1, bCoord2, bCoord3, bCoord4};
            BoardJPanel.setBounds(newBounds);
            GraphicalUI.updateBoundsButton(true);
            GraphicalUI.updateMessage("Bounds updated");

            // Tell GUI that user has set bounds/objectives
            GraphicalUI.setProblemSettings(true);

            cancelled = false;
            setVisible(false);
        } else {
            cancelled = true;
            setVisible(false);
        }
    }
}
