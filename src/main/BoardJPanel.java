package main;

import java.awt.*;
import java.awt.event.*;

import javax.swing.JPanel;

public class BoardJPanel extends JPanel {

	// constants
	public static final int BOARD_LENGTH = 600;
	int lines = 9;
	public Board board = new Board();

	// Board constructor
	public BoardJPanel() {
		// TODO: Get constructor to take number of lines

		// Create panel
		super();
		setPreferredSize(new Dimension(BOARD_LENGTH, BOARD_LENGTH));
		// Add stone to board when user clicks
		this.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				double x = e.getPoint().getX();
				double y = e.getPoint().getY();
				int squareSize = BOARD_LENGTH / (lines-1);
				
				// Get position of counter
				int xPos = (int)x/squareSize;
				int xRemainder = (int)x-(xPos*squareSize);
				if (xRemainder > squareSize/2) xPos++;
				int yPos = (int)y/squareSize;
				int yRemainder = (int)y-(yPos*squareSize);
				if (yRemainder > squareSize/2) yPos++;
				
				// Update board with co-ordinates and stone colour
				updateBoard(yPos,xPos,1); // SET TO BLACK STONE
			}
		});
	}
	
	// Draw counter onto position
	public void updateBoard(int x, int y, int colour) {
		board.set(x,y,colour);
		repaint();
	}
	
	// Load and draw board
	public void loadBoard(Board board) {
		this.board = board;
		this.lines = board.getWidth();
		repaint();
	}

	// Draw board
	public void paint(Graphics g) {
		// Frame variables
		int squareSize = (BOARD_LENGTH) / (lines-1);
		int stoneSize = squareSize / 2;

		// Board colour and border fill
		g.setColor(Color.black);
		g.fillRect(0,0,squareSize*(lines-1),squareSize*(lines-1));
		g.setColor(new Color(205,133,63)); // rgb of brown colour
		g.fillRect(1,1,squareSize*(lines-1)-2,squareSize*(lines-1)-2);
		
		// Draw grid of rectangles
		for (int x = 0; x < lines-1; x++) {
			for (int y = 0; y < lines-1; y++) {
				g.setColor(Color.black);
				g.drawRect(x * squareSize, y * squareSize, squareSize,
						squareSize);
			}
		}
		
		// Test counters
		board.set(1,1,1);
		board.set(2,4,2);

		// Draws counters on grid
		for (int i = 0; i < lines; i++) {
			for (int j = 0; j < lines; j++) {
				if (board.get(i, j) == 1) {
					// draw black stones
					g.setColor(Color.black);
					g.fillOval(j * squareSize - stoneSize, i * squareSize
							- stoneSize, squareSize, squareSize);
				} else if (board.get(i, j) == 2) {
					// white stones with border
					g.setColor(Color.black);
					g.fillOval((j * squareSize - stoneSize)-2, (i * squareSize
							- stoneSize)-2, squareSize+4, squareSize+4);
					g.setColor(Color.white);
					g.fillOval(j * squareSize - stoneSize, i * squareSize
							- stoneSize, squareSize, squareSize);
				}
			}
		}
	}

}
