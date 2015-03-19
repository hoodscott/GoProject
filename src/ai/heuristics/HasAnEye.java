package ai.heuristics;

import ai.Objective;
import ai.Objective.Action;
import ai.heuristics.Heuristic;

import java.util.ArrayList;
import java.util.Arrays;

import main.Board;
import main.Coordinate;
import main.LegalMoveChecker;
import ai.heuristics.Rating;
//create a first heuristic to detect eyes and 
// return appropriate value
public class HasAnEye implements Heuristic{
	
	@Override
	/// gives a board a move, obj and player to move 
	// should return COMPLETES_AN_EYE if a move
	// is found to return an eye else 0
	public int assess(Board initialBoard, Board currentBoard, LegalMoveChecker lmc, Objective obj, int colourAI) {
		
		int x_limit = initialBoard.getWidth();
		int y_limit = initialBoard.getHeight();
		// variable for determining how many out of 4 stones are in place
		// to create an eye (x+1,y),(x-1,y),(x,y-1) and (x,y+1)
		int eye_counter = 0;
		int edgeEye_counter = 0;
		int cornerEye_counter = 0;
		int twoEye_counter = 0;
		
		boolean is2pointEdgeEye = false;
		boolean hasTwoEyes = false;
		boolean is2pointEye = false;
		boolean isEye = false;
		boolean isEdgeEye = false;
		boolean isCornerEye = false;
		
		
		
		
		//iterate over the board excluding the edges
		for (int x = 1; x <= (x_limit - 2);x++){
			//eye_counter = 0;
			for (int y = 1; y <= (y_limit - 2); y++){
				
				if(initialBoard.get( x+1, y) == colourAI )
					eye_counter++;
				if(initialBoard.get(x-1,y) == colourAI )
					eye_counter++;
				if(initialBoard.get(x, y+1) == colourAI )
					eye_counter++;
				if(initialBoard.get(x, y-1) == colourAI )
					eye_counter++;
				
				
				
				if (eye_counter == 4 && currentBoard.get(x,y) == Board.EMPTY_AI){
					isEye = true;
				    eye_counter = 0;
				    twoEye_counter++;}
				
			}
		}
		
		//check edges i.e. [0,y], [x,0], [x_limit - 1,y], [x,y_limit -1]
		// omitting corner points
		
		//search [0,y]
		edgeEye_counter = 0;
		for (int y = 1; y <= (y_limit - 2); y++){
			
			if(initialBoard.get(0,y-1) == colourAI )
				edgeEye_counter++;
			if(initialBoard.get(1,y) == colourAI )
				edgeEye_counter++;
			if(initialBoard.get(0,y+1) == colourAI ){
				edgeEye_counter++;
			}else{
				edgeEye_counter = 0;
			}
			if (edgeEye_counter == 3 && currentBoard.get(0,y) == Board.EMPTY_AI)
				isEdgeEye = true;
			    twoEye_counter++;
			    
		}
		
		// search [x,0]
		edgeEye_counter = 0;
		for (int x = 1; x <= (x_limit - 2); x++){
			
			if(initialBoard.get(x-1,0) == colourAI )
				edgeEye_counter++;
			if(initialBoard.get(x,1) == colourAI )
				edgeEye_counter++;
			if(initialBoard.get(x+1,0) == colourAI ){
				edgeEye_counter++;
			}else{
				edgeEye_counter = 0;
			}
			if (edgeEye_counter == 3 && currentBoard.get(x,0) == Board.EMPTY_AI)
				isEdgeEye = true;
				twoEye_counter++;
		}		
		
		//search [x,y_limit-1]
		edgeEye_counter = 0;
		for (int x = 1; x <= (x_limit - 2); x++){
			
			if(initialBoard.get(x-1,y_limit-1) == colourAI )
				edgeEye_counter++;
			if(initialBoard.get(x, y_limit-2) == colourAI )
				edgeEye_counter++;
			if(initialBoard.get(x+1, y_limit-1) == colourAI ){
				edgeEye_counter++;
			}else{
				edgeEye_counter = 0;
			}
			if (edgeEye_counter == 3 && currentBoard.get(x, y_limit) == Board.EMPTY_AI)
				isEdgeEye = true;
				twoEye_counter++;
				
		}
		
		//search [x_limit -1,y]
				edgeEye_counter = 0;
				for (int y = 1; y <= (y_limit - 2); y++){
					
					if(initialBoard.get(x_limit-1, y-1) == colourAI )
						edgeEye_counter++;
					if(initialBoard.get(x_limit-2,y) == colourAI )
						edgeEye_counter++;
					if(initialBoard.get(x_limit-1, y+1) == colourAI ){
						edgeEye_counter++;
					}else{
						edgeEye_counter = 0;
					}
					if (edgeEye_counter == 3 && currentBoard.get(x_limit ,y) == Board.EMPTY_AI)
						isEdgeEye = true;
						twoEye_counter++;
						
				}
				
		// now check the 4 corner points
		
		if (initialBoard.get(0,1) == colourAI && initialBoard.get(1,0) == colourAI && currentBoard.get(0,0) == Board.EMPTY_AI ){
			isCornerEye = true;
			
		}
		if (initialBoard.get(0, y_limit-2) == colourAI && initialBoard.get(1, y_limit-1) == colourAI && currentBoard.get(0, y_limit-1) == Board.EMPTY_AI ){
			isCornerEye = true;
		}
		if (initialBoard.get(x_limit-2, 0) == colourAI && initialBoard.get(x_limit-1,1) == colourAI && currentBoard.get(x_limit -1, 0) == Board.EMPTY_AI){
			isCornerEye = true;
		}
		if (initialBoard.get(x_limit-1, y_limit-2) == colourAI && initialBoard.get(x_limit-2, y_limit-1) == colourAI && currentBoard.get(x_limit - 1,y_limit -1) == Board.EMPTY_AI){
			isCornerEye = true;
		}
		
				
		////////////////////////////////////////////////////////////////////////////////		
		// check for 2 point eyes
		//iterate over the board excluding the edges
		for (int x = 1, x1 = 2; x <= (x_limit - 2) && x1 < (x_limit - 2);x++){
			eye_counter = 0;
			for (int y = 1, y1 = 1; y <= (y_limit - 2) && y1 < (y_limit - 2); y++){
						
				if(initialBoard.get(x,y-1) == colourAI )
					eye_counter++;
				if(initialBoard.get(x-1,y) == colourAI )
					eye_counter++;
				if(initialBoard.get(x,y+1) == colourAI )
					eye_counter++;
				if(initialBoard.get(x1,y1-1) == colourAI )
					eye_counter++;
				if(initialBoard.get(x1,y1+1) == colourAI )
					eye_counter++;
				if(initialBoard.get(x1+1, y1) == colourAI )
					eye_counter++;
						
				if (eye_counter == 6 && initialBoard.get(x,y) == Board.EMPTY_AI &&  initialBoard.get(x1,y1) == Board.EMPTY_AI){
					is2pointEye = true;
				    eye_counter = 0;
				    twoEye_counter++;}
						
			}
		}
				
		//check edges i.e. [0,y], [x,0], [x_limit - 1,y], [x,y_limit -1]
				// omitting corner points
				
				//search [0,y]
				edgeEye_counter = 0;
				for (int y = 1, y1 = 2; y1 <= (y_limit - 2); y++, y1++){
					
					if(initialBoard.get(0,y-1) == colourAI )
						edgeEye_counter++;
					if(initialBoard.get(1,y) == colourAI )
						edgeEye_counter++;
					if(initialBoard.get(1,y1) == colourAI )
						edgeEye_counter++;
					if(initialBoard.get(0,y1+1) == colourAI ){
						edgeEye_counter++;
					}else{
						edgeEye_counter = 0;
					}
					
					if (edgeEye_counter == 4){
						is2pointEdgeEye = true;
					    twoEye_counter++;
					    edgeEye_counter=0;
				    }			
				
				}
				
				// search [x,0]
				edgeEye_counter = 0;
				for (int x = 1, x1 = 2; x1 <= (x_limit - 2); x++, x1++){
					
					if(initialBoard.get(x-1,0) == colourAI )
						edgeEye_counter++;
					if(initialBoard.get(x,1) == colourAI )
						edgeEye_counter++;
					if(initialBoard.get(x1,1) == colourAI ){
						edgeEye_counter++;
					if(initialBoard.get(x+1,0) == colourAI ){
						edgeEye_counter++;
					}else{
						edgeEye_counter = 0;
					}
					if (edgeEye_counter == 4)
						is2pointEdgeEye = true;
						twoEye_counter++;
						edgeEye_counter=0;
					}
				}	
				
			    //search [x,y_limit-1]
				edgeEye_counter = 0;
				for (int x = 1, x1 = 2; x1 <= (x_limit - 2); x++, x1++){
						
					if(initialBoard.get(x-1, y_limit-1) == colourAI )
						edgeEye_counter++;
					if(initialBoard.get(x, y_limit-2) == colourAI )
						edgeEye_counter++;
					if(initialBoard.get(x1, y_limit-2) == colourAI )
						edgeEye_counter++;
					if(initialBoard.get(x1+1, y_limit-1) == colourAI ){
						edgeEye_counter++;
					}else{
						edgeEye_counter = 0;
					}
					if (edgeEye_counter == 4){
						is2pointEdgeEye = true;
						twoEye_counter++;
						edgeEye_counter=0;
					}
				}
					
				//search [x_limit -1,y]
				edgeEye_counter = 0;
				for (int y = 1, y1 = 2; y1 <= (y_limit - 2); y++, y1++){
					
					if(initialBoard.get(x_limit-1,y-1) == colourAI )
						edgeEye_counter++;
					if(initialBoard.get(x_limit-2,y) == colourAI )
						edgeEye_counter++;
					if(initialBoard.get(x_limit-2,y1) == colourAI )
						edgeEye_counter++;
					if(initialBoard.get(x_limit-1,y1+1) == colourAI ){
						edgeEye_counter++;
					}else{
						edgeEye_counter = 0;
					}
					if (edgeEye_counter == 4){
						is2pointEdgeEye = true;
						twoEye_counter++;
						edgeEye_counter=0;
					}	
				}
				
		
		
				
		if (twoEye_counter >= 2){
			return Rating.HAS_A_SECOND_EYE.getValue();
		}else{
		
		if (isEye || isEdgeEye || isCornerEye){
		return Rating.HAS_AN_EYE.getValue();
		}else if (is2pointEye || is2pointEdgeEye){
			return Rating.HAS_AN_2_POINT_EYE.getValue();
		}else{
			return 0;
		}
		
		}
	}
		
	}
	
	
