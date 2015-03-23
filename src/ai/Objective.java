package ai;

//Holds objective for a player and assesses whether it is met or not.
import main.Board;
import main.Coordinate;

public class Objective {

    public enum Action {

        KILL, DEFEND
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

    //Returns original action
    public String getOriginalAction() {
        return translateToString(action);
    }

    //Gets action corresponding to colour
    public Action getAction(int colour) {
        switch (colour) {
            case Board.BLACK:
                return black;
            case Board.WHITE:
                return white;
        }
        return null;
    }

    //Returns position to be defended
    public Coordinate getPosition() {
        return position;
    }

    //Translates Action to String
    private String translateToString(Action action) {
        switch (action) {
            case KILL:
                return "kill";
            case DEFEND:
                return "defend";
        }
        return null;
    }

    //Translates the action String into the corresponding Action
    private Action translateToAction(String action) {
        if ("kill".equals(action)) {
            return Action.KILL;
        }
        return Action.DEFEND;
    }

    //Opposing Action
    private Action getOpposingAction(Action action) {
        switch (action) {
            case KILL:
                return Action.DEFEND;
            case DEFEND:
                return Action.KILL;
        }
        return null;
    }

    // Checks if the objective succeeded for the given player.
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

    //Returns starting colour
    public int getStartingColour() {
        return startingColour;
    }

    //Gets opponent colour
    public static int getOtherColour(int colour) {
        if (colour == Board.BLACK) {
            return Board.WHITE;
        } else {
            return Board.BLACK;
        }
    }

    // helper method for toString to get string of colour
    private String toColour(int c) {
        if (c == Board.BLACK) {
            return "Black";
        } else {
            return "White";
        }
    }

    //ToString, Somewhat obsolete but will stay here for tests.
    @Override
    public String toString() {
        return toColour(this.startingColour) + " to "
                + translateToString(this.action) + " "
                + this.position.toString();
    }
}
