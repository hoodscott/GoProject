package main;

import java.util.ArrayList;
import java.lang.Math;

public class MiniMax extends AI{

    private Objective evaluator;
    private LegalMoveChecker lmc;

    public MiniMax(Objective objective, int c, int[] searchScope){
        evaluator = objective;
        colour = c;
        lmc = new LegalMoveChecker();
        setBounds(searchScope);
    }

    // AI is the maximizer in every case !
    @Override
    public Coordinate nextMove(Board b) {
        Coordinate bestMove = null;
        ArrayList <Coordinate> moves = new ArrayList <>();
        ArrayList <Board> boards = new ArrayList <>();
        getLMList(b, moves, boards, colour);
        System.out.println("Predicted Search Space: "+moves.size()+"! or "+factorial(moves.size()));

        int score = 0;
        // for every legal move put a stone down
        for (int i = 0; i < moves.size(); i++) {
            Coordinate move = moves.get(i);
            Board clone = boards.get(i);
            // evaluate current move
            if(evaluator.getAction(colour).equals("kill") && evaluator.checkSucceeded(clone, colour)){
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
            return new Coordinate(-1,-1);

        return bestMove;
    }


    private int min(Board b) {
            int lowestScore = 1;
            ArrayList <Coordinate> moves = new ArrayList <>();
            ArrayList <Board> boards = new ArrayList <>();
            int score;
            int opponentColour = evaluator.getOtherColour(colour);                
            getLMList(b, moves, boards, opponentColour);



            // Base Case
            if (moves.isEmpty()) {
                if(evaluator.checkSucceeded(b, opponentColour)){
                    //System.out.println("Min considers moves bad.");
                    return -1;                    
                }

                else{
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
                    if(evaluator.getAction(opponentColour).equals("kill") && evaluator.checkSucceeded(clone, opponentColour)){	
                        return -1;
                    }
                    score = max(clone);
                    if (score < lowestScore)
                        lowestScore = score;
            }

            return lowestScore;
    }


    private int max(Board b) {	
            int highestScore = -1;
            ArrayList <Coordinate> moves = new ArrayList <>();
            ArrayList <Board> boards = new ArrayList <>();
            getLMList(b, moves, boards, colour);

            int score = 0;

            //Base case
            if (moves.isEmpty()) {
                if(evaluator.checkSucceeded(b, colour)){
                    //System.out.println("Max considers moves good.");
                    return 1;
                }
                else{
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

                    if(evaluator.getAction(colour).equals("kill") && evaluator.checkSucceeded(b, colour)){	
                        return 1;
                    }
                    score = min(clone);
                    if (score > highestScore)
                            highestScore = score;
            }

            return highestScore;
    }

     public int getOtherColour(){
            if(colour == Board.BLACK) return Board.WHITE;
            return Board.BLACK;
     }

     // create array list containing the coordinates of the legal moves
     public void getLMList (Board b, ArrayList <Coordinate> moves, ArrayList <Board> boards, int colour){
            for(int i = lowerBoundX; i <= upperBoundX; i++){
                for(int j = lowerBoundY; j <= upperBoundY;j++){
                    Coordinate c = new Coordinate(i,j);
                    if (lmc.checkMove(b, c, colour)){
                        moves.add(c);
                        boards.add(lmc.getLastLegal());
                        //System.out.println("AI considers move: ("+i+", "+j+")");
                    }
                }
            }
     }

    public boolean[][] getLegalMoves(Board b, int colour){
        int xDim = b.getWidth();
        int yDim = b.getHeight();
        boolean[][] legalMoves = new boolean[xDim][yDim];

        for (int i = 0; i<xDim; i++)
          for (int j = 0; j<yDim; j++)
            legalMoves[i][j] =  lmc.checkMove(b, new Coordinate(i, j), colour);

        return legalMoves;
      }

        public void printGameBoard(Board b){

        int[][] board = b.getRaw();
        ArrayList<String> lines = new ArrayList<>();

        try{
                for(int i = 0; i < board[0].length; i++){
                        lines.add("");
                        int p = lines.size()-1;
                        for(int j = 0; j < board.length; j++)
                                lines.set(p, lines.get(p)+Translator.translateToChar(board[j][i]));
                }
                printBoard(lines);
        }
        catch(BoardFormatException bad){System.err.println(bad.getMsg()+"\n> The board could not be printed");}
        }

    //Prints general boards.
    public void printBoard(ArrayList<String> lines){

    System.out.println();
    lines = addBoardDetails(lines);

    for(String s : lines){
            System.out.println(s);
        }

    System.out.println();

    }
    //Adds details to a board view. Currently just board indexing.
    public ArrayList <String> addBoardDetails(ArrayList <String> board){

            //indices
            int width = board.get(0).length();
            int height = board.size();
            int wLength = String.valueOf(width).length(); //Digit length for buffering.
            int hLength = String.valueOf(height).length();

            ArrayList <String> detailedBoard = new ArrayList<>();

            //Row indices
            for(int i = 0; i < height; i++){

                    String spacing = "";
                    int buffer = String.valueOf(i).length();

                    while(buffer++ < hLength)
                            spacing += ' ';

                    spacing += Integer.toString(i) + ' ';
                    detailedBoard.add(spacing + board.get(i));
            }

            detailedBoard.add("");

            //Column indices
            for(int i = 0; i < wLength; i++){

                    String line = "";
                    for(int j = 0; j < width; j++){
                            if(String.valueOf(j).length() > i)
                                    line += Integer.toString(j).charAt(i);
                            else
                                    line += ' ';
                    }
                    for(int j = 0; j < hLength; j++)
                            line = ' '+line;
                    detailedBoard.add(' '+line);
            }
            return detailedBoard;
        }
    public long factorial(long n){
        if(n > 2)
            return n * factorial(n-1);
        else
            return 1;
    }
}
