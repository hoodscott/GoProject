package junit;

import static org.junit.Assert.*;
import main.Board;
import main.Coordinate;
import main.Objective;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ObjectiveTest {

    private Objective objective;

    @Before
    public void setUp() throws Exception {
        objective = new Objective("kill", 1, new Coordinate(2, 4));
    }

    @After
    public void tearDown() throws Exception {
        objective = null;
    }

    @Test
    public void testNewObjective() {
        Objective objTest = new Objective("defend", 2, new Coordinate(1, 5));
        assertTrue(objTest instanceof Objective);
    }

    @Test
    public void testGetOriginalAction() {
        String original = objective.getOriginalAction();
        assertEquals(original, "kill");
    }
    /*
    @Test
    public void testGetAction() {
        String enemyAction = objective.getAction(2);
        String action = objective.getAction(1);

        assertEquals(enemyAction, "defend");
        assertEquals(action, "kill");
    }
    */
    @Test
    public void testGetPosition() {
        Coordinate position = objective.getPosition();
        Coordinate coord = new Coordinate(2, 4);
        assertEquals(coord.toString(), position.toString());
    }

    @Test
    public void testCheckSucceeded() {
        Board board = new Board();
        boolean killed = objective.checkSucceeded(board, 1);
        assertEquals(true, killed);

        board.set(2, 4, 2);
        boolean alive = objective.checkSucceeded(board, 1);
        assertEquals(false, alive);
    }

    @Test
    public void testIsStarting() {
        boolean start = objective.isStarting(1);
        assertEquals(true, start);
    }

    @Test
    public void testGetStartingColour() {
        int colour = objective.getStartingColour();
        assertEquals(1, colour);
    }

    @Test
    public void testGetOtherColour() {
        int enemy = objective.getOtherColour(1);
        int player = objective.getOtherColour(2);
        assertEquals(2, enemy);
        assertEquals(1, player);
    }

    @Test
    public void testToString() {
        String expected = ("Black to kill 2 ,4");
        System.out.println(objective.toString());
        assertEquals(objective.toString(), expected);
    }

}
