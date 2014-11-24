package main;

//Holds objective for a player and assesses whether it is met or not.
public class Objective {
    
    enum Action{
        KILL,
        DEFEND
    }
    
    Action black;
    Action white;
    Coordinate position;
    
    //Objective Constructor, the text should adhere the appropriate format, containing the colour this objective is for.
    public Objective(String action, int colour, Coordinate position){
            
    }
    
    //Checks if the objective failed for the given player.
    public boolean checkFailed(){return false;}
    
    //Checks if the objective succeeded for the given player.
    public boolean checkSucceeded(){return false;}
    
    //Returns whether the player plays first or not.
    public boolean isStarting(){return false;}
    
}
