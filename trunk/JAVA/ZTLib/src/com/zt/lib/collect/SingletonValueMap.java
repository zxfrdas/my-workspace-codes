package com.zt.lib.collect;

import java.util.Hashtable;
import java.util.Map;

public class SingletonValueMap<K, V> extends Hashtable<K, V> {

	private static final long serialVersionUID = -6033585881328750826L;

	@Override
	public synchronized V put(K key, V value)
	{
		for (Map.Entry<K, V> entry : entrySet()) {
			if (entry.getValue().equals(value) && !entry.getKey().equals(key)) {
				key = entry.getKey();
			}
		}
		return super.put(key, value);
	}
	
	public synchronized K getKeyByValue(Object value)
	{
		for (Map.Entry<K, V> entry : entrySet()) {
			if (entry.getValue().equals(value)) {
				return entry.getKey();
			}
		}
		return null;
	}

}
