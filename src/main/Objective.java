package main;

//Holds objective for a player and assesses whether it is met or not.
public class Objective {

    private int eyes;

    public enum Action {
        KILL,
        DEFEND
    }

    Action black;
    Action white;
    private Action action;
    private final int startingColour;

    private final Coordinate position;

    //Objective Constructor, the text should adhere the appropriate format, containing the colour this objective is for.
    public Objective(String action, int colour, Coordinate position) {
        startingColour = colour;
        this.action = translateToAction(action);
        if (startingColour == Board.BLACK) {
            black = this.action;
            white = getOpposingAction(black);
        } else {
            white = this.action;
            black = getOpposingAction(white);
        }
        this.position = position;
    }
    
    public String getOriginalAction() {
        return translateToString(action);
    }
    
    public Action getAction(int colour) {
        switch (colour) {
            case Board.BLACK:
                return black;
            case Board.WHITE:
                return white;
        }
        return null;
    }

    public int getColour() {
        return startingColour;
    }

    public Coordinate getPosition() {
        return position;
    }

    private String translateToString(Action action) {
        switch (action) {
            case KILL:
                return "kill";
            case DEFEND:
                return "defend";
        }
        return null;
    }

    private Action translateToAction(String action) {
        if ("kill".equals(action)) {
            return Action.KILL;
        }
        return Action.DEFEND;
    }

    private Action getOpposingAction(Action action) {
        switch (action) {
            case KILL:
                return Action.DEFEND;
            case DEFEND:
                return Action.KILL;
        }
        return null;
    }

    //Checks if the objective succeeded for the given player.
    // !!!!!!!!!!!! add colour parameter and remove the second objective from minimax
    public boolean checkSucceeded(Board board, int colour) {
        if (colour == Board.BLACK) {
            if (black.equals(Action.KILL)) {
                return board.get(position.x, position.y) != getOtherColour(colour);
            } else {
                return board.get(position.x, position.y) == colour;
            }
        } else {
            if (white.equals(Action.KILL)) {
                return board.get(position.x, position.y) != getOtherColour(colour);
            } else {
                return board.get(position.x, position.y) == colour;
            }
        }
    }

    //Returns whether the player plays first or not.
    public boolean isStarting(int colour) {
        return colour == startingColour;
    }

    public int getStartingColour() {
        return startingColour;
    }

    public int getOtherColour(int colour) {
        if (colour == Board.BLACK) {
            return Board.WHITE;
        } else {
            return Board.BLACK;
        }
    }
    
    public String toString(){
    	// TODO write the objective to a string
    	return "#the #objective";
    }
}
