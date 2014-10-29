package junit;

import static org.junit.Assert.*;

import java.util.Arrays;

import main.Board;
import main.LegalMoveChecker;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class LegalMoveCheckerTest {

	private LegalMoveChecker moveChecker;
	
	@Before
	public void setUp() throws Exception{
		moveChecker = new LegalMoveChecker();
	}
	
	@After
	public void tearDown() throws Exception {
		moveChecker = null;
	}
	
	@Test
	public void testOccupiedB() {
		int b[][] = {{0,0,0,0},{0,1,1,0},{1,2,0,1},{0,1,1,0}};
		Board board = new Board(b);
		boolean result;
		for(int i = 0; i < board.getWidth() ;i++){
			for(int j = 0; j < board.getHeight() ;j++){
				if(board.get(i, j) != 0){ 
					result = moveChecker.checkMove(board, i, j, 1);
					assertEquals(false,result);
				}
			}
		}
	}
	
	@Test
	public void testOccupiedW() {
		int b[][] = {{0,0,0,0},{0,1,1,0},{1,2,0,1},{0,1,1,0}};
		Board board = new Board(b);
		boolean result;
		for(int i = 0; i < board.getWidth() ;i++){
			for(int j = 0; j < board.getHeight() ;j++){
				if(board.get(i, j) != 0){ 
					result = moveChecker.checkMove(board, i, j, 2);
					assertEquals(false,result);
				}
			}
		}
	}
	
	@Test
	public void testFreeB() {
		int b[][] = {{0,0,0,0},{0,1,0,0},{1,2,0,1},{0,1,1,0}};
		Board board = new Board(b);
		boolean result;
		for(int i = 0; i < board.getWidth() ;i++){
			for(int j = 0; j < board.getHeight() ;j++){
				if(board.get(i, j) == 0){ 
					result = moveChecker.checkMove(board, i, j, 1);
					assertEquals(true,result);
				}
			}
		}
	}
	
	@Test
	public void testFreeW() {
		int b[][] = {{0,0,0,0},{1,1,0,0},{0,2,0,0},{0,1,1,0}};
		Board board = new Board(b);
		boolean result;
		for(int i = 0; i < board.getWidth() ;i++){
			for(int j = 0; j < board.getHeight() ;j++){
				if(board.get(i, j) == 0){ 
					result = moveChecker.checkMove(board, i, j, 2);
					assertEquals(true,result);
				}
			}
		}
	}
	
	@Test
	public void testIllegalFree() {
		int b[][] = {{0,0,0,0},{0,1,1,0},{1,2,0,1},{0,1,1,0}};
		Board board = new Board(b);
		boolean result = moveChecker.checkMove(board, 2, 2, 2);
		assertEquals(false,result);
	}
	
	@Test
	public void testcapture() {
		int b[][] = {{0,0,0,0},{0,1,1,0},{1,2,0,1},{0,1,1,0}};
		int b1[][] = {{0,0,0,0},{0,1,1,0},{1,0,1,1},{0,1,1,0}};
		Board board = new Board(b);
		Board board1 = new Board(b1);
		moveChecker.checkMove(board, 2, 2, 1);
		boolean result = (Arrays.deepEquals(moveChecker.getLastLegal().getRaw(),board1.getRaw()));
		assertEquals(true,result);
	}
	
	@Test
	public void testSuperKo() {
		int b[][] = {{0,0,0,0},{1,1,2,2},{1,0,1,2},{1,1,2,2}};
		Board board = new Board(b);
		moveChecker.checkMove(board, 2, 1, 2);
		boolean result = moveChecker.checkMove(board, 2, 2, 1);
		assertEquals(false,result);
	}
	
}
