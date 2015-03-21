package graphicalUI;

import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.util.Arrays;
import ai.AI;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.NumberFormatter;

public class HeuristicChooseDialog extends JDialog implements ActionListener, ListSelectionListener {

    private static final long serialVersionUID = 1L;
    private JPanel heuristicsChoice, finishButtons, depthPanel;
    private JLabel depthLabel;
    private JFormattedTextField depthField;
    NumberFormatter depthFormat;
    @SuppressWarnings("rawtypes")
    private JList hList;
    private JButton OKButton, cancelButton;
    private int[] selectedHeuristicsIndices;
    private String[] selectedHeuristics;
    public static boolean cancelled;
    private final String[] heuristics = {"EightStonesInARow","EyeCreator","Hane","LibertyCounter","LivingSpace","SixStonesInARow","ThreeLiberties","TwoPointEye","UnsettledThree"};
    private static String searchDepthMessage = "Search Depth: ";

    // Constructor
    @SuppressWarnings({"unchecked", "rawtypes"})
    public HeuristicChooseDialog(Frame GUIFrame) {
        super(GUIFrame, true);
        Arrays.sort(heuristics);
        this.setLayout(new BoxLayout(getContentPane(), BoxLayout.PAGE_AXIS));

        setTitle("Choose Heuristics");

        // Create components for heuristic selection
        hList = new JList(heuristics);
        hList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        hList.setLayoutOrientation(JList.HORIZONTAL_WRAP);
        hList.setVisibleRowCount(-1);
        if(GraphicalUI.heuristicIndices != null)
            hList.setSelectedIndices(GraphicalUI.heuristicIndices);
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
        
        //Create text field for editing search depth
        depthFormat = new NumberFormatter();
        depthFormat.setMinimum(1);
        depthFormat.setMaximum(1000);
        depthFormat.setValueClass(Integer.class);
        depthField = new JFormattedTextField(depthFormat);
        depthLabel = new JLabel(searchDepthMessage);
        depthField.setColumns(3);
        depthField.setEditable(true);
        depthField.setValue(AI.getSearchDepth());
        depthPanel = new JPanel();
        depthPanel.add(depthLabel); 
        depthPanel.add(depthField);
        this.add(depthPanel);

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
        if ("OK".equals(e.getActionCommand())) {
            // Set heuristic choice
            selectedHeuristicsIndices = hList.getSelectedIndices();
            AI.setSearchDepth((Integer)depthField.getValue());
            selectedHeuristics = new String[selectedHeuristicsIndices.length];
            //int index = hList.getSelectedIndex();
            //System.out.println(index);
            for (int i = 0; i < selectedHeuristicsIndices.length; i++) {
                selectedHeuristics[i] = heuristics[selectedHeuristicsIndices[i]];
                //System.out.println(selectedHeuristics[i]);
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

    public int[] getSelectedHeuristicsIndices() {
        return this.selectedHeuristicsIndices;
    }
}
