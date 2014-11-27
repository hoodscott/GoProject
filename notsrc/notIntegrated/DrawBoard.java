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
		// Draw board of specified lines
		int lines = 9;
		int frameSize = 600;
		int squareSize = (frameSize)/lines;
		int stoneSize = squareSize/2;
			
		for(int x=0;x<lines;x++) {
		    for(int y=0;y<lines;y++) {
		        g.drawRect(x*squareSize,y*squareSize,squareSize,squareSize);
		    }
		}
		
		int[][] exampleBoard = {{0,0,0,1,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,2,0,0,0},
				{0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0},
				{0,1,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,2,1,0,0},
				{0,2,0,0,0,0,0,0,0}};
		
		// Draws counters on grid
		for (int i = 0; i<lines; i++) {
			for (int j = 0; j<lines; j++) {
				if (exampleBoard[i][j] == 1) {
					g.setColor(Color.black);
					g.fillOval(j*squareSize-stoneSize,i*squareSize-stoneSize,squareSize,squareSize);
				}
				else if (exampleBoard[i][j] == 2) {
					g.drawOval(j*squareSize-stoneSize,i*squareSize-stoneSize,squareSize,squareSize);
					g.setColor(Color.white);
					g.fillOval(j*squareSize-stoneSize,i*squareSize-stoneSize,squareSize,squareSize);
				}
			}
		}
	}
	
   }
  