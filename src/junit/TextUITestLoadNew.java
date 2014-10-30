import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;


public class TextUITestLoadNew {
	
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
}
