import java.util.Arrays;


public class Board {
	
	private int[][] boardRep;
	private static final int DIMENSION = 9; //Default dimension for empty boards
	
	// Create empty board
	public Board() {
		boardRep = new int[DIMENSION][DIMENSION];
	}
	
	// Create new board from given character array
	public Board(char[][] board) {

		int width = board.length;
		int height = board[0].length;
		boardRep = new int[width][height];

		for (int row = 0; row < boardRep.length; row++) 
			for (int column = 0; column < boardRep[0].length; column++)
				boardRep[row][column] = translate(board[row][column]);
	}

	//Create an empty board that takes in a pair of dimensions
	public Board(int width, int height){
		boardRep = new int[width][height];
	}
	
	// Get x and y dimensions of board
	public int getxDimension(){return boardRep.length;}
	public int getyDimension(){return boardRep[0].length;}
	
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
	
	// Method for translation of char positions to int values
	public int translate(char value) {
		if (value == '.') return 0;
		else if (value == 'b') return 1;
		else return 2;
	}
	
	/** Code to test board is working 
	public static void main(String [] args) {
		Board b = new Board();
		int x = 0; int y = 0;
		while (x<9) {
			while (y<9) {
				//System.out.print(b.get(x,y));
				y++;
			}
			//System.out.print("\n");
			x++;
			y = 0;
		}
	}**/
	
}
