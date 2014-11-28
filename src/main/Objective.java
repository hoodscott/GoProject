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
    private Action action;
    private int colour;
    private int startingColour;
    
    private Coordinate position; 
    
    //Objective Constructor, the text should adhere the appropriate format, containing the colour this objective is for.
    public Objective(String action, int colour, Coordinate position){
    	this.action = translateToAction(action);
        this.colour = startingColour = colour;
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
		if ("kill".equals(action)) return Action.KILL;
		return Action.DEFEND;	
	}
    
	//Checks if the objective succeeded for the given player.
    // !!!!!!!!!!!! add colour parameter and remove the second objective from minimax
    public boolean checkSucceeded(Board board){
    	if (action.equals(Action.KILL)){
    		if(board.get(position.x, position.y) != getOtherColour()) 
    			return true;
    	}
    	
    	// check if the group has more than one eye
    	// check if the defending stone is dead
    	if(action.equals(Action.DEFEND)){
    		/*eyes = 0;
    		boolean b[][] = new boolean[board.getWidth()][board.getHeight()];
    		Coordinate p = new Coordinate(position.x,position.y);
    		countEyes(board, b, p);
    		if (eyes > 1) return true;*/
    		if(board.get(position.x, position.y) != colour) 
    			return true;
    		
    	}
    	
    	return false;
    }
    
    
    
  //Returns whether the player plays first or not.
    public boolean isStarting(int colour){return colour == startingColour;}
    
    public int getOtherColour(){
    	if(colour == Board.BLACK) return Board.WHITE;
    	return Board.BLACK;
    }
    
    // functions to count the eyes of the defending group
    /*
    public void countEyes(Board board, boolean b[][], Coordinate p){
		// if checked already or opponent return
    	if (b[p.x][p.y] == true || board.get(p.x, p.y) == getOtherColour()) return;
		// else mark as checked
		b[p.x][p.y]=true;
		// if position is empty -> check if eye
		if (board.get(p.x, p.y) == Board.EMPTY) {
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
		// check all eight positions (whichever exist) around
		for (int i = p.x-1 ; i <= p.x+1; i++){
			loop2: for(int j = p.y-1; j <= p.y+1; j++){
				// do not check the potential eye itself 
				if (i==p.x && j == p.y ) continue loop2;
				if(i >= 0 && j >= 0 && i<=board.getWidth() - 1 && j<=board.getHeight() - 1){
					if (board.get(i,j) != colour) return false;
				}
			}
		}		
		return true;
	}     */
}
