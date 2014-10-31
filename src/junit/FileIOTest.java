import static org.junit.Assert.*;
import java.io.*;
import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class FileIOTest {

	//private variables;
	
	@Before
	public void setUp() throws Exception {
		//instantiate objects
	}

	@After
	public void tearDown() throws Exception {
		//dereference object;
	}
	
	/* PATH OS TEST METHODS */
	
	@Test
	// test converting unix path to windows
	public void testPathOSUnix() {
		String winPath = "src\\saveData\\logs\\";
		String unixPath = "src/saveData/logs";
		//assertEquals(winPath,pathOS(unixPath));
		assertTrue(false);
	}
	@Test
	// test converting windows path to unix
	public void testPathOSWin() {
		String winPath = "src\\saveData\\logs\\";
		String unixPath = "src/saveData/logs";
		//assertEquals(pathOS(winPath),unixPath);
		assertTrue(false);
	}
	
	@Test
	// verify if whole file is read from right path
	public void testReadFileCorrectly() {
		ArrayList<String> expected = new ArrayList<>();
		// TODO: add expected log to arraylist
		ArrayList<String> input= new ArrayList<>();
		try {
			String path = "saveData/log/dummylog";
			//input = readFile(path);
		} catch (Exception e) {
		}
		assertEquals(input,expected);
	}
	@Test
	// ascertain IOException is thrown when it can't read from file
	public void testReadFileBadPath(){
		ArrayList<String> input = new ArrayList<>();
		try {
			String path = "/badpath/testboard";
			//input = readFile(path);
		} catch (Exception e) {
			assertTrue(e instanceof IOException);
		}
	}
	
	/* READ BOARD TEST METHODS */
	
	@Test
	// test both methods to see whether the correct paths are used
	public void readBoardCheckPath(){
		Board noArgs, withArgs;
		try{
			//noArgs = readBoard();
			//withArgs = readBoard("saveData/boards/board");
		} catch (Exception e) {
		}
		assertEquals(noArgs,withArgs);
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
		assertEquals(expected, b);
	}
	
	@Test
	// verify that if a BoardFormatException is thrown, a default board is returned.
	public void readBoardDefault(){
		Board b;
		Board expected = new Board();
		try{
			//b = readBoard("saveDdata/logs/log");
		} catch (Exception e) {
		}
		assertEquals(expected, b);
	}
	
}