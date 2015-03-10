package ai.heuristics;

//List all scores for general heuristics here. This is mostly to make editing/adjusting easier.
public enum Rating {

    //Jamie's Heuristics   
    HAS_A_SECOND_EYE(Integer.MAX_VALUE), 
    HAS_AN_EYE(800), 
    HAS_EIGHT_IN_A_ROW(1200), 
    HAS_SIX_IN_A_ROW(1200),
    
    //Kiril's Heuristics
    LIBERTY(100),
    
    // Eilidh's Heuristics
    UNSETTLED_THREE(700);

    //Used to access/create ratings.
    private final int rating;

    private Rating(int rating) {
        this.rating = rating;
    }

    public int getValue() {
        return rating;
    }
};
