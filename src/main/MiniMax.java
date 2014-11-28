package main;

import java.util.ArrayList;

import main.Objective.Action;

public class MiniMax extends AI{

	Objective evaluator;
	private LegalMoveChecker lmc;
	
	Objective otherEvaluator = new Objective(getOpponentObjective(), getOtherColour(), evaluator.position);
	
	public MiniMax(Objective objective, int c){//), int[] searchSp){
		evaluator = objective;
		colour = c;
		//searchSpace = searchSp;
	}
	
	private String getOpponentObjective() {
		if(evaluator.action == Action.KILL) return "defend";
		else return "kill";
	}
	
	@Override
	public Coordinate nextMove(Board b, boolean[][] legalMoves) {
		Coordinate bestMove = null;
		ArrayList<Coordinate> moves = getLMList(b, legalMoves);	
		int score = 0;
		for (Coordinate move : moves) {
			Board clone = b.clone();
			clone.set(move.x, move.y, colour);
			score = min(clone);
			if (score > 0) {
				bestMove = move;
			}
		}
		return bestMove;
	}
	
	
	private int min(Board b) {
		int lowestScore = 1;
		boolean[][] lm = getLegalMoves(b, getOtherColour());
		ArrayList<Coordinate> moves = getLMList(b, lm);
		int score = 0;
		for (Coordinate move : moves) {
			Board clone = b.clone();
			clone.set(move.x, move.y, getOtherColour());
			if (otherEvaluator.checkSucceeded(clone)) return -1;
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
		for (Coordinate move : moves) {
			Board clone = b.clone();
			clone.set(move.x, move.y, colour);
			if (evaluator.checkSucceeded(clone)) return 1;
			min(clone);
			if (score > highestScore)
				highestScore = score;
		}
		return highestScore;
	}
	
	 public int getOtherColour(){
	    	if(colour == 1) return 2;
	    	return 1;
	 }

	 // create array list containing the coordinates of the legal moves
	 public ArrayList<Coordinate> getLMList (Board b, boolean lm[][]){
		 ArrayList<Coordinate> al = new ArrayList<Coordinate>();
		 for(int i = 0; i < b.getWidth(); i++){
				for(int j=0;j<b.getHeight();j++){
					if (lm[i][j]){
						al.add(new Coordinate(i,j));
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
