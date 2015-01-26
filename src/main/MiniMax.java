package main;

import java.util.ArrayList;
import main.Objective.Action;

public class MiniMax extends AI {

    private final Objective evaluator;
    private LegalMoveChecker lmc;
    private static final int SEARCH_SPACE_MAXIMUM = 7;
    int opponent;
    int passCount = 0;

    public MiniMax(Objective objective, int c, int[] searchScope) {
        evaluator = objective;
        colour = c;
        opponent = evaluator.getOtherColour(colour);
        setBounds(searchScope);
    }
    
    
    @Override
    public Coordinate nextMove(Board b, LegalMoveChecker legalMoves){
        lmc = legalMoves.clone();
        int searchSpace = (upperBoundX - lowerBoundX + 1)*(upperBoundY - lowerBoundY + 1);
        System.out.println("Possible Search Space: "+searchSpace+"! or " + factorial(searchSpace));
        
        //Checks if objective for killing is already met and passes accordingly. 
        //For defending, all possible substates need to be checked.
        if(evaluator.getAction(colour) == Action.KILL && evaluator.checkSucceeded(b, colour))
            return new Coordinate(-1,-1);
        
        for(int x = lowerBoundX; x <= upperBoundX; x++)
            for(int y = lowerBoundY; y <= upperBoundY; y++){
                Coordinate currentCoord = new Coordinate(x,y);
                if(lmc.checkMove(b, currentCoord, colour)){
                    Board currentState = lmc.getLastLegal();
                    //System.out.println("Considering ("+x+","+y+"):");
                    //printGameBoard(currentState);
                    lmc.addBoard(currentState);
                    int result = min(currentState, false);
                    //System.out.println("Result: "+result);
                    lmc.removeLast();
                    //If success is guaranteed.
                    if(result == 1)
                        return currentCoord;                
                }
            }
        //If no move improves the situation (result is 0 or -1), pass.
        return new Coordinate(-1,-1);
    }
    
    //Occurs after opponent move
    public int max(Board b, boolean passed){
        
        //boolean moved = false;
                
        //If the defended group has been killed, return failure.
        if(evaluator.getAction(opponent) == Action.KILL && evaluator.checkSucceeded(b, opponent)){
            //System.out.println("Defeated at:");
            printGameBoard(b);
            return -1;
        }
            
        
        for(int x = lowerBoundX; x <= upperBoundX; x++)
            for(int y = lowerBoundY; y <= upperBoundY; y++){
                Coordinate currentCoord = new Coordinate(x,y);
                if(lmc.checkMove(b, currentCoord, colour)){
                    //moved = true;
                    Board currentState = lmc.getLastLegal();
                    //System.out.println("Considering ("+x+","+y+"):");
                    //printGameBoard(currentState);
                    lmc.addBoard(currentState);
                    int result = min(currentState, false);
                    //System.out.println("Min: "+result);
                    lmc.removeLast();
                    
                    //If success is guaranteed.
                    if(result == 1)
                        return result;                
                }
            }
        
        if(!passed){
           //System.out.println("AI passed.");
           return min(b, true);          
        }
        
        //If the AI can no longer kill the opponent
        if(evaluator.getAction(opponent) == Action.DEFEND && evaluator.checkSucceeded(b, opponent)){
            //System.out.println("AI successfully defended at: lb");
            printGameBoard(b);
            return -1;
        }
        //If there are no more legal moves and the AI's defended group still lives.
        /*
        else if(!moved && evaluator.getAction(colour) == Action.DEFEND)
        {
            System.out.println("Out of moves and successfully defended at: ");
            printGameBoard(b);
            return 1;
        }
        */
        else
            return 1;
    }
    
    //Occurs after AI move
    public int min(Board b, boolean passed){
        
        //If the AI has captured the opposing group
        if(evaluator.getAction(colour) == Action.KILL && evaluator.checkSucceeded(b, colour)){
            //System.out.println("Succeeded at:");
            printGameBoard(b);
            return 1;
        }
        
        //Tries all legal moves in search scope
        for(int x = lowerBoundX; x <= upperBoundX; x++)
            for(int y = lowerBoundY; y <= upperBoundY; y++){
                Coordinate currentCoord = new Coordinate(x,y);
                if(lmc.checkMove(b, currentCoord, opponent)){
                    Board currentState = lmc.getLastLegal();
                    //System.out.println("Considering ("+x+","+y+"):");
                    printGameBoard(currentState);
                    lmc.addBoard(currentState);
                    int result = max(currentState, false);
                    //System.out.println("Max: "+result);
                    lmc.removeLast();
                    
                    //If failure is guaranteed.
                    if(result == -1)
                        return result;
                }
            }
        
        //Passes and tests if the opponent still can/will make moves
        if(!passed){
           //System.out.println("Opponent passed.");
           return max(b, true);          
        }
        
        //If the AI's stone group can no longer be captured.
        if(evaluator.getAction(colour) == Action.DEFEND && evaluator.checkSucceeded(b, colour)){
            //System.out.println("Opponent successfully defended");
            //printGameBoard(b);
            return 1;
        }
        //If there are no more legal moves and the AI's defended group still lives.
        /*
        else if(!moved && evaluator.getAction(opponent) == Action.DEFEND)
        {
            System.out.println("Out of moves and opponent successfully defended at: ");
            printGameBoard(b);
            return -1;
        }
        */
        else
            return -1;
    }
    /*
    // AI is the maximizer in every case !
    @Override
    public Coordinate nextMove(Board b, LegalMoveChecker lmc) {
        this.lmc = lmc;
        Coordinate bestMove = null;
        ArrayList<Coordinate> moves = new ArrayList<>();
        ArrayList<Board> boards = new ArrayList<>();
        getLMList(b, moves, boards, colour);
        System.out.println("Predicted Search Space: " + moves.size() + "! or " + factorial(moves.size()));

        int score = 0;
        // for every legal move put a stone down
        for (int i = 0; i < moves.size(); i++) {
            Coordinate move = moves.get(i);
            Board clone = boards.get(i);
            // evaluate current move
            if (evaluator.getAction(colour).equals("kill") && evaluator.checkSucceeded(clone, colour)) {
                return move;
            }
            // continue to opponent`s best reaction to this particular move
            score = min(clone);
            // return final result
            if (score == 1) {
                bestMove = move;
            }
        }

        if (bestMove == null) // pass
        {
            return new Coordinate(-1, -1);
        }

        return bestMove;
    }

    private int min(Board b) {
        int lowestScore = 1;
        ArrayList<Coordinate> moves = new ArrayList<>();
        ArrayList<Board> boards = new ArrayList<>();
        int score;
        int opponentColour = evaluator.getOtherColour(colour);
        getLMList(b, moves, boards, opponentColour);

        // Base Case
        if (moves.isEmpty()) {
            if (evaluator.checkSucceeded(b, opponentColour)) {
                //System.out.println("Min considers moves bad.");
                return -1;
            } else {
                //System.out.println("Min considers moves good.");
                return 1;
            }
        }

        for (int i = 0; i < moves.size(); i++) {
            Coordinate move = moves.get(i);
            Board clone = boards.get(i);
            //printGameBoard(clone);
            Coordinate pos = evaluator.getPosition();
            //System.out.println("Minimizes ("+move.x+", "+move.y+")");

            //Checks successful kill
            if (evaluator.getAction(opponentColour).equals("kill") && evaluator.checkSucceeded(clone, opponentColour)) {
                return -1;
            }
            score = max(clone);
            if (score < lowestScore) {
                lowestScore = score;
            }
        }

        return lowestScore;
    }

    private int max(Board b) {
        int highestScore = -1;
        ArrayList<Coordinate> moves = new ArrayList<>();
        ArrayList<Board> boards = new ArrayList<>();
        getLMList(b, moves, boards, colour);

        int score = 0;

        //Base case
        if (moves.isEmpty()) {
            if (evaluator.checkSucceeded(b, colour)) {
                //System.out.println("Max considers moves good.");
                return 1;
            } else {
                //System.out.println("Max considers moves bad.");
                return -1;
            }
        }

        for (int i = 0; i < moves.size(); i++) {
            Coordinate move = moves.get(i);
            Board clone = boards.get(i);
            //printGameBoard(clone);
            Coordinate pos = evaluator.getPosition();
            //System.out.println("Maximizes ("+move.x+", "+move.y+")");

            if (evaluator.getAction(colour).equals("kill") && evaluator.checkSucceeded(b, colour)) {
                return 1;
            }
            score = min(clone);
            if (score > highestScore) {
                highestScore = score;
            }
        }

        return highestScore;
    }

    public int getOtherColour() {
        if (colour == Board.BLACK) {
            return Board.WHITE;
        }
        return Board.BLACK;
    }

    // create array list containing the coordinates of the legal moves
    public void getLMList(Board b, ArrayList<Coordinate> moves, ArrayList<Board> boards, int colour) {
        for (int i = lowerBoundX; i <= upperBoundX; i++) {
            for (int j = lowerBoundY; j <= upperBoundY; j++) {
                Coordinate c = new Coordinate(i, j);
                if (lmc.checkMove(b, c, colour)) {
                    moves.add(c);
                    boards.add(lmc.getLastLegal());
                    //System.out.println("AI considers move: ("+i+", "+j+")");
                }
            }
        }
    }
    */
    
    //-----------------------------
    //METHODS USED FOR TESTING ONLY
    //-----------------------------
    
    public void printGameBoard(Board b) {

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
    public void printBoard(ArrayList<String> lines) {

        System.out.println();
        lines = addBoardDetails(lines);

        for (String s : lines) {
            System.out.println(s);
        }

        System.out.println();

    }

    //Adds details to a board view. Currently just board indexing.

    public ArrayList<String> addBoardDetails(ArrayList<String> board) {

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

    public long factorial(long n) {
        if (n > 1) {
            return n * factorial(n - 1);
        } else {
            return 1;
        }
    }
}
