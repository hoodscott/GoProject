package junit;

import static org.junit.Assert.*;
import java.util.Arrays;
import main.Board;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
/** 
 * Test class for Board,
 * tests insantiation of Boards 
 * and setting of coordinates on the
 * board.
 */
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

    // test default board has the intended default height and width.
    @Test
    public void defaultBoard() {
        Board b = new Board();
        int resulth = b.getHeight();
        int resultw = b.getHeight();
        boolean result = (resulth == 9 && resultw == 9);

        assertEquals(result, true);
    }

    // test default board has the height and width that it has 
    //been initialised with, in this case 9.
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
        byte b[][] = {{0, 0, 0, 0}, {0, 1, 1, 0}, {1, 2, 0, 1},
        {0, 1, 1, 0}};
        Board board = new Board(b);
        boolean result = (Arrays.deepEquals(board.getRaw(), b));

        assertEquals(result, true);
    }
    
    // tests that our board.clone() is returning the correct board.
    @Test
    public void boardClone() {
        byte b[][] = {{0, 0, 0, 0}, {0, 1, 1, 0}, {1, 2, 0, 1},
        {0, 1, 1, 0}};
        Board board = new Board(b);
        Board boardC = board.clone();
        boolean result = (Arrays.deepEquals(board.getRaw(), boardC.getRaw()));

        assertEquals(result, true);
    }

    // tests Board equality
    @Test
    public void boardEquals() {
        Board a = new Board(2, 2);
        Board b = new Board(2, 2);
        Board c = null;
        byte[][] raw = {{1, 0}, {0, 0}};
        Board d = new Board(raw);

        assertTrue(a.equals(b));
        a.set(0, 0, (byte) 1);

        assertFalse(a.equals(b));
        assertTrue(a.equals(d));
        assertFalse(b.equals(c));
    }

    // tests the board.set() method to make sure
    // i sets the correct value in the correct 
    // position.
    @Test
    public void settingBoardPositions() {
        board = new Board();
        board.set(1, 1, (byte) 1);
        board.set(5, 7, (byte) 2);
        assertEquals(1, board.get(1, 1));
        assertEquals(2, board.get(5, 7));
    }
}
