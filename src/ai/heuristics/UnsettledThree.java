package ai.heuristics;

import main.Board;
import main.Coordinate;
import main.LegalMoveChecker;
import ai.Objective;

public class UnsettledThree implements Heuristic {

	boolean unsettledThreeCheck;
	Coordinate middleCoord;
	int liberties;

	// Finds unsettled three case where opponent group is surrounding 
	// to enable stopping opponent creating 2 eyes
	@Override
	public int assess(Board initialBoard, Board currentBoard,
			LegalMoveChecker lmc, Objective obj, int colourAI) {

		// Get opponent colour
		int opponent = 0;
		if (colourAI == 1)
			opponent = 2;
		else
			opponent = 1;

		// Check original board for unsettled three pattern - return 0 if not
		for (int x = 0; x < currentBoard.getHeight() - 1; x++) {
			for (int y = 0; y < currentBoard.getHeight() - 1; y++) {

				// Look for unsettled three within bounds
				if (initialBoard.get(x, y) == Board.EMPTY_AI) {

					// Check for 2 liberties in surrounding stones
					// Case: When NOT on edge/corner
					if ((x != 0 && x != currentBoard.getHeight() - 1)
							&& (y != 0 && y != currentBoard.getHeight())) {
						if (initialBoard.get(x + 1, y) == Board.EMPTY_AI)
							liberties++;
						if (initialBoard.get(x + 1, y - 1) == Board.EMPTY_AI)
							liberties++;
						if (initialBoard.get(x + 1, y + 1) == Board.EMPTY_AI)
							liberties++;
						if (initialBoard.get(x, y - 1) == Board.EMPTY_AI)
							liberties++;
						if (initialBoard.get(x, y + 1) == Board.EMPTY_AI)
							liberties++;
						if (initialBoard.get(x - 1, y) == Board.EMPTY_AI)
							liberties++;
						if (initialBoard.get(x - 1, y - 1) == Board.EMPTY_AI)
							liberties++;
						if (initialBoard.get(x - 1, y + 1) == Board.EMPTY_AI)
							liberties++;
					}

					// TODO: Add edge/corner cases

					if (liberties == 2) {
						// Check group is opponent
						unsettledThreeCheck = true;
						if (initialBoard.get(x + 1, y) != Board.EMPTY_AI
								|| initialBoard.get(x + 1, y) != opponent)
							unsettledThreeCheck = false;
						if (initialBoard.get(x + 1, y - 1) != Board.EMPTY_AI
								|| initialBoard.get(x + 1, y - 1) != opponent)
							unsettledThreeCheck = false;
						if (initialBoard.get(x + 1, y + 1) != Board.EMPTY_AI
								|| initialBoard.get(x + 1, y + 1) != opponent)
							unsettledThreeCheck = false;
						if (initialBoard.get(x, y - 1) != Board.EMPTY_AI
								|| initialBoard.get(x, y - 1) != opponent)
							unsettledThreeCheck = false;
						if (initialBoard.get(x, y + 1) != Board.EMPTY_AI
								|| initialBoard.get(x, y + 1) != opponent)
							unsettledThreeCheck = false;
						if (initialBoard.get(x - 1, y) != Board.EMPTY_AI
								|| initialBoard.get(x - 1, y) != opponent)
							unsettledThreeCheck = false;
						if (initialBoard.get(x - 1, y - 1) != Board.EMPTY_AI
								|| initialBoard.get(x - 1, y - 1) != opponent)
							unsettledThreeCheck = false;
						if (initialBoard.get(x - 1, y + 1) != Board.EMPTY_AI
								|| initialBoard.get(x - 1, y + 1) != opponent)
							unsettledThreeCheck = false;
					}

					if (unsettledThreeCheck)
						middleCoord = new Coordinate(x, y);

					liberties = 0;
				}
			}
		}

		// Check current board for AI move in middle of unsettled three
		if (currentBoard.get(middleCoord.x, middleCoord.y) == colourAI) {
			return Rating.UNSETTLED_THREE.getValue();
		}

		return 0;
	}

}