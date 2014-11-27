import java.awt.*;
import java.awt.event.*;

public class DrawBoard extends Frame {

	public static void main(String args[]) {
		new DrawBoard();
	}

	public DrawBoard() {

		// Create frame
		super("Frame Title");
		setSize(600,600);
		setVisible(true);

		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				dispose(); System.exit(0);}
			}
		);
   }
  
	// Draw board
	public void paint(Graphics g) {
		// draw board of 9 lines 50 pixels in from sides
		int lines = 9;
		int squareSize = 550/9;
			
		for ( int i = 0; i < lines; i++) {
			g.drawLine(i*squareSize+squareSize,squareSize,i*squareSize+squareSize,squareSize*lines);
			g.drawLine(squareSize,i*squareSize+squareSize,squareSize*lines,i*squareSize+squareSize);
		}
	}
	
   }
  