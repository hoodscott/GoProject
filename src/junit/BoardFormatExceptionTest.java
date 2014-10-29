import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class BoardFormatExceptionTest {
	
	private BoardFormatException bfe;

	@Before
	public void setUp() throws Exception {
		bfe = new BoardFormatException();
	}

	@After
	public void tearDown() throws Exception {
		bfe = null;
	}

	@Test
	public void testBoardFormatException() {
		BoardFormatException outTest = new BoardFormatException();
		assertEquals(outTest.getMsg(),null);
	}

	@Test
	public void testBoardFormatExceptionString() {
		String message = "Test error output.";
		BoardFormatException outTest = new BoardFormatException(message);
		assertEquals(outTest.getMsg(),message);
	}

}