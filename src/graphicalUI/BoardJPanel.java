package graphicalUI;

import java.awt.*;
import java.awt.event.*;
import javax.swing.JPanel;
import main.Board;
import main.Coordinate;
import main.GameEngine;
import ai.Objective;
import ai.AIException;

/**
 * Object for graphical representation of the board within the GUI. Graphics
 * includes board, coordinates, transparent stones and handicaps. Reacts to
 * user's mouse selection on board - placing stones, deleting stones, setting
 * bounds and calling AI move reaction to user play.
 */
@SuppressWarnings("serial")
public class BoardJPanel extends JPanel {

	// Constants
	private static final int BOARD_LENGTH = 600;
	private static int lines;
	private Board board, greyCounters;
	public static byte colour = 1;
	public static GameEngine gameE;
	public int numStones = 0;
	static boolean listeners, AIvsAI, humanVShuman, boundsCheck,
			boundsSelectionMode; // booleans for specific mode conditions
	private static boolean updated;

	// Board constructor
	public BoardJPanel(GameEngine gameEngine) {

		// Create panel
		super();
		setPreferredSize(new Dimension(BOARD_LENGTH, BOARD_LENGTH));

		// Set private variables
		gameE = gameEngine;
		board = gameEngine.getCurrentBoard();
		lines = board.getHeight();
		greyCounters = new Board(lines, lines);
		boundsCheck = gameE.hasBounds();
		listeners = true;
		updated = true;

		// Add stone to board when user clicks
		this.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (!listeners)
					return; // No reaction when listeners disabled

				// Create blank board for transparent stones each user click
				greyCounters = new Board(lines, lines);
				repaint();

				int squareSize = BOARD_LENGTH / (lines + 1);
				double x = e.getPoint().getX() - squareSize;
				double y = e.getPoint().getY() - squareSize;

				// Get position of stone in relation to mouse position
				int xPos = (int) x / squareSize;
				int xRemainder = (int) x - (xPos * squareSize);
				if (xRemainder > squareSize / 2)
					xPos++;

				int yPos = (int) y / squareSize;
				int yRemainder = (int) y - (yPos * squareSize);
				if (yRemainder > squareSize / 2)
					yPos++;

				// Place stone when close enough to intersection
				int border = squareSize / 4;

				if ((xRemainder < squareSize / 2 - border)
						|| (xRemainder > squareSize / 2 + border)
						&& (yRemainder < squareSize / 2 - border)
						|| (yRemainder > squareSize / 2 + border)) {
					// Check if user is attempting to delete stone
					if (GraphicalUI.getDeleteStones()
							&& !GraphicalUI.getCompetitive()) {
						// Remove selected stone from board
						gameE.getCurrentBoard().set(xPos, yPos, Board.EMPTY);
						GraphicalUI.updateMessage("Stone removed from: " + xPos
								+ ", " + yPos);
						repaint();
						// Check if user is attempting to add to bounds
					} else if (BoardJPanel.boundsSelectionMode
							&& !GraphicalUI.getCompetitive()) {
						// If there are no bounds already there, then add them
						if (gameE.getCurrentBoard().get(xPos, yPos) == (Board.EMPTY)) {
							gameE.getCurrentBoard().set(xPos, yPos,
									Board.EMPTY_AI);
							GraphicalUI.updateMessage("Bounds added at: "
									+ xPos + ", " + yPos);
							repaint();
						} // If there are already bounds, then remove them
						else if (gameE.getCurrentBoard().get(xPos, yPos) == (Board.EMPTY_AI)) {
							gameE.getCurrentBoard()
									.set(xPos, yPos, Board.EMPTY);
							GraphicalUI.updateMessage("Bounds removed from: "
									+ xPos + ", " + yPos);
							repaint();
						}
						// Else adding stone to board (default: black on first
						// click)
					} else {
						updated = updateBoard(xPos, yPos, colour);
					}
				} else {
					GraphicalUI.updateMessage("Select Closer To Intersection");
					updated = false;
				}

				// Let AI move if stone placed, listeners activated and correct
				// mode
				if (updated && listeners && !humanVShuman) {
					GUIAIMove();
				}

				repaint();
			}
		});

		// Show transparent grey stones when mouse hovers
		this.addMouseMotionListener(new MouseAdapter() {
			public void mouseMoved(MouseEvent e) {
				if (!listeners)
					return; // No reaction when listeners disabled

				// Create blank board for transparent stones each mouse move
				greyCounters = new Board(lines, lines);
				repaint();

				int squareSize = BOARD_LENGTH / (lines + 1);
				double x = e.getPoint().getX() - squareSize;
				double y = e.getPoint().getY() - squareSize;

				// Get current position of mouse in relation to intersection
				int xPos = (int) x / squareSize;
				int xRemainder = (int) x - (xPos * squareSize);
				if (xRemainder > squareSize / 2) {
					xPos++;
				}
				int yPos = (int) y / squareSize;
				int yRemainder = (int) y - (yPos * squareSize);
				if (yRemainder > squareSize / 2) {
					yPos++;
				}

				// Show appropriate transparent stones when close enough to
				// intersection
				int border = squareSize / 4;

				if (xPos >= 0 && yPos >= 0 && xPos < lines && yPos < lines) {
					if ((xRemainder < squareSize / 2 - border)
							|| (xRemainder > squareSize / 2 + border)
							&& (yRemainder < squareSize / 2 - border)
							|| (yRemainder > squareSize / 2 + border)) {
						boolean[][] legalMoves = gameE.getLegalMoves(colour);
						// Show grey stones when deleting
						if (GraphicalUI.getDeleteStones()) {
							greyCounters.set(xPos, yPos, (byte) -1);
						} // Show colour of stone being placed
						else if (legalMoves[xPos][yPos]) {
							greyCounters.set(xPos, yPos, colour);
						} // Show red stones for illegal moves
						else {
							greyCounters.set(xPos, yPos, (byte) 3);
						}
						repaint();
					}
				}
			}
		});
	}

	/*
	 * Draws stone onto intersection selected (x,y) with the colour (c)
	 * provided.
	 * 
	 * @return success of updating and repainting of the board
	 */
	public boolean updateBoard(int x, int y, int c) {
		try {
			if (gameE.makeMove(new Coordinate(x, y), c, !humanVShuman)) {
				// Move made, repaint board
				repaint();

				// Change player when mixed stones selected or competitive mode
				if (GraphicalUI.getMixedStones()
						|| GraphicalUI.getCompetitive()) {
					changePlayer();
				}

				GraphicalUI.player.setText(getPlayer() + " to move");
				GraphicalUI.updateMessage(getColour(c) + " to position: " + x
						+ ", " + y);
				numStones = numStones + 1;
				return true;
			} else {
				GraphicalUI.updateMessage("Invalid move");
				return false;
			}
		} catch (Exception e) {
			// Occasional error when clicking outside board intersections
			System.out.println("Mouse outwith board intersections.");
		}
		return false;
	}

	/*
	 * Lets AI make a move on the board when competitive play mode selected with
	 * bounds and objective set by the user.
	 */
	public void GUIAIMove() {
		boolean competitive = GraphicalUI.getCompetitive();
		
		// Check for competitive mode, bounds and objective
		// If check successful -> turn off listeners and call AI
		if (competitive && gameE.getObjective() != null && boundsCheck) {
			listeners = false;
			switch (GraphicalUI.aiType) {
			case "MiniMax":
				gameE.setMiniMax(colour);
				break;
			case "AlphaBeta":
				gameE.setAlphaBeta(colour, GraphicalUI.heuristics);
				break;
			case "HybridMiniMax":
				gameE.setHybridMinimax(colour, GraphicalUI.heuristics);
				break;
			default:
				System.out
						.println("WARNING: AI not found. MiniMax selected by default.");
				gameE.setMiniMax(colour);
				break;
			}
			
			// Swap AI types in AI vs AI mode
			// Allows 2 different AIs to play against each other
			if (PlayerChooseDialog.AIvsAI) {
				String nextAI = GraphicalUI.aiType2;
				GraphicalUI.aiType2 = GraphicalUI.aiType;
				GraphicalUI.aiType = nextAI;
			}
			
			// Allow AI to make move on current board, return feedback
			String move;
			try {
				move = gameE.aiMove();
			} catch (AIException e) {
				move = e.getMsg();
			}
			GraphicalUI.updateMessage("AI move: " + move);
			GraphicalUI.aiLabel.setText(GraphicalUI.aiType
					+ "'s considered moves: ");
			GraphicalUI.currentAILabel.setText(Integer.toString(gameE.getAI()
					.getNumberOfMovesConsidered()));
			changePlayer();

			// Show AI's move without user mouse movement
			repaint();

			// Allow user to move next if not AI vs AI mode
			if (!AIvsAI) listeners = true;
			
		} else if (competitive
				&& (gameE.getObjective() == null || !boundsCheck)) {
			GraphicalUI.updateMessage("Please specify bounds and objective");
		}
	}

	// Load and paint board from current GameEngine
	public void loadBoard(GameEngine ge) {
		gameE = ge;
		repaint();
	}

	// Paint board using Graphics package
	public void paint(Graphics g) {

		// Drawing grey background behind board
		g.setColor(Color.lightGray);
		g.fillRect(0, 0, BOARD_LENGTH, BOARD_LENGTH);

		// Frame variables
		lines = gameE.getCurrentBoard().getHeight();
		int squareSize = (BOARD_LENGTH) / (lines + 1);
		int stoneSize = squareSize / 2;

		// Board colour and border fill
		g.setColor(Color.black);
		g.fillRect(0, 0, squareSize * (lines + 1), squareSize * (lines + 1));
		g.setColor(new Color(205, 133, 63)); // RGB of brown colour
		g.fillRect(1, 1, squareSize * (lines + 1) - 2, squareSize * (lines + 1)
				- 2);

		// Draw search space as grey rectangles when specified
		// and bounds button selected
		if (GraphicalUI.getBounds()) {
			if (boundsCheck || boundsSelectionMode) {
				g.setColor(Color.gray); // Re-colour board as grey
				g.fillRect(1, 1, squareSize * (lines + 1) - 2, squareSize
						* (lines + 1) - 2);

				// Re-colour brown rectangles included in bounds
				for (int x = 0; x < lines; x++) {
					for (int y = 0; y < lines; y++) {
						if (board.get(x, y) == 3) {
							g.setColor(new Color(205, 133, 63));
							g.fillRect((squareSize + (x - 1) * squareSize)
									+ (squareSize / 2), (squareSize + (y - 1)
									* squareSize)
									+ (squareSize / 2), squareSize, squareSize);

						}
					}
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

		// Show handicap circles on Go Board
		// For 9x9 - show 5 circles
		if (lines == 9) {
			g.setColor(Color.black);
			// create 5 small ovals in place
			g.fillOval(squareSize + 2 * squareSize - 10, squareSize + 2
					* squareSize - 10, 20, 20);
			g.fillOval(squareSize + 6 * squareSize - 10, squareSize + 2
					* squareSize - 10, 20, 20);
			g.fillOval(squareSize + 2 * squareSize - 10, squareSize + 6
					* squareSize - 10, 20, 20);
			g.fillOval(squareSize + 6 * squareSize - 10, squareSize + 6
					* squareSize - 10, 20, 20);
			g.fillOval(squareSize + 4 * squareSize - 10, squareSize + 4
					* squareSize - 10, 20, 20);
		}

		// For 13x13 - show 5 circles
		if (lines == 13) {
			g.setColor(Color.black);
			// create 5 small ovals in place
			g.fillOval(squareSize + 3 * squareSize - 8, squareSize + 3
					* squareSize - 8, 17, 17);
			g.fillOval(squareSize + 9 * squareSize - 8, squareSize + 3
					* squareSize - 8, 17, 17);
			g.fillOval(squareSize + 6 * squareSize - 8, squareSize + 6
					* squareSize - 8, 17, 17);
			g.fillOval(squareSize + 3 * squareSize - 8, squareSize + 9
					* squareSize - 10, 17, 17);
			g.fillOval(squareSize + 9 * squareSize - 8, squareSize + 9
					* squareSize - 8, 17, 17);
		}

		// For 19x19 - show 9 circles
		if (lines == 19) {
			g.setColor(Color.black);
			// create 9 small ovals in place
			g.fillOval(squareSize + 3 * squareSize - 7, squareSize + 3
					* squareSize - 7, 14, 14);
			g.fillOval(squareSize + 9 * squareSize - 7, squareSize + 3
					* squareSize - 7, 14, 14);
			g.fillOval(squareSize + 15 * squareSize - 7, squareSize + 3
					* squareSize - 7, 14, 14);
			g.fillOval(squareSize + 9 * squareSize - 7, squareSize + 9
					* squareSize - 7, 14, 14);
			g.fillOval(squareSize + 9 * squareSize - 7, squareSize + 15
					* squareSize - 7, 14, 14);
			g.fillOval(squareSize + 3 * squareSize - 7, squareSize + 9
					* squareSize - 7, 14, 14);
			g.fillOval(squareSize + 3 * squareSize - 7, squareSize + 15
					* squareSize - 7, 14, 14);
			g.fillOval(squareSize + 15 * squareSize - 7, squareSize + 15
					* squareSize - 7, 14, 14);
			g.fillOval(squareSize + 15 * squareSize - 7, squareSize + 9
					* squareSize - 7, 14, 14);
		}

		// Draws stones onto board
		board = gameE.getCurrentBoard();
		for (int i = 0; i < lines; i++) {
			for (int j = 0; j < lines; j++) {
				if (board.get(i, j) == 1) {
					// Draw black stones
					g.setColor(Color.black);
					g.fillOval(squareSize + i * squareSize - stoneSize,
							squareSize + j * squareSize - stoneSize,
							squareSize, squareSize);
				} else if (board.get(i, j) == 2) {
					// Draw white stones
					g.setColor(Color.black);
					g.fillOval((squareSize + i * squareSize - stoneSize) - 2,
							(squareSize + j * squareSize - stoneSize) - 2,
							squareSize + 4, squareSize + 4);
					g.setColor(Color.white);
					g.fillOval(squareSize + i * squareSize - stoneSize,
							squareSize + j * squareSize - stoneSize,
							squareSize, squareSize);
				}
			}
		}

		// Paint transparent stones due to mouse hovering
		if (board.getHeight() == greyCounters.getHeight()
				&& !boundsSelectionMode) {
			for (int i = 0; i < lines; i++) {
				for (int j = 0; j < lines; j++) {
					// Show transparent black stones
					if (greyCounters.get(i, j) == 1) {
						g.setColor(new Color(0, 0, 0, 50));
						g.fillOval(
								(squareSize + i * squareSize - stoneSize) - 2,
								(squareSize + j * squareSize - stoneSize) - 2,
								squareSize + 4, squareSize + 4);
						// Show transparent white stones
					} else if (greyCounters.get(i, j) == 2) {
						g.setColor(new Color(255, 255, 255, 50));
						g.fillOval(
								(squareSize + i * squareSize - stoneSize) - 2,
								(squareSize + j * squareSize - stoneSize) - 2,
								squareSize + 4, squareSize + 4);
					} else if (greyCounters.get(i, j) == 3) {
						// Show transparent red stones - illegal
						g.setColor(new Color(125, 0, 0, 150));
						g.fillOval(
								(squareSize + i * squareSize - stoneSize) - 2,
								(squareSize + j * squareSize - stoneSize) - 2,
								squareSize + 4, squareSize + 4);
					} else if (greyCounters.get(i, j) == -1) {
						// Show transparent grey stones when deleting
						g.setColor(new Color(50, 50, 50, 150));
						g.fillOval(
								(squareSize + i * squareSize - stoneSize) - 2,
								(squareSize + j * squareSize - stoneSize) - 2,
								squareSize + 4, squareSize + 4);
					}
				}
			}
		}

		// Paints coordinates 
		if (GraphicalUI.getRowNumbers()) {
			for (int i = 1; i <= lines; i++) {
				g.setColor(new Color(0, 0, 0, 255));
				g.setFont(new Font("Ariel", Font.BOLD, 14));
				// Print row numbers, skipping 0
				g.drawString(Integer.toString(i - 1), i * squareSize - 4,
						(squareSize - 4) / 2);
				if (lines <= 9) {
					g.drawString(Integer.toString(i - 1), i * squareSize - 4,
							BOARD_LENGTH - (squareSize - 4) / 5);
				} else if (lines >= 19) {
					g.drawString(Integer.toString(i - 1), i * squareSize - 4,
							BOARD_LENGTH - (squareSize - 4) / 5);
				} else {
					g.drawString(Integer.toString(i - 1), i * squareSize - 4,
							BOARD_LENGTH - 15);
				}
			}
			// Print column numbers
			for (int j = 1; j <= lines; j++) {
				g.setColor(new Color(0, 0, 0, 255));
				g.drawString(Integer.toString(j - 1), (squareSize - 18) / 5, j
						* squareSize);
				if (lines <= 9) {
					g.drawString(Integer.toString(j - 1), BOARD_LENGTH - 1
							* (squareSize - 1) / 5, j * squareSize);
				} else if (lines >= 19) {
					String coord = Integer.toString(j - 1);
					if ((j - 1) < 10)
						coord = "  " + coord;
					g.drawString(coord,
							BOARD_LENGTH - 3 * (squareSize - 1) / 5, j
									* squareSize);
				} else {
					String coord = Integer.toString(j - 1);
					if ((j - 1) < 10)
						coord = " " + coord;
					g.drawString(coord,
							BOARD_LENGTH - 4 * (squareSize - 1) / 5, j
									* squareSize);
				}
			}
		}

	}

	// Change colour of current player
	// Called after moves, stone placement and manual calls
	public void changePlayer() {
		if (colour == 1) {
			colour++;
		} else {
			colour--;
		}
		GraphicalUI.player.setText(getPlayer() + " to move");
	}

	// Returns colour of current player
	public static String getPlayer() {
		if (colour == 1) {
			return "Black";
		} else {
			return "White";
		}
	}

	// Translates colour String to int representation
	public static int translatePlayer(String col) {
		if (col == "Black") {
			return 1;
		} else {
			return 2;
		}
	}

	// Sets current player colour
	public static void setPlayer(String player) {
		if (player == "black") {
			colour = Board.BLACK;
		}
		if (player == "white") {
			colour = Board.WHITE;
		}
	}

	// Set current player colour
	public static void setPlayerInt(int player) {
		if (player == 1) {
			colour = Board.BLACK;
		}
		if (player == 2) {
			colour = Board.WHITE;
		}
	}

	// Set objective within Game Engine
	public static void setObjective(Objective objective) {
		gameE.setObjective(objective);
	}

	// Returns number of lines of current board (height/width)
	public static int getLines() {
		return lines;
	}

	// Returns colour of stone
	public String getColour(int c) {
		if (c == 1) {
			return "Black";
		} else if (c == 2) {
			return "White";
		} else {
			return "Blank";
		}

	}

	// Returns GUI's board's GameEngine object
	public GameEngine getGE() {
		return gameE;
	}

}
