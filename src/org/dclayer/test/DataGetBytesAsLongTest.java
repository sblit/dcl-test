package org.dclayer.test;
import static org.junit.Assert.assertEquals;

import org.dclayer.net.Data;
import org.junit.Test;


public class DataGetBytesAsLongTest {

	@Test
	public void testDataGetBytesAsLong() {
		
		String s = "1102030405060708";
		Data data = new Data(s);
		
		long n = data.getBytes(-8, 8);
		
		assertEquals(s, Long.toHexString(n));
		
	}

}
