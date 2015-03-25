package junit;

import static org.junit.Assert.*;

import main.BadInputException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
/** 
 * Test class for BadinputException,
 * verify appropriate message is output 
 * when exception is thrown
 */
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
    public void BadInputException() {
        BadInputException outTest = new BadInputException();
        assertEquals(outTest.getMsg(), null);
    }

    @Test
    public void BadInputExceptionString() {
        String message = "Test error output.";
        BadInputException outTest = new BadInputException(message);
        assertEquals(outTest.getMsg(), message);
    }
}
