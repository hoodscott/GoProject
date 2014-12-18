package main;

public abstract class AI {
    
    //These bounds define the space that should be searched by the AI on the board. It is ensured beforehand, that they are within the board.
    protected int lowerBoundX;
    protected int upperBoundX;
    protected int lowerBoundY;
    protected int upperBoundY;
    
    protected int colour;
    
    public abstract Coordinate nextMove(Board b);
    
    public void setBounds(int[] data){
        if(data[0] > data[2]){
            upperBoundX = data[0];
            lowerBoundX = data[2];
        }
        else{            
            upperBoundX = data[2];
            lowerBoundX = data[0];
        }
        if(data[1] > data[3]){
            upperBoundY = data[1];
            lowerBoundY = data[3];
        }
        else{            
            upperBoundY = data[3];
            lowerBoundY = data[1];
        }
    }
    
    public int getColour(){return this.colour;}
}
