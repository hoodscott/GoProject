package junit;

import main.GameEngine;
import main.TextUI;
import main.BadInputException;
import main.Board;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TextUITest {
    
     private GameEngine g;
     private TextUI ti;

     @Before
     public void setUp() throws Exception {
     g = new GameEngine();
     ti = new TextUI();
     }

     @After
     public void tearDown() throws Exception {
     g = null;
     ti = null;
     }

     @Test
     public void New9x9() {
     String cmd[] = {"n"};
     ti.startGame(cmd);
     }

     @Test
     public void NewCustomBoard() {
     String cmd1[] = {"n", "19", "19"};
     ti.startGame(cmd1);
     String cmd2[] = {"n","11","11"};
     ti.startGame(cmd2);
     }

     @Test
     public void NewGameTooManyArgs() {
     try {
     String cmd[] = {"One","Two"};
     ti.startGame(cmd);
     } catch (Exception e) {
     assertTrue(e instanceof BadInputException);
     }
     }

     @Test
     public void NewGameWrongDimensions() {
     try {
     String cmd[] = {"n","a","b"};
     ti.startGame(cmd);
     } catch (Exception e) {
     assertTrue(e instanceof BadInputException);
     }
     }

     @Test
     public void LoadBoard() {
     String cmd1[] = {"Filepath"};
     ti.loadBoard(cmd1);
     String cmd2[] = {"lb","filepath"};
     ti.loadBoard(cmd2);
     }

     @Test
     public void WrongArgsNum() {
     try {
     String cmd[] = {"1","2","3"};
     ti.loadBoard(cmd);
     } catch (Exception e) {
     assertTrue(e instanceof BadInputException);
     }
     }

     @Test
     //input interpreted correctly
     public void SaveLogInterpreted() {
     String cmd[] = {"sl", "testLog"};
     String log = "logged history";
     ti.addToLog(log);
     ti.saveLog(cmd);
     // check FileIO.writeLog(log) is called
     assertTrue(false);
     }

     //exceptions thrown when input is incorrect
     @Test
     public void SaveLogBadPath() {
     try {
     String cmd[] = {"sl","/badpath/testboard"};
     String log = "logged history";
     ti.addToLog(log);
     ti.saveLog(cmd);
     } catch (Exception e) {
     assertTrue(e instanceof BadInputException);
     }
     }
     @Test
     public void SaveLogTooManyArgs() {
     try {
     String cmd[] = {"sl","/badpath/testboard", "extraArg"};
     String log = "logged history";
     ti.addToLog(log);
     ti.saveLog(cmd);
     } catch (Exception e) {
     assertTrue(e instanceof BadInputException);
     }
     }

     //exception thrown when log is empty
     @Test
     public void SaveLogEmpty() {
     try {
     String cmd[] = {"sl"};
     ti.saveLog(cmd);
     } catch (Exception e) {
     assertTrue(e instanceof BadInputException);
     }
     }

     @Test
     //input interpreted correctly
     public void SaveBoardInterpreted() {
     String cmd[] = {"sb", "testBoard"};
     g.startGame();
     ti.saveBoard(cmd);
     // check FileIO.writeBoard(board) is called
     assertTrue(false);
     }

     //exceptions thrown when input is incorrect
     @Test
     public void SaveBoardBadPath() {
     try {
     String cmd[] = {"sb","/badpath/testboard"};
     g.startGame();
     ti.saveBoard(cmd);
     } catch (Exception e) {
     assertTrue(e instanceof BadInputException);
     }
     }
     @Test
     public void SaveBoardTooManyArgs() {
     try {
     String cmd[] = {"sb","/badpath/testboard", "extraArg"};
     g.startGame();
     ti.saveBoard(cmd);
     } catch (Exception e) {
     assertTrue(e instanceof BadInputException);
     }
     }

     //exception thrown when there is no board to save
     @Test
     public void SaveBoardEmpty() {
     try {
     String cmd[] = {"sb"};
     ti.saveBoard(cmd);
     } catch (Exception e) {
     assertTrue(e instanceof BadInputException);
     }
     }

     //Testing view on non-instantiated Board.
     @Test
     public void View() {
     try{
     ti.view();
     }
     catch(Exception e){
     assertTrue(e instanceof BadInputException);
     }
     }


     //Testing trying move on non-instantiated Board.
     @Test
     public void AbsentBoard() {
     try{
     ti.move(new String[] {"m","0","0","b"});
     }
     catch(Exception e){
     assertTrue(e instanceof BadInputException);
     }
     }

     //Testing for to few arguments in command.
     @Test
     public void TooFewArgs() {
     try{g.startGame();
     ti.move(new String[] {"m","0","0"});}
     catch(Exception e){
     assertTrue(e instanceof BadInputException);
     }                       
     }

     //Testing for to few arguments in command.
     @Test
     public void moveTooManyArgs() {
     try{g.startGame();
     ti.move(new String[] {"m","0","0","b","bananas"});}
     catch(Exception e){
     assertTrue(e instanceof BadInputException);
     }                       
     }

     //Testing for bad numbers in command.
     @Test
     public void moveBadNumbers() {
     try{g.startGame();
     ti.move(new String[] {"m","a","0","w"});}
     catch(Exception e){
     assertTrue(e instanceof BadInputException);
     }

     try{g.startGame();
     ti.move(new String[] {"m","0","b","w"});}
     catch(Exception e){
     assertTrue(e instanceof BadInputException);
     }

     try{g.startGame();
     ti.move(new String[] {"m","a","b","w"});}
     catch(Exception e){
     assertTrue(e instanceof BadInputException);
     }  
     }

     //Testing for negative numbers in command.
     @Test
     public void moveNegativeNumbers() {
     try{g.startGame();
     ti.move(new String[] {"m","-1","0","w"});}
     catch(Exception e){
     assertTrue(e instanceof BadInputException);
     }

     try{g.startGame();
     ti.move(new String[] {"m","0","-4","w"});}
     catch(Exception e){
     assertTrue(e instanceof BadInputException);
     }

     try{g.startGame();
     ti.move(new String[] {"m","-6","-100000","w"});}
     catch(Exception e){
     assertTrue(e instanceof BadInputException);
     }  
     }
     /*
     //Testing for numbers outside of board command.
     @Test
     public void moveOutofBounds() {
     try{g.startGame(new Board(20, 20));
     ti.move(new String[] {"m","20","0","w"});}
     catch(Exception e){
     assertTrue(e instanceof BadInputException);
     }

     try{g.newGame(new Board(20, 20));
     ti.move(new String[] {"m","0","21","w"});}
     catch(Exception e){
     assertTrue(e instanceof BadInputException);
     }

     try{g.newGame(new Board(20, 20));
     ti.move(new String[] {"m","23","49","w"});}
     catch(Exception e){
     assertTrue(e instanceof BadInputException);
     }  
     }

     //Testing for bad colours in board command.
     @Test
     public void moveColour() {
     try{g.newGame(new Board(20, 20));
     ti.move(new String[] {"m","10","0","blaaaaack"});}
     catch(Exception e){
     assertTrue(e instanceof BadInputException);
     }

     try{g.newGame(new Board(20, 20));
     ti.move(new String[] {"m","0","15","WHITE"});}
     catch(Exception e){
     assertTrue(e instanceof BadInputException);
     }

     try{g.newGame(new Board(20, 20));
     ti.move(new String[] {"m","13","12","yoMomma"});}
     catch(Exception e){
     assertTrue(e instanceof BadInputException);
     }  
     }

     //Tests for a response to a very basic illegal move.
     @Test
     public void moveIllegal() {
     try{g.newGame(new Board(new int[][] {{1,1},{2,0}}));
     ti.move(new String[] {"m","0","0","b"});}
     catch(Exception e){
     assertTrue(e instanceof BadInputException);
     }  
     }
     */
}
