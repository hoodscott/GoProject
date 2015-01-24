package junit;

import static org.junit.Assert.*;

import java.util.Arrays;

import main.Coordinate;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class CoordinateTest {

	private Coordinate coordinate;
	
	@Before
	public void setUp() throws Exception {
		coordinate = new Coordinate(0, 0);
	}

	@After
	public void tearDown() throws Exception {
		coordinate = null;
	}

	@Test
	public void testNewCoordinate(){
		Coordinate c  = new Coordinate(4,16);
		
		assertTrue(c instanceof Coordinate);
		assertEquals(c.x,4);
		assertEquals(c.y,16);
	}
}