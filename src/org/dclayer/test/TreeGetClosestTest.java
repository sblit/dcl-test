package org.dclayer.test;
import static org.junit.Assert.fail;
import static org.junit.Assert.assertEquals;

import org.dclayer.datastructure.tree.ParentTreeNode;
import org.dclayer.net.Data;
import org.junit.Test;


public class TreeGetClosestTest {

	@Test
	public void testTreeGetClosest() {
		
		ParentTreeNode<Integer> tree = new ParentTreeNode<>(0);
		tree.put(new Data("ffffffff"), 1);
		tree.put(new Data("7f000000"), 2);
		tree.put(new Data("7fffffff"), 3);
		tree.put(new Data("80000000"), 4);
		tree.put(new Data("fffffffd"), 5);
		
		assertEquals(1, (int) tree.getClosest(new Data("00000000")));
		assertEquals(4, (int) tree.getClosest(new Data("8fffffff")));
		assertEquals(2, (int) tree.getClosest(new Data("40000000")));
		assertEquals(2, (int) tree.getClosest(new Data("3fffffff")));
		assertEquals(1, (int) tree.getClosest(new Data("3effffff")));
		assertEquals(5, (int) tree.getClosest(new Data("fffffffd")));
		assertEquals(1, (int) tree.getClosest(new Data("10000000")));
		
	}

}
