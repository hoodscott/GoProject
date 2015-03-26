package ai;

import main.Board;
import main.Coordinate;
import main.LegalMoveChecker;
import ai.Objective.Action;
import main.Translator;
/**
 * Alpha-Beta tree search which works by either performing a brute force search or
 * By performing a depth-first search to a given depth and calling the heuristics.
 * In both cases, the alpha and beta values are passed back up and used for pruning.
 */
public class AlphaBeta extends HeuristicsAI {

    private Objective evaluator;
    private LegalMoveChecker lmc;
    
    private int globalScore = Integer.MIN_VALUE;
    private static final int MINIMUM = Integer.MIN_VALUE;
    private static final int MAXIMUM = Integer.MAX_VALUE;

    int opponent;
    Action abAction;
    Action opponentAction;
    Board initialBoard;
    
    // constructor
    public AlphaBeta(Objective objective, int c, String[] heuristics) {
        evaluator = objective;
        colour = c;
        opponent = evaluator.getOtherColour(colour);
        abAction = evaluator.getAction(colour);
        opponentAction = evaluator.getAction(opponent);
        if (heuristics != null){
            setHeuristics(heuristics);
            usingHeuristics = true;       
        } 
    }
    

    //Main call to the AI. Initialises as maximiser
    @Override
    public Coordinate nextMove(Board b, LegalMoveChecker legalMoves) {
        // reset the number of moves considered  
    	movesConsidered = 0;
        //Checks number of heuristics in use
    	if(heuristics.isEmpty())
            System.out.println("WARNING: No heuristic selected for alpha-beta.");
        if(heuristicsFirst)
            System.out.println("Heuristics set to trigger at first depth.");
        
        this.lmc = legalMoves.clone();
        Coordinate bestMove = null;
        
        // save the initial board to use during heurisic evaluation
        initialBoard = b.clone();
        
        // if objective is initially met pass
        if (abAction == Action.KILL && evaluator.checkSucceeded(b, colour)) {
            return new Coordinate(-1, -1);
        }

        int score = 0;
        Translator.printGameBoard(b);
        // put a stone down for every legal move
        for (int x = 0; x < b.getWidth(); x++) {
            for (int y = 0; y < b.getHeight(); y++) {
                Coordinate currentCoord = new Coordinate(x, y);
                if (b.get(x, y) == Board.EMPTY_AI && lmc.checkMove(b, currentCoord, colour, true)) {
                    Board currentState = lmc.getLastLegal();
                    lmc.addBoard(currentState);
                    // continue to opponent`s best reaction to this particular move
                    score = beta(currentState, MINIMUM, MAXIMUM, moveDepth-1, false);
                    lmc.removeLast();

                    // compare the scores of all initial moves
                    if(score == MAXIMUM)
                        return currentCoord;
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
    
    private int alpha(Board b, int alpha, int beta, int depth, boolean passed){
        movesConsidered++;
        // If the opponent managed to capture the group.
        if (opponentAction == Action.KILL && evaluator.checkSucceeded(b, opponent)) {
            return MINIMUM;
        }
        
        if(usingHeuristics){
            if(heuristicsFirst && depth == moveDepth-1){
                if (depth == 0) 
                    return 0;
                int heuristicScore = getHeuristicScores(initialBoard, b,  lmc,  evaluator);
                if (heuristicScore > 0)
                    return heuristicScore;
            }
            else if(depth == 0)
                return getHeuristicScores(initialBoard, b,  lmc,  evaluator);
        }
        
        //Initialises maximizer
        int score = MINIMUM;
            for (int x = 0; x < b.getWidth(); x++) {
                for (int y = 0; y < b.getHeight(); y++) {
                    Coordinate currentCoord = new Coordinate(x, y);
                    if (b.get(x, y) == Board.EMPTY_AI && lmc.checkMove(b, currentCoord, colour, true)) {
                        
                        Board currentState = lmc.getLastLegal();
                        lmc.addBoard(currentState);
                        
                        // get response to current move from other player
                        score = Math.max(score, beta(currentState, alpha, beta, depth-1,false));
                        lmc.removeLast();
                        alpha = Math.max(alpha, score);
                        if (beta <= alpha) {
                            return score;          // beta cut-off
                        }
                    }
                }
            }
        if (!passed) {
            return beta(b, alpha, beta, depth-1, true);
        }

        //If the AI can no longer kill the opponent
        if (opponentAction == Action.DEFEND && evaluator.checkSucceeded(b, opponent)) {
            return MINIMUM;
        } //If there are no more legal moves and the AI's defended group still lives.
        else {
            return MAXIMUM;
        }
    }
    private int beta(Board b, int alpha, int beta, int depth, boolean passed){
        movesConsidered++;
        // If the AI managed to capture the group.
        if (abAction == Action.KILL && evaluator.checkSucceeded(b, colour)) {         
            return MAXIMUM;
        }
        
        if(usingHeuristics){
            if(heuristicsFirst && depth == moveDepth-1){
                if (depth == 0) 
                    return 0;
                int heuristicScore = getHeuristicScores(initialBoard, b,  lmc,  evaluator);
                if (heuristicScore > 0)
                    return heuristicScore;
            }
            else if(depth == 0)
                return getHeuristicScores(initialBoard, b,  lmc,  evaluator);
        }
        
        
        //Initialises minimiser
        int score = MAXIMUM;
            for (int x = 0; x < b.getWidth(); x++) {
                for (int y = 0; y < b.getHeight(); y++) {
                    Coordinate currentCoord = new Coordinate(x, y);
                    if (b.get(x, y) == Board.EMPTY_AI && lmc.checkMove(b, currentCoord, opponent, true)) {          
                        Board currentState = lmc.getLastLegal();
                        lmc.addBoard(currentState);              
                        
                        // get response to current move from other player
                        score = Math.min(score, alpha(currentState, alpha, beta, depth-1, false));
                        lmc.removeLast();
                        beta = Math.min(beta, score);
                        if (beta <= alpha) {
                            return score;          // alpha cut-off
                        }
                    }
                }
            }
            
        if (!passed) {
            return alpha(b, alpha, beta, depth-1, true);
        }

        //If the AI's stone group can no longer be captured.
        if (abAction == Action.DEFEND && evaluator.checkSucceeded(b, colour)) {
            return MAXIMUM;
        } //If there are no more legal moves and the opponent's defended group still lives.
        else {
            return MINIMUM;
        }
    }
}
