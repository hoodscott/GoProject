
public class Board {
	
	private int[][] boardRep;
	private static final int DIMENSION = 9; //Default dimension for empty boards
	
	// Create empty board
	public Board() {
		boardRep = new int[DIMENSION][DIMENSION];
	}
	
	// Create new board from given int array
	public Board(int[][] board) {
		boardRep = board;
	}

	//Create an empty board that takes in a pair of dimensions
	public Board(int width, int height){
		boardRep = new int[width][height];
	}
	
	// Get x and y dimensions of board
	public int getWidth(){return boardRep.length;}
	public int getHeight(){return boardRep[0].length;}

	//Get raw representation of the board
	public int[][] getRaw(){return boardRep;}
	
	// Get int representation of board position
	public int get(int x, int y) {
		return boardRep[x][y];
	}
	
	// Set int representation of board position
	public void set(int x, int y, int value) {
		boardRep[x][y] = value;
	}
	
	
	// Make deep copy of original Board
	public Board clone() {
		Board clone = new Board(boardRep.length, boardRep[0].length);
		for (int i = 0; i < boardRep.length; i++)
			for(int j = 0; j < boardRep[0].length; j++) 
				clone.set(i,j,boardRep[i][j]);

		return clone;
	}
	
}
