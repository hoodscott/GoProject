package ai.heuristics.general;

import main.Board;
import main.LegalMoveChecker;
import ai.Objective;
import ai.heuristics.Heuristic;
import ai.heuristics.Rating;
/*
 * Returns move with at least three liberties - useful for eye making and
 * other strong moves such as defending or attacking a rabbity six 
 */
public class ThreeLiberties implements Heuristic {

     
    @Override
    public int assess(Board initialBoard, Board currentBoard,
            LegalMoveChecker lmc, Objective obj, int colourAI) {

        int liberties = 0;

        // Check original board for moves that will have 3 to 4 liberties
        for (int x = 0; x < currentBoard.getHeight(); x++) {
            for (int y = 0; y < currentBoard.getHeight(); y++) {

                // Check for empty space
                if (initialBoard.get(x, y) == Board.EMPTY_AI) {
                    // Check for at least 3 more empty spaces surrounding
                    try {
                        if (initialBoard.get(x + 1, y) == Board.EMPTY_AI) {
                            liberties++;
                        }
                    } catch (Exception e) {
                    }
                    try {
                        if (initialBoard.get(x, y - 1) == Board.EMPTY_AI) {
                            liberties++;
                        }
                    } catch (Exception e) {
                    }
                    try {
                        if (initialBoard.get(x, y + 1) == Board.EMPTY_AI) {
                            liberties++;
                        }
                    } catch (Exception e) {
                    }
                    try {
                        if (initialBoard.get(x - 1, y) == Board.EMPTY_AI) {
                            liberties++;
                        }
                    } catch (Exception e) {
                    }
                }

                if (liberties >= 3 && currentBoard.get(x, y) == colourAI) {
                    return Rating.THREE_LIBERTIES.getValue();
                }

                liberties = 0;
            }
        }

        return 0;
    }
}
