package graphicalUI;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import main.Coordinate;
import ai.Objective;

public class HeuristicChooseDialog extends JDialog implements ActionListener, ListSelectionListener {

    private static final long serialVersionUID = 1L;
    private JPanel heuristicsChoice, finishButtons;
    @SuppressWarnings("rawtypes")
	private JList hList;
    @SuppressWarnings("rawtypes")
    private JButton OKButton, cancelButton;
    public int[] selectedHeuristics;
    public static boolean cancelled;

    // Constructor
    @SuppressWarnings({"unchecked", "rawtypes"})
    public HeuristicChooseDialog(Frame GUIFrame) {
        super(GUIFrame, true);

        this.setLayout(new GridLayout(0, 1));

        setTitle("Choose Heuristics");

        // Create components for heuristic selection
        Object[] heuristics = {"Heuristic 1","Heuristic 2","Remove Specified Heuristics"};
        hList = new JList(heuristics);
        hList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        hList.setLayoutOrientation(JList.HORIZONTAL_WRAP);
        hList.setVisibleRowCount(-1);
        JScrollPane listScroller = new JScrollPane(hList);

        // Buttons for user selection
        OKButton = new JButton("OK");
        cancelButton = new JButton("Cancel");

        // Create panel for heuristic Choice
        heuristicsChoice = new JPanel();
        heuristicsChoice.add(new JLabel("Choose A Heuristic: "));
        heuristicsChoice.add(listScroller);
        this.add(heuristicsChoice);
        
        // Create panel for OK/Cancel
        finishButtons = new JPanel();
        finishButtons.add(OKButton);
        finishButtons.add(cancelButton);
        this.add(finishButtons);

        // Add listener to OK/Cancel buttons
        OKButton.addActionListener(this);
        cancelButton.addActionListener(this);
        
        // Add listener to list
        hList.addListSelectionListener(this);
    }

    @Override
    // React to OK or cancel buttons
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand() == "OK") {
            // Set heuristic choice
        	int index = hList.getSelectedIndex();
        	System.out.println(index);
            
            cancelled = false;
            setVisible(false);
        } else {
            cancelled = true;
            setVisible(false);
        }
    }

	@Override
	// React to list selections
	public void valueChanged(ListSelectionEvent e) {
	    if (e.getValueIsAdjusting() == false) {

	        if (hList.getSelectedIndex() == -1) {
	        	return;
	        } else {
	        	selectedHeuristics = hList.getSelectedIndices();
	        	}
	        }
	    }
	
	public int[] getSelectedHeuristics() {
		return this.selectedHeuristics;
	}
}