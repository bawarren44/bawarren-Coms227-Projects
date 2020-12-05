package hw2;

import static org.junit.Assert.*;

import org.junit.Test;
import static hw2.BallDirection.*;
public class TennisTest {

	@Test
	public void testreceiverScore() {
		TennisGame b = new TennisGame();
		b.serve(false);
		b.hit(false, false);
		b.miss();
		assertEquals(1, b.getReceiverPoints());
		
	}

}
