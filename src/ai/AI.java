package ai;
/**
This is the abstract class for all AI types to extend.
*/
import main.Board;
import main.Coordinate;
import main.LegalMoveChecker;

public abstract class AI {

    protected String thoughts;
    protected int movesConsidered;
    protected static int moveDepth = 5;
    protected long moveStartTime = 0;
    protected long moveFinishTime = 0;

    //Colour of the AI
    protected int colour;
    
    //Same as next move but takes time stamps before and after.
    public Coordinate nextTimedMove(Board b, LegalMoveChecker lmc) throws AIException{
        moveStartTime = System.currentTimeMillis();
        Coordinate c = nextMove(b, lmc);
        moveFinishTime = System.currentTimeMillis();
        return c;
    }
    
    //NextMove method to be implemented by subclasses
    public abstract Coordinate nextMove(Board b, LegalMoveChecker lMC) throws AIException;

    //Returns AI's colour
    public int getColour() {
        return this.colour;
    }

    //Returns moves considered by a nextMove call.
    //This counter needs to be set/incremented explicitly in the implementation.
    public int getNumberOfMovesConsidered() {
        return movesConsidered;
    }
    
    public String getTimeTaken(){
        long time = moveFinishTime - moveStartTime;
        if(time < 10000)
            return Long.toString(time)+"ms";
        else
            time /= 1000; //reduces to seconds
        
        if(time < 1000)
            return Long.toString(time)+"s";
        else
            time /= 60; //reduces to minutes
        
        if(time < 120)
            return Long.toString(time)+"m";
        else
            time /= 60; //reduces to hours
        if(time < 72)
            return Long.toString(time)+"h";
        else
            return Long.toString(time/24)+"d";
    }
    
    public static int getSearchDepth(){return moveDepth;}
    public static void setSearchDepth(int i){moveDepth = i;} 
}
