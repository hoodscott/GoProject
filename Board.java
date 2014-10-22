import java.util.Arrays;


public class Board {
	
	private int[][] boardRep;
	private int dimension;
	
	// Create empty board (9x9)
	public Board() {
		this.dimension = 9;
		boardRep = new int[this.dimension][this.dimension];
		for (int row = 0; row < boardRep.length; row++) {
			for (int column = 0; column < boardRep.length; column++)
				boardRep[row][column] = 0;
		}
	}
	
	// Create new board from given character array
	public Board(char[][] board) {
		this.dimension = board.length;
		boardRep = new int[this.dimension][this.dimension];
		for (int row = 0; row < boardRep.length; row++) {
			for (int column = 0; column < boardRep.length; column++) {
				int boardPos = translate(board[row][column]);
				boardRep[row][column] = boardPos;
			}
		}
	}
	
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
		Board clone = new Board();
		int dimension = this.dimension;
		clone.boardRep = new int[dimension][dimension];
		for (int i = 0; i < this.boardRep.length; i++) {
			clone.boardRep[i] = Arrays.copyOf(this.boardRep[i],this.boardRep[i].length);
		}
		return clone;
	}
	
	// Method for translation of char positions to int values
	public int translate(char value) {
		String s = "" + value;
		if (s == ".") return 0;
		else if (s == "b") return 1;
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
