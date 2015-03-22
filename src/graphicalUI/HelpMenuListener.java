package graphicalUI;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JTextArea;

import main.FileIO;

/*
 * Listener used by the help menu.
 */
public class HelpMenuListener implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Show Keyboard Shortcuts")) {
        	// Displays keyboard shortcuts in window using info file
            String filePath = FileIO.pathOS(FileIO.RELATIVEPATH + "\\info\\GUIShortcuts");
            ArrayList<String> keyboardFile = FileIO
                    .readFile(filePath);
            JFrame helpFrame = new JFrame("Keyboard Shortcuts");
            helpFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            helpFrame.setBounds(150, 150, 300, 600);
            StringBuilder sb = new StringBuilder();
            
            // Append shortcuts to list
            for (String t : keyboardFile) {
                sb.append(t);
                sb.append("\n");
            }
            
            helpFrame.getContentPane().add(new JTextArea(sb.toString()), BorderLayout.CENTER);
            helpFrame.setVisible(true);
            GraphicalUI.updateMessage("Displayed Keyboard Shortcuts");
        }
    }
}
