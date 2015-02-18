package ai;

import main.Board;
import main.LegalMoveChecker;

public interface Heuristic {

    //General heuristic method to implement and use by alpha-beta.
    //When the search space is adapted, will be added.
    public int assess(Board initialBoard, Board currentBoard, LegalMoveChecker lmc, Objective obj, int colourAI);
}
