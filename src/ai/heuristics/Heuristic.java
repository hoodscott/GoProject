package ai.heuristics;

import ai.Objective;
import main.Board;
import main.LegalMoveChecker;

public interface Heuristic {
    //Whenn adding a Heuristic, make sure it is referenced both in the HeuristicDialogChooser and the Alpha-beta
    //General heuristic method to implement and use by alpha-beta.
    //When the search space is adapted, will be added.
    public int assess(Board initialBoard, Board currentBoard, LegalMoveChecker lmc, Objective obj, int colourAI);
}
