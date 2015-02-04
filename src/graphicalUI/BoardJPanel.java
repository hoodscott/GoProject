package graphicalUI;

import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;

import javax.swing.JPanel;

import main.Board;
import main.Coordinate;
import main.GameEngine;
import main.Objective;
import main.AIException;

@SuppressWarnings("serial")
public class BoardJPanel extends JPanel {

    // constants
    private static final int BOARD_LENGTH = 600;
    private static int lines;
    private Board board, greyCounters;
    public static int colour = 1;
    public static GameEngine gameE;
    public int numStones = 0;
    static int[] searchSpace;
    static boolean listeners;
    private static boolean updated;

    // Board constructor
    public BoardJPanel(GameEngine gameEngine) {

        // Create panel
        super();
        setPreferredSize(new Dimension(BOARD_LENGTH, BOARD_LENGTH));

        // set private variables
        gameE = gameEngine;
        board = gameEngine.getCurrentBoard();
        lines = board.getHeight();
        greyCounters = new Board(lines, lines);
        searchSpace = gameE.getAISearchValues();
        listeners = true;
        updated = true;

        // Add stone to board when user clicks
        this.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (!listeners) {
                    return;
                }
                greyCounters = new Board(lines, lines); // create blank board
                // each time
                // mouse clicks
                repaint();

                int squareSize = BOARD_LENGTH / (lines + 1);
                double x = e.getPoint().getX() - squareSize;
                double y = e.getPoint().getY() - squareSize;

                // Get position of counter
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

                // Update counter when close enough to intersection
                int border = squareSize / 4;

                if ((xRemainder < squareSize / 2 - border)
                        || (xRemainder > squareSize / 2 + border)
                        && (yRemainder < squareSize / 2 - border)
                        || (yRemainder > squareSize / 2 + border)) {
                    // check if user is attempting to delete stone
                    if (GraphicalUI.getDeleteStones()
                            && !GraphicalUI.getCompetitive()) {
                        // remove selected stone from board
                        gameE.getCurrentBoard().set(xPos, yPos, 0);
                        GraphicalUI.updateMessage("Stone removed from: "
                                + xPos + ", " + yPos);
                        repaint();
                        // else add stone to board
                    } else {
                        updated = updateBoard(xPos, yPos, colour); // SET TO
                        // BLACK
                        // STONE
                        // ON FIRST MOVE
                    }
                } else {
                    GraphicalUI.updateMessage("Select Closer To Intersection");
                    updated = false;
                }
                
                // Let AI move and repaint if selected
                GUIAIMove();
                
                repaint();
            }
        });

        // Show transparent grey stones
        this.addMouseMotionListener(new MouseAdapter() {
            public void mouseMoved(MouseEvent e) {
                if (!listeners) {
                    return;
                }
                greyCounters = new Board(lines, lines); // create blank board
                // each time
                // mouse moves
                repaint();

                int squareSize = BOARD_LENGTH / (lines + 1);
                double x = e.getPoint().getX() - squareSize;
                double y = e.getPoint().getY() - squareSize;

                // Get position of counter
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

                // Show transparent stones when close enough to intersection
                int border = squareSize / 4;

                // sanity check for hovering over the intersection
                if (xPos >= 0 && yPos >= 0 && xPos < lines && yPos < lines) {
                    // draw grey ghost counters on board
                    if ((xRemainder < squareSize / 2 - border)
                            || (xRemainder > squareSize / 2 + border)
                            && (yRemainder < squareSize / 2 - border)
                            || (yRemainder > squareSize / 2 + border)) {
                        boolean[][] legalMoves = gameE.getLegalMoves(colour);
                        // show grey stones when deleting
                        if (GraphicalUI.getDeleteStones()){
                        	greyCounters.set(xPos, yPos, -1);
                        }
                        // show colour of stone being placed
                        else if (legalMoves[xPos][yPos]) {
                            greyCounters.set(xPos, yPos, colour);
                        }
                        // show red stones for illegal moves
                        else {
                            greyCounters.set(xPos, yPos, 3);
                        }
                        repaint();
                    }
                }
            }
        });
    }

    // Draw counter onto position
    public boolean updateBoard(int x, int y, int c) {
    	
        if (gameE.makeMove(new Coordinate(x, y), c)) {
            // move made, repaint board
            repaint();

            // change player when mixed stones selected or competitive mode
            if (GraphicalUI.getMixedStones() || GraphicalUI.getCompetitive()) {
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
        
    }

    public void GUIAIMove() {
		// Let AI make move when competitive play mode selected with
        // bounds and objective
        boolean competitive = GraphicalUI.getCompetitive();
        if (competitive && gameE.getObjective() != null
                && gameE.getAISearchValues() != null && listeners && updated) {
            listeners = false;
            switch (GraphicalUI.aiType) {
                case "MiniMax":
                    gameE.setMiniMax(colour);
                    ;
                    break;
                case "AlphaBeta":
                    gameE.setAlphaBeta(colour);
                    ;
                    break;
                default:
                    gameE.setMiniMax(colour);
                    ;
                    break;
            }
            String move;
            try{
                move = gameE.aiMove();
            }
            catch(AIException e){move = e.getMsg();}
            
            GraphicalUI.updateMessage("AI move: " + move);
            changePlayer();
            
            // Show AI's move without mouse movement
            repaint();
            listeners = true;
        } else if (competitive
                && (gameE.getObjective() == null || gameE.getAISearchValues() == null)) {
            GraphicalUI.updateMessage("Please specify bounds and objective");
        }
    }
 
    // Load and draw board
    public void loadBoard(GameEngine ge) {
        gameE = ge;
        repaint();
    }

    // Draw board
    public void paint(Graphics g) {

        // drawing t gray box behind board to account for rounding
        g.setColor(Color.lightGray);
        g.fillRect(0, 0, BOARD_LENGTH, BOARD_LENGTH);

        // Frame variables
        lines = gameE.getCurrentBoard().getHeight();
        int squareSize = (BOARD_LENGTH) / (lines + 1);
        int stoneSize = squareSize / 2;
        int[] noBounds = {0, 0, lines, lines};

        // Board colour and border fill
        g.setColor(Color.black);
        g.fillRect(0, 0, squareSize * (lines + 1), squareSize * (lines + 1));
        g.setColor(new Color(205, 133, 63)); // rgb of brown colour
        g.fillRect(1, 1, squareSize * (lines + 1) - 2, squareSize * (lines + 1)
                - 2);

		// Draw search space as grey rectangles when specified
        // and bounds button selected
        if (GraphicalUI.getBounds()) {
            if (searchSpace != null && !Arrays.equals(searchSpace, noBounds)) {
                g.setColor(Color.gray); // re-colour board as grey
                g.fillRect(1, 1, squareSize * (lines + 1) - 2, squareSize
                        * (lines + 1) - 2);

				// re-colour brown rectangles included in search space
                // NOTE: assumes 0,0 as top left co-ordinate
                int x2 = searchSpace[2];
                int y2 = searchSpace[3];
                for (int x1 = searchSpace[0]; x1 <= x2; x1++) {
                    for (int y1 = searchSpace[1]; y1 <= y2; y1++) {
                        g.setColor(new Color(205, 133, 63));
                        g.fillRect((squareSize + (x1-1) * squareSize)+(squareSize/2), (squareSize
                                + (y1-1) * squareSize)+(squareSize/2), squareSize, squareSize);
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

		// Show circles on Go Board
        // For 9x9 - Show 5 circles
        if (lines == 9) {
            g.setColor(Color.black);
            // Create 5 small ovals in place
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

        // For 13x13 - Show 5 circles
        if (lines == 13) {
            g.setColor(Color.black);
            // Create 5 small ovals in place
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

        // For 19x19 - Show 9 circles
        if (lines == 19) {
            g.setColor(Color.black);
            // Create 9 small ovals in place
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

        // Draws counters on grid
        board = gameE.getCurrentBoard();
        for (int i = 0; i < lines; i++) {
            for (int j = 0; j < lines; j++) {
                if (board.get(i, j) == 1) {
                    // draw black stones
                    g.setColor(Color.black);
                    g.fillOval(squareSize + i * squareSize - stoneSize,
                            squareSize + j * squareSize - stoneSize,
                            squareSize, squareSize);
                } else if (board.get(i, j) == 2) {
                    // white stones with border
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

        // Show grey transparent counters
        if (board.getHeight() == greyCounters.getHeight()) {
            for (int i = 0; i < lines; i++) {
                for (int j = 0; j < lines; j++) {
                    // show transparent black stones
                    if (greyCounters.get(i, j) == 1) {
                        g.setColor(new Color(0, 0, 0, 50));
                        g.fillOval(
                                (squareSize + i * squareSize - stoneSize) - 2,
                                (squareSize + j * squareSize - stoneSize) - 2,
                                squareSize + 4, squareSize + 4);
                        // show transparent white stones
                    } else if (greyCounters.get(i, j) == 2) {
                        g.setColor(new Color(255, 255, 255, 50));
                        g.fillOval(
                                (squareSize + i * squareSize - stoneSize) - 2,
                                (squareSize + j * squareSize - stoneSize) - 2,
                                squareSize + 4, squareSize + 4); // show
                        // transparent
                        // red
                        // stones -
                        // illegal
                    } else if (greyCounters.get(i, j) == 3) {
                        g.setColor(new Color(125, 0, 0, 150));
                        g.fillOval(
                                (squareSize + i * squareSize - stoneSize) - 2,
                                (squareSize + j * squareSize - stoneSize) - 2,
                                squareSize + 4, squareSize + 4);
                    }
                    else if (greyCounters.get(i, j) == -1) {
                    	// show transparent grey stones when deleting
                    	g.setColor(new Color(50,  50, 50, 150));
                        g.fillOval(
                                (squareSize + i * squareSize - stoneSize) - 2,
                                (squareSize + j * squareSize - stoneSize) - 2,
                                squareSize + 4, squareSize + 4);
                    }
                }
            }
        }
        
        // shows row numbers
        if (GraphicalUI.getRowNumbers()){
        	// print row nums execpt 0
        	for (int i = 2; i <= lines; i++){
        		g.setColor(new Color(0,0,0,255));
        		g.setFont(new Font("Ariel", Font.BOLD, 14));
        		// skip 0 so numbers look more presentable
        		g.drawString(Integer.toString(i-1),i * squareSize-4, squareSize-4);
        	}
        	// print column nums
        	for (int j = 1; j <= lines; j++){
        		g.setColor(new Color(0,0,0,255));
        		g.drawString(Integer.toString(j-1),squareSize-18, j * squareSize);
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
        GraphicalUI.player.setText(getPlayer() + " to move");
    }

    public static String getPlayer() {
        if (colour == 1) {
            return "Black";
        } else {
            return "White";
        }
    }
    
    public static int translatePlayer(String col) {
        if (col == "Black") {
            return 1;
        } else {
            return 2;
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

    public static void setBounds(int[] aiSearchValues) {
        searchSpace = aiSearchValues;
        gameE.setBounds(aiSearchValues);
    }
    
    public static void resetBounds(){
    	searchSpace  = null;
    }

    public static void setObjective(Objective objective) {
        gameE.setObjective(objective);
    }

    public static int getLines() {
        return lines;
    }

    public String getColour(int c) {
        if (c == 1) {
            return "Black";
        } else if (c == 2) {
            return "White";
        } else {
            return "Blank";
        }

    }

}
