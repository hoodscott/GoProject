package junit;

import static org.junit.Assert.*;
import java.util.Arrays;
import main.Board;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class BoardTest {

	private Board board;

	@Before
	public void setUp() throws Exception {
		board = new Board();
	}

	@After
	public void tearDown() throws Exception {
		board = null;
	}

	@Test
	public void defaultBoard() {
		Board b = new Board();
		int resulth = b.getHeight();
		int resultw = b.getHeight();
		boolean result = (resulth == 9 && resultw == 9);
		
		assertEquals(result, true);
	}

	@Test
	public void boardGivenDimensions() {
		Board b = new Board(9, 9);
		int resulth = b.getHeight();
		int resultw = b.getHeight();
		boolean result = (resulth == 9 && resultw == 9);
		
		assertEquals(result, true);
	}

	@Test
	public void boardGivenArray() {
		int b[][] = { { 0, 0, 0, 0 }, { 0, 1, 1, 0 }, { 1, 2, 0, 1 },
				{ 0, 1, 1, 0 } };
		Board board = new Board(b);
		boolean result = (Arrays.deepEquals(board.getRaw(), b));
		
		assertEquals(result, true);
	}

	@Test
	public void boardClone() {
		int b[][] = { { 0, 0, 0, 0 }, { 0, 1, 1, 0 }, { 1, 2, 0, 1 },
				{ 0, 1, 1, 0 } };
		Board board = new Board(b);
		Board boardC = board.clone();
		boolean result = (Arrays.deepEquals(board.getRaw(), boardC.getRaw()));
		
		assertEquals(result, true);
	}

	@Test
	public void boardEquals() {
		Board a = new Board(2, 2);
		Board b = new Board(2, 2);
		Board c = null;
		int[][] raw = { { 1, 0 }, { 0, 0 } };
		Board d = new Board(raw);

		assertTrue(a.equals(b));
		a.set(0, 0, 1);
		
		assertFalse(a.equals(b));
		assertTrue(a.equals(d));
		assertFalse(b.equals(c));
	}
	
	@Test
	public void settingBoardPositions() {
		board = new Board();
		board.set(1,1,1);
		board.set(5,7,2);
		assertEquals(1,board.get(1,1));
		assertEquals(2,board.get(5,7));
	}
}