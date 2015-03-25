package ai;

import main.Board;
import main.Coordinate;
import main.LegalMoveChecker;
import ai.Objective.Action;
/**
 * fill this
 */
public class MiniMax extends AI {

    private final Objective evaluator;
    private LegalMoveChecker lmc;
    int opponent;
    long timeSpent;
    Action miniAction;
    Action opponentAction;
    Board winner;

    @SuppressWarnings("static-access")
	public MiniMax(Objective objective, int c) {
        evaluator = objective;
        colour = c;
        opponent = evaluator.getOtherColour(colour);
        miniAction = evaluator.getAction(colour);
        opponentAction = evaluator.getAction(opponent);
    }

    @Override
    public Coordinate nextMove(Board b, LegalMoveChecker legalMoves) throws AIException {
        lmc = legalMoves.clone();
        movesConsidered = 0;
        timeSpent = 0;
        //printGameBoard(b);

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
                    lmc.addBoard(currentState);
                    int result = min(currentState, false);
                    lmc.removeLast();

                    //If success is guaranteed.
                    if (result == 1) {
                        //Translator.printGameBoard(winner);
                        //UnconditionalLife.printLastDetails();
                        return currentCoord;
                    }
                }
            }
        }
        //If no move improves the situation (result is 0 or -1), pass.
        return new Coordinate(-1, -1);
    }

    //Occurs after opponent move
    public int max(Board b, boolean passed) {
        
        movesConsidered++;
        //If the defended group has been killed, return failure.
        if (opponentAction == Action.KILL && evaluator.checkSucceeded(b, opponent)) {
            //winner = b;
            return -1;
        }

        for (int x = 0; x < b.getWidth(); x++) {
            for (int y = 0; y < b.getHeight(); y++) {
                Coordinate currentCoord = new Coordinate(x, y);
                if (b.get(x, y) == Board.EMPTY_AI && lmc.checkMove(b, currentCoord, colour, true)) {

                    Board currentState = lmc.getLastLegal();
                    lmc.addBoard(currentState);
                    int result = min(currentState, false);
                    lmc.removeLast();

                    //If success is guaranteed.
                    if (result == 1) {
                        return result;
                    }
                }
            }
        }

        if (!passed) {
            //System.out.println("AI passed.");
            return min(b, true);
        }

        //If the AI can no longer kill the opponent
        if (opponentAction == Action.DEFEND && evaluator.checkSucceeded(b, opponent)) {
            //if(UnconditionalLife.isItAlive(b, evaluator.getPosition()))
                return -1;
            //winner = b;
            //return 1;
        } //If there are no more legal moves and the AI's defended group still lives.
        else {
            //if(UnconditionalLife.isItAlive(b, evaluator.getPosition())){
                //winner = b;
                return 1;
            //}
            //return -1;
        }
    }

    //Occurs after AI move
    public int min(Board b, boolean passed) {
        
        movesConsidered++;
        
        //If the AI has captured the opposing group
        if (miniAction == Action.KILL && evaluator.checkSucceeded(b, colour)) {
            //winner = b;
            return 1;
        }

        //Tries all legal moves in search scope
        for (int x = 0; x < b.getWidth(); x++) {
            for (int y = 0; y < b.getHeight(); y++) {
                Coordinate currentCoord = new Coordinate(x, y);
                if (b.get(x, y) == Board.EMPTY_AI && lmc.checkMove(b, currentCoord, opponent, true)) {
                    Board currentState = lmc.getLastLegal();
                    lmc.addBoard(currentState);
                    int result = max(currentState, false);
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
            return max(b, true);
        }

        //If the AI's stone group can no longer be captured.
        if (miniAction == Action.DEFEND && evaluator.checkSucceeded(b, colour)) {
            //if(UnconditionalLife.isItAlive(b, evaluator.getPosition())){
                //winner = b;
                return 1;
            //}
            //return -1;
        } //If the opponent's group still lives.
        else {
            //if(UnconditionalLife.isItAlive(b, evaluator.getPosition()))
                return -1;
            //winner = b;
            //return 1;
        }
    }
}
