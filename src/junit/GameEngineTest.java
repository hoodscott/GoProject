import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class GameEngineTest {

	private GameEngine gameengine;
	
	@Before
	public void setUp() throws Exception {
		gameengine = new GameEngine();
	}

	@After
	public void tearDown() throws Exception {
		gameengine = null;
	}

	@Test
	public void testDummyMethod() {
		assertTrue(true);
	}

}