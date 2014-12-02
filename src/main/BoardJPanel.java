package main;

import java.awt.*;
import java.awt.event.*;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class BoardJPanel extends JPanel {

	// constants
	private static final int BOARD_LENGTH = 600;
	private int lines;
	private Board board;
	public static int colour = 1;
	public GameEngine gameE;

	// Board constructor
	public BoardJPanel(GameEngine gameEngine) {

		// Create panel
		super();
		setPreferredSize(new Dimension(BOARD_LENGTH, BOARD_LENGTH));
		
		// set private variables
		this.gameE = gameEngine;
		board = gameEngine.getCurrentBoard();
		lines = board.getHeight();

		// Add stone to board when user clicks
		this.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				double x = e.getPoint().getX();
				double y = e.getPoint().getY();
				int squareSize = BOARD_LENGTH / (lines - 1);

				// Get position of counter
				int xPos = (int) x / squareSize;
				int xRemainder = (int) x - (xPos * squareSize);
				if (xRemainder > squareSize / 2)
					xPos++;
				int yPos = (int) y / squareSize;
				int yRemainder = (int) y - (yPos * squareSize);
				if (yRemainder > squareSize / 2)
					yPos++;

				updateBoard(yPos, xPos, colour); // SET TO BLACK STONE

			}
		});
	}

	// Draw counter onto position
	public void updateBoard(int x, int y, int c) {
		if (gameE.makeMove(x, y, c)) {
			// move made, repaint board
			repaint();
			// change player
			changePlayer();
		}
		else {
			//TODO report invalid move here
		}

	}

	// Load and draw board
	public void loadBoard(GameEngine ge) {
		this.gameE = ge;
		repaint();
	}

	// Draw board
	public void paint(Graphics g) {
		// Frame variables
		int squareSize = (BOARD_LENGTH) / (lines - 1);
		int stoneSize = squareSize / 2;

		// Board colour and border fill
		g.setColor(Color.black);
		g.fillRect(0, 0, squareSize * (lines - 1), squareSize * (lines - 1));
		g.setColor(new Color(205, 133, 63)); // rgb of brown colour
		g.fillRect(1, 1, squareSize * (lines - 1) - 2, squareSize * (lines - 1)
				- 2);

		// Draw grid of rectangles
		for (int x = 0; x < lines - 1; x++) {
			for (int y = 0; y < lines - 1; y++) {
				g.setColor(Color.black);
				g.drawRect(x * squareSize, y * squareSize, squareSize,
						squareSize);
			}
		}

		// Draws counters on grid
		board = gameE.getCurrentBoard();
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
					g.fillOval((j * squareSize - stoneSize) - 2, (i
							* squareSize - stoneSize) - 2, squareSize + 4,
							squareSize + 4);
					g.setColor(Color.white);
					g.fillOval(j * squareSize - stoneSize, i * squareSize
							- stoneSize, squareSize, squareSize);
				}
			}
		}
	}
	
	// helper method to change the colour of the current player
	public void changePlayer(){
		if (colour == 1) {
			colour++;
		} else {
			colour--;
		}
	}

}