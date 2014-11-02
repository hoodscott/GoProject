import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class FileIOTestAdjustWrite {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	/* WRITE BOARD TEST METHODS */
	
	@Test
	public void testWriteBoard() {
		Board b1 = new Board();
		FileIO.writeBoard(b1);
		Board b2 = new Board(9,9);
		FileIO.writeBoard(b2);
		int[][] board = b2.getRaw();
		Board b3 = new Board(board);
		FileIO.writeBoard(b3);
	}

	@Test
	public void testWriteBoardString() {
		Board b1 = new Board();
		FileIO.writeBoard(b1,"path");
		Board b2 = new Board(9,9);
		FileIO.writeBoard(b2,"path");
		int[][] board = b2.getRaw();
		Board b3 = new Board(board);
		FileIO.writeBoard(b3,"path");
	}
	
	@Test
	public void testWriteBadBoard() {
		Board b = new Board();
		// set illegal character
		b.set(1,1,'i');
		try {
			FileIO.writeBoard(b);
		} catch (Exception e) {
			assertTrue(e instanceof BoardFormatException);
		}
	}
	
	/* WRITE FILE TEST METHODS */

	@Test
	public void testWriteFile() {
		//FileIO.writeFile("Board output","Board path");
	}
	
	@Test
	public void testIOExceptionWriteFile() {
		try {
			FileIO.writeFile("Board","Not available path");
		} catch (Exception e) {
			assertTrue(e instanceof IOException);
		}
		
	}

	/* ADJUST PATH TEST METHODS */
	
	@Test
	public void testAdjustPathNoInts() {
		String s = "path";
		String adjusted = FileIO.adjustPath(s);
		assertEquals(adjusted,"path1");
	}
	
	@Test
	public void testAdjustPathWithInts() {
		String s = "path1";
		String adjusted = FileIO.adjustPath(s);
		assertEquals(adjusted,"path2");
		s = "path123";
		adjusted = FileIO.adjustPath(s);
		assertEquals(adjusted,"path124");
	}

}
