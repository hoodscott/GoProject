public class GameEngine{
  /** Class to keep track of the current game of Go and make moves on the board */
  
  // Attributes
  /** Instance of LegalMoveChecker, used in functions to check the legality of moves */
  private LegalMoveChecker moveChecker;
  /** The object representing the current Go board */
  Board currentBoard;
  
  // Operations
  /** Default Constructor */
  public GameEngine(){
  }
  
  /** Initialises a new game with a blank board */
  public void newGame(){
    currentBoard = new Board();
    moveChecker = new LegalMoveChecker();
  }

  /** Initialises a new game with a scenario on the board */
  public void newGame(Board board){
    currentBoard = board;
    moveChecker = new LegalMoveChecker();
  }

  /** Checks entire board for legal moves and returns them as a 2D boolean array */
  public boolean[][] getLegalMoves(int colour){
	int xDim = currentBoard.getWidth();
	int yDim = currentBoard.getHeight();
    boolean[][] legalMoves = new boolean[xDim][yDim];
    for (int i = 0; i<xDim; i++){
      for (int j = 0; j<yDim; j++){
        legalMoves[i][j] =  moveChecker.checkMove(currentBoard, i, j, colour);
      }
    }
    return legalMoves;
  }

  /** Returns the current board to the caller */
  public Board getCurrentBoard(){
    return currentBoard;
  }

  /**Places a piece at the co-ordinates (x,y) given a respective colour (black or white
  Checks whether the move is legal and if so, place the piece and return true
  if the move is illegal, return false */
  public boolean makeMove(int x, int y, int colour){
    if (moveChecker.checkMove(currentBoard, x, y, colour)){
      moveChecker.addBoard(currentBoard);
      currentBoard.set(x, y, colour);
      return true;
    }
    else
      return false;
  }
}