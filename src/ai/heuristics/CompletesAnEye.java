package ai.heuristics;

import ai.Objective;
import ai.heuristics.Heuristic;
import java.util.ArrayList;
import java.util.Arrays;

import main.Board;
import main.LegalMoveChecker;
import ai.heuristics.Rating;
//create a first heuristic to detect eyes and 
// return appropriate value
public class CompletesAnEye implements Heuristic{
	
	@Override
	/// gives a board a move, obj and player to move 
	// should return COMPLETES_AN_EYE if a move
	// is found to return an eye else 0
	public int assess(Board initialBoard, Board b, LegalMoveChecker lmc, Objective obj, int colourAI) {
		
		int x_limit = b.getWidth();
		int y_limit = b.getHeight();
		// variable for determining how many out of 4 stones are in place
		// to create an eye (x+1,y),(x-1,y),(x,y-1) and (x,y+1)
		int eye_counter = 0;
		boolean isAlmostEye = false;
		byte[][] board_to_assess = b.getRaw();
		//iterate over the board 
		for (int x = 0; x < x_limit;x++){
			eye_counter = 0;
			for (int y = 0; y < y_limit; y++){
				
				if(board_to_assess[x+1][y] == colourAI )
					eye_counter++;
				if(board_to_assess[x-1][y] == colourAI )
					eye_counter++;
				if(board_to_assess[x][y+1] == colourAI )
					eye_counter++;
				if(board_to_assess[x][y-1] == colourAI )
					eye_counter++;
				if (eye_counter == 3)
					isAlmostEye = true;
			}
		}
			
		if (isAlmostEye){
		return Rating.COMPLETES_AN_EYE.getValue();
		}else{
			return 0;
		}
	}
	
}
