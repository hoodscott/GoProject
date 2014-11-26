import java.awt.*;
import java.awt.event.*;

public class DrawBoard extends Frame {

	public static void main(String args[]) {
		new DrawBoard();
	}

	public DrawBoard() {

		// Create frame
		super("Frame Title");
		setSize(400,300);
		setVisible(true);

		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				dispose(); System.exit(0);}
			}
		);
   }
  
	// Draw 2 rectangles
	public void paint(Graphics g) {
		g.setColor(Color.red);
		g.drawRect(50,50,200,200);
		Graphics2D g2d = (Graphics2D)g;
		g2d.setColor(Color.blue);
		g2d.drawRect(75,75,300,200);
   }
  
 }