package ai;

import ai.heuristics.*;
import main.Board;
import main.Coordinate;
import main.LegalMoveChecker;
import ai.Objective.Action;
import java.util.ArrayList;
import main.Translator;

public class AlphaBeta extends AI {

    private Objective evaluator;
    private LegalMoveChecker lmc;
    
    ArrayList<Heuristic> heuristics = new ArrayList();
    private int globalScore = Integer.MIN_VALUE;
    private static final int ALPHA = Integer.MIN_VALUE;
    private static final int BETA = Integer.MAX_VALUE;
    private int moveDepth = 30;
    private final boolean heuristicsFirst = false;

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
        if (heuristics != null) setHeuristics(heuristics);
    }
    
    //Method for setting heuristics
    public void setHeuristics(String[] names){
        heuristics = new ArrayList();
        for(String name : names)
            addHeuristic(name);
    }
    
    public void addHeuristic(String heuristicName){
        switch(heuristicName){
            case "Hane": heuristics.add(new Hane()); break;
            // case "HasAnEye": heuristics.add(new HasAnEye()); break;
            case "EightStonesInARow": heuristics.add(new EightStonesInARow()); break;
            case "TwoPointEye": heuristics.add(new TwoPointEye()); break;
            case "UnsettledThree": heuristics.add(new UnsettledThree()); break;
            case "ThreeLiberties": heuristics.add(new ThreeLiberties()); break;
            case "LibertyCounter": heuristics.add(new LibertyCounter()); break;
            case "LivingSpace": heuristics.add(new LivingSpace()); break;
            case "EyeCreator": heuristics.add(new EyeCreator()); break;
            case "SixStonesInARow": heuristics.add(new SixStonesInARow()); break;
            default: System.err.println("WARNING: heuristic \'"+heuristicName+"\' could not be found."); return;
        }
        System.out.println("Added heuristic: "+heuristicName);
    }
    
    // get the number of boards evaluated

    
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
        // want to call a heuristic after the 8th move if no winner by then
        
        // put a stone down for every legal move
        for (int x = 0; x < b.getWidth(); x++) {
            for (int y = 0; y < b.getHeight(); y++) {
                Coordinate currentCoord = new Coordinate(x, y);
                if (b.get(x, y) == Board.EMPTY_AI && lmc.checkMove(b, currentCoord, colour, true)) {
                    Board currentState = lmc.getLastLegal();
                            
                    movesConsidered++;
                    
                    lmc.addBoard(currentState);
                    // continue to opponent`s best reaction to this particular move
                    score = alphaBeta(currentState, ALPHA, BETA, opponent,moveDepth-1);
                    lmc.removeLast();

                    // compare the scores of all initial moves
                    if (score > globalScore && score > 0) {
                        globalScore = score;
                        bestMove = currentCoord;
                    }
                }
            }
        }
        
        System.out.println("Score: "+globalScore);

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
        //Translator.printGameBoard(currentBoard);
        if (abAction == Action.KILL && evaluator.checkSucceeded(currentBoard, colour)) {
            //System.out.println("Successfully captured target.");            
            return BETA;
        }
        // if AI failed to defend 
        if (opponentAction == Action.KILL && evaluator.checkSucceeded(currentBoard, opponent)) {
            //System.out.println("Lost target.");
            return ALPHA;
        }

        // if no more valid moves evaluate the board
        if (noMoreLegalMoves(currentBoard, player)) {
            if (player == colour) {
                if (evaluator.checkSucceeded(currentBoard, colour)) {
                    System.out.println("Successfully defended.");
                    return BETA;
                }
                
                else {
                    System.out.println("Test A");
                    return ALPHA;
                }
                
            } else {
                if (evaluator.checkSucceeded(currentBoard, opponent)) {
                    System.out.println("Opponent successfully defended.");
                    return ALPHA;
                }
                
                else {
                    System.out.println("Test B");
                    return BETA;
                }
                
            }
        }
        
        ///////////////////////////////////////////////////////////////////////////
        // heuristic call
        // only call heuristics on first move being evaluated and don't return if less than 0
        
        
        
        if (heuristicsFirst && depth == moveDepth -1) {
            if (depth == 0) return 0;
            int heuristicScore = getHeuristicScores(currentBoard);
            if (heuristicScore > 0) {
            	return heuristicScore;
            }
        }
        else
            if(depth == 0){
                System.out.println("Heuristics called.");
                return getHeuristicScores(currentBoard);
            }
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
    
    //calculates score from heuristic
    private int getHeuristicScores(Board currentBoard){
        int sum = 0;
        for(Heuristic h : heuristics)
            sum += h.assess(initialBoard, currentBoard, lmc, evaluator, colour);
        
        return sum;
    }
}
