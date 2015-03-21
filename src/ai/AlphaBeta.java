package ai;

import main.Board;
import main.Coordinate;
import main.LegalMoveChecker;
import ai.Objective.Action;
import java.util.ArrayList;
import main.BoardFormatException;
import main.Translator;

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
                    //try{System.out.println(Translator.translateToString(colour)+" made move "+x+" "+y);} catch(BoardFormatException e){}        
                    //Translator.printGameBoard(currentState);
                    lmc.addBoard(currentState);
                    // continue to opponent`s best reaction to this particular move
                    //score = alphaBeta(currentState, MINIMUM, MAXIMUM, opponent,moveDepth-1);
                    score = beta(currentState, MINIMUM, MAXIMUM, moveDepth-1, false);
                    lmc.removeLast();

                    // compare the scores of all initial moves
                    if(score == MAXIMUM)
                        return currentCoord;
                    if (score > globalScore && score > 0) {
                        System.out.println("score: " + score);
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
    
    private int alpha(Board b, int alpha, int beta, int depth, boolean passed){
        movesConsidered++;
        // If the opponent managed to capture the group.
        if (opponentAction == Action.KILL && evaluator.checkSucceeded(b, opponent)) {
            //System.out.println("Lost target.");
            return MINIMUM;
        }
        
        if(usingHeuristics){
            if(heuristicsFirst && depth == moveDepth-1){
                if (depth == 0) return 0;
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
                        
                        //try{System.out.println(Translator.translateToString(colour)+" made move "+x+" "+y);} catch(BoardFormatException e){}
                        
                        Board currentState = lmc.getLastLegal();
                        //Translator.printGameBoard(currentState);
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
            //try{System.out.println(Translator.translateToString(colour)+" passed.");} catch(BoardFormatException e){}
            //Translator.printGameBoard(b);
            return beta(b, alpha, beta, depth-1, true);
        }

        //If the AI can no longer kill the opponent
        if (opponentAction == Action.DEFEND && evaluator.checkSucceeded(b, opponent)) {
            //try{System.out.println(Translator.translateToString(opponent)+" successfully defended.");} catch(BoardFormatException e){}
            //Translator.printGameBoard(b);
            return MINIMUM;
        } //If there are no more legal moves and the AI's defended group still lives.
        else {
            //try{System.out.println(Translator.translateToString(colour)+" successfully defended.");} catch(BoardFormatException e){}
            //Translator.printGameBoard(b);
            return MAXIMUM;
        }
    }
    private int beta(Board b, int alpha, int beta, int depth, boolean passed){
        movesConsidered++;
        // If the AI managed to capture the group.
        if (abAction == Action.KILL && evaluator.checkSucceeded(b, colour)) {
            //System.out.println("Successfully captured target.");            
            return MAXIMUM;
        }
        
        if(usingHeuristics){
            if(heuristicsFirst && depth == moveDepth-1){
                if (depth == 0) return 0;
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
                        //try{System.out.println(Translator.translateToString(opponent)+" made move "+x+" "+y);} catch(BoardFormatException e){}
                        //Translator.printGameBoard(currentState);                        
                        
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
            //try{System.out.println(Translator.translateToString(opponent)+" passed.");} catch(BoardFormatException e){}
            //Translator.printGameBoard(b);
            return alpha(b, alpha, beta, depth-1, true);
        }

        //If the AI's stone group can no longer be captured.
        if (abAction == Action.DEFEND && evaluator.checkSucceeded(b, colour)) {
            //try{System.out.println(Translator.translateToString(colour)+" successfully defended.");} catch(BoardFormatException e){}
            //Translator.printGameBoard(b);
            return MAXIMUM;
        } //If there are no more legal moves and the opponent's defended group still lives.
        else {
            //try{System.out.println(Translator.translateToString(opponent)+" successfully defended.");} catch(BoardFormatException e){}
            //Translator.printGameBoard(b);
            return MINIMUM;
        }
    }
/*
    // recursive alphaBeta pruning  
    private int alphaBeta(Board currentBoard, int alpha, int beta, int player, int depth) {
    	///////////////////////////////////////////////////////////////////////////
        // check for terminal position
        // if killing objective is completed at this stage
        //Translator.printGameBoard(currentBoard);
        if (abAction == Action.KILL && evaluator.checkSucceeded(currentBoard, colour)) {
            //System.out.println("Successfully captured target.");            
            return MAXIMUM;
        }
        // if AI failed to defend 
        if (opponentAction == Action.KILL && evaluator.checkSucceeded(currentBoard, opponent)) {
            //System.out.println("Lost target.");
            return MINIMUM;
        }

        // if no more valid moves evaluate the board
        if (noMoreLegalMoves(currentBoard, player)) {
            if (player == colour) {
                if (evaluator.checkSucceeded(currentBoard, colour)) {
                    System.out.println("Successfully defended.");
                    return MAXIMUM;
                }
                
                else {
                    System.out.println("Test A");
                    return MINIMUM;
                }
                
            } else {
                if (evaluator.checkSucceeded(currentBoard, opponent)) {
                    System.out.println("Opponent successfully defended.");
                    return MINIMUM;
                }
                
                else {
                    System.out.println("Test B");
                    return MAXIMUM;
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
            int score = MINIMUM;
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
            int score = MAXIMUM;
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
*/    
}
