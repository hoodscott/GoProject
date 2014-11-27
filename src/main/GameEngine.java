package main;

public class GameEngine{
  /** Class to keep track of the current game of Go and make moves on the board */
  
  /* Instance of LegalMoveChecker, used in functions to check the legality of moves */
  private LegalMoveChecker moveChecker;
  /* The object representing the current Go board */
  private Board currentBoard;
  private AI ai;
  private Objective objective;
  private boolean boardInstantiated;
  private boolean inGame;

  // Default Constructor
  public GameEngine(){this(new Board());}
  
  //Initialises GameEngine with existing board.
  public GameEngine(Board board){
    currentBoard = board;
    moveChecker = new LegalMoveChecker();
    boardInstantiated = true;
  }
  
  //Initialises GameEngine with Board, Objective and InitialAI data.
  public GameEngine(Board board, Objective objective, AI ai){
      currentBoard = board;
      this.objective = objective;
      this.ai = ai;
      boardInstantiated = true;
      moveChecker = new LegalMoveChecker();
  }

  /* Checks entire board for legal moves and returns them as a 2D boolean array */
  public boolean[][] getLegalMoves(int colour){
    int xDim = currentBoard.getWidth();
    int yDim = currentBoard.getHeight();
    boolean[][] legalMoves = new boolean[xDim][yDim];
    
    for (int i = 0; i<xDim; i++)
      for (int j = 0; j<yDim; j++)
        legalMoves[i][j] =  moveChecker.checkMove(currentBoard, i, j, colour);
      
    return legalMoves;
  }

  /* Returns the current board to the caller */
  public Board getCurrentBoard(){
    return currentBoard;
  }
  
  /* Checks if a board has already been instantiatey by newGame() or not.*/
  public boolean boardExists(){
    return boardInstantiated;
  }

  /*Places a piece at the co-ordinates (x,y) given a respective colour (black or white
  Checks whether the move is legal and if so, place the piece and return true
  if the move is illegal, return false */
  public boolean makeMove(int x, int y, int colour){
    if (moveChecker.checkMove(currentBoard, x, y, colour)){
      moveChecker.addBoard(currentBoard);
      currentBoard = moveChecker.getLastLegal();
      return true;
    }
    else
      return false;
  }

  public boolean undoLastMove(){
    if(moveChecker.isEmpty())
      return false;
    currentBoard = moveChecker.removeLast();
    return true;
  }
}