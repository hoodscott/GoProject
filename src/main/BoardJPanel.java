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
				greyCounters = new Board(); // create blank board each time mouse clicks
				repaint();
				
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
				
				// Update counter when close enough to intersection
				int border = squareSize / 4;
				
				if ((xRemainder < squareSize / 2 - border) || (xRemainder > squareSize / 2 + border)
					&& (yRemainder < squareSize / 2 - border) || (yRemainder > squareSize / 2 + border)) {
					updateBoard(yPos, xPos, colour); // SET TO BLACK STONE
				} else {
					GraphicalUI.invMove.setText("Select Closer To Intersection");
				}
			}
		});
		
		// Show transparent grey stones
		this.addMouseMotionListener(new MouseAdapter() {
			public void mouseMoved(MouseEvent e){
				greyCounters = new Board(); // create blank board each time mouse moves
				repaint();
				
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
				
				// Show grey transparent counter when close enough to intersection
				int border = squareSize / 4;
				
				if ((xRemainder < squareSize / 2 - border) || (xRemainder > squareSize / 2 + border)
					&& (yRemainder < squareSize / 2 - border) || (yRemainder > squareSize / 2 + border)) {
					greyCounters.set(yPos,xPos,1);
					repaint();
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
			numStones= numStones +1;
		}
		else {
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
		
		// Show grey transparent counters
		for (int i = 0; i < lines; i++) {
			for (int j = 0; j < lines; j++) {
				if (greyCounters.get(i, j) == 1) {
					g.setColor(new Color(0,0,0,50));
					g.fillOval((j * squareSize - stoneSize) - 2, (i
							* squareSize - stoneSize) - 2, squareSize + 4,
							squareSize + 4);
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

	public static String getPlayer() {
		if (colour == 1) {
			return "Black to move";
		} else {
			return "White to move";
		}
	}
	
	public static String getInvMove(int i){
		if (i == 0){
			return "Invalid Move";
		}else{
			return "Valid Move";
		}
		
	}
	
	public static void setPlayer(String player){
		if(player == "black"){
			colour = Board.BLACK;	
		}
		if(player == "white"){
			colour = Board.WHITE;	
		}
	}
	
	//public static int getNumStones() {
	//	return numStones;
	//}

}