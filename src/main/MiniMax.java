package main;

import java.util.ArrayList;

public class MiniMax extends AI{

	private Objective evaluator;
	private LegalMoveChecker lmc;
	
	//Objective otherEvaluator = new Objective(getOpponentObjective(), getOtherColour(), evaluator.getPosition());
	
	public MiniMax(Objective objective, int c){
		evaluator = objective;
		colour = c;
	}
	
	//private String getOpponentObjective() {
		//if(evaluator.getAction() == "kill") return "defend";
		//else return "kill";
	//}
	
	/* AI is the maximizer in every case !*/
	@Override
	public Coordinate nextMove(Board b, boolean[][] legalMoves) {
		Coordinate bestMove = null;
		ArrayList<Coordinate> moves = getLMList(b, legalMoves);	
		int score = 0;
		// for every legal move put a stone down
		for (Coordinate move : moves) {
			Board clone = b.clone();
			clone.set(move.x, move.y, colour);	
			Coordinate pos = evaluator.getPosition();
			// evaluate current move
			if(evaluator.getAction() == "kill" && clone.get(pos.x, pos.y) == Board.EMPTY){	
				// if action is kill and killing is done 	
				return move;
			}
			// continue to opponent`s best reaction to this particular move
			score = min(clone);
			// return final result
			if (score > 0) {
				bestMove = move;
			}
			if (score == 0) // pass
				return new Coordinate(-1,-1);	
		}
		return bestMove;
	}
	
	
	private int min(Board b) {
		int lowestScore = 1;
		boolean[][] lm = getLegalMoves(b, getOtherColour());
		ArrayList<Coordinate> moves = getLMList(b, lm);
		int score = 0;
		// if there are no legal moves -> pass
		if (moves.isEmpty()) {
			return 0;
		}
		for (Coordinate move : moves) {
			Board clone = b.clone();
			clone.set(move.x, move.y, getOtherColour());
			Coordinate pos = evaluator.getPosition();
			//
			if(evaluator.getAction() == "defend" && clone.get(pos.x, pos.y) == Board.EMPTY){	
				// if action of opponent is kill and killing is done 	
				return -1; // -> best move for opponent
			}
			score = max(clone);
			if (score < lowestScore)
				lowestScore = score;
		}
		return lowestScore;
	}
	
	
	private int max(Board b) {	
		int highestScore = -1;
		
		boolean[][] lm = getLegalMoves(b, getOtherColour());
		ArrayList<Coordinate> moves = getLMList(b, lm);
		int score = 0;
		// if no legal moves -> pass
		if (moves.isEmpty()) {
			return 0;
		}
		for (Coordinate move : moves) {
			Board clone = b.clone();
			clone.set(move.x, move.y, colour);
			Coordinate pos = evaluator.getPosition();
			if(evaluator.getAction() == "kill" && clone.get(pos.x, pos.y) == Board.EMPTY){	
				// if action is kill and killing is done 	
				return 1; // best move for AI
			}
			score = min(clone);
			if (score > highestScore)
				highestScore = score;
		}
		return highestScore;
	}
	
	 public int getOtherColour(){
	    	if(colour == Board.BLACK) return Board.WHITE;
	    	return Board.BLACK;
	 }

	 // create array list containing the coordinates of the legal moves
	 public ArrayList<Coordinate> getLMList (Board b, boolean lm[][]){
		 ArrayList<Coordinate> al = new ArrayList<Coordinate>();
		 for(int i = 0; i < b.getWidth(); i++){
				for(int j=0;j<b.getHeight();j++){
					if (i>=lowerBoundX && i<=upperBoundX && j>=lowerBoundY && j<=upperBoundY){
						if (lm[i][j]){
							al.add(new Coordinate(i,j));
						}
					}
				}
			}
		 return al;
	 }
	  
	public boolean[][] getLegalMoves(Board b, int colour){
	    int xDim = b.getWidth();
	    int yDim = b.getHeight();
	    boolean[][] legalMoves = new boolean[xDim][yDim];
	    
	    for (int i = 0; i<xDim; i++)
	      for (int j = 0; j<yDim; j++)
	        legalMoves[i][j] =  lmc.checkMove(b, i, j, colour);
	      
	    return legalMoves;
	  }

}
