package junit;

import static org.junit.Assert.*;
import main.Board;
import main.Coordinate;
import ai.Objective;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
/** 
 * Test class for Objective,
 * tests new Objectives and whether or not
 * the objective has been completed.
 */
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

    // Tests instantiation of a new Objective 
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

    // Tests the getPosition() method to ensure
    // it returns the same String representation 
    // as Coordinate does
    @Test
    public void testGetPosition() {
        Coordinate position = objective.getPosition();
        Coordinate coord = new Coordinate(2, 4);
        assertEquals(coord.toString(), position.toString());
    }

    // tests the CheckSucceded method to ensure i returns
    // the right value when a group has been killed and when it is alive
    @Test
    public void testCheckSucceeded() {
        Board board = new Board();
        boolean killed = objective.checkSucceeded(board, 1);
        assertEquals(true, killed);

        board.set(2, 4, (byte) 2);
        boolean alive = objective.checkSucceeded(board, 1);
        assertEquals(false, alive);
    }

    
    @Test
    public void testIsStarting() {
        boolean start = objective.isStarting(1);
        assertEquals(true, start);
    }

    // tests that the correct colour is returned when
    // the getStartingColour method is returned
    @Test
    public void testGetStartingColour() {
        int colour = objective.getStartingColour();
        assertEquals(1, colour);
    }

    // tests that the correct colour is returned when
    // the getOtherColour method is returned
    @Test
    public void testGetOtherColour() {
        int enemy = objective.getOtherColour(1);
        int player = objective.getOtherColour(2);
        assertEquals(2, enemy);
        assertEquals(1, player);
    }

    // tests the toString method to make sure the 
    // correct string is intended string is returned
    @Test
    public void testToString() {
        String expected = ("Black to kill 2, 4");
        System.out.println(objective.toString());
        assertEquals(objective.toString(), expected);
    }

}
