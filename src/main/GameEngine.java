package main;

import ai.MiniMax;
import ai.AI;
import ai.Objective;
import ai.AlphaBeta;
import ai.AIException;
import ai.HybridMinimax;
/**
 * Class used for the basic playing
 * functions(making moves, gets legal moves form 
 * Legal move checker)
 *
 */
public class GameEngine {
  //* Class to keep track of the current game of Go and make moves on the board 

    // Instance of LegalMoveChecker, used in functions to check the legality of moves 
    private LegalMoveChecker moveChecker;
    private Board currentBoard; // The object representing the current Go board 
    private AI ai;
    private Objective objective;
    private boolean inGame;
    private Board initialBoard;

    // Default Constructor
    public GameEngine() {
        this(new Board());
    }

    //Initialises GameEngine with existing board.
    public GameEngine(Board board) {
        this(board, null);
    }

    //Initialises GameEngine with Board, Objective.
    public GameEngine(Board board, Objective objective) {
        currentBoard = initialBoard = board;
        this.objective = objective;
        moveChecker = new LegalMoveChecker();
    }

    // Checks entire board for legal moves and returns them as a 2D boolean array
    public boolean[][] getLegalMoves(int colour) {
        int xDim = currentBoard.getWidth();
        int yDim = currentBoard.getHeight();
        boolean[][] legalMoves = new boolean[xDim][yDim];

        for (int i = 0; i < xDim; i++) {
            for (int j = 0; j < yDim; j++) {
                legalMoves[i][j] = moveChecker.checkMove(currentBoard, new Coordinate(i, j), colour, false);
            }
        }

        return legalMoves;
    }

    // Returns the current board to the caller 
    public Board getCurrentBoard() {
        return currentBoard;
    }

    //Returns objective to caller
    public Objective getObjective() {
        return objective;
    }

    //Returns ai search values
    public AI getAI() {
        return ai;
    }

    // Checks if the game has already been started by startGame()
    public boolean isInGame() {
        return inGame;
    }

    //Sets game to start, enabling moves 
    public void startGame() {
        inGame = true;
    }

    // Setters for search space and objective for GUI use
    //Sets the objective on the game engine
    public void setObjective(Objective objective) {
        this.objective = objective;
    }

    //Sets the AI to be a minimax algorithm
    public void setMiniMax(int colour) {
        ai = new MiniMax(objective, colour);
    }
    
    //Sets AI to be the heuristics using minimax algorithm.
    public void setMagicalMiniMax(int colour, String[] heuristicNames){
        ai = new HybridMinimax(objective, colour, heuristicNames);
    }

    // Sets the AI to be an alpha beta algorithm
    public void setAlphaBeta(int colour, String[] heuristicNames) {
        ai = new AlphaBeta(objective, colour, heuristicNames);
    }

    //Places a piece at the co-ordinates (x,y) given a respective colour (black or white
    public String aiMove() throws AIException {
        Coordinate c = ai.nextTimedMove(currentBoard, moveChecker);
        if (c.x == -1 && c.y == -1) {
            return "passes";
        } else {
            makeMove(c, ai.getColour(), true);
            return "moves to (" + c.x + "," + c.y + ")";
        }
    }

    //Checks whether the move is legal and if so, place the piece and return true
    //if the move is illegal, return false    
    public boolean makeMove(Coordinate coord, int colour, boolean aiMove) {
        if (moveChecker.checkMove(currentBoard, coord, colour, aiMove)) {
            moveChecker.addBoard(currentBoard);
            currentBoard = moveChecker.getLastLegal();
            return true;
        } else {
            return false;
        }
    }

    //Attempts to undo last move. If there are no more moves, returns false.
    public boolean undoLastMove() {
        if (moveChecker.isEmpty()) {
            return false;
        }
        currentBoard = moveChecker.removeLast();
        return true;
    }

    //Restarts board by undoing all moves on the board.
    public boolean restartBoard() {
        if (moveChecker.isEmpty()) {
            return false;
        }
        currentBoard = initialBoard;
        moveChecker = new LegalMoveChecker();
        return true;
    }

    //Checks if the board has bounds
    public boolean hasBounds() {
        for (int x = 0; x < currentBoard.getWidth(); x++) {
            for (int y = 0; y < currentBoard.getHeight(); y++) {
                if (currentBoard.get(x, y) == Board.EMPTY_AI) {
                    return true;
                }
            }
        }
        return false;
    }
}
