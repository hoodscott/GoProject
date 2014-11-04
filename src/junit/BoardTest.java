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
	public void defaultBoard(){
		Board b = new Board();
		int resulth = b.getHeight();
		int resultw = b.getHeight();
		boolean result = (resulth==9 && resultw==9);
		assertEquals(result,true);
	}

	@Test
	public void boardGivenDimensions(){
		Board b = new Board(9, 9);
		int resulth = b.getHeight();
		int resultw = b.getHeight();
		boolean result = (resulth==9 && resultw==9);
		assertEquals(result,true);
	}
	
	@Test
	public void boardGivenArray(){
		int b[][] = {{0,0,0,0},{0,1,1,0},{1,2,0,1},{0,1,1,0}};
		Board board = new Board(b);
		boolean result = (Arrays.deepEquals(board.getRaw(),b));
		assertEquals(result,true);
	}
	
	@Test
	public void boardClone(){
		int b[][] = {{0,0,0,0},{0,1,1,0},{1,2,0,1},{0,1,1,0}};
		Board board = new Board(b);
		Board boardC = board.clone();
		boolean result = (Arrays.deepEquals(board.getRaw(),boardC.getRaw()));
		assertEquals(result,true);
	}
}