package junit;
import main.GameEngine;
import main.TextUI;
import main.BadInputException;
import main.Board;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TextUITest {

    private GameEngine g;
    private TextUI ti;

    @Before
    public void setUp() throws Exception {
        g = new GameEngine();
        ti = new TextUI(g);
    }

    @After
    public void tearDown() throws Exception {
        g = null;
        ti = null;
    }

    /* NEW GAME METHOD TESTS */

    @Test
    public void testNew9x9() {
        String cmd[] = {"n"};
        ti.newGame(cmd);
    }

    @Test
    public void testNewCustomBoard() {
        String cmd1[] = {"n", "19", "19"};
        ti.newGame(cmd1);
        String cmd2[] = {"n","11","11"};
        ti.newGame(cmd2);
    }

    @Test
    public void testNewGameTooManyArgs() {
        try {
                String cmd[] = {"One","Two"};
                ti.newGame(cmd);
        } catch (Exception e) {
                assertTrue(e instanceof BadInputException);
        }
    }

    @Test
    public void testNewGameWrongDimensions() {
        try {
                String cmd[] = {"n","a","b"};
                ti.newGame(cmd);
        } catch (Exception e) {
                assertTrue(e instanceof BadInputException);
        }
    }

    /* LOAD BOARD METHOD TESTS */

    @Test
    public void testLoadBoard() {
        String cmd1[] = {"Filepath"};
        ti.loadBoard(cmd1);
        String cmd2[] = {"lb","filepath"};
        ti.loadBoard(cmd2);
    }

    @Test
    public void testWrongArgsNum() {
        try {
                String cmd[] = {"1","2","3"};
                ti.loadBoard(cmd);
        } catch (Exception e) {
                assertTrue(e instanceof BadInputException);
        }
    }

    /* SAVE LOG METHOD TESTS */

    @Test
    //input interpreted correctly
    public void testSaveLogInterpreted() {
        String cmd[] = {"sl", "testLog"};
        String log = "logged history";
        ti.addToLog(log);
        ti.saveLog(cmd);
        // check FileIO.writeLog(log) is called
        assertTrue(false);
    }

    //exceptions thrown when input is incorrect
    @Test
    public void testSaveLogBadPath() {
        try {
                String cmd[] = {"sl","/badpath/testboard"};
                String log = "logged history";
                ti.addToLog(log);
                ti.saveLog(cmd);
        } catch (Exception e) {
                assertTrue(e instanceof BadInputException);
        }
    }
    @Test
    public void testSaveLogTooManyArgs() {
        try {
                String cmd[] = {"sl","/badpath/testboard", "extraArg"};
                String log = "logged history";
                ti.addToLog(log);
                ti.saveLog(cmd);
        } catch (Exception e) {
                assertTrue(e instanceof BadInputException);
        }
    }

    //exception thrown when log is empty
    @Test
    public void testSaveLogEmpty() {
        try {
                String cmd[] = {"sl"};
                ti.saveLog(cmd);
        } catch (Exception e) {
                assertTrue(e instanceof BadInputException);
        }
    }

    /* SAVE BOARD METHOD TESTS */

    @Test
    //input interpreted correctly
    public void testSaveBoardInterpreted() {
        String cmd[] = {"sb", "testBoard"};
        g.newGame();
        ti.saveBoard(cmd);
        // check FileIO.writeBoard(board) is called
        assertTrue(false);
    }

    //exceptions thrown when input is incorrect
    @Test
    public void testSaveBoardBadPath() {
        try {
                String cmd[] = {"sb","/badpath/testboard"};
                g.newGame();
                ti.saveBoard(cmd);
        } catch (Exception e) {
                assertTrue(e instanceof BadInputException);
        }
    }
    @Test
    public void testSaveBoardTooManyArgs() {
        try {
                String cmd[] = {"sb","/badpath/testboard", "extraArg"};
                g.newGame();
                ti.saveBoard(cmd);
        } catch (Exception e) {
                assertTrue(e instanceof BadInputException);
        }
    }

    //exception thrown when there is no board to save
    @Test
    public void testSaveBoardEmpty() {
        try {
                String cmd[] = {"sb"};
                ti.saveBoard(cmd);
        } catch (Exception e) {
                assertTrue(e instanceof BadInputException);
        }
    }

    /* VIEW BOARD METHOD TESTS */

    //Testing view on non-instantiated Board.
    @Test
    public void testView() {
        try{
            ti.view();
        }
        catch(Exception e){
            assertTrue(e instanceof BadInputException);
        }
    }

    /* MOVE BOARD METHOD TESTS */

    //Testing trying move on non-instantiated Board.
    @Test
    public void moveAbsentBoard() {
        try{
           ti.move(new String[] {"m","0","0","b"});
        }
        catch(Exception e){
            assertTrue(e instanceof BadInputException);
        }
    }

    //Testing for to few arguments in command.
    @Test
    public void moveTooFewArgs() {
        try{g.newGame();
            ti.move(new String[] {"m","0","0"});}
        catch(Exception e){
            assertTrue(e instanceof BadInputException);
        }                       
    }

    //Testing for to few arguments in command.
    @Test
    public void moveTooManyArgs() {
        try{g.newGame();
            ti.move(new String[] {"m","0","0","b","bananas"});}
        catch(Exception e){
            assertTrue(e instanceof BadInputException);
        }                       
    }

    //Testing for bad numbers in command.
    @Test
    public void moveBadNumbers() {
        try{g.newGame();
            ti.move(new String[] {"m","a","0","w"});}
        catch(Exception e){
            assertTrue(e instanceof BadInputException);
        }

        try{g.newGame();
            ti.move(new String[] {"m","0","b","w"});}
        catch(Exception e){
            assertTrue(e instanceof BadInputException);
        }

        try{g.newGame();
            ti.move(new String[] {"m","a","b","w"});}
        catch(Exception e){
            assertTrue(e instanceof BadInputException);
        }  
    }

    //Testing for negative numbers in command.
    @Test
    public void moveNegativeNumbers() {
        try{g.newGame();
            ti.move(new String[] {"m","-1","0","w"});}
        catch(Exception e){
            assertTrue(e instanceof BadInputException);
        }

        try{g.newGame();
            ti.move(new String[] {"m","0","-4","w"});}
        catch(Exception e){
            assertTrue(e instanceof BadInputException);
        }

        try{g.newGame();
            ti.move(new String[] {"m","-6","-100000","w"});}
        catch(Exception e){
            assertTrue(e instanceof BadInputException);
        }  
    }

    //Testing for numbers outside of board command.
    @Test
    public void moveOutofBounds() {
        try{g.newGame(new Board(20, 20));
            ti.move(new String[] {"m","20","0","w"});}
        catch(Exception e){
            assertTrue(e instanceof BadInputException);
        }

        try{g.newGame(new Board(20, 20));
            ti.move(new String[] {"m","0","21","w"});}
        catch(Exception e){
            assertTrue(e instanceof BadInputException);
        }

        try{g.newGame(new Board(20, 20));
            ti.move(new String[] {"m","23","49","w"});}
        catch(Exception e){
            assertTrue(e instanceof BadInputException);
        }  
    }

    //Testing for bad colours in board command.
    @Test
    public void moveColour() {
        try{g.newGame(new Board(20, 20));
            ti.move(new String[] {"m","10","0","blaaaaack"});}
        catch(Exception e){
            assertTrue(e instanceof BadInputException);
        }

        try{g.newGame(new Board(20, 20));
            ti.move(new String[] {"m","0","15","WHITE"});}
        catch(Exception e){
            assertTrue(e instanceof BadInputException);
        }

        try{g.newGame(new Board(20, 20));
            ti.move(new String[] {"m","13","12","yoMomma"});}
        catch(Exception e){
            assertTrue(e instanceof BadInputException);
        }  
    }

    //Tests for a response to a very basic illegal move.
    @Test
    public void moveIllegal() {
        try{g.newGame(new Board(new int[][] {{1,1},{2,0}}));
            ti.move(new String[] {"m","0","0","b"});}
        catch(Exception e){
            assertTrue(e instanceof BadInputException);
        }  
    }
}