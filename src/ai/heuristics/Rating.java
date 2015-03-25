package ai.heuristics;
/**
 * List all scores for general heuristics here. This is mostly to make editing/adjusting easier.
 */

public enum Rating {

    //Jamie's Heuristics   
    HAS_EIGHT_IN_A_ROW(900),
    HAS_SIX_IN_A_ROW(500),
    //Kiril's  & Niklas' Heuristics
    LIBERTY(100),
    LIVINGSPACE(200),
    // Eilidh's Heuristics
    UNSETTLED_THREE(700),
    HANE(900),
    THREE_LIBERTIES(500), 
    EYE_CREATOR(1200),
    TWO_POINT_EYE(1000);

    //Used to access/create ratings.
    private final int rating;

    private Rating(int rating) {
        this.rating = rating;
    }

    public int getValue() {
        return rating;
    }
};
