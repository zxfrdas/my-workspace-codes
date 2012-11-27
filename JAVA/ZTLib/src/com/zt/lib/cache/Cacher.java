package com.zt.lib.cache;

public interface Cacher<K, V> {
	
	public V put(K key);
	
	
}
