package graphicalUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DebugMenuListener implements ActionListener {

    // TODO implement debugging actions
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Toggle Row Numbers")) {
            GraphicalUI.toggleRowNumbers();
        }
    }
}
