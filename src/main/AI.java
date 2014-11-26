package main;

public abstract class AI {
    protected Coordinate[] searchSpace; //A 4 element array that should define the corners of the array.
    protected int colour;
    
    public abstract Coordinate nextMove(Board b, boolean[][] legalMoves);
}
