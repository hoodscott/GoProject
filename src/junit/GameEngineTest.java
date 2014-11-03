package junit;

import static org.junit.Assert.*;

import main.GameEngine;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class GameEngineTest {

	private GameEngine gameEngine;
	
	@Before
	public void setUp() throws Exception {
		gameEngine = new GameEngine();
	}

	@After
	public void tearDown() throws Exception {
		gameEngine = null;
	}

	@Test
	public void testDummyMethod() {
		assertTrue(true);
	}

}