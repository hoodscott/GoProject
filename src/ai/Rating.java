package ai;

//List all scores for general heuristics here. This is mostly to make editing/adjusting easier.
public enum Rating {
    //E.G HALF_EYE(1000)
    SOME_VALUE(1000);
    
    
    
    //Used to access/create ratings.
    private final int rating;
    
    private Rating(int rating){
        this.rating = rating;
    }
    
    public int getValue(){return rating;}
};
