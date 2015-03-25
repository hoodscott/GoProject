package graphicalUI;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JDialog;
import javax.swing.JPanel;
import java.awt.*;
import java.awt.event.*;

/**
 * Creates input box for player choice when entering competitive play mode.
 */
public class PlayerChooseDialog extends JDialog implements ActionListener {

	// Variables for input box
    private static final long serialVersionUID = 1L;
    private JPanel playerBlackChoice, playerWhiteChoice, finishButtons;
    @SuppressWarnings("rawtypes")
    private JComboBox optionsWhiteBox, optionsBlackBox;
    private JButton OKButton, cancelButton;
    public static boolean cancelled, AIvsAI, humanVShuman;

    // Constructor using GUI's frame
    @SuppressWarnings({"unchecked", "rawtypes"})
    public PlayerChooseDialog(Frame GUIFrame) {
        super(GUIFrame, true);

        this.setLayout(new GridLayout(0, 1));

        setTitle("Choose Players");

        // Create components for player selection
        String[] playerOptions = {"Human", "MiniMax", "AlphaBeta", "HybridMiniMax"};
        optionsWhiteBox = new JComboBox(playerOptions);
        optionsBlackBox = new JComboBox(playerOptions);

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
        if (e.getActionCommand().equals("OK")) {
            // Get player choices
            String blackPlayer = (String) optionsBlackBox.getSelectedItem();
            String whitePlayer = (String) optionsWhiteBox.getSelectedItem();

            // Assume human player will play first and swap accordingly
            String currentPlayer = BoardJPanel.getPlayer();
            if (blackPlayer.equals("Human") && !whitePlayer.equals("Human")) {
                if (currentPlayer.equals("White")) {
                    GraphicalUI.boardJP.changePlayer();
                }
                GraphicalUI.aiType = whitePlayer;
                GraphicalUI.updateMessage("AI type selected: " + whitePlayer);
                AIvsAI = false;
                GraphicalUI.currentAILabel.setText(whitePlayer);
            } else if (whitePlayer.equals("Human") && !blackPlayer.equals("Human")) {
                if (currentPlayer.equals("Black")) {
                    GraphicalUI.boardJP.changePlayer();
                }
                GraphicalUI.aiType = blackPlayer;
                GraphicalUI.updateMessage("AI type selected: " + blackPlayer);
                AIvsAI = false;
                GraphicalUI.currentAILabel.setText(blackPlayer);
            }

            // Else AI vs AI mode
            if (!blackPlayer.equals("Human") && !whitePlayer.equals("Human")) {
                GraphicalUI.aiType = blackPlayer;
                GraphicalUI.aiType2 = whitePlayer;
                GraphicalUI.updateMessage("AI type selected: " + blackPlayer);
                GraphicalUI.currentAILabel.setText(blackPlayer);
                AIvsAI = true;
            }

            // Else Human vs Human mode
            if ((blackPlayer.equals("Human")) && whitePlayer.equals("Human")) {
                GraphicalUI.currentAILabel.setText(blackPlayer);
                humanVShuman = true;
                AIvsAI = false;
            }

            cancelled = false;
            setVisible(false);
        } else {
            cancelled = true;
            setVisible(false);
        }
    }
}
