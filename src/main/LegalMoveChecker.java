package main;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Arrays;

public class LegalMoveChecker {
	private ArrayList<Board> moveHistory; //= new ArrayList<int[][]>();
	private Board lastChecked;
	private static final int EMPTY = 0;
	private static final int BLACK = 1;
	private static final int WHITE = 2;
	private static final int CHECKED = 3;
	private int liberties;
	
        //Constructor
	public LegalMoveChecker(){
		moveHistory = new ArrayList<>();
	}

	//Main MoveChecking method.
	public boolean checkMove(Board board, int x, int y, int colour){

		Board bCopy = board.clone();
		int aggressor;
		int defender;
		boolean match;
		lastChecked = null;

		//1. if there is a stone already - illegal
		if (bCopy.get(x,y) != EMPTY){
			return false;
		} 
		
		//2. put a stone down
		if (colour == (BLACK)){
			bCopy.set(x,y,BLACK);
			aggressor = BLACK;
			defender = WHITE;
		} 
		else{ 
			bCopy.set(x,y,WHITE);
			aggressor = WHITE;
			defender = BLACK;
		}
		
		//3. check if any enemy stones have no liberty; if so remove them
		// 3.1. check if there are any adjacent enemy stones to the current one
		LinkedList<Coordinate> enemyCoordinates = new LinkedList<>();

		if (x > 0 && bCopy.get(x-1,y) == defender)
			enemyCoordinates.add(new Coordinate(x-1,y));

		if (x < bCopy.getWidth() - 1 && bCopy.get(x+1,y) == defender) 
			enemyCoordinates.add(new Coordinate(x+1,y));
		
		if (y > 0 && bCopy.get(x,y-1) == defender)
			enemyCoordinates.add(new Coordinate(x,y-1));
		//?
		if (y < bCopy.getHeight() - 1 && bCopy.get(x,y+1) == defender)
			enemyCoordinates.add(new Coordinate(x,y+1));
		
		// if yes check if there are enemy stones without liberty and remove them if

		while(!enemyCoordinates.isEmpty()){
			Coordinate c = enemyCoordinates.remove();
			liberties = 0;
			checkLiberty(bCopy, c, aggressor);
			
			// restore defending checked stones to either black/white or remove if no liberty found
			for(int column = 0; column < bCopy.getWidth(); column++){
				for(int row = 0; row < bCopy.getHeight(); row++){
					if (liberties == 0 && bCopy.get(column,row) == CHECKED)
						bCopy.set(column,row,EMPTY);	
					else if (bCopy.get(column,row) == CHECKED) 
						bCopy.set(column,row,defender);
		
				}
			}
		}	
		
		
		//4. does the new stone group have a liberty; if no - illegal return false
		liberties = 0;
		
		checkLiberty(bCopy, new Coordinate(x,y),defender);

		if(liberties == 0) {
			bCopy.set(x,y,EMPTY);
			return false; //illegal
		}
		//4.1 Turns checked stones back into normal ones
		for(int column = 0; column < bCopy.getWidth(); column++)
			for(int row = 0; row < bCopy.getHeight(); row++)
				if (bCopy.get(column,row) == CHECKED) 
						bCopy.set(column,row,aggressor);

		//5. Tests for SuperKo; if yes - illegal
		for (Board b : moveHistory){
		/*			
		match = true;
			for(int column = 0; column < b.getWidth(); column++)
				for(int row = 0; row < b.getHeight(); row++)
					if(b.get(x,y) != board.get(x,y))
						match = false;
			
			if(match)
				return false; 
				*/
			if(Arrays.deepEquals(b.getRaw(),bCopy.getRaw())){
				//System.out.println("This move has already been made.");
				return false;
			}
				
		}
		//6. legal
		lastChecked = bCopy;
		return true;	
	}
	
	//Adds board to moveHistory
	public void addBoard(Board board){moveHistory.add(board.clone());}
	//Removes the last board from the moveHistory
	public Board removeLast(){return moveHistory.remove(moveHistory.size()-1);}
	//Gets last Board if it was legal. If not, returns null.
	public Board getLastLegal(){return lastChecked;}
	//Checks whether the moveHistory contains anything.
	public boolean isEmpty(){return moveHistory.isEmpty();}
	
	//recursive function to update the global liberty counter 
	private void checkLiberty(Board board, Coordinate c, int otherPlayer){
		if (board.get(c.x,c.y) == CHECKED || board.get(c.x,c.y) == otherPlayer || liberties > 0) return;
		if (board.get(c.x,c.y) == EMPTY){liberties++; return;}
		
		board.set(c.x,c.y,CHECKED);
		//out of boundary check + recursion // 
		if (c.x>0) 
			checkLiberty(board, new Coordinate(c.x - 1, c.y), otherPlayer);
		if (c.x<board.getWidth() - 1)
			checkLiberty(board, new Coordinate(c.x+1, c.y), otherPlayer);	
		if (c.y>0) 
			checkLiberty(board, new Coordinate(c.x, c.y-1), otherPlayer);
		if (c.y<board.getHeight() - 1) 
			checkLiberty(board, new Coordinate(c.x, c.y+1), otherPlayer);	
	}
	
	// inner class
	private class Coordinate{
		public int x;
		public int y;
		
		public Coordinate(int x, int y){
			this.x = x;
			this.y = y;
		}
	}// end of inner class
}
