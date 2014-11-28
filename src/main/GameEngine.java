package main;

public class GameEngine{
  //* Class to keep track of the current game of Go and make moves on the board 
  
  // Instance of LegalMoveChecker, used in functions to check the legality of moves 
  private LegalMoveChecker moveChecker;
  // The object representing the current Go board 
  private Board currentBoard;
  private AI ai;
  private Objective objective;
  private boolean inGame;
  private int[] aiSearchValues;

  // Default Constructor
  public GameEngine(){this(new Board());}
  
  //Initialises GameEngine with existing board.
  public GameEngine(Board board){this(board, null);}
  
  //Initialises GameEngine with Board and Objective .
  public GameEngine(Board board, Objective objective){this(board,objective,null);}
  
  //Initialises GameEngine with Board, Objective and searchValues to be used by the AI.
  public GameEngine(Board board, Objective objective, int[] searchValues){
      currentBoard = board;
      this.objective = objective;
      moveChecker = new LegalMoveChecker();
      aiSearchValues = searchValues;
  }

  // Checks entire board for legal moves and returns them as a 2D boolean array
  public boolean[][] getLegalMoves(int colour){
    int xDim = currentBoard.getWidth();
    int yDim = currentBoard.getHeight();
    boolean[][] legalMoves = new boolean[xDim][yDim];
    
    for (int i = 0; i<xDim; i++)
      for (int j = 0; j<yDim; j++)
        legalMoves[i][j] =  moveChecker.checkMove(currentBoard, i, j, colour);
      
    return legalMoves;
  }

  // Returns the current board to the caller 
  public Board getCurrentBoard(){return currentBoard;}
  //Returns objective to caller
  public Objective getObjective(){return objective;}
  //Returns ai search values
  public int[] getAISearchValues(){return aiSearchValues;}
  
  // Checks if a board has already been instantiatey by newGame() or not.
  public boolean isInGame(){return inGame;}
  
  //Sets the AI to be a minimax algorithm
  public void setMiniMax(int colour){ai = new MiniMax(objective, colour);}

  //Places a piece at the co-ordinates (x,y) given a respective colour (black or white
  //Checks whether the move is legal and if so, place the piece and return true
  //if the move is illegal, return false 
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