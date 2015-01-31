package graphicalUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JToggleButton;

public class GridToggleListener implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        JToggleButton button = (JToggleButton) e.getSource();
        if (button.getText().equals("Show Bounds")) {
            if (!GraphicalUI.getBounds()) {
                GraphicalUI.setBounds(true);
                GraphicalUI.boardJP.loadBoard(GraphicalUI.getGameEngine());
                GraphicalUI.feedback.setText("Bounds shown");
            } else {
                GraphicalUI.setBounds(false);
                GraphicalUI.boardJP.loadBoard(GraphicalUI.getGameEngine());
                GraphicalUI.feedback.setText("Bounds hidden");
            }
        }
        if (button.getText().equals("Competitive Play Mode")) {
            GraphicalUI.enterCompetitive();
        }
        if (button.getText().equals("Problem Creation Mode")) {
            GraphicalUI.enterCreation();
        }
    }

}
