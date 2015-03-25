package junit;

import static org.junit.Assert.*;

import java.io.*;
import
java.util.ArrayList;
import java.io.IOException;

import main.*;

import
org.junit.After;
import org.junit.Before;
import org.junit.Test;
/** 
 * Test class for FileIO,
 * tests loading and saving of boards to files
 * and exceptions are thrown when appropriate
 * 
 */
public
class FileIOTest {
	
    //private variables
    //Disabled until FileIO is
	//adjusted for M2.
	private GameEngine gameEngine;
	
        
     @Before
    
public void setUp() throws Exception {
    	 gameEngine = new GameEngine();
  
}

     @After
     public void tearDown() throws Exception {
    
//dereference object;
     }
	
     // WRITE BOARD TEST METHODS
	
       

     @Test
     public void WriteBoard() throws BoardFormatException {
    
     Board b1 = new Board();
     FileIO.writeBoard(gameEngine);
     Board b2 =new Board(9,9);
     FileIO.writeBoard(gameEngine);
     byte[][] board = b2.getRaw();
     Board b3 = new Board(board);
    
     FileIO.writeBoard(gameEngine);
     }

     @Test
     public void WriteBoardString() throws BoardFormatException {
     Board b1 = new Board();
     FileIO.writeBoard(gameEngine,"path");
     Board b2 = new Board(9,9);
    
     FileIO.writeBoard(gameEngine,"path");
     byte[][] board = b2.getRaw();
    
     Board b3 = new Board(board);
     FileIO.writeBoard(gameEngine,"path");
    
}
	
     @Test
     public void WriteBadBoard() {
     Board b = new
     Board();
     // set illegal character
     byte one = 1;
    
     b.set(1,1,one);
     try {
    	 FileIO.writeBoard(gameEngine);
     	} catch (Exception e) {
     assertTrue(e instanceof BoardFormatException);
     }
  
}
	
     // WRITE FILE TEST METHODS

     @Test
     public void WriteFile() {
     //FileIO.writeFile("Board output","Board path");
    
}
	
     @Test
     public void IOExceptionWriteFile() {
     try {
    	 	FileIO.writeFile("Board","Not available path");
     } catch (Exception e) {
 
    	 assertTrue(e instanceof IOException);
     }
		
     }

     // ADJUST PATH
     //	TEST METHODS
	 /*Adjust path method has been commented out in FileIO class*/
 
/*
     @Test
     public void AdjustPathNoInts() {
     String s = "path";

String adjusted = FileIO.adjustPath(s);
     assertEquals(adjusted,"path1");
 
}
	
     @Test
     public void AdjustPathWithInts() {
     String s =
"path1";
     String adjusted = FileIO.adjustPath(s);
    
assertEquals(adjusted,"path2");
     s = "path123";
     adjusted =
FileIO.adjustPath(s);
     assertEquals(adjusted,"path124");
     }
	 */
   
// PATH OS TEST METHODS
	
     @Test
     // test converting unix path to windows
     public void ConvertPathOS() {
     String winPath =
    		 "src\\saveData\\logs\\";
     String unixPath = "src/saveData/logs/";
		
    
     String os = System.getProperty("os.name").toLowerCase();
		
    
     if(os.contains("win")){
    	 unixPath = FileIO.pathOS(unixPath);
     	}
     else if(os.contains("linux") || os.contains("unix")){
    	 winPath = FileIO.pathOS(winPath);
     	}
     	else {
     		//undetected system
     		winPath = "fail";
     	}
     	assertEquals(winPath,unixPath);
     	}
	
    
     @Test
     // verify if whole file is read from right path
     public void
     ReadFileCorrectly() {
     ArrayList<String> expected = new ArrayList<>();
   
     // TODO: add expected log to arraylist
     ArrayList<String> input= new ArrayList<>();
     try {
     String path = "saveData/log/dummylog";
    
     input = FileIO.readFile(path);
     } catch (Exception e) {
     }
    
     //assertEquals(input,expected);
     // TODO finish this test once paths aresorted
     assertTrue(false);
     }
     @Test
     // ascertain
     //IOException is thrown when it can't read from file
     public void ReadFileBadPath(){
     ArrayList<String> input = new ArrayList<>();
     try
     	{
     String path = "/badpath/testboard";
     input = FileIO.readFile(path);
     } catch (Exception e) {
     assertTrue(e instanceof IOException);
     }
     }
	
     // READ BOARD TEST METHODS
	
     @Test
     // test both methods to see whether the correct paths are used
     public void readBoardCheckPath(){
     Board noArgs, withArgs;
     try{
     //noArgs = readBoard();
     //withArgs = readBoard("saveData/boards/board");
     } catch (Exception e) {
     }
    
     //assertEquals(noArgs,withArgs);
     // TODO finish this test once paths are sorted
     assertTrue(false);
     }
	
     @Test
     // verify whether the board data read in is what is returned.
     public void readBoardCorrectly(){
     Board b;
     Board expected = new Board();
    
     // TODO: create expected board
     try{
     //b = readBoard();
     	} catch (Exception e) {
     }
     //assertEquals(expected, b);
     // TODO finish this test once paths are sorted
     assertTrue(false);
     }
	
    
@Test
     // verify that if a BoardFormatException is thrown, a default board is returned.
     public void readBoardDefault(){
     GameEngine b = null;
 
     Board expected = new Board();
     try{
     b = FileIO.readBoard("saveData/logs/log");
     } catch (Exception e) {
     }
 
     	assertTrue(expected.equals(b));
     }
        
     
}
