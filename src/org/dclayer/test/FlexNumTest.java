package org.dclayer.test;
import static org.junit.Assert.fail;

import org.dclayer.exception.net.buf.BufException;
import org.dclayer.net.Data;
import org.dclayer.net.buf.DataByteBuf;
import org.junit.Test;


public class FlexNumTest {

	@Test
	public void test() throws BufException {
		
		Data data = new Data(9);
		DataByteBuf dataByteBuf = new DataByteBuf(data);
		
		for(int i = 0; i < 1000000; i++) {
			
			long num = (long)(Math.random() * Long.MAX_VALUE);
			
			dataByteBuf.seek(0);
			dataByteBuf.writeFlexNum(num);
			dataByteBuf.seek(0);
			if(dataByteBuf.readFlexNum() != num) {
				fail();
			}
			
		}
		
	}

}
