import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class LegalMoveCheckerTest {

	private LegalMoveChecker moveChecker;
	
	@Before
	public void setUp() throws Exception {
		moveChecker = new LegalMoveChecker();
	}

	@After
	public void tearDown() throws Exception {
		moveChecker = null;
	}

	@Test
	public void testDummyMethod() {
		assertTrue(true);
	}

}