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

public class PlayerChooseDialog extends JDialog implements ActionListener {

    private static final long serialVersionUID = 1L;
    private JPanel playerBlackChoice, playerWhiteChoice, finishButtons;
    @SuppressWarnings("rawtypes")
    private JComboBox optionsWhiteBox, optionsBlackBox;
    private JButton OKButton, cancelButton;
    public static boolean cancelled, AIvsAI;

    // Constructor
    @SuppressWarnings({"unchecked", "rawtypes"})
    public PlayerChooseDialog(Frame GUIFrame) {
        super(GUIFrame, true);

        this.setLayout(new GridLayout(0, 1));

        setTitle("Choose Players");

        // Create components for objective selection
        String[] playerOptionsWhite = {"Human", "MiniMax", "AlphaBeta"};
        optionsWhiteBox = new JComboBox(playerOptionsWhite);
        optionsBlackBox = new JComboBox(playerOptionsWhite);

        // Buttons for user selection
        OKButton = new JButton("OK");
        cancelButton = new JButton("Cancel");

        // Create panel for player black choices
        playerBlackChoice = new JPanel();
        playerBlackChoice.add(new JLabel("Black Player: "));
        playerBlackChoice.add(optionsBlackBox);
        this.add(playerBlackChoice);

        // Create panel for player white choices
        playerWhiteChoice = new JPanel();
        playerWhiteChoice.add(new JLabel("White Player: "));
        playerWhiteChoice.add(optionsWhiteBox);
        this.add(playerWhiteChoice);

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
            // get player choices
            String blackPlayer = (String) optionsBlackBox.getSelectedItem();
            String whitePlayer = (String) optionsWhiteBox.getSelectedItem();

            // assume human player will play first and swap accordingly
            String currentPlayer = BoardJPanel.getPlayer();
            if (blackPlayer.equals("Human") && (whitePlayer.equals("AlphaBeta") || whitePlayer.equals("MiniMax"))) {
                if (currentPlayer.equals("White")) {
                    GraphicalUI.boardJP.changePlayer();
                }
                GraphicalUI.aiType = whitePlayer;
                GraphicalUI.updateMessage("AI type selected: " + whitePlayer);
                AIvsAI = false;
                GraphicalUI.currentAILabel.setText(whitePlayer);
            } else if (whitePlayer.equals("Human") && (blackPlayer.equals("AlphaBeta") || blackPlayer.equals("MiniMax"))) {
                if (currentPlayer.equals("Black")) {
                    GraphicalUI.boardJP.changePlayer();
                }
                GraphicalUI.aiType = blackPlayer;
                GraphicalUI.updateMessage("AI type selected: " + blackPlayer);
                AIvsAI = false;
                GraphicalUI.currentAILabel.setText(blackPlayer);
            }

            // else AI vs AI mode
            if ((blackPlayer.equals("AlphaBeta") || blackPlayer.equals("MiniMax"))
                    && (whitePlayer.equals("AlphaBeta") || whitePlayer.equals("MiniMax"))) {
                // TODO: Allow 2 separate AIs to play against each other?
                GraphicalUI.aiType = blackPlayer;
                GraphicalUI.updateMessage("AI type selected: " + blackPlayer);
                GraphicalUI.currentAILabel.setText(blackPlayer);
                AIvsAI = true;
            }

            cancelled = false;
            setVisible(false);
        } else {
            cancelled = true;
            setVisible(false);
        }
    }
}
