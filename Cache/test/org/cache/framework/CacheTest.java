package org.cache.framework;

import static org.junit.Assert.fail;

import org.junit.Test;

public class CacheTest {

	@Test
	public void testMap() throws Throwable {
		AbstractCacheMap<Integer, Integer> map = new LRUCache<Integer, Integer>(2,
				1000 * 300);
		map.put(1, 1);
		map.put(2, 2);
		map.put(3, 3);
		map.printMap();
	}

	@Test
	public void testSize() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetDefaultExpire() {
		fail("Not yet implemented");
	}

	@Test
	public void testPutKV() {
		fail("Not yet implemented");
	}

	@Test
	public void testPutKVLong() {
		fail("Not yet implemented");
	}

	@Test
	public void testGet() {
		fail("Not yet implemented");
	}

	@Test
	public void testEliminate() {
		fail("Not yet implemented");
	}

	@Test
	public void testIsFull() {
		fail("Not yet implemented");
	}

	@Test
	public void testRemove() {
		fail("Not yet implemented");
	}

	@Test
	public void testClear() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetCacheSize() {
		fail("Not yet implemented");
	}

	@Test
	public void testIsEmpty() {
		fail("Not yet implemented");
	}

	@Test
	public void testContainsKey() {
		fail("Not yet implemented");
	}

}
