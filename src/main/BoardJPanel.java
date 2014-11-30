package main;

import java.awt.*;
import java.awt.event.*;

import javax.swing.JPanel;

public class BoardJPanel extends JPanel {

	// constants
	public static final int BOARD_LENGTH = 610;

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
				int squareSize = 590 / (8); // TODO: make global variable
				updateBoard((int)x/squareSize,(int)y/squareSize);
			}
		});
	}
	
	public void updateBoard(int x, int y) {
		System.out.println(x);
		System.out.println(y);
	}

	// Draw board
	public void paint(Graphics g) {
		int lines = 9; // test int
		int frameSize = BOARD_LENGTH-10; // allow 10 pixel space for int division
		int squareSize = (frameSize) / (lines-1);
		int stoneSize = squareSize / 2;

		// Fill in colour of board
		g.setColor(new Color(205,133,63)); //rgb of brown colour
		g.fillRect(0,0,squareSize*(lines-1),squareSize*(lines-1));
		
		// Draw grid of rectangles
		for (int x = 0; x < lines-1; x++) {
			for (int y = 0; y < lines-1; y++) {
				g.setColor(Color.black);
				g.drawRect(x * squareSize, y * squareSize, squareSize,
						squareSize);
			}
		}

		int[][] exampleBoard = { { 0, 0, 0, 1, 0, 0, 0, 0, 0}, 
				{ 0, 0, 0, 1, 0, 0, 0, 0, 0}, 
				{ 0, 0, 0, 0, 0, 0, 2, 0, 0}, 
				{ 0, 0, 0, 1, 2, 0, 0, 0, 0}, 
				{ 0, 0, 0, 0, 0, 2, 0, 0, 0}, 
				{ 0, 0, 0, 0, 0, 0, 0, 0, 0}, 
				{ 0, 0, 0, 0, 0, 0, 0, 0, 0}, 
				{ 0, 0, 0, 0, 0, 2, 0, 0, 0}, 
				{ 0, 0, 0, 0, 0, 0, 0, 0, 0}};

		// Draws counters on grid
		for (int i = 0; i < lines; i++) {
			for (int j = 0; j < lines; j++) {
				if (exampleBoard[i][j] == 1) {
					g.setColor(Color.black);
					g.fillOval(j * squareSize - stoneSize, i * squareSize
							- stoneSize, squareSize, squareSize);
				} else if (exampleBoard[i][j] == 2) {
					// draw border of white stones
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
