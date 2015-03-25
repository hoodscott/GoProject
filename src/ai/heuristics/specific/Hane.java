package ai.heuristics.specific;

import main.Board;
import main.Coordinate;
import main.LegalMoveChecker;
import ai.Objective;
import ai.heuristics.Heuristic;
import ai.heuristics.Rating;
/**
 * Checks current board for hane move
 * (wrapping round an opponents stones)
 */
public class Hane implements Heuristic {

    @Override
    public int assess(Board initialBoard, Board currentBoard,
            LegalMoveChecker lmc, Objective obj, int colourAI) {

        // Get opponent colour
        int opponent = 0;
        if (colourAI == 1) {
            opponent = 2;
        } else {
            opponent = 1;
        }

        int AIStones = 0;
        int spaces = 0;
        Coordinate space = new Coordinate(-1, -1);

        // Check original board for hane moves
        for (int x = 0; x < currentBoard.getHeight(); x++) {
            for (int y = 0; y < currentBoard.getHeight(); y++) {

                // Check for opponent stones
                if (initialBoard.get(x, y) == opponent) {
                    // Check for at least 2 AIStones in surrounding stones
                    // Use try/catches for corner and edge cases
                    try {
                        if (initialBoard.get(x + 1, y) == colourAI) {
                            AIStones++;
                        }
                    } catch (Exception e) {
                    }
                    try {
                        if (initialBoard.get(x, y - 1) == colourAI) {
                            AIStones++;
                        }
                    } catch (Exception e) {
                    }
                    try {
                        if (initialBoard.get(x, y + 1) == colourAI) {
                            AIStones++;
                        }
                    } catch (Exception e) {
                    }
                    try {
                        if (initialBoard.get(x - 1, y) == colourAI) {
                            AIStones++;
                        }
                    } catch (Exception e) {
                    }

                    // Check for at least 1 empty AI space and save co-ordinate
                    try {
                        if (initialBoard.get(x + 1, y) == Board.EMPTY_AI) {
                            spaces++;
                            space.x = x + 1;
                            space.y = y;
                        }
                    } catch (Exception e) {
                    }
                    try {
                        if (initialBoard.get(x, y - 1) == Board.EMPTY_AI) {
                            spaces++;
                            space.x = x;
                            space.y = y - 1;
                        }
                    } catch (Exception e) {
                    }
                    try {
                        if (initialBoard.get(x, y + 1) == Board.EMPTY_AI) {
                            spaces++;
                            space.x = x;
                            space.y = y + 1;
                        }
                    } catch (Exception e) {
                    }
                    try {
                        if (initialBoard.get(x - 1, y) == Board.EMPTY_AI) {
                            spaces++;
                            space.x = x - 1;
                            space.y = y;
                        }
                    } catch (Exception e) {
                    }
                }

                // Return hane if on current board
                if ((AIStones >= 2) && (spaces >= 1) && currentBoard.get(space.x, space.y) == colourAI) {
                    return Rating.HANE.getValue();
                }

                AIStones = 0;
                spaces = 0;
            }
        }

        return 0;
    }

}
