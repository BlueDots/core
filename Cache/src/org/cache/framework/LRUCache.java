package org.cache.framework;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map.Entry;

import org.cache.interceptor.Interceptor;

/**
 * LRU(最近最少使用置换算法)具体实现
 * 
 * @author mastery
 * @Time 2015-3-14 下午10:35:44
 * 
 * @param <K>
 * @param <V>
 */
public class LRUCache<K, V> extends AbstractCacheMap<K, V> {

	@SuppressWarnings("serial")
	public LRUCache(int cacheSize, long defaultExpire) {
		super(cacheSize, defaultExpire);

		/*
		 * linkedHash已经实现LRU算法 是通过双向链表来实现，当某个位置被命中，通过调整链表的指向将该位置调整到头位置，
		 * 新加入的内容直接放在链表头，如此一来，最近被命中的内容就向链表头移动，
		 * 需要替换时，链表最后的位置就是最近最少使用的位置
		 */
		this.cacheMap = new LinkedHashMap<K, CacheObject<K, V>>(cacheSize + 1,
				1f, true) {

			@Override
			protected boolean removeEldestEntry(
					Entry<K,CacheObject<K, V>> eldest) {
				// TODO Auto-generated method stub
				return LRUCache.this.removeEldestEntry(eldest);
			}

		};
	}

	public LRUCache(int cacheSize, long defaultExpire, Interceptor interceptor) {
		this(cacheSize, defaultExpire);
		this.interceptor = interceptor;
	}

	private boolean removeEldestEntry(Entry<K, CacheObject<K, V>> eldest) {
		if(cacheSize == 0) {
			return false;
		}
		return size() > cacheSize;
	}

	/* 
	 *  只需要实现清除过期对象就可以了,linkedHashMap已经实现LRU
	 * @see org.cache.framework.AbstractCacheMap#eliminateCache()
	 */
	@Override
	protected int eliminateCache() {
		if(!isNeedClearExpiredObject()) {return 0;}
		
		Iterator<CacheObject<K , V>> iterator = cacheMap.values().iterator();
		int count = 0 ;
		while(iterator.hasNext()) {
			CacheObject<K , V> co = iterator.next();
			if(co.isExpired()) {
				iterator.remove();
				count++;
			}
		}
		
		return count;
	}

}
