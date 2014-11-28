package main;

import java.awt.*;
import java.awt.event.*;

import javax.swing.JPanel;

public class BoardJPanel extends JPanel {

	// contants
	public static final int BOARD_LENGTH = 600;

	public static void main(String args[]) {
		new BoardJPanel();
	}

	public BoardJPanel() {

		// Create panel
		super();
		setPreferredSize(new Dimension(BOARD_LENGTH, BOARD_LENGTH));

	}

	// Draw board
	public void paint(Graphics g) {
		// Draw board of specified lines
		int lines = 9;
		int frameSize = BOARD_LENGTH ;
		int squareSize = (frameSize) / lines;
		int stoneSize = squareSize / 2;

		for (int x = 0; x < lines; x++) {
			for (int y = 0; y < lines; y++) {
				g.drawRect(x * squareSize, y * squareSize, squareSize,
						squareSize);
			}
		}

		int[][] exampleBoard = { { 0, 0, 0, 1, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 2, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 1, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 2, 1, 0, 0 }, { 0, 2, 0, 0, 0, 0, 0, 0, 0 } };

		// Draws counters on grid
		for (int i = 0; i < lines; i++) {
			for (int j = 0; j < lines; j++) {
				if (exampleBoard[i][j] == 1) {
					g.setColor(Color.black);
					g.fillOval(j * squareSize - stoneSize, i * squareSize
							- stoneSize, squareSize, squareSize);
				} else if (exampleBoard[i][j] == 2) {
					g.drawOval(j * squareSize - stoneSize, i * squareSize
							- stoneSize, squareSize, squareSize);
					g.setColor(Color.white);
					g.fillOval(j * squareSize - stoneSize, i * squareSize
							- stoneSize, squareSize, squareSize);
				}
			}
		}
	}

}
