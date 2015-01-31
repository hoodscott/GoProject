package graphicalUI;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JDialog;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import java.beans.*; //property change stuff
import java.awt.*;
import java.awt.event.*;

/* 1.4 example used by DialogDemo.java. */
public class BoundsObjectiveDialog extends JDialog
                   implements ActionListener,
                              PropertyChangeListener {

	private static final long serialVersionUID = 1L;
	private JComboBox actionsBox, coloursBox, coord1, coord2;
	private JRadioButton objective, bounds;
	private JOptionPane optionPane;
	private boolean started = false, objComplete = false, boundsComplete = false;
	private int count = 0;

    // Constructor
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public BoundsObjectiveDialog(Frame GUIFrame) {
        super(GUIFrame, true);

        setTitle("Problem Settings");

        // Create components for objective selection
        String obj = "Objective";
        String[] actions = {"Kill","Defend"};
        actionsBox = new JComboBox(actions);
        String[] colours = {"Black","White"};
        coloursBox = new JComboBox(colours);
        
        // Create array of suitable co-ordinate positions for objective 
        String[] positions = new String[BoardJPanel.getLines()];
        for (int i = 0; i < BoardJPanel.getLines(); i++) positions[i] = i + "";
        coord1 = new JComboBox(positions);
        coord2 = new JComboBox(positions);
        
        // Radio button and listener
        objective = new JRadioButton("Objective Complete");
        objective.addActionListener(this);
        
        // Create component for bounds selection
        String bds = "Bounds";
        
        // Radio button and listener
        bounds = new JRadioButton("Bounds Complete");
        bounds.addActionListener(this);
        
        // Create option pane 
        // TODO: Change layout
        Object[] array = {obj,coloursBox,actionsBox,coord1,coord2,objective,bds,bounds};

        // Display option pane
        optionPane = new JOptionPane(array,JOptionPane.QUESTION_MESSAGE);
        this.add(optionPane);
        
        // Add listeners for pane's property changes
        optionPane.addPropertyChangeListener(this);
    }
    
	@Override
	public void actionPerformed(ActionEvent e) {
		// adding toggling for complete radio buttons
		if (e.getActionCommand() == "Objective Complete" && !objComplete) {
			objComplete = true;
		} else if (e.getActionCommand() == "Objective Complete" && objComplete){
			objComplete = false;
		}
		
		if (e.getActionCommand() == "Bounds Complete" && !boundsComplete) {
			boundsComplete = true;
		} else if (e.getActionCommand() == "Bounds Complete" && boundsComplete) {
			boundsComplete = false;
		}
	}

	// React to OK button or Cancel button
	@Override
	public void propertyChange(PropertyChangeEvent e) {
		System.out.println(e.getPropertyName());
		// Check if complete radio buttons selected
		if (boundsComplete && objComplete) {
			// Get selected objective attributes
			String objAction = (String)actionsBox.getSelectedItem();
			String colourAction = (String)coloursBox.getSelectedItem();
			int coord1Action = Integer.parseInt((String)coord1.getSelectedItem());
			int coord2Action = Integer.parseInt((String)coord2.getSelectedItem());
			
			// TODO: Move objective/bounds updating to here
		
		// Tell user to complete both bounds and objective
		} else if ((!boundsComplete || !objComplete) && !e.getPropertyName().equals("ancestor") && count == 0) {
			JOptionPane.showMessageDialog(this, "Please complete both bounds and objective.");
			count++;
		} else if ((!boundsComplete || !objComplete) && !e.getPropertyName().equals("ancestor")) {
			count--;
		}
		
		// Reset pane value at end
        optionPane.setValue(JOptionPane.UNINITIALIZED_VALUE);
	}
}