package junit;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;

import main.Board;
import main.BoardFormatException;
import main.Coordinate;
import main.GameEngine;
import ai.Objective;
import main.Translator;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TranslatorTest {
    
     private Translator translator;

     @Before
     public void setUp() throws Exception {
     translator = new Translator();
     }

     @After
     public void tearDown() throws Exception {
     translator = null;
     }

     @Test
     public void testTranslateGameInstructions() {
     String instructions = "9 9"
     + "\n...b..b.."
     + "\n........."
     + "\n........."
     + "\n........."
     + "\n........."
     + "\n........."
     + "\n........."
     + "\n........."
     + "\n........."
     + "\nblack defend 1 4"
     + "\n0 0 3 9";
     String[] gi = instructions.split("\n");
     ArrayList<String> testInstructions = new ArrayList<String>();
     for (String s : gi) {
     testInstructions.add(s);
     }
     // create board same as input game instructions for comparison
     GameEngine ge = new GameEngine(new Board(9, 9), new Objective("defend", 1, new Coordinate(1, 4)));
     byte one = 1;
     ge.getCurrentBoard().set(3, 0, one);
     ge.getCurrentBoard().set(6, 0, one);
     GameEngine ge2 = new GameEngine();

     try {
     ge2 = Translator.translateGameInstructions(testInstructions);
     } catch (NumberFormatException e) {
     e.printStackTrace();
     } catch (BoardFormatException e) {
     e.printStackTrace();
     }

     assertTrue(ge.getCurrentBoard().equals(ge2.getCurrentBoard()));
     assertEquals(ge.getObjective().toString(), ge2.getObjective().toString());
     
     }

     @Test
     public void testTranslateToObjective() {
     String obj = "white kill 4 5";
     Objective objective = new Objective("defend", 1, new Coordinate(0, 0));

     try {
     objective = Translator.translateToObjective(obj, new Board());
     } catch (NumberFormatException e) {
     e.printStackTrace();
     } catch (BoardFormatException e) {
     e.printStackTrace();
     }

     assertEquals(objective.getStartingColour(), 2);
     assertEquals(objective.getOriginalAction(), "kill");
     assertEquals(objective.getPosition().toString(), new Coordinate(4, 5).toString());
     }

     @Test
     public void testTranslateToSearchValues() {
     String sv = "0 0 4 5";
     int[] searchSpace = new int[4];

     try {
     searchSpace = Translator.translateToSearchValues(sv, new Board());
     } catch (NumberFormatException e) {
     e.printStackTrace();
     } catch (BoardFormatException e) {
     e.printStackTrace();
     }

     assertTrue(Arrays.equals(searchSpace, new int[]{0, 0, 4, 5}));
     }

     @Test
     public void testTranslateToFile() {
     String file = "";
     String instructions = "9 9"
     + "\n...b..b.."
     + "\n........."
     + "\n........."
     + "\n........."
     + "\n........."
     + "\n........."
     + "\n........."
     + "\n........."
     + "\n........."
     + "\nblack defend 1 4"
     + "\n0 0 3 9";
     GameEngine ge = new GameEngine(new Board(9, 9), new Objective("defend", 1, new Coordinate(1, 4)));
     byte one = 1;
     ge.getCurrentBoard().set(3, 0, one);
     ge.getCurrentBoard().set(6, 0, one);

     try {
     file = Translator.translateToFile(ge);
     } catch (BoardFormatException e) {
     e.printStackTrace();
     }

     assertEquals(file, instructions);
     }

     @Test
     public void testTranslateToBoardInstruction() {
     String instructions = "9 9"
     + "\n...b..b.."
     + "\n........."
     + "\n........."
     + "\n........."
     + "\n........."
     + "\n........."
     + "\n........."
     + "\n........."
     + "\n........."
     + "\n";
     String board = "";
     GameEngine ge = new GameEngine(new Board(9, 9), new Objective("defend", 1, new Coordinate(1, 4)));
     byte one = 1;
     ge.getCurrentBoard().set(3, 0, one);
     ge.getCurrentBoard().set(6, 0, one);

     try {
     board = Translator.translateToBoardInstruction(ge.getCurrentBoard());
     } catch (BoardFormatException e) {
     e.printStackTrace();
     }

     assertEquals(instructions, board);
     }

     @Test
     public void testTranslateToObjectiveInstruction() {
     Objective obj = new Objective("kill", 2, new Coordinate(3, 3));
     String s = "";

     try {
     s = Translator.translateToObjectiveInstruction(obj);
     } catch (BoardFormatException e) {
     e.printStackTrace();
     }

     assertEquals(s, "white kill 3 3\n");
     }

     // No TranslateToSearchSpaceInstruction() anymore
     /*
     @Test
     public void testTranslateToSearchSpaceInstruction() {
     int[] ss = {0, 0, 5, 6};
     String result = "";

     try {
     result = Translator.translateToSearchSpaceInstruction(ss);
     } catch (BoardFormatException e) {
     e.printStackTrace();
     }

     assertEquals(result, "0 0 5 6");
     }
	*/
     @Test
     public void testTranslateToInt() {
     try {
     assertEquals(Translator.translateToInt('.'), 0);
     assertEquals(Translator.translateToInt('b'), 1);
     assertEquals(Translator.translateToInt('w'), 2);
     } catch (BoardFormatException e) {
     e.printStackTrace();
     }
     }

     @Test
     public void testTranslateToChar() {
     try {
     assertEquals(Translator.translateToChar(0), '.');
     assertEquals(Translator.translateToChar(1), 'b');
     assertEquals(Translator.translateToChar(2), 'w');
     } catch (BoardFormatException e) {
     e.printStackTrace();
     }
     }

     @Test
     public void testTranslateToString() {
     try {
     assertEquals(Translator.translateToString(0), "empty");
     assertEquals(Translator.translateToString(1), "black");
     assertEquals(Translator.translateToString(2), "white");
     } catch (BoardFormatException e) {
     e.printStackTrace();
     }
     }
     
}
