package ai;

import ai.heuristics.libertyCounterHeuristic;
import main.Board;
import main.Coordinate;
import main.LegalMoveChecker;
import ai.Objective.Action;
//import ai.libertyCounterHeuristic;

public class AlphaBeta extends AI {

    private Objective evaluator;
    private LegalMoveChecker lmc;
    private libertyCounterHeuristic lcheuristic = new libertyCounterHeuristic();
    // 
    private int globalScore = Integer.MIN_VALUE;
    private static final int ALPHA = Integer.MIN_VALUE;
    private static final int BETA = Integer.MAX_VALUE;

    //add total considered moves counter
    private int movesConsidered;
    
    int opponent;
    Action abAction;
    Action opponentAction;
    
    //save the initial board as a global variable
    private Board initialBoard;
    
    // constructor
    public AlphaBeta(Objective objective, int c) {
        evaluator = objective;
        colour = c;
        opponent = evaluator.getOtherColour(colour);
        abAction = evaluator.getAction(colour);
        opponentAction = evaluator.getAction(opponent);
    }

    // get the number of boards evaluated
    public int getNumberOfMovesConsidered(){
    	return movesConsidered;
    }
    
    @Override
    public Coordinate nextMove(Board b, LegalMoveChecker legalMoves) {
        // reset the number of moves considered  
    	movesConsidered = 0;
    	
    	this.lmc = legalMoves.clone();
        Coordinate bestMove = null;
        // save the initial board to use during heurisic evaluation
        initialBoard = b.clone();
        
        // if objective is initially met pass
        if (abAction == Action.KILL && evaluator.checkSucceeded(b, colour)) {
            return new Coordinate(-1, -1);
        }

        int score = 0;
        // want to call a heuristic after the 8th move if no winner by then
        int depth = 8;
        
        // put a stone down for every legal move
        for (int x = 0; x < b.getWidth(); x++) {
            for (int y = 0; y < b.getHeight(); y++) {
                Coordinate currentCoord = new Coordinate(x, y);
                if (b.get(x, y) == Board.EMPTY_AI && lmc.checkMove(b, currentCoord, colour, true)) {
                    Board currentState = lmc.getLastLegal();
                    lmc.addBoard(currentState);
                    
                    movesConsidered++;
                    
                    // check the possibility of only one move needed
                    if (abAction == Action.KILL && evaluator.checkSucceeded(currentState, colour)) {
                        return currentCoord;
                    }

                    // continue to opponent`s best reaction to this particular move
                    score = alphaBeta(currentState, ALPHA, BETA, opponent,depth-1);
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
        if (bestMove == null) {
            return new Coordinate(-1, -1); // pass
        }
        return bestMove;
    }

    // recursive alphaBeta pruning  
    public int alphaBeta(Board currentBoard, int alpha, int beta, int player, int depth) {
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
            if (player == colour) {
                if (evaluator.checkSucceeded(currentBoard, colour)) {
                    return BETA;
                } else {
                    return ALPHA;
                }
            } else {
                if (evaluator.checkSucceeded(currentBoard, opponent)) {
                    return ALPHA;
                } else {
                    return BETA;
                }
            }
        }
        
        ///////////////////////////////////////////////////////////////////////////
        // heuristic call
        
        if (depth == 0) {
        	//System.out.println("enter heuristics");
        	int r = lcheuristic.assess(initialBoard, currentBoard, lmc, evaluator, colour); 
        	//System.out.println(r);
        	return r;
        }
        //
        //////////////////////////////////////////////////////////////////////////
        
        // if none of the conditions above is met then:
        // if maximizing player`s turn 
        if (player == colour) {
            int score = ALPHA;
            for (int x = 0; x < currentBoard.getWidth(); x++) {
                for (int y = 0; y < currentBoard.getHeight(); y++) {
                    Coordinate currentCoord = new Coordinate(x, y);
                    if (currentBoard.get(x, y) == Board.EMPTY_AI && lmc.checkMove(currentBoard, currentCoord, colour, true)) {
                        Board currentState = lmc.getLastLegal();
                        lmc.addBoard(currentState);
                        
                        movesConsidered++;
                        
                        // get response to current move from other player
                        score = Math.max(score, alphaBeta(currentState, alpha, beta, opponent, depth-1));
                        lmc.removeLast();
                        alpha = Math.max(alpha, score);
                        if (beta <= alpha) {
                            break;          // beta cut-off
                        }
                    }
                }
            }
            return score;
        } // if minimizing player`s turn
        else {
            int score = BETA;
            for (int x = 0; x < currentBoard.getWidth(); x++) {
                for (int y = 0; y < currentBoard.getHeight(); y++) {
                    Coordinate currentCoord = new Coordinate(x, y);
                    if (currentBoard.get(x, y) == Board.EMPTY_AI && lmc.checkMove(currentBoard, currentCoord, opponent, true)) {
                        Board currentState = lmc.getLastLegal();
                        lmc.addBoard(currentState);
                        
                        movesConsidered++;
                        
                        // get response to current move from other player
                        score = Math.min(score, alphaBeta(currentState, alpha, beta, colour, depth-1));
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
        for (int i = 0; i < b.getWidth(); i++) {
            for (int j = 0; j < b.getHeight(); j++) {
                Coordinate c = new Coordinate(i, j);
                if (lmc.checkMove(b, c, colour, true)) {
                    return false;
                }
            }
        }
        return true;
    }
}
