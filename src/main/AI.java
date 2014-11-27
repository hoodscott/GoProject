package main;

public abstract class AI {
    protected static Coordinate[] searchSpace; //A 4 element array that should define the corners of the array. It is static as it is assumed,
    //that AIs will search the same areas.
    protected int colour;
    
    public abstract Coordinate nextMove(Board b, boolean[][] legalMoves);
}
