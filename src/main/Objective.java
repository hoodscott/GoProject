package main;


//Holds objective for a player and assesses whether it is met or not.
public class Objective {
   
	private int eyes;
	
	enum Action{
        KILL,
        DEFEND
    }
    
    // Action black;
    // Action white;
    Action action;
    int colour;
    int otherColour;
    Coordinate position; 
    
    //Objective Constructor, the text should adhere the appropriate format, containing the colour this objective is for.
    public Objective(String action, int colour, Coordinate position){
    	this.action = translateToAction(action);
        this.colour = colour;
        this.position = position;
    }
    
    public String getAction(){return translateToString(action);}
    public int getColour(){return colour;}
    public Coordinate getPosition(){return position;}
    
    private String translateToString(Action action){
        switch(action){
            case KILL: return "kill";
            case DEFEND: return "defend";
        }
        return null;
    }
    
    private Action translateToAction(String action) {
		if (action == "kill") return Action.KILL;
		return Action.DEFEND;	
	}
    
	//Checks if the objective succeeded for the given player.
    public boolean checkSucceeded(Board board){
    	if (action.equals(Action.KILL)){
    		if(board.get(position.x, position.y) != getOtherColour()) 
    			return true;
    	}
    	
    	// check if the group has more than one eye
    	if(action.equals(Action.DEFEND)){
    		eyes = 0;
    		boolean b[][] = new boolean[board.getWidth()][board.getHeight()];
    		Coordinate p = new Coordinate(position.x,position.y);
    		countEyes(board, b, p);
    		if (eyes > 1) return true;
    	}
    	
    	return false;
    }
    
  //Returns whether the player plays first or not.
    public boolean isStarting(){return false;}
    
    public int getOtherColour(){
    	if(colour == 1) return 2;
    	return 1;
    }
    
    // functions to count the eyes of the defending group
    
    public void countEyes(Board board, boolean b[][], Coordinate p){
		// if checked already or black
    	if (b[p.x][p.y] == true || board.get(p.x, p.y) == 1) return;
		// mark as checked
		b[p.x][p.y]=true;
		// if liberty found -> check if eye
		if (board.get(p.x, p.y) == 0) {
			if (checkIfEye(board,p) == true) {
				eyes++;
			}
			return;
		}
		
		//out of boundary check + recursion //
		if (p.x>0) {
			Coordinate np = new Coordinate(p.x-1,p.y);
			countEyes(board, b, np);
		}
		if (p.x<board.getWidth() - 1) {
			Coordinate np = new Coordinate(p.x+1,p.y);
			countEyes(board, b, np);
		}
		if (p.y>0) {
			Coordinate np = new Coordinate(p.x,p.y-1);
			countEyes(board, b, np);
		}
		if (p.y<board.getHeight() - 1){
			Coordinate np = new Coordinate(p.x,p.y+1);
			countEyes(board, b, np);
		}
	}

	public boolean checkIfEye(Board board, Coordinate p){
		// check all eight positions around if empty and not edge
		if (board.get(p.x, p.y) == 0 && p.x > 0 && p.y > 0 && p.x<board.getWidth() - 1 && p.y<board.getHeight() - 1) {
			if 	(	board.get(p.x-1, p.y-1) == colour && 
					board.get(p.x-1, p.y) == colour &&	
					board.get(p.x-1, p.y+1) == colour &&	
					board.get(p.x, p.y-1) == colour &&
					board.get(p.x, p.y+1) == colour &&
					board.get(p.x+1,p.y-1) == colour &&
					board.get(p.x+1,p.y) == colour &&
					board.get(p.x+1,p.y+1) == colour) 
				return true;
		}
		
		// check top left corner
		if(board.get(p.x, p.y) == 0 && p.x == 0 && p.y == 0){
			if 	(	board.get(p.x, p.y+1) == colour &&
					board.get(p.x+1,p.y) == colour &&
					board.get(p.x+1,p.y+1) == colour)
				return true;
				
		}
		
		// check bottom left corner
		if(board.get(p.x, p.y) == 0 && p.x == board.getWidth() - 1 && p.y == 0){
			if 	(	board.get(p.x-1, p.y) == colour &&
					board.get(p.x-1, p.y+1) == colour &&
					board.get(p.x, p.y+1) == colour) 
				return true;		
		}
		
		// check bottom right corner
		if(board.get(p.x, p.y) == 0 && p.x == board.getWidth() - 1 && p.y == board.getHeight() - 1){
			if 	(	board.get(p.x-1, p.y) == colour &&
					board.get(p.x-1, p.y-1) == colour &&
					board.get(p.x, p.y-1) == colour ) 
				return true;			
		}
		
		// check top right corner
		if(board.get(p.x, p.y) == 0 && p.x == 0 && p.y == board.getHeight() - 1){
			if 	(	board.get(p.x, p.y-1) == colour &&
					board.get(p.x+1,p.y) == colour &&
					board.get(p.x+1,p.y-1) == colour) 
				return true;				
		}

		// check 0 column
		if(board.get(p.x, p.y) == 0 && p.x > 0 && p.x < board.getWidth() - 1 && p.y == 0){
			if 	(	board.get(p.x-1, p.y) == colour &&	
					board.get(p.x-1, p.y+1) == colour &&	
					board.get(p.x, p.y+1) == colour &&
					board.get(p.x+1,p.y) == colour &&
					board.get(p.x+1,p.y+1) == colour) 
				return true;	
		}
		
		// check 0 row
		if(board.get(p.x, p.y) == 0 && p.x == 0 && p.y > 0 && p.y < board.getHeight() - 1){
			if 	(	board.get(p.x, p.y-1) == colour &&
					board.get(p.x, p.y+1) == colour &&
					board.get(p.x+1,p.y-1) == colour &&
					board.get(p.x+1,p.y) == colour &&
					board.get(p.x+1,p.y+1) == colour) 
				return true;	
		}
		

		// check last column
		if(board.get(p.x, p.y) == 0 && p.x<board.getWidth() - 1 && p.x > 0 && p.y==board.getHeight() - 1){
			if 	(	board.get(p.x-1, p.y) == colour &&
					board.get(p.x-1, p.y-1) == colour &&
					board.get(p.x, p.y-1) == colour && 
					board.get(p.x+1,p.y-1) == colour &&
					board.get(p.x+1,p.y) == colour)
				return true;
		}
		
		// check last row
		if(board.get(p.x, p.y) == 0 && p.x==board.getWidth() - 1 && p.y > 0 && p.y<board.getHeight() - 1){
			if 	(	board.get(p.x-1,p.y-1) == colour &&
					board.get(p.x-1,p.y) == colour &&
					board.get(p.x-1,p.y+1) == colour &&
					board.get(p.x, p.y-1) == colour &&
					board.get(p.x, p.y+1) == colour)
				return true;	
		}
		// not an eye
		return false;
	}     
}
