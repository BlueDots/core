package org.cache.framework;

import java.util.HashMap;
import java.util.Iterator;

import org.cache.interceptor.Interceptor;

/**
 * LFU(最不经常使用页置换算法)实现类
 * 
 * @author mastery
 * @Time 2015-3-14 下午10:47:11
 * 
 * @param <K>
 * @param <V>
 */
public class LFUCache<K, V> extends AbstractCacheMap<K, V> {

	public LFUCache(int cacheSize, long defaultExpire) {
		super(cacheSize, defaultExpire);
		this.cacheMap = new HashMap<K, CacheObject<K, V>>(cacheSize + 1);
	}

	public LFUCache(int cacheSize, long defaultExpire, Interceptor interceptor) {
		this(cacheSize, defaultExpire);
		this.interceptor = interceptor;
	}

	/*
	 * 实现删除过期对象 和 删除访问次数最少的对象
	 * 
	 * @see org.cache.framework.AbstractCacheMap#eliminateCache()
	 */
	@Override
	protected int eliminateCache() {
		Iterator<CacheObject<K, V>> iterator = cacheMap.values().iterator();
		int count = 0;
		long minAccessCount = Long.MAX_VALUE;
		while(iterator.hasNext()){
            CacheObject<K, V> co = iterator.next();
            if(co.isExpired()) {
            	iterator.remove();
            	count++;
            	continue;
            }else  {
            	minAccessCount = Math.min(co.accessCount, minAccessCount);
            }
		}
		
		if(count > 0) {
			return count ;
		}
		
		if(minAccessCount != Long.MAX_VALUE) {
			iterator = cacheMap.values().iterator();
            
            while(iterator.hasNext()){
                CacheObject<K, V> co = iterator.next();
                 
                co.accessCount  -=  minAccessCount ;
                 
                if(co.accessCount <= 0 ){
                	proceed(co.cacheObject);
                    iterator.remove();
                    count++ ;
                }
            }
		}
		return count;
	}

}
