package org.dclayer.test;
import static org.junit.Assert.fail;

import org.dclayer.crypto.hash.HashAlgorithm;
import org.dclayer.net.Data;
import org.dclayer.net.circle.CircleNetworkType;
import org.dclayer.net.network.routing.RouteQuality;
import org.junit.Test;


public class CircleRouteQualityTest {

	@Test
	public void testCircleRouteQuality() {
		
		CircleNetworkType networkType = new CircleNetworkType(HashAlgorithm.SHA1, HashAlgorithm.SHA1.getDigestNumBytes());

		Data d00 = new Data("0000000000000000000000000000000000000000");
		Data d70 = new Data("7000000000000000000000000000000000000000");
		Data d7f = new Data("7fffffffffffffffffffffffffffffffffffffff");
		Data d80 = new Data("8000000000000000000000000000000000000000");
		Data d8f = new Data("8fffffffffffffffffffffffffffffffffffffff");
		Data dff = new Data("ffffffffffffffffffffffffffffffffffffffff");
		
		RouteQuality d00_dff = networkType.getRouteQuality(d00, dff);
		RouteQuality d7f_d80 = networkType.getRouteQuality(d7f, d80);
		
		RouteQuality d00_d80 = networkType.getRouteQuality(d00, d80);
		RouteQuality d7f_dff = networkType.getRouteQuality(d7f, dff);
		
		RouteQuality d70_d8f = networkType.getRouteQuality(d70, d8f);
		RouteQuality d8f_d70 = networkType.getRouteQuality(d8f, d70);
		
		if(!d00_dff.critical) fail("must be critical");
		if(!d7f_d80.critical) fail("must be critical");
		
		if(d00_d80.critical) fail("must not be critical");
		if(d7f_dff.critical) fail("must not be critical");
		
		if(d70_d8f.quality != d8f_d70.quality) fail("must equal");
		
		if(d70_d8f.quality < d7f_dff.quality) fail("must not be smaller");
		
	}

}
