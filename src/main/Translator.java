package main;

/**
 *
 * @author niklasz
 */
public class Translator {

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
