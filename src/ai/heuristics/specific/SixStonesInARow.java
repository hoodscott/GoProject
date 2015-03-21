package ai.heuristics.specific;

import main.Board;
import main.LegalMoveChecker;
import ai.Objective;
import ai.heuristics.Heuristic;
import ai.heuristics.Rating;

// Determines whether a move will give six stones in a row in the corners - useful for capturing
public class SixStonesInARow implements Heuristic {

	@Override
	// / gives a board a move, obj and player to move
	// should return HAS_SIX_IN_A_ROW
	public int assess(Board initialBoard, Board b, LegalMoveChecker lmc,
			Objective obj, int colourAI) {

		int x_limit = b.getWidth() - 1;
		int y_limit = b.getHeight() - 1;

		int stone_counter = 0;
		byte[][] board_to_assess = b.getRaw();
		byte[][] initialB = initialBoard.getRaw();

		// search for 6 stones in the corners
		// search [1,y] corners
		for (int y = 0; y <= 5; y++) {
			if (board_to_assess[1][y] == colourAI) {
				stone_counter++;
			} else {
				stone_counter = 0;
			}
			if (stone_counter == 6) {
				stone_counter = 0;
				// check initial board for 5 in a row
				for (y = 0; y <= 5; y++) {
					if (initialB[1][y] == colourAI) {
						stone_counter++;
					}
				}
				if (stone_counter == 5) {
					return Rating.HAS_SIX_IN_A_ROW.getValue();
				}
			}
		}

		stone_counter = 0;
		for (int y = y_limit - 6; y <= y_limit; y++) {

			if (board_to_assess[1][y] == colourAI) {
				stone_counter++;
			} else {
				stone_counter = 0;
			}
			if (stone_counter == 6) {
				stone_counter = 0;
				// check initial board for 5 in a row
				for (y = y_limit-6; y <= y_limit; y++) {
					if (initialB[1][y] == colourAI) {
						stone_counter++;
					}
				}
				if (stone_counter == 5) {
					return Rating.HAS_SIX_IN_A_ROW.getValue();
				}
			}
		}

		// search [x,1] corners
		stone_counter = 0;
		for (int x = 0; x <= 5; x++) {
			if (board_to_assess[x][1] == colourAI) {
				stone_counter++;
			} else {
				stone_counter = 0;
			}
			if (stone_counter == 6) {
				stone_counter = 0;
				// check initial board for 5 in a row
				for (x = 0; x <= 5; x++) {
					if (initialB[x][1] == colourAI) {
						stone_counter++;
					}
				}
				if (stone_counter == 5) {
					return Rating.HAS_SIX_IN_A_ROW.getValue();
				}
			}
		}

		stone_counter = 0;
		for (int x = x_limit - 6; x <= x_limit; x++) {
			if (board_to_assess[x][1] == colourAI) {
				stone_counter++;
			} else {
				stone_counter = 0;
			}
			if (stone_counter == 6) {
				stone_counter = 0;
				// check initial board for 5 in a row
				for (x = x_limit-6; x <= x_limit; x++) {
					if (initialB[x][1] == colourAI) {
						stone_counter++;
					}
				}
				if (stone_counter == 5) {
					return Rating.HAS_SIX_IN_A_ROW.getValue();
				}
			}
		}

		// search [width -1 ,y] corners
		stone_counter = 0;
		for (int y = 0; y <= 5; y++) {
			if (board_to_assess[x_limit - 1][y] == colourAI) {
				stone_counter++;
			} else {
				stone_counter = 0;
			}
			if (stone_counter == 6) {
				stone_counter = 0;
				// check initial board for 5 in a row
				for (y = 0; y <= 5; y++) {
					if (initialB[x_limit-1][y] == colourAI) {
						stone_counter++;
					}
				}
				if (stone_counter == 5) {
					return Rating.HAS_SIX_IN_A_ROW.getValue();
				}
			}
		}

		stone_counter = 0;
		for (int y = y_limit - 6; y <= y_limit; y++) {
			if (board_to_assess[x_limit - 1][y] == colourAI) {
				stone_counter++;
			} else {
				stone_counter = 0;
			}
			if (stone_counter == 6) {
				stone_counter = 0;
				// check initial board for 5 in a row
				for (y = y_limit-6; y <= y_limit; y++) {
					if (initialB[x_limit-1][y] == colourAI) {
						stone_counter++;
					}
				}
				if (stone_counter == 5) {
					return Rating.HAS_SIX_IN_A_ROW.getValue();
				}
			}
		}

		// search [x ,height -1] corners
		stone_counter = 0;
		for (int x = 0; x <= 5; x++) {

			if (board_to_assess[x][y_limit - 1] == colourAI) {
				stone_counter++;
			} else {
				stone_counter = 0;
			}
			if (stone_counter == 6) {
				stone_counter = 0;
				// check initial board for 5 in a row
				for (x = 0; x <= 5; x++) {
					if (initialB[x][y_limit - 1] == colourAI) {
						stone_counter++;
					}
				}
				if (stone_counter == 5) {
					return Rating.HAS_SIX_IN_A_ROW.getValue();
				}
			}
		}

		stone_counter = 0;
		for (int x = x_limit - 6; x <= x_limit; x++) {
			if (board_to_assess[x][y_limit - 1] == colourAI) {
				stone_counter++;
			} else {
				stone_counter = 0;
			}
			if (stone_counter == 6) {
				stone_counter = 0;
				// check initial board for 5 in a row
				for (x = x_limit-6; x <= x_limit; x++) {
					if (initialB[x][y_limit-1] == colourAI) {
						stone_counter++;
					}
				}
				if (stone_counter == 5) {
					return Rating.HAS_SIX_IN_A_ROW.getValue();
				}
			}
		}
		
		return 0;
	}

}
