package ai.heuristics;

import main.Board;
import main.Coordinate;
import main.LegalMoveChecker;
import ai.Objective;

public class EyeCreator implements Heuristic {

	@Override
	public int assess(Board initialBoard, Board currentBoard,
			LegalMoveChecker lmc, Objective obj, int colourAI) {

		int AIStones = 0;
		Coordinate eye = new Coordinate(-1,-1);

		// Check original board for moves that will have 3 to 4 liberties
		for (int x = 0; x < currentBoard.getHeight(); x++) {
			for (int y = 0; y < currentBoard.getHeight(); y++) {

				// Check for empty space
				if (initialBoard.get(x, y) == Board.EMPTY_AI) {
					// Check for 3 AI stones surrounding
					try {
						if (initialBoard.get(x + 1, y) == colourAI) {
							AIStones++;
						} else if (initialBoard.get(x + 1, y) == Board.EMPTY_AI) {
							eye.x = x+1;
							eye.y = y;
						}
					} catch (Exception e) {}
					try {
						if (initialBoard.get(x, y - 1) == colourAI) {
							AIStones++;
						} else if (initialBoard.get(x, y - 1) == Board.EMPTY_AI) {
							eye.x = x;
							eye.y = y-1;
						}
					} catch (Exception e) {}
					try {
						if (initialBoard.get(x, y + 1) == colourAI) {
							AIStones++;
						} else if (initialBoard.get(x, y + 1) == Board.EMPTY_AI) {
							eye.x = x;
							eye.y = y+1;
						}
					} catch (Exception e) {}
					try {
						if (initialBoard.get(x - 1, y) == colourAI) {
							AIStones++;
						} else if (initialBoard.get(x - 1, y) == Board.EMPTY_AI) {
							eye.x = x-1;
							eye.y = y;
						}
					} catch (Exception e) {}
				}
				
				// Return eye if 3 AI stones surround and AI stone in empty space of current board
				if (AIStones == 3 && eye.x != -1 && eye.y != -1 && (currentBoard.get(eye.x, eye.y) == colourAI)) {
					return Rating.EYE_CREATOR.getValue();
				}
				
				AIStones = 0;
				eye.x = -1;
				eye.y = -1;
				
			}
		}

		return 0;
	}

}
