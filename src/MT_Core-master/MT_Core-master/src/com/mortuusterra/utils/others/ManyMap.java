/*
 * Copyright (C) 2017 Mortuss Terra Team
 * You should have received a copy of the GNU General Public License along with this program. 
 * If not, see https://github.com/kadeska/MT_Core/blob/master/LICENSE.
 */
package com.mortuusterra.utils.others;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * Util map-like object to have 1 key correspond to several values.
 * @author Horsey
 *
 * @param <K> key
 * @param <V> values
 */

public class ManyMap<K,V>{
	
	// We're just using a hashmap.
	Map<K, List<V>> map = new HashMap<K, List<V>>();
	
	/**
	 * Adds a value to a key.
	 * @param key the key
	 * @param val the value to be added
	 */
	public void addValue(K key, V val){
		List<V> list = map.containsKey(key) ? map.get(key) : new ArrayList<V>();
		list.add(val);
		
		map.put(key, list);
	}

	/**
	 * Removes a certain value from a key
	 * 
	 * @param key the key
	 * @param val the value to be removed
	 */
	public void removeValue(K key, V val){
		List<V> list = map.containsKey(key) ? map.get(key) : new ArrayList<V>();
		list.remove(val);

		map.put(key, list);
	}
	
	/** 
	 * Return the list for a key. Useful for .contains methods and such.
	 * @param key the key
	 * @return the list bound to key
	 */
	public List<V> getList(K key){
		return map.containsKey(key) ? map.get(key) : new ArrayList<>();
	}
	
	/**
	 * Binds a key to a list.
	 * 
	 * @param key the key
	 * @param values the list
	 */
	public void setList(K key, List<V> values){
		map.put(key, values);
	}
	
	/**
	 * Get all the lists
	 * @return all lists
	 */
	public Collection<List<V>> values(){
		return map.values();
	}
	
	/**
	 * Get all the entries
	 * @return returns all the entries
	 */
	public Set<Entry<K, List<V>>> entrySet(){
		return map.entrySet();
	}
	
	// This is a very limited implementation, feel free to add new methods as required // 
}
