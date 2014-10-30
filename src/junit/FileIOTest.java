import static org.junit.Assert.*;

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

	@Test
	// test converting windows path to unix
	// 	''			   unix path to windows
	public void testPathOS() {
		assertTrue(false);
	}
	
	@Test
	// verify if whole file is read from right path
	// ascertain IOException are thrown and caught as required
	public void testReadFile() {
		assertTrue(false);
	}
	
	@Test
	// test both methods to see whether the correct paths are used
	// verify whether the board data read in is what is returned.
	// verify that if a BoardFormatException is thrown, a default board is returned.
	public void testReadBoard() {
		assertTrue(false);
	}

}