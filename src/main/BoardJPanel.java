package main;

import java.awt.*;
import java.awt.event.*;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class BoardJPanel extends JPanel {

	// constants
	private static final int BOARD_LENGTH = 600;
	private int lines;
	private Board board, greyCounters;
	public static int colour = 1;
	public GameEngine gameE;
	public int numStones = 0;

	// Board constructor
	public BoardJPanel(GameEngine gameEngine) {

		// Create panel
		super();
		setPreferredSize(new Dimension(BOARD_LENGTH, BOARD_LENGTH));

		// set private variables
		this.gameE = gameEngine;
		board = gameEngine.getCurrentBoard();
		greyCounters = new Board();
		lines = board.getHeight();

		// Add stone to board when user clicks
		this.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				greyCounters = new Board(); // create blank board each time
											// mouse clicks
				repaint();

				int squareSize = BOARD_LENGTH / (lines + 1);
				double x = e.getPoint().getX() - squareSize;
				double y = e.getPoint().getY() - squareSize;

				// Get position of counter
				int xPos = (int) x / squareSize;
				int xRemainder = (int) x - (xPos * squareSize);
				if (xRemainder > squareSize / 2)
					xPos++;
				int yPos = (int) y / squareSize;
				int yRemainder = (int) y - (yPos * squareSize);
				if (yRemainder > squareSize / 2)
					yPos++;

				// Update counter when close enough to intersection
				int border = squareSize / 4;

				if ((xRemainder < squareSize / 2 - border)
						|| (xRemainder > squareSize / 2 + border)
						&& (yRemainder < squareSize / 2 - border)
						|| (yRemainder > squareSize / 2 + border)) {
					updateBoard(yPos, xPos, colour); // SET TO BLACK STONE
				} else {
					GraphicalUI.invMove
							.setText("Select Closer To Intersection");
				}
			}
		});

		// Show transparent grey stones
		this.addMouseMotionListener(new MouseAdapter() {
			public void mouseMoved(MouseEvent e) {
				greyCounters = new Board(); // create blank board each time
											// mouse moves
				repaint();

				int squareSize = BOARD_LENGTH / (lines + 1);
				double x = e.getPoint().getX() - squareSize;
				double y = e.getPoint().getY() - squareSize;

				// Get position of counter
				int xPos = (int) x / squareSize;
				int xRemainder = (int) x - (xPos * squareSize);
				if (xRemainder > squareSize / 2)
					xPos++;
				int yPos = (int) y / squareSize;
				int yRemainder = (int) y - (yPos * squareSize);
				if (yRemainder > squareSize / 2)
					yPos++;

				// Show transparent stones when close enough to intersection
				int border = squareSize / 4;
				if (xPos < lines && yPos < lines) {
					if ((xRemainder < squareSize / 2 - border)
							|| (xRemainder > squareSize / 2 + border)
							&& (yRemainder < squareSize / 2 - border)
							|| (yRemainder > squareSize / 2 + border)) {
						boolean[][] legalMoves = gameE.getLegalMoves(colour);
						if (legalMoves[yPos][xPos]) {
							greyCounters.set(yPos, xPos, colour);
						} else {
							// show red transparent stones for illegal moves
							greyCounters.set(yPos, xPos, 3);
						}
						repaint();
					}
				}
			}
		});
	}

	// Draw counter onto position
	public void updateBoard(int x, int y, int c) {
		int i = 1;
		if (gameE.makeMove(new Coordinate(x, y), c)) {
			// move made, repaint board
			repaint();
			// change player
			changePlayer();
			GraphicalUI.player.setText(getPlayer());
			GraphicalUI.invMove.setText(getInvMove(i));
			numStones = numStones + 1;
		} else {
			i = 0;
			GraphicalUI.invMove.setText(getInvMove(i));

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
		int squareSize = (BOARD_LENGTH) / (lines + 1);
		int stoneSize = squareSize / 2;

		// Board colour and border fill
		g.setColor(Color.black);
		g.fillRect(0, 0, squareSize * (lines + 1), squareSize * (lines + 1));
		g.setColor(new Color(205, 133, 63)); // rgb of brown colour
		g.fillRect(1, 1, squareSize * (lines + 1) - 2, squareSize * (lines + 1)
				- 2);

		// Draw search space as grey rectangles when specified
		int[] searchSpace = gameE.getAISearchValues();
		if (searchSpace != null) {
			g.setColor(Color.gray); // re-colour board as grey
			g.fillRect(1, 1, squareSize * (lines - 1) - 2, squareSize
					* (lines - 1) - 2);

			// re-colour brown rectangles included in search space
			// NOTE: assumes 0,0 as top left co-ordinate
			int x2 = searchSpace[2];
			int y2 = searchSpace[3];
			for (int x1 = searchSpace[0]; x1 < x2; x1++) {
				for (int y1 = searchSpace[1]; y1 < y2; y1++) {
					g.setColor(new Color(205, 133, 63));
					g.fillRect(x1 * squareSize, y1 * squareSize, squareSize,
							squareSize);
				}
			}
		}

		// Draw grid of rectangles
		for (int x = 1; x < lines; x++) {
			for (int y = 1; y < lines; y++) {
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
					g.fillOval(squareSize + j * squareSize - stoneSize,
							squareSize + i * squareSize - stoneSize,
							squareSize, squareSize);
				} else if (board.get(i, j) == 2) {
					// white stones with border
					g.setColor(Color.black);
					g.fillOval((squareSize + j * squareSize - stoneSize) - 2,
							(squareSize + i * squareSize - stoneSize) - 2,
							squareSize + 4, squareSize + 4);
					g.setColor(Color.white);
					g.fillOval(squareSize + j * squareSize - stoneSize,
							squareSize + i * squareSize - stoneSize,
							squareSize, squareSize);
				}
			}
		}

		// Show grey transparent counters
		for (int i = 0; i < lines; i++) {
			for (int j = 0; j < lines; j++) {
				// show transparent black stones
				if (greyCounters.get(i, j) == 1) {
					g.setColor(new Color(0, 0, 0, 50));
					g.fillOval((squareSize + j * squareSize - stoneSize) - 2,
							(squareSize + i * squareSize - stoneSize) - 2,
							squareSize + 4, squareSize + 4);
					// show transparent white stones
				} else if (greyCounters.get(i, j) == 2) {
					g.setColor(new Color(255, 255, 255, 50));
					g.fillOval((squareSize + j * squareSize - stoneSize) - 2,
							(squareSize + i * squareSize - stoneSize) - 2,
							squareSize + 4, squareSize + 4); // show transparent
																// red stones -
																// illegal
				} else if (greyCounters.get(i, j) == 3) {
					g.setColor(new Color(125, 0, 0, 150));
					g.fillOval((squareSize + j * squareSize - stoneSize) - 2,
							(squareSize + i * squareSize - stoneSize) - 2,
							squareSize + 4, squareSize + 4);
				}
			}
		}

	}

	// helper method to change the colour of the current player
	public void changePlayer() {
		if (colour == 1) {
			colour++;
		} else {
			colour--;
		}
	}

	public static String getPlayer() {
		if (colour == 1) {
			return "Black to move";
		} else {
			return "White to move";
		}
	}

	public static String getInvMove(int i) {
		if (i == 0) {
			return "Invalid Move";
		} else {
			return "Valid Move";
		}

	}

	public static void setPlayer(String player) {
		if (player == "black") {
			colour = Board.BLACK;
		}
		if (player == "white") {
			colour = Board.WHITE;
		}
	}

	// public static int getNumStones() {
	// return numStones;
	// }

}