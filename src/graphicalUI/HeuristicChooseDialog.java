package graphicalUI;

import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class HeuristicChooseDialog extends JDialog implements ActionListener, ListSelectionListener {

    private static final long serialVersionUID = 1L;
    private JPanel heuristicsChoice, finishButtons;
    @SuppressWarnings("rawtypes")
	private JList hList;
    private JButton OKButton, cancelButton;
    private int[] selectedHeuristicsIndices;
    private String[] selectedHeuristics; 
    public static boolean cancelled;
    private String[] heuristics = {"EightStonesInARow","HasAnEye","Hane","LibertyCounter","LivingSpace","ThreeLiberties","UnsettledThree"};

    // Constructor
    @SuppressWarnings({"unchecked", "rawtypes"})
    public HeuristicChooseDialog(Frame GUIFrame) {
        super(GUIFrame, true);

        this.setLayout(new BoxLayout(getContentPane(),BoxLayout.PAGE_AXIS));

        setTitle("Choose Heuristics");

        // Create components for heuristic selection
        hList = new JList(heuristics);
        hList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        hList.setLayoutOrientation(JList.HORIZONTAL_WRAP);
        hList.setVisibleRowCount(-1);
        JScrollPane listScroller = new JScrollPane(hList);
        listScroller.setViewportView(hList);

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
            selectedHeuristicsIndices = hList.getSelectedIndices();
            selectedHeuristics = new String[selectedHeuristicsIndices.length];
            //int index = hList.getSelectedIndex();
            //System.out.println(index);
            for(int i = 0; i < selectedHeuristicsIndices.length; i++){
                selectedHeuristics[i] = heuristics[selectedHeuristicsIndices[i]];
                System.out.println(selectedHeuristics[i]);
            }
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
	        	selectedHeuristicsIndices = hList.getSelectedIndices();
	        	}
	        }
	    }
	
	public String[] getSelectedHeuristics() {
		return this.selectedHeuristics;
	}
}