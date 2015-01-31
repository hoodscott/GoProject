package main;

import main.Objective.Action;

public class AlphaBeta extends AI {

    private Objective evaluator;
    private LegalMoveChecker lmc;
    // 
    private int globalScore = Integer.MIN_VALUE;
    private static final int ALPHA = Integer.MIN_VALUE;
    private static final int BETA = Integer.MAX_VALUE;
    //
    int opponent;
    Action abAction;
    Action opponentAction;
    
    // constructor
    public AlphaBeta(Objective objective, int c, int[] searchScope) {
        evaluator = objective;
        colour = c;
        setBounds(searchScope);
        opponent = evaluator.getOtherColour(colour);
        abAction = evaluator.getAction(colour);
        opponentAction = evaluator.getAction(opponent);
    }
    
    @Override
    public Coordinate nextMove(Board b, LegalMoveChecker lmc) {
        this.lmc = lmc.clone();
        Coordinate bestMove = null;
        
        // if objective is initially met pass
        if (abAction == Action.KILL && evaluator.checkSucceeded(b, colour)) {
        	return new Coordinate(-1, -1);
        }
       
        int score = 0;

        // put a stone down for every legal move
        for (int x = lowerBoundX; x <= upperBoundX; x++) {
            for (int y = lowerBoundY; y <= upperBoundY; y++) {
                Coordinate currentCoord = new Coordinate(x, y);
                if (lmc.checkMove(b, currentCoord, colour)) {
                	Board currentState = lmc.getLastLegal();
                	lmc.addBoard(currentState);
                	
                	// check the possibility of only one move needed
                	if (abAction == Action.KILL && evaluator.checkSucceeded(currentState, colour)) {
                		return currentCoord;
                	}
            
                	// continue to opponent`s best reaction to this particular move
                	score = alphaBeta(currentState, ALPHA, BETA, opponent);
                	lmc.removeLast();
                	
                	// compare the scores of all initial moves
                	if (score > globalScore && score > 0) {
                		globalScore = score;
                		bestMove = currentCoord;
                	}
                }
            }
        }

        // pass if no move will improve the situation 
        if (bestMove == null) 
        {
            return new Coordinate(-1, -1); // pass
        }
        return bestMove;
    }

    // recursive alphaBeta pruning  
    public int alphaBeta(Board currentBoard, int alpha, int beta, int player) {
    	///////////////////////////////////////////////////////////////////////////
    	// check for terminal position
    	// if killing objective is completed at this stage
    	if (abAction == Action.KILL && evaluator.checkSucceeded(currentBoard, colour)) {
            return BETA;
        }
        // if AI failed to defend 
        if (opponentAction == Action.KILL && evaluator.checkSucceeded(currentBoard, opponent)) {
            return ALPHA;
        }
    	
    	// if no more valid moves evaluate the board
        if (noMoreLegalMoves(currentBoard, player)) {
            if (player == colour){
            	if (evaluator.checkSucceeded(currentBoard, colour)) {
                    return BETA;
                } else {
                    return ALPHA;
                }
            }
            else{
            	if (evaluator.checkSucceeded(currentBoard, opponent)) {
                    return ALPHA;
                } else {
                    return BETA;
                }
            }
        }
        ///////////////////////////////////////////////////////////////////////////
        
    	// if none of the conditions above is met then:
        // if maximizing player`s turn 
        if (player == colour) {
            int score = ALPHA;
            for (int x = lowerBoundX; x <= upperBoundX; x++) {
                for (int y = lowerBoundY; y <= upperBoundY; y++) {
                    Coordinate currentCoord = new Coordinate(x, y);
                    if (lmc.checkMove(currentBoard, currentCoord, colour)) {
                    	Board currentState = lmc.getLastLegal();
                    	lmc.addBoard(currentState);
                    	// get response to current move from other player
                    	score = Math.max(score, alphaBeta(currentState, alpha, beta, opponent));
                    	lmc.removeLast();
                    	alpha = Math.max(alpha, score);
                    	if (beta <= alpha) {
                    		break;          // beta cut-off
                    	}
                    }
                }
            }
            return score;
        } 
        
        // if minimizing player`s turn
        else {
            int score = BETA;
            for (int x = lowerBoundX; x <= upperBoundX; x++) {
                for (int y = lowerBoundY; y <= upperBoundY; y++) {
                    Coordinate currentCoord = new Coordinate(x, y);
                    if (lmc.checkMove(currentBoard, currentCoord, opponent)) {
                    	Board currentState = lmc.getLastLegal();
                    	lmc.addBoard(currentState);
                    	// get response to current move from other player
                    	score = Math.min(score, alphaBeta(currentState, alpha, beta, colour));
                    	lmc.removeLast();
                    	beta = Math.min(beta, score);
                    	if (beta <= alpha) {
                    		break;          // alpha cut-off
                    	}
                    }
                }
            }
            return score;
        }
    }
    
    
    // checks if there are not legal moves
    public boolean noMoreLegalMoves(Board b, int colour) {
        for (int i = lowerBoundX; i <= upperBoundX; i++) {
            for (int j = lowerBoundY; j <= upperBoundY; j++) {
                Coordinate c = new Coordinate(i, j);
                if (lmc.checkMove(b, c, colour)) {
                    return false;
                }
            }
        }
        return true;
    }
}
	