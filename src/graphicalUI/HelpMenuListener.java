package graphicalUI;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JTextArea;

import main.FileIO;

/**
 * Listener used by the help menu.
 */
public class HelpMenuListener implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Show Keyboard Shortcuts")) {
        	// Displays keyboard shortcuts in window using info file
        	// Retrieve path of saved problem boards and convert to match OS
    		String filepath = System.getProperty("user.dir")
    				+ "\\info\\GUIShortcuts";
    		filepath = FileIO.pathOS(filepath);
    		if (Files.notExists(Paths.get(filepath))) {
    			filepath = System.getProperty("user.dir")
    					+ "\\src\\info\\GUIShortcuts";
    			filepath = FileIO.pathOS(filepath);
    		}
            ArrayList<String> keyboardFile = FileIO
                    .readFile(filepath);
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
            helpFrame.setDefaultCloseOperation(2);
            GraphicalUI.updateMessage("Displayed Keyboard Shortcuts");
        }
    }
}
