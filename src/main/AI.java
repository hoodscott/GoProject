package main;

public abstract class AI {
    
    //These bounds define the space that should be searched by the AI on the board. It is ensured beforehand, that they are within the board.
    protected int lowerBoundX;
    protected int upperBoundX;
    protected int lowerBoundY;
    protected int upperBoundY;
    
    protected int colour;
    
    public abstract Coordinate nextMove(Board b);
    
    public int getColour(){return this.colour;}
}
