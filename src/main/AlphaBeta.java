package main;

import java.util.ArrayList;

public class AlphaBeta extends AI  {

	private Objective evaluator;
    private LegalMoveChecker lmc;
	// 
    private int globalScore = Integer.MIN_VALUE;
    private static final int ALPHA = Integer.MIN_VALUE;
    private static final int BETA = Integer.MAX_VALUE;
	
	// constructor
    public AlphaBeta(Objective objective, int c, int[] searchScope) {
        evaluator = objective;
        colour = c;
        setBounds(searchScope);
    }
    
	@Override
	public Coordinate nextMove(Board b, LegalMoveChecker lmc) {
		this.lmc = lmc;
        Coordinate bestMove = null;
        // get available legal moves
        ArrayList<Coordinate> moves = new ArrayList<>();
        ArrayList<Board> boards = new ArrayList<>();
        getLMList(b, moves, boards, colour);
        int depth = moves.size()-1;
        int score = 0;
        
        // put a stone down for every legal move
        for (int i = 0; i < moves.size(); i++) {
            Coordinate move = moves.get(i);
            Board clone = boards.get(i);
            // evaluate current move
            if (evaluator.getAction(colour).equals("kill") && evaluator.checkSucceeded(clone, colour)) {
                return move;
            }
            // continue to opponent`s best reaction to this particular move
            int opponentColour = evaluator.getOtherColour(colour);
            score = alphaBeta(clone, depth-1, ALPHA, BETA, opponentColour);
            
            // compare the scores of all initial moves
            if (score > globalScore && score > 0) {
                globalScore = score;
            	bestMove = move;
            }
        }
        
        // return the best one or pass if 
        if (bestMove == null) // pass
        {
            return new Coordinate(-1, -1);
        }
        return bestMove;
    }
    
	// recursive alphaBeta pruning  
    public int alphaBeta(Board currentBoard, int depth, int alpha, int beta, int player){
    	// if no more valid moves
    	if (depth == 0) {
    		// evaluate the board
    		if (evaluator.checkSucceeded(currentBoard, colour)) {	
    			return BETA;
    		} else {
    			return ALPHA;
    		}
    	}

    	// if objective is completed at this stage
    	if (evaluator.getAction(colour).equals("kill") && evaluator.checkSucceeded(currentBoard, colour)) {
            return BETA;
        }
    	
    	// if none of the conditions above is met then:
    	// if maximizing player`s turn 
    	if (player == colour){
    		int score = ALPHA;
    		ArrayList<Coordinate> moves = new ArrayList<>();
            ArrayList<Board> boards = new ArrayList<>();
    		getLMList(currentBoard, moves, boards, colour);
    		for (int i = 0; i < moves.size(); i++) {
       	            Board clone = boards.get(i);
    	            // get response to current move from other player
    	            score = Math.max(score, alphaBeta(clone, depth-1, alpha, beta, evaluator.getOtherColour(colour)));
    	            alpha = Math.max(alpha, score);
    	            if (beta <= alpha) break;          // beta cut-off
    		}
    		return score;
    	}
    	
    	// if minimizing player`s turn
    	else{
    		int score = BETA;
    		ArrayList<Coordinate> moves = new ArrayList<>();
            ArrayList<Board> boards = new ArrayList<>();
    		getLMList(currentBoard, moves, boards, colour);
    		for (int i = 0; i < moves.size(); i++) {
    	            Board clone = boards.get(i);
    	            // get response to current move from other player
    	            score = Math.min(score, alphaBeta(clone, depth-1, alpha, beta, colour));
    	            beta = Math.min(beta, score);
    	            if (beta <= alpha) break;          // alpha cut-off
    		}
    		return score;
    	}
    }    
        
	// create array list containing the coordinates of the legal moves
    public void getLMList(Board b, ArrayList<Coordinate> moves, ArrayList<Board> boards, int colour) {
        for (int i = lowerBoundX; i <= upperBoundX; i++) {
            for (int j = lowerBoundY; j <= upperBoundY; j++) {
                Coordinate c = new Coordinate(i, j);
                if (lmc.checkMove(b, c, colour)) {
                    moves.add(c);
                    boards.add(lmc.getLastLegal());
                }
            }
        }
    }
}