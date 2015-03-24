package ai.heuristics;

import ai.Objective;
import main.Board;
import main.LegalMoveChecker;
/*
 *  General heuristic method to implement and use by alpha-beta.
 */
public interface Heuristic {
    //When adding a Heuristic, make sure it is referenced both in the HeuristicDialogChooser and the Alpha-beta
   
    
    public int assess(Board initialBoard, Board currentBoard, LegalMoveChecker lmc, Objective obj, int colourAI);
}
