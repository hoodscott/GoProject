package junit;

import static org.junit.Assert.*;

import java.util.Arrays;

import main.GameEngine;
import main.Board;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class GameEngineTest {

	private GameEngine gameEngine;
	
	@Before
	public void setUp() throws Exception {
		gameEngine = new GameEngine();
	}

	@After
	public void tearDown() throws Exception {
		gameEngine = null;
	}
        
        //Checks whether legal move is successfully made on board.
	@Test
	public void makeMoveSuccess() {
            gameEngine.newGame(new Board(2,2));
            Board moved = new Board(new int[][] {{0,0},{0,1}});
            
            //Checks whether move was made successfully
            assertTrue(gameEngine.makeMove(1, 1, 1));
            //Checks if the resulting boards are equal.
            assertTrue(Arrays.deepEquals(moved.getRaw(), gameEngine.getCurrentBoard().getRaw()));
	}
        
        //Checks whether an illegal move is rejected.
        @Test
            public void makeMoveFailure() {
            gameEngine.newGame(new Board(new int[][] {{1,0},{0,1}}));
            
            Board previous = gameEngine.getCurrentBoard().clone();
            
            //Checks whether move was made unsuccessfully
            assertFalse(gameEngine.makeMove(1, 1, 2));
            //Verify the board has not changed.
            assertTrue(Arrays.deepEquals(previous.getRaw(), gameEngine.getCurrentBoard().getRaw()));
	}
            
        //Checks whether undoing moves on a non-changed board is rejected.
        @Test
            public void undoLastMoveEmptyBoard() {
            gameEngine.newGame(new Board(new int[][] {{1,0},{0,1}}));
                       
            //Checks whether an undo was made unsuccessfully
            assertFalse(gameEngine.undoLastMove());
	}
            
        //Checks undoing a move on a board with moves
        @Test
            public void undoLastMovePopulatedBoard() {
            gameEngine.newGame(new Board(new int[][] {{0,0},{0,0}}));
            Board stateA = gameEngine.getCurrentBoard().clone();
            gameEngine.makeMove(0, 0, 1);
            Board stateB = gameEngine.getCurrentBoard().clone();
            gameEngine.makeMove(0, 1, 1);
            Board stateC = gameEngine.getCurrentBoard().clone();
            gameEngine.makeMove(1, 1, 2);
                       
            //Checks whether an undo was made successfully
            assertTrue(gameEngine.undoLastMove());
            assertTrue(Arrays.deepEquals(gameEngine.getCurrentBoard().getRaw(),stateC.getRaw()));
            
            assertTrue(gameEngine.undoLastMove());
            assertTrue(Arrays.deepEquals(gameEngine.getCurrentBoard().getRaw(),stateB.getRaw()));
            
            assertTrue(gameEngine.undoLastMove());
            assertTrue(Arrays.deepEquals(gameEngine.getCurrentBoard().getRaw(),stateA.getRaw()));
            
            //Checks whether an undo was made unsuccessfully
            assertFalse(gameEngine.undoLastMove());
	}
}