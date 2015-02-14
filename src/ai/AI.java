package ai;

//This is the abstract class for all AI types to extend.
import main.Board;
import main.Coordinate;
import main.LegalMoveChecker;

public abstract class AI {

    protected String thoughts;

    //Colour of the AI
    protected int colour;

    //NextMove method to be implemented by subclasses
    public abstract Coordinate nextMove(Board b, LegalMoveChecker lMC) throws AIException;

    //Returns AI's colour
    public int getColour() {
        return this.colour;
    }
}
