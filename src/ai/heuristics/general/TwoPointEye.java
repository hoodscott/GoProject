package ai.heuristics.general;

import ai.Objective;
import ai.heuristics.Heuristic;
import ai.heuristics.Rating;

import main.Board;
import main.Coordinate;
import main.LegalMoveChecker;

/**
 * Looks for almost complete two point eyes
 * returns TWO_POINT_EYE value if found
 */
public class TwoPointEye implements Heuristic {
	@Override
	public int assess(Board initialBoard, Board currentBoard,
			LegalMoveChecker lmc, Objective obj, int colourAI) {

		// Check original board for 5 stones for creation of a 2 point eye
		for (int x = 0; x < currentBoard.getHeight(); x++) {
			for (int y = 0; y < currentBoard.getHeight(); y++) {

				int stoneCount = 0;
				Coordinate eyeCompleteMove = new Coordinate(-1, -1);

				// Check for empty space
				if (initialBoard.get(x, y) == Board.EMPTY_AI) {
					// Check around empty space for 5 stones leaving room for
					// another empty space
					// Finds 2 point VERTICAL eyes
					try {
						if (initialBoard.get(x + 1, y) == colourAI) {
							stoneCount++;
						} else if (initialBoard.get(x + 1, y) == Board.EMPTY_AI) {
							eyeCompleteMove.x = x + 1;
							eyeCompleteMove.y = y;
						}
					} catch (Exception e) {
					}
					try {
						if (initialBoard.get(x - 1, y - 1) == colourAI) {
							stoneCount++;
						} else if (initialBoard.get(x - 1, y - 1) == Board.EMPTY_AI) {
							eyeCompleteMove.x = x - 1;
							eyeCompleteMove.y = y - 1;
						}
					} catch (Exception e) {
					}
					try {
						if (initialBoard.get(x + 1, y - 1) == colourAI) {
							stoneCount++;
						} else if (initialBoard.get(x + 1, y - 1) == Board.EMPTY_AI) {
							eyeCompleteMove.x = x + 1;
							eyeCompleteMove.y = y - 1;
						}
					} catch (Exception e) {
					}
					try {
						if (initialBoard.get(x, y - 2) == colourAI) {
							stoneCount++;
						} else if (initialBoard.get(x, y - 2) == Board.EMPTY_AI) {
							eyeCompleteMove.x = x;
							eyeCompleteMove.y = y - 2;
						}
					} catch (Exception e) {
					}
					try {
						if (initialBoard.get(x - 1, y) == colourAI) {
							stoneCount++;
						} else if (initialBoard.get(x - 1, y) == Board.EMPTY_AI) {
							eyeCompleteMove.x = x - 1;
							eyeCompleteMove.y = y;
						}
					} catch (Exception e) {
					}
					try {
						if (initialBoard.get(x, y + 1) == colourAI) {
							stoneCount++;
						} else if (initialBoard.get(x, y + 1) == Board.EMPTY_AI) {
							eyeCompleteMove.x = x;
							eyeCompleteMove.y = y + 1;
						}
					} catch (Exception e) {
					}
				}

				if (stoneCount == 5 && eyeCompleteMove.x != -1) {
					if (currentBoard.get(eyeCompleteMove.x, eyeCompleteMove.y) == colourAI) {
						return Rating.TWO_POINT_EYE.getValue();
					}
				}

				stoneCount = 0;
				eyeCompleteMove.x = -1;
				eyeCompleteMove.y = -1;

				// Check for empty space
				if (initialBoard.get(x, y) == Board.EMPTY_AI) {
					// Check around empty space for 5 stones leaving room for
					// another empty space
					// Finds 2 point HORIZONTAL eyes
					try {
						if (initialBoard.get(x + 1, y) == colourAI) {
							stoneCount++;
						} else if (initialBoard.get(x + 1, y) == Board.EMPTY_AI) {
							eyeCompleteMove.x = x + 1;
							eyeCompleteMove.y = y;
						}
					} catch (Exception e) {
					}
					try {
						if (initialBoard.get(x - 1, y - 1) == colourAI) {
							stoneCount++;
						} else if (initialBoard.get(x - 1, y - 1) == Board.EMPTY_AI) {
							eyeCompleteMove.x = x - 1;
							eyeCompleteMove.y = y - 1;
						}
					} catch (Exception e) {
					}
					try {
						if (initialBoard.get(x - 1, y + 1) == colourAI) {
							stoneCount++;
						} else if (initialBoard.get(x - 1, y + 1) == Board.EMPTY_AI) {
							eyeCompleteMove.x = x - 1;
							eyeCompleteMove.y = y + 1;
						}
					} catch (Exception e) {
					}
					try {
						if (initialBoard.get(x-2, y) == colourAI) {
							stoneCount++;
						} else if (initialBoard.get(x-2, y) == Board.EMPTY_AI) {
							eyeCompleteMove.x = x-2;
							eyeCompleteMove.y = y;
						}
					} catch (Exception e) {
					}
					try {
						if (initialBoard.get(x, y-1) == colourAI) {
							stoneCount++;
						} else if (initialBoard.get(x, y-1) == Board.EMPTY_AI) {
							eyeCompleteMove.x = x;
							eyeCompleteMove.y = y-1;
						}
					} catch (Exception e) {
					}
					try {
						if (initialBoard.get(x, y + 1) == colourAI) {
							stoneCount++;
						} else if (initialBoard.get(x, y + 1) == Board.EMPTY_AI) {
							eyeCompleteMove.x = x;
							eyeCompleteMove.y = y + 1;
						}
					} catch (Exception e) {
					}
				}

				if (stoneCount == 5 && eyeCompleteMove.x != -1) {
					if (currentBoard.get(eyeCompleteMove.x, eyeCompleteMove.y) == colourAI) {
						return Rating.TWO_POINT_EYE.getValue();
					}
				}
			}
		}

		return 0;
	}
}
