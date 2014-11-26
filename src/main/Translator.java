package main;
import java.util.ArrayList;
/**
 *
 * @author niklasz
 */
public class Translator {
    
    public static GameEngine translateGameInstructions(ArrayList<String> contents){
        
        GameEngine gameEngine = new GameEngine();
        try{
            Board b = translateToBoard(contents);
            AI ai = null;
            
        }
        catch(BoardFormatException b){System.out.println(b.getMessage());}
        return null;
    }

    //Checks the integrity of an inputted board data and translates it to an int[][]. If a condition fails, returns null.
    public static Board translateToBoard(ArrayList<String> raw) throws BoardFormatException{
        int w;
        int h;
        int[][] rawBoard;
        if (raw.size() < 2) {
            throw new BoardFormatException("ERROR: The given input does not contain enough lines for a Board.");
        }
        String[] rawInts = raw.get(0).split(" ");
        if (rawInts.length == 2 && FileIO.isNumber(rawInts[0]) && FileIO.isNumber(rawInts[1])) {
            w = Integer.parseInt(rawInts[0]);
            h = Integer.parseInt(rawInts[1]);
        } else {
            throw new BoardFormatException("ERROR: The initial line does not contain two valid integers.");
        }
        if (h != raw.size() - 1) {
            throw new BoardFormatException("ERROR: There is a mismatch in the given number of rows.");
        }
        for (int i = 0; i < h; i++) {
            if (raw.get(i + 1).length() != w) {
                throw new BoardFormatException("ERROR: There is a mismatch in the given number of columns.");
            }
        }
        rawBoard = new int[w][h];
        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                rawBoard[j][i] = Translator.translateToInt(raw.get(i + 1).charAt(j));
            }
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
    
}
