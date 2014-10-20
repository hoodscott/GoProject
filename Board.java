
public class Board implements Cloneable {
	
	private int[][] boardRep;
	
	// Create empty board (9x9?)
	public Board() {
		boardRep = new int[9][9];
		for (int row = 0; row < boardRep.length; row++) {
			for (int column = 0; column < boardRep.length; column++)
				boardRep[row][column] = 0;
		}
	}
	
	// Create new board (9x9?) from given character array
	public Board(char[][] board) {
		boardRep = new int[9][9];
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
	
	
	// Not sure how to do this?
	public Board clone() {
		return this;
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
		b.set(1, 1, 1);
		b.set(2,3,1);
		int x = 0; int y = 0;
		while (x<9) {
			while (y<9) {
				System.out.print(b.get(x,y));
				y++;
			}
			System.out.print("\n");
			x++;
			y = 0;
		}
	} **/
	
}
