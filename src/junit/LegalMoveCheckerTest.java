package junit;

import static org.junit.Assert.*;

import java.util.Arrays;

import main.Board;
import main.Coordinate;
import main.LegalMoveChecker;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
/** 
 * Test class forLegalMoveChecker,
 * checks legal moves, captures, if a stone
 * is occupied and the superKo rule.
 */
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
     public void OccupiedB() {
     byte b[][] = {{0,0,0,0},{0,1,1,0},{1,2,0,1},{0,1,1,0}};
     Board board = new Board(b);
     boolean result;
     for(int i = 0; i < board.getWidth() ;i++){
     for(int j = 0; j < board.getHeight() ;j++){
     if(board.get(i, j) != 0){ 
    	 Coordinate coord = new Coordinate(i, j);
    	 result = moveChecker.checkMove(board, coord, 1 ,false);
     assertEquals(false,result);
     }
     }
     }
     }
	
     @Test
     public void OccupiedW() {
     byte b[][] = {{0,0,0,0},{0,1,1,0},{1,2,0,1},{0,1,1,0}};
     Board board = new Board(b);
     boolean result;
     for(int i = 0; i < board.getWidth() ;i++){
     for(int j = 0; j < board.getHeight() ;j++){
     if(board.get(i, j) != 0){ 
    	 Coordinate coord = new Coordinate(i, j);
    	 result = moveChecker.checkMove(board, coord, 2 ,false);
     assertEquals(false,result);
     }
     }
     }
     }
	
     @Test
     public void FreeB() {
     byte b[][] = {{0,0,0,0},{0,1,0,0},{1,2,0,1},{0,1,1,0}};
     Board board = new Board(b);
     boolean result;
     for(int i = 0; i < board.getWidth() ;i++){
     for(int j = 0; j < board.getHeight() ;j++){
     if(board.get(i, j) == 0){ 
    	 Coordinate coord = new Coordinate(i, j);
    	 result = moveChecker.checkMove(board, coord, 1 ,false);
     assertEquals(true,result);
     }
     }
     }
     }
	
     @Test
     public void FreeW() {
     byte b[][] = {{0,0,0,0},{1,1,0,0},{0,2,0,0},{0,1,1,0}};
     Board board = new Board(b);
     boolean result;
     for(int i = 0; i < board.getWidth() ;i++){
     for(int j = 0; j < board.getHeight() ;j++){
     if(board.get(i, j) == 0){ 
    	 Coordinate coord = new Coordinate(i, j);
    	 result = moveChecker.checkMove(board, coord, 2 ,false);
     assertEquals(true,result);
     }
     }
     }
     }
	
     @Test
     public void IllegalFree() {
     
     byte b[][] = {{0,0,0,0},{0,1,1,0},{1,2,0,1},{0,1,1,0}};
     Board board = new Board(b);
     boolean result;
     Coordinate coord = new Coordinate(2, 2);
	 result = moveChecker.checkMove(board, coord, 2 ,false);
     assertEquals(false,result);
     }
	
     @Test
     public void Capture() {
     byte b[][] = {{0,0,0,0},{0,1,1,0},{1,2,0,1},{0,1,1,0}};
     byte b1[][] = {{0,0,0,0},{0,1,1,0},{1,0,1,1},{0,1,1,0}};
     boolean result;
     Board board = new Board(b);
     Board board1 = new Board(b1);
     Coordinate coord = new Coordinate(2, 2);
	 result = moveChecker.checkMove(board, coord, 1 ,false);
     result = (moveChecker.getLastLegal().equals(board1));
     assertEquals(true,result);
     }
	
     @Test
     public void SuperKo() {
     byte b[][] = {{0,0,0,0},{1,1,2,2},{1,0,1,2},{1,1,2,2}};
     boolean result;
     Board board = new Board(b);
    // moveChecker.checkMove(board, 2, 1, 2);
     Coordinate coord = new Coordinate(2, 1);
     result = moveChecker.checkMove(board, coord, 2 ,false);
     //result = moveChecker.checkMove(board, 2, 2, 1);
     Coordinate coord1 = new Coordinate(2, 2);
     result = moveChecker.checkMove(board, coord1, 1 ,false);
     assertEquals(false,result);
     }
     
}
