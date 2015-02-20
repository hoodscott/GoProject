package ai.heuristics;
import ai.Objective;
import main.Board;
import main.LegalMoveChecker;
//SIX DIE, EIGHT LIVE HUERISTIC
// Determines whether a move will make 8 in a row
public class SevenStonesInARow implements Heuristic{
	
	@Override
	/// gives a board a move, obj and player to move 
	// should return MAKES_EIGHT_IN_A_ROW if a move
	// is found to return an eye else 0
	public int assess(Board initialBoard, Board b, LegalMoveChecker lmc, Objective obj, int colourAI) {
		
		int x_limit = b.getWidth();
		int y_limit = b.getHeight();
		
		int stone_counter = 0;
		boolean isSevenInARow = false;
		boolean isFiveInARow = false;
		byte[][] board_to_assess = b.getRaw();
		//iterate over the board 
		// only search edge rows + columns [1,y], [x,1], [width -1,y] and [x, height-1]
		
			// search [1,y]
			stone_counter = 0;
			for (int y = 0; y < y_limit; y++){
				
				if(board_to_assess[1][y] == colourAI ){
					stone_counter++;
				}else{
					stone_counter = 0;
				}
				if (stone_counter == 7)
					isSevenInARow = true;
			}
			
			// search [width -1,y]
			for (int y = 0; y < y_limit; y++){
				
				if(board_to_assess[x_limit - 1][y] == colourAI ){
					stone_counter++;
				}else{
					stone_counter = 0;
				}
				if (stone_counter == 7)
					isSevenInARow = true;
			}
			// search [x,1]
			for (int x = 0; x < x_limit; x++){
				
				if(board_to_assess[x][1] == colourAI ){
					stone_counter++;
				}else{
					stone_counter = 0;
				}
				if (stone_counter == 7)
					isSevenInARow = true;
			}
			// search [x, height -1]
			for (int x = 0; x < x_limit; x++){
				
				if(board_to_assess[x][y_limit - 1] == colourAI ){
					stone_counter++;
				}else{
					stone_counter = 0;
				}
				if (stone_counter == 7)
					isSevenInARow = true;
			}
			
		
		
		
		// search for 5 stones in the corners
		
		// search [1,y] corners
					
					for (int y = 0; y <= 5; y++){
						
						if(board_to_assess[1][y] == colourAI ){
							stone_counter++;
						}else{
							stone_counter = 0;
						}
						if (stone_counter == 5)
							isFiveInARow = true;
					}
					
					stone_counter = 0;
					for (int y = 13; y <= 17; y++){
						
						if(board_to_assess[1][y] == colourAI ){
							stone_counter++;
						}else{
							stone_counter = 0;
						}
						if (stone_counter == 5)
							isFiveInARow = true;
					}
					
		// search [x,1] corners
					stone_counter = 0;
					for (int x = 0; x <= 5; x++){
									
							if(board_to_assess[x][1] == colourAI ){
									stone_counter++;
							}else{
									stone_counter = 0;
							}
							if (stone_counter == 5)
								isFiveInARow = true;
								}
								
					stone_counter = 0;
					for (int x = 13; x <= 17; x++){
									
							if(board_to_assess[x][1] == colourAI ){
									stone_counter++;
							}else{
									stone_counter = 0;
							}
							if (stone_counter == 5)
								isFiveInARow = true;
								}
					
		// search [width -1 ,y] corners
					stone_counter = 0;
					for (int y = 0; y <= 5; y++){
									
							if(board_to_assess[x_limit - 1][y] == colourAI ){
									stone_counter++;
							}else{
									stone_counter = 0;
							}
							if (stone_counter == 5)
								isFiveInARow = true;
								}
								
					stone_counter = 0;
					for (int y = 13; y <= 17; y++){
									
							if(board_to_assess[x_limit - 1][y] == colourAI ){
									stone_counter++;
							}else{
									stone_counter = 0;
							}
							if (stone_counter == 5)
								isFiveInARow = true;
								}
					
		// search [x ,height -1] corners
					stone_counter = 0;
					for (int x = 0; x <= 5; x++){
									
							if(board_to_assess[x][y_limit - 1] == colourAI ){
									stone_counter++;
							}else{
									stone_counter = 0;
							}
							if (stone_counter == 5)
								isFiveInARow = true;
								}
								
					stone_counter = 0;
					for (int x = 13; x <= 17; x++){
									
							if(board_to_assess[x][y_limit -1] == colourAI ){
									stone_counter++;
							}else{
									stone_counter = 0;
							}
							if (stone_counter == 5)
								isFiveInARow = true;
								}
				
					
	if (isFiveInARow)
		return Rating.MAKES_SIX_IN_A_ROW.getValue();
	if(isSevenInARow){
		return Rating.MAKES_EIGHT_IN_A_ROW.getValue();
	}else{
		return 0;
	}
	
	}
}
