package org.dclayer.test;
import static org.junit.Assert.fail;

import java.util.LinkedList;

import org.dclayer.DCLService;
import org.dclayer.crypto.hash.HashAlgorithm;
import org.dclayer.net.circle.CircleNetworkType;
import org.dclayer.net.lla.CachedLLA;
import org.dclayer.net.network.NetworkType;
import org.dclayer.simulation.DCLNetworkSimulation;
import org.junit.Test;


public class DCLServiceTest {

	/**
	 * Connect two services, B and C, to a third service, A.
	 * After some time, A and B should be connected to each other.
	 */
	@Test
	public void testDCLConnecting() {
		
		DCLNetworkSimulation dclNetworkSimulation = new DCLNetworkSimulation();
		NetworkType networkType = new CircleNetworkType(HashAlgorithm.SHA1, HashAlgorithm.SHA1.getDigestNumBytes());
		
		DCLService serviceA = dclNetworkSimulation.add(networkType);
		
		DCLService serviceB = dclNetworkSimulation.add(networkType, serviceA.getLocalLLA());
		DCLService serviceC = dclNetworkSimulation.add(networkType, serviceA.getLocalLLA());
		
		LinkedList<CachedLLA> bCachedLLAs = serviceB.getConnectionManager().getConnectedCachedLLAs();
		LinkedList<CachedLLA> cCachedLLAs = serviceC.getConnectionManager().getConnectedCachedLLAs();
		
		boolean bConnected = false;
		boolean cConnected = false;
		
		long start = System.nanoTime()/1000000L;
		
		for(;;) {
			
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				fail();
			}
			
			if(!bConnected) {
				synchronized(bCachedLLAs) {
					for(CachedLLA cachedLLA : bCachedLLAs) {
						if(cachedLLA.getLLA().equals(serviceC.getLocalLLA())) {
							bConnected = true;
							break;
						}
					}
				}
			}
			
			if(!cConnected) {
				synchronized(cCachedLLAs) {
					for(CachedLLA cachedLLA : cCachedLLAs) {
						if(cachedLLA.getLLA().equals(serviceB.getLocalLLA())) {
							cConnected = true;
							break;
						}
					}
				}
			}
			
			if(bConnected && cConnected) break;
			
			if((System.nanoTime()/1000000L - start) > 20000) {
				fail("services not connected");
				return;
			}
			
		}
		
	}

}
