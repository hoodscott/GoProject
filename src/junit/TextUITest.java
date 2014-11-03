package junit;
import main.GameEngine;
import main.TextUI;
import main.BadInputException;

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
}