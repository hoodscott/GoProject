package ai;

import main.Board;
import main.Coordinate;
import main.LegalMoveChecker;
import ai.Objective.Action;
import java.util.AbstractMap;
import java.util.Comparator;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.ArrayList;
import main.BoardFormatException;
import main.Translator;

public class MagicalMiniMax extends HeuristicsAI {

    private Objective evaluator;
    private LegalMoveChecker lmc;
    
    private final boolean heuristicsFirst = true;
    private boolean usingHeuristics = false;

    int opponent;
    Action miniAction;
    Action opponentAction;
    
    // constructor
    public MagicalMiniMax(Objective objective, int c, String[] heuristics) {
        evaluator = objective;
        colour = c;
        opponent = evaluator.getOtherColour(colour);
        miniAction = evaluator.getAction(colour);
        opponentAction = evaluator.getAction(opponent);
        if (heuristics != null){
            setHeuristics(heuristics);
            usingHeuristics = true;       
        } 
    }
    @Override
    public Coordinate nextMove(Board b, LegalMoveChecker legalMoves) throws AIException {
        lmc = legalMoves.clone();
        Board initialState = b.clone();
        movesConsidered = 0;
        //Translator.printGameBoard(b);

        //Checks if objective for killing is already met and passes accordingly. 
        //For defending, all possible substates need to be checked.
        if (miniAction == Action.KILL && evaluator.checkSucceeded(b, colour)) {
            return new Coordinate(-1, -1);
        }

        for (int x = 0; x < b.getWidth(); x++) {
            for (int y = 0; y < b.getHeight(); y++) {
                Coordinate currentCoord = new Coordinate(x, y);
                if (b.get(x, y) == Board.EMPTY_AI && lmc.checkMove(b, currentCoord, colour, true)) {
                    Board currentState = lmc.getLastLegal();
                    //try{System.out.println(Translator.translateToString(colour)+" made move "+x+" "+y);} catch(BoardFormatException e){}        
                    //Translator.printGameBoard(currentState);
                    lmc.addBoard(currentState);
                    int result = min(initialState, currentState, false, moveDepth-1);
                    lmc.removeLast();

                    //If success is guaranteed.
                    if (result == 1) {
                        return currentCoord;
                    }
                }
            }
        }

        //If no move improves the situation (result is 0 or -1), pass.
        return new Coordinate(-1, -1);
    }

    //Occurs after opponent move
    public int max(Board initialState, Board b, boolean passed, int depth) {
        
        movesConsidered++;
        //If the defended group has been killed, return failure.
        if (opponentAction == Action.KILL && evaluator.checkSucceeded(b, opponent)) {
            //try{System.out.println(Translator.translateToString(colour)+"\'s defending group was lost.");} catch(BoardFormatException e){} 
            return -1;
        }
        
        if(depth == 0 && usingHeuristics)
            return heuristicsMax(initialState,  b,  passed);

        for (int x = 0; x < b.getWidth(); x++) {
            for (int y = 0; y < b.getHeight(); y++) {
                Coordinate currentCoord = new Coordinate(x, y);
                if (b.get(x, y) == Board.EMPTY_AI && lmc.checkMove(b, currentCoord, colour, true)) {
                    Board currentState = lmc.getLastLegal();
                    //try{System.out.println(Translator.translateToString(colour)+" made move "+x+" "+y);} catch(BoardFormatException e){}        
                    //Translator.printGameBoard(currentState);
                    lmc.addBoard(currentState);
                    int result = min(initialState, currentState, false, depth-1);
                    lmc.removeLast();

                    //If success is guaranteed.
                    if (result == 1) {
                        return result;
                    }
                }
            }
        }

        if (!passed) {
            //try{System.out.println(Translator.translateToString(colour)+" passed");} catch(BoardFormatException e){} 
            return min(initialState, b, true, depth-1);
        }

        //If the AI can no longer kill the opponent
        if (opponentAction == Action.DEFEND && evaluator.checkSucceeded(b, opponent)) {
            //try{System.out.println(Translator.translateToString(opponent)+"\'s group can no longer be captured.");} catch(BoardFormatException e){} 
            return -1;
        } //If there are no more legal moves and the AI's defended group still lives.
        else {
            //try{System.out.println(Translator.translateToString(colour)+"\'s succesfully defended.");} catch(BoardFormatException e){} 
            return 1;
        }
    }

    //Occurs after AI move
    public int min(Board initialState, Board b, boolean passed, int depth) {
        
        movesConsidered++;      
        //If the AI has captured the opposing group
        if (miniAction == Action.KILL && evaluator.checkSucceeded(b, colour)) {
            //try{System.out.println(Translator.translateToString(opponent)+"\'s group was successfully captured.");} catch(BoardFormatException e){}
            return 1;
        }
        
        if(depth == 0 && usingHeuristics)
            return heuristicsMin(initialState,  b,  passed);

        //Tries all legal moves in search scope
        for (int x = 0; x < b.getWidth(); x++) {
            for (int y = 0; y < b.getHeight(); y++) {
                Coordinate currentCoord = new Coordinate(x, y);
                if (b.get(x, y) == Board.EMPTY_AI && lmc.checkMove(b, currentCoord, opponent, true)) {
                    Board currentState = lmc.getLastLegal();
                    //try{System.out.println(Translator.translateToString(opponent)+" made move "+x+" "+y);} catch(BoardFormatException e){}        
                    //Translator.printGameBoard(currentState);
                    lmc.addBoard(currentState);
                    int result = max(initialState, currentState, false, depth-1);
                    lmc.removeLast();

                    //If failure is guaranteed.
                    if (result == -1) {
                        return result;
                    }
                }
            }
        }

        //Passes and tests if the opponent still can/will make moves
        if (!passed) {
            //try{System.out.println(Translator.translateToString(opponent)+" passed");} catch(BoardFormatException e){} 
            return max(initialState, b, true, depth-1);
        }

        //If the AI's stone group can no longer be captured.
        if (miniAction == Action.DEFEND && evaluator.checkSucceeded(b, colour)) {
            //try{System.out.println(Translator.translateToString(colour)+"\'s succesfully defended.");} catch(BoardFormatException e){} 
            return 1;
        } //If there are no more legal moves and the AI's defended group still lives.
        else {
            //try{System.out.println(Translator.translateToString(opponent)+"\'s group can no longer be captured.");} catch(BoardFormatException e){} 
            return -1;
        }
    }
    
    public int heuristicsMax(Board initialState, Board b, boolean passed){
        //System.out.println("Max heuristic called");
        ArrayList<Entry<Integer,Board>> boards = new ArrayList();
        
        for (int x = 0; x < b.getWidth(); x++) {
            for (int y = 0; y < b.getHeight(); y++) {
                Coordinate currentCoord = new Coordinate(x, y);
                if (b.get(x, y) == Board.EMPTY_AI && lmc.checkMove(b, currentCoord, colour, true)) {
                    Board currentState = lmc.getLastLegal();
                    //try{System.out.println(Translator.translateToString(colour)+" made move "+x+" "+y);} catch(BoardFormatException e){}        
                    //Translator.printGameBoard(currentState);
                    Integer score = getHeuristicScores(initialState, b, lmc, evaluator);
                    boards.add(new AbstractMap.SimpleEntry(score,currentState));
                }
            }
        }
        //Sorts by descending heuristic score.
        numericSort(boards);
        //System.out.println(boards.size()+" possible moves");
        Iterator<Entry<Integer,Board>> it = boards.iterator();
        //Iterates over each board.
        while(it.hasNext()){ 
            Entry<Integer, Board> pair = (Entry<Integer,Board>)it.next();
            Board currentState = pair.getValue();
            //System.out.println("Score: "+pair.getKey());
            lmc.addBoard(currentState);
            int result = min(b, currentState, false, moveDepth-1);
            lmc.removeLast();
            
            //If success is guaranteed.
            if (result == 1) {
                return result;
            }
        }
        
        if (!passed){
            //try{System.out.println(Translator.translateToString(colour)+" passed");} catch(BoardFormatException e){}
            return min(initialState, b, true, moveDepth-1);
        }

        //If the AI can no longer kill the opponent
        if (opponentAction == Action.DEFEND && evaluator.checkSucceeded(b, opponent)) {
            //try{System.out.println(Translator.translateToString(opponent)+"\'s group can no longer be captured.");} catch(BoardFormatException e){} 
            return -1;
        } //If there are no more legal moves and the AI's defended group still lives.
        else {
            //try{System.out.println(Translator.translateToString(colour)+"\'s succesfully defended.");} catch(BoardFormatException e){} 
            return 1;
        }
    }
    
    public int heuristicsMin(Board initialState, Board b, boolean passed){
        //System.out.println("Min heuristic called");
        ArrayList<Entry<Integer,Board>> boards = new ArrayList();
        for (int x = 0; x < b.getWidth(); x++) {
            for (int y = 0; y < b.getHeight(); y++) {
                Coordinate currentCoord = new Coordinate(x, y);
                if (b.get(x, y) == Board.EMPTY_AI && lmc.checkMove(b, currentCoord, opponent, true)) {
                    Board currentState = lmc.getLastLegal();
                    //try{System.out.println(Translator.translateToString(opponent)+" made move "+x+" "+y);} catch(BoardFormatException e){}        
                    //Translator.printGameBoard(currentState);
                    Integer score = getHeuristicScores(initialState, b, lmc, evaluator);
                    boards.add(new AbstractMap.SimpleEntry(score,currentState));
                }
            }
        }
        //Sorts by descending heuristic score.
        numericSort(boards);
        //System.out.println(boards.size()+" possible moves");
        Iterator<Entry<Integer,Board>> it = boards.iterator();
        //Iterates over each board.
        while(it.hasNext()){ 
            Entry<Integer, Board> pair = (Entry<Integer,Board>)it.next();
            //System.out.println("Score: "+pair.getKey());
            Board currentState = pair.getValue();
            lmc.addBoard(currentState);
            int result = max(b, currentState, false, moveDepth-1);
            lmc.removeLast();
            
            //If failure is guaranteed.
            if (result == -1) {
                return result;
            }
        }
        
        //Passes and tests if the opponent still can/will make moves
        if (!passed) {
            //try{System.out.println(Translator.translateToString(opponent)+" passed");} catch(BoardFormatException e){} 
            return max(initialState, b, true, moveDepth-1);
        }

        //If the AI's stone group can no longer be captured.
        if (miniAction == Action.DEFEND && evaluator.checkSucceeded(b, colour)) {
            //try{System.out.println(Translator.translateToString(colour)+"\'s succesfully defended.");} catch(BoardFormatException e){}
            return 1;
        } //If there are no more legal moves and the AI's defended group still lives.
        else {
            //try{System.out.println(Translator.translateToString(opponent)+"\'s group can no longer be captured.");} catch(BoardFormatException e){}
            return -1;
        }
    }
    //Sorts list by integer.
    public void numericSort(ArrayList <Entry<Integer, Board>> m){
        Collections.sort(m, NUMERIC);
    }
    
    //Comparator to use by collections.sort
    //Sorts into descending order
    private static final Comparator<Entry<Integer,Board>> NUMERIC = new Comparator<Entry<Integer,Board>>(){
        
        @Override
        public int compare(Entry<Integer, Board> e1, Entry<Integer, Board> e2){
            return e2.getKey().compareTo(e1.getKey());
        }
    };
}