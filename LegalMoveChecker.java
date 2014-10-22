import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class LegalMoveChecker {
	private ArrayList<int[][]> moveHistory; //= new ArrayList<int[][]>();
	private int libCounter;
	
	public LegalMoveChecker(){
		moveHistory = new ArrayList<int[][]>();
		libCounter = 0;
	}
	
	public int getLibCounter(){
		return this.libCounter;
	}
	
	public void setLibCounter(int libCounter){
		this.libCounter = libCounter;
	}
	
	//public static boolean checkMove(Board board, int x, int y, int colour){
	public boolean checkMove(int board[][], int x, int y, int colour){
		//1. if there is a stone already - illegal
		if (board[x][y] != 0) return false;
		
		//2. put a stone down
		if (colour == 1) board[x][y] = 1;
		else board[x][y] = 2;
		
		//3. check if any enemy stones have no liberty; if so remove them
		// 3.1. check if there are any adjacent enemy stones to the current one
		
		Map<Integer, Coordinates> enemyCoordinates = new HashMap<Integer, Coordinates>();
		int coordFound = 0;
		// use board size to determine boundaries
		// make Coordinates inner private class		
		if (x > 0 && board[x-1][y] == switchPlayer(colour)) { 
			enemyCoordinates.put(++coordFound, new Coordinates(x-1,y));
		}
		//?
		if (x < board[0].length - 1 && board[x+1][y] == switchPlayer(colour)) {
			enemyCoordinates.put(++coordFound, new Coordinates(x+1,y));
		}
		
		if (y > 0 && board[x][y-1] == switchPlayer(colour)) {
				enemyCoordinates.put(++coordFound, new Coordinates(x, y-1));
		}
		//?
		if (y < board.length - 1 && board[x][y+1] == switchPlayer(colour)) {
			enemyCoordinates.put(++coordFound, new Coordinates(x,y+1));
		}
		
		// if yes check if there are enemy stones without liberty and remove them if
		if(coordFound != 0){
			for(int i=1; i<=coordFound; i++){
				Coordinates c = enemyCoordinates.get(i);
				checkLiberty(board, c.getX(),c.getY(), switchPlayer(colour), 3);
				
				// restore checked stones to either black/white or remove if no liberty found
				for(int row = 0; row < board[0].length ; row++){
					for(int column = 0; column < board.length; column++){
						if (getLibCounter() == 0){
							if (board[row][column] == 3) board[row][column] = 0;	
						}
						else {
							if (board[row][column] == 3) board[row][column] = switchPlayer(colour);
						}	
					}
				}
				setLibCounter(0);
			}	
		}
	
		//4. does the new stone group has liberty; if no - illegal return false
		checkLiberty(board, x,y,colour,3);
		for(int row = 0; row < 9; row++){
			for(int column = 0; column < 9; column++){
				if (board[row][column] == 3) board[row][column] = colour;	
			}
		}
		if(getLibCounter() == 0) {
			board[x][y] = 0; // remove the stone
			return false; //illegal
			}
		setLibCounter(0);
		
		//5. has the board position appeared before; if yes - illegal
		for (int prevBoard[][] : moveHistory){
			if (Arrays.equals(prevBoard, board)) return false;
		}
		
		//6. legal
		return true;	
	}
	
	//public static void addBoard(Board board){
	public void addBoard(int board[][]){
		moveHistory.add(board);
	}
	
	//recursive function to update the global liberty counter 
	public void checkLiberty(int board[][], int x, int y, int pl, int checked){
		if (board[x][y] == checked || board[x][y] == switchPlayer(pl)) return;
		if (board[x][y] == 0) { 
			setLibCounter(getLibCounter()+1); return;
			}
		if (board[x][y] == pl) {
			board[x][y] = checked;
			//out of boundary check + recursion // 
			if (x>=1) checkLiberty(board, x-1, y, pl, checked);
			if (x<=7) checkLiberty(board, x+1, y, pl, checked);	
			if (y>=1) checkLiberty(board, x, y-1, pl, checked);
			if (y<=7) checkLiberty(board, x, y+1, pl, checked);	
		}
	}
	
	public static int switchPlayer(int pl){
		if(pl == 1) return 2;
		else return 1;
	}

	// inner class (used for the hash-map that stores the enemy coordinates)
	public class Coordinates {
		int x;
		int y;
		
		public Coordinates(int x, int y){
			this.x = x;
			this.y = y;
		}

		public void setX(int x){
			this.x = x;
		}
		
		public int getX(){
			return x;	
		}
		
		public int getY(){
			return y;
		}
		
		public void setY(int y){
			this.y = y;
		}

	}// end of inner class
}
