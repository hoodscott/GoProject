package junit;

import static org.junit.Assert.*;

import main.BadInputException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class BadInputExceptionTest {
	
	private BadInputException bie;

	@Before
	public void setUp() throws Exception {
		bie = new BadInputException();
	}

	@After
	public void tearDown() throws Exception {
		bie = null;
	}
	
	@Test
	public void testBadInputException() {
		BadInputException outTest = new BadInputException();
		assertEquals(outTest.getMsg(),null);
	}

	@Test
	public void testBadInputExceptionString() {
		String message = "Test error output.";
		BadInputException outTest = new BadInputException(message);
		assertEquals(outTest.getMsg(),message);
	}
}
