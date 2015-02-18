package ai;

//List all scores for general heuristics here. This is mostly to make editing/adjusting easier.
public enum Rating {

    //E.G HALF_EYE(1000)

    SOME_VALUE(1000), COMPLETES_A_SECOND_EYE(Integer.MAX_VALUE), 
    COMPLETES_AN_EYE(800), MAKES_EIGHT_IN_A_ROW(1200), MAKES_SIX_IN_A_ROW(1200);
      
	 

    //Used to access/create ratings.
    private final int rating;

    private Rating(int rating) {
        this.rating = rating;
    }

    public int getValue() {
        return rating;
    }
};
