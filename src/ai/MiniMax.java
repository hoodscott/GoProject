package ai;

import java.util.ArrayList;
import main.Board;
import main.BoardFormatException;
import main.Coordinate;
import main.LegalMoveChecker;
import ai.Objective.Action;
import main.Translator;

public class MiniMax extends AI {

    private final Objective evaluator;
    private LegalMoveChecker lmc;
    private static final int SEARCH_SPACE_MAXIMUM = 8; //RECOMMENDED MAXIMUM
    int opponent;
    Action miniAction;
    Action opponentAction;

    public MiniMax(Objective objective, int c, int[] searchScope) {
        evaluator = objective;
        colour = c;
        opponent = evaluator.getOtherColour(colour);
        setBounds(searchScope);
        miniAction = evaluator.getAction(colour);
        opponentAction = evaluator.getAction(opponent);
    }

    @Override
    public Coordinate nextMove(Board b, LegalMoveChecker legalMoves) throws AIException{
        lmc = legalMoves.clone();
        //int searchSpace = (upperBoundX - lowerBoundX + 1) * (upperBoundY - lowerBoundY + 1);
        //System.out.println("Possible Search Space: " + searchSpace + "! or " + factorial(searchSpace));

        //Checks if objective for killing is already met and passes accordingly. 
        //For defending, all possible substates need to be checked.
        
        if (miniAction == Action.KILL && evaluator.checkSucceeded(b, colour)) {
            return new Coordinate(-1, -1);
        }

        for (int x = lowerBoundX; x <= upperBoundX; x++) {
            for (int y = lowerBoundY; y <= upperBoundY; y++) {
                Coordinate currentCoord = new Coordinate(x, y);
                if (lmc.checkMove(b, currentCoord, colour)) {
                    Board currentState = lmc.getLastLegal();
                    lmc.addBoard(currentState);
                    int result = min(currentState, false);
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
    public int max(Board b, boolean passed) {

        //If the defended group has been killed, return failure.
        if (opponentAction == Action.KILL && evaluator.checkSucceeded(b, opponent)) {
            return -1;
        }

        for (int x = lowerBoundX; x <= upperBoundX; x++) {
            for (int y = lowerBoundY; y <= upperBoundY; y++) {
                Coordinate currentCoord = new Coordinate(x, y);
                if (lmc.checkMove(b, currentCoord, colour)) {
                    //moved = true;
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
            return -1;
        } 
        //If there are no more legal moves and the AI's defended group still lives.
        else {
            return 1;
        }
    }

    //Occurs after AI move
    public int min(Board b, boolean passed) {

        //If the AI has captured the opposing group
        if (miniAction == Action.KILL && evaluator.checkSucceeded(b, colour)) {
            return 1;
        }

        //Tries all legal moves in search scope
        for (int x = lowerBoundX; x <= upperBoundX; x++) {
            for (int y = lowerBoundY; y <= upperBoundY; y++) {
                Coordinate currentCoord = new Coordinate(x, y);
                if (lmc.checkMove(b, currentCoord, opponent)) {
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
            return 1;
        } 
        //If there are no more legal moves and the AI's defended group still lives.
        else {
            return -1;
        }
    }
    
    //-----------------------------
    //METHODS USED FOR TESTING ONLY
    //-----------------------------
    private void printGameBoard(Board b) {

        int[][] board = b.getRaw();
        ArrayList<String> lines = new ArrayList<>();

        try {
            for (int i = 0; i < board[0].length; i++) {
                lines.add("");
                int p = lines.size() - 1;
                for (int j = 0; j < board.length; j++) {
                    lines.set(p, lines.get(p) + Translator.translateToChar(board[j][i]));
                }
            }
            printBoard(lines);
        } catch (BoardFormatException bad) {
            System.err.println(bad.getMsg() + "\n> The board could not be printed");
        }
    }

    //Prints general boards.
    private void printBoard(ArrayList<String> lines) {

        System.out.println();
        lines = addBoardDetails(lines);

        for (String s : lines) {
            System.out.println(s);
        }

        System.out.println();

    }

    //Adds details to a board view. Currently just board indexing.
    private ArrayList<String> addBoardDetails(ArrayList<String> board) {

        //indices
        int width = board.get(0).length();
        int height = board.size();
        int wLength = String.valueOf(width).length(); //Digit length for buffering.
        int hLength = String.valueOf(height).length();

        ArrayList<String> detailedBoard = new ArrayList<>();

        //Row indices
        for (int i = 0; i < height; i++) {

            String spacing = "";
            int buffer = String.valueOf(i).length();

            while (buffer++ < hLength) {
                spacing += ' ';
            }

            spacing += Integer.toString(i) + ' ';
            detailedBoard.add(spacing + board.get(i));
        }

        detailedBoard.add("");

        //Column indices
        for (int i = 0; i < wLength; i++) {

            String line = "";
            for (int j = 0; j < width; j++) {
                if (String.valueOf(j).length() > i) {
                    line += Integer.toString(j).charAt(i);
                } else {
                    line += ' ';
                }
            }
            for (int j = 0; j < hLength; j++) {
                line = ' ' + line;
            }
            detailedBoard.add(' ' + line);
        }
        return detailedBoard;
    }

    private long factorial(long n) {
        if (n > 1) {
            return n * factorial(n - 1);
        } else {
            return 1;
        }
    }
}
