package graphicalUI;

import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

import main.Coordinate;
import ai.Objective;

public class HeuristicChooseDialog extends JDialog implements ActionListener {

    private static final long serialVersionUID = 1L;
    private JPanel heuristicsChoice, finishButtons;
    @SuppressWarnings("rawtypes")
    private JComboBox heuristicsBox;
    private JButton OKButton, cancelButton;
    public static boolean cancelled;
    public static String heuristicChoice;

    // Constructor
    @SuppressWarnings({"unchecked", "rawtypes"})
    public HeuristicChooseDialog(Frame GUIFrame) {
        super(GUIFrame, true);

        this.setLayout(new GridLayout(0, 1));

        setTitle("Problem Settings");

        // Create components for heuristic selection
        String[] heuristicsList = {"Heuristic 1","Heuristic 2","Remove Specified Heuristic"};
        heuristicsBox = new JComboBox(heuristicsList);

        // Buttons for user selection
        OKButton = new JButton("OK");
        cancelButton = new JButton("Cancel");

        // Create panel for heuristic Choice
        heuristicsChoice = new JPanel();
        heuristicsChoice.add(new JLabel("Choose A Heuristic: "));
        heuristicsChoice.add(heuristicsBox);
        this.add(heuristicsChoice);
        
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
            // Set heuristic choice
            heuristicChoice = ((String) heuristicsBox.getSelectedItem()).toLowerCase();
            cancelled = false;
            setVisible(false);
        } else {
            cancelled = true;
            setVisible(false);
        }
    }
}