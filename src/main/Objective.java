package main;


//Holds objective for a player and assesses whether it is met or not.
public class Objective {
    
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
    	this.action = getAction(action);
        this.colour = colour;
        this.position = position;
    }
    
    //Checks if the objective failed for the given player.
    public boolean checkFailed(Board board){
    	if (action.equals(Action.DEFEND)){
    		if(board.get(position.getX(), position.getY()) == 0) 
    			return true;
    	}
    	
    	if (action.equals(Action.KILL)){
    		if(board.get(position.getX(), position.getY()) == getOtherColour()) 
    			return true;
    	} 
    	
    	/*
    	// check if there are 2 eyes
    	if (action.equals(Action.KILL)){
    		// TODO
    	}
    	
    	// also if both players pass and KILL objective is not accomplished => failed?
    	// or just if both players pass => false (defend) and true (kill)
    	*/
    	return false;
    }
    
    //Checks if the objective succeeded for the given player.
    public boolean checkSucceeded(Board board){
    	if (action.equals(Action.DEFEND)){
    		if(board.get(position.getX(), position.getY()) == colour) 
    			return true;
    	}
    	
    	if (action.equals(Action.KILL)){
    		if(board.get(position.getX(), position.getY()) != getOtherColour()) 
    			return true;
    	}
    	
    	/*
    	// check if there are 2 eyes
    	if (action.equals(Action.DEFEND)){
    		// TODO
    		// if( 2 eyes) return false;
    	}
    	// also if both players pass and KILL objective is not accomplished => failed?
    	// or just if both players pass => false (defend) and true (kill)
    	*/
    	
    	return false;
    }
    
    public int getOtherColour(){
    	if(colour == 1) return 2;
    	return 1;
    }
    
    //Returns whether the player plays first or not.
    public boolean isStarting(){return false;}
    
    private Action getAction(String action){
        if(action.equalsIgnoreCase("kill"))
            return Action.KILL;
        else if(action.equalsIgnoreCase("defend"))
            return Action.DEFEND;
        
        return null;
    }
}
