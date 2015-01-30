package main;

import java.util.ArrayList;

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
    
    // iteratevam
    // na vseki move pravq check dali e terminal position (ili veche e captured ili defended, ili nqma legal move )
    // ako veche captured ili defended - returnvam beta 
    // ako 0 moves ako capture return Beta inache Alpha
    
    
    @Override
    public Coordinate nextMove(Board b, LegalMoveChecker lmc) {
        this.lmc = lmc.clone();
        Coordinate bestMove = null;
        
        // if objective is initially met pass
        if (abAction == Action.KILL && evaluator.checkSucceeded(b, colour)) {
        	return new Coordinate(-1, -1);
        }
        
        // get available legal moves
        ArrayList<Coordinate> moves = new ArrayList<>();
        ArrayList<Board> boards = new ArrayList<>();
        getLMList(b, moves, boards, colour);
        int score = 0;

        // put a stone down for every legal move
        for (int i = 0; i < moves.size(); i++) {
            Coordinate move = moves.get(i);
            Board clone = boards.get(i);
            // check the possibility of only one move needed
            if (abAction == Action.KILL && evaluator.checkSucceeded(clone, colour)) {
                return move;
            }
            // continue to opponent`s best reaction to this particular move
            score = alphaBeta(clone, ALPHA, BETA, opponent);

            // compare the scores of all initial moves
            if (score > globalScore && score > 0) {
                globalScore = score;
                bestMove = move;
            }
        }

        // return the best one or pass if no move will improve the situation 
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
    	 // if objective is completed at this stage
        if (abAction == Action.KILL && evaluator.checkSucceeded(currentBoard, colour)) {
            return BETA;
        }
        // if AI failed to defend 
        if (opponentAction == Action.KILL && evaluator.checkSucceeded(currentBoard, opponent)) {
            return ALPHA;
        }
        
    	//get the coordinates and number of legal moves for current player
    	ArrayList<Coordinate> moves = new ArrayList<>();
        ArrayList<Board> boards = new ArrayList<>();
        getLMList(currentBoard, moves, boards, player);
        int numberOfLegalMoves = moves.size();
    	
    	// if no more valid moves
        if (numberOfLegalMoves == 0) {
            // evaluate the board
            if (evaluator.checkSucceeded(currentBoard, colour)) {
                return BETA;
            } else {
                return ALPHA;
            }
        }
        ///////////////////////////////////////////////////////////////////////////
        
    	// if none of the conditions above is met then:
        // if maximizing player`s turn 
        if (player == colour) {
            int score = ALPHA;
            for (int i = 0; i < moves.size(); i++) {
                Board clone = boards.get(i);
                // get response to current move from other player
                score = Math.max(score, alphaBeta(clone, alpha, beta, opponent));
                alpha = Math.max(alpha, score);
                if (beta <= alpha) {
                    break;          // beta cut-off
                }
            }
            return score;
        } 
        
        // if minimizing player`s turn
        else {
            int score = BETA;
            for (int i = 0; i < moves.size(); i++) {
                Board clone = boards.get(i);
                // get response to current move from other player
                score = Math.min(score, alphaBeta(clone, alpha, beta, colour));
                beta = Math.min(beta, score);
                if (beta <= alpha) {
                    break;          // alpha cut-off
                }
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
	