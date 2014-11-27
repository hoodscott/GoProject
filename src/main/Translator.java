package main;
import java.util.ArrayList;

//A class containing translation operations for the FileIO and TextUI.
public class Translator {
    
    //Translates raw lines into GameEngine Object components.
    //It is required to have enough data for a board. It is optional to have any data for the Objective or AI.
    public static GameEngine translateGameInstructions(ArrayList<String> contents) throws BoardFormatException{
        
        Objective objective;
        Board board;
        AI ai;
            board = translateToBoard(contents);
            if(contents.size() > 0)
                objective = translateToObjective(contents.remove(0), board);
            else{
                System.out.println("NOTE: No objective has been specified. Competitive play disabled.");
                return new GameEngine(board);
            }
            if(contents.size() > 0)
                ai = translateToAI(contents.remove(0), board);
            else{
                System.out.println("NOTE: No AI search space has been specified. This can lead to performance issues with large boards.");
                return new GameEngine(board);
            }
        return new GameEngine(board, objective, ai);
    }
    
    //Checks the integrity of an inputted board data and translates it to a Board object. Throws an exception when encountering an error. 
    public static Objective translateToObjective(String raw, Board board) throws BoardFormatException{
        String[] parts = raw.split(" ");
        int colour;
        String action;
        Coordinate coord;
        
        if(parts.length != 4)
            throw new BoardFormatException("ERROR: The given objective is not formatted correctly.");
        
        switch(parts[0]){
            case "black": colour = Board.BLACK; break;
            case "white": colour = Board.WHITE; break;
            default: throw new BoardFormatException("ERROR: The given objective colour \'"+parts[0]+"\' is not white or black.");
        }
        switch(parts[1]){
            case "kill": action = parts[1]; break;
            case "defend": action = parts[1]; break;
            default: throw new BoardFormatException("ERROR: The given objective action \'"+parts[0]+"\' is undefined.");
        }
        
        int x,y;
        if(isNumber(parts[2]) && isNumber(parts[3])){
            x = Integer.parseInt(parts[2]);
            y = Integer.parseInt(parts[3]);
        }
        else
            throw new BoardFormatException("ERROR: The given objective coordinates are not numbers.");
        
        if(x >= 0 && x < board.getWidth() && y >= 0 && y < board.getHeight())
            coord = new Coordinate(x,y);
        else
            throw new BoardFormatException("ERROR:  The given objective coordinates are not within the board's dimensions.");
        
        return new Objective(action, colour, coord);
    }
    
    public static AI translateToAI(String raw, Board board){
        
    }

    //Checks the integrity of an inputted board data and translates it to a Board object. Throws an exception when encountering an error.
    public static Board translateToBoard(ArrayList<String> raw) throws BoardFormatException{
        int w;
        int h;
        int[][] rawBoard;
        if (raw.size() < 2) {
            throw new BoardFormatException("ERROR: The given input does not contain enough lines for a Board.");
        }
        String[] rawInts = raw.remove(0).split(" ");
        if (rawInts.length == 2 && isNumber(rawInts[0]) && isNumber(rawInts[1])) {
            w = Integer.parseInt(rawInts[0]);
            h = Integer.parseInt(rawInts[1]);
        } 
        else 
            throw new BoardFormatException("ERROR: The initial line does not contain two valid integers.");
        
        if (h > raw.size() - 1)
            throw new BoardFormatException("ERROR: There are not enough rows in the specification.");
        
        for (int i = 0; i < h; i++)
            if (raw.get(i).length() != w)
                throw new BoardFormatException("ERROR: There is a mismatch in the given number of columns.");
        
        rawBoard = new int[w][h];
        for (int i = 0; i < h; i++) {
            String row = raw.remove(i);
            for (int j = 0; j < w; j++)
                rawBoard[j][i] = Translator.translateToInt(row.charAt(j));
        }
        return new Board(rawBoard);
    }
    
    
    // Method for translation of char positions to int values
    public static int translateToInt(char value) throws BoardFormatException {
        switch (value) {
            case Board.C_EMPTY:
                return Board.EMPTY;
            case Board.C_BLACK:
                return Board.BLACK;
            case Board.C_WHITE:
                return Board.WHITE;
            default:
                throw new BoardFormatException("ERROR: The board to translate contains an illegal character " + value + ". Board not translated.");
        }
    }

    //Translates Board int elements into text
    public static char translateToChar(int i) throws BoardFormatException {
        switch (i) {
            case Board.EMPTY:
                return Board.C_EMPTY;
            case Board.BLACK:
                return Board.C_BLACK;
            case Board.WHITE:
                return Board.C_WHITE;
            default:
                throw new BoardFormatException("ERROR: The board to translate contains an illegal integer " + i + ". Board not translated.");
        }
    }

    //Checks if a string is an int
    public static boolean isNumber(String s) {
        try {
            Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }
    
}
