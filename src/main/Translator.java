package main;
import java.util.ArrayList;

//A class containing translation operations for the FileIO and TextUI.
public class Translator {
    
    public static GameEngine translateGameInstructions(ArrayList<String> contents){
        
        GameEngine gameEngine = new GameEngine();
        Objective objective;
        Board board;
        AI ai;
        try{
            board = translateToBoard(contents);
            if(contents.size() > 0)
                objective = translateToObjective(contents.remove(0), board);
            else
                System.out.println("NOTE: No objective has been specified. Competitive play disabled.");
            if(contents.size() > 0)
                ai = translateToAI(contents.remove(0), board);
            else
                System.out.println("NOTE: No AI search space is given. This can lead to performance issues with large boards.");
        }
        catch(BoardFormatException b){System.out.println(b.getMessage());}
        return null;
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
            case "black": colour = 1; break;
            case "white": colour = 2; break;
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
        
        if(x >= 0 && x < board.getWidth())
            return null;
        return null;
    }
    
    public static AI translateToAI(String raw, Board board){
        return null;
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
            case '.':
                return 0;
            case 'b':
                return 1;
            case 'w':
                return 2;
            default:
                throw new BoardFormatException("ERROR: The board to translate contains an illegal character " + value + ". Board not translated.");
        }
    }

    //Translates Board int elements into text
    public static char translateToChar(int i) throws BoardFormatException {
        switch (i) {
            case 0:
                return '.';
            case 1:
                return 'b';
            case 2:
                return 'w';
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
