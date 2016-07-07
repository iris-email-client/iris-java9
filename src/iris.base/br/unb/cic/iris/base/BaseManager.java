package br.unb.cic.iris.base;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

/***
 * added by dBase
 */
public class BaseManager<T> {
	private Map<String, Integer> map;
	private List<T> list;

	public BaseManager(){
		clear();
	}
	
	public void add(String key, T value) {
		synchronized (list) {
			map.put(key, list.size());
			list.add(value);
		}
	}

	public T get(String key) {
		if(exists(key)){
			return list.get(map.get(key));
		}
		return null;
	}

	public boolean exists(String key){
		return map.get(key) != null;
	}
	
	public List<T> getAll() {
		return Collections.unmodifiableList(list);
	}

	public void clear() {
		map = Collections.synchronizedMap(new HashMap<String, Integer>());
		list = new Vector<>();
	}

	public void print() {
		list.forEach(System.out::println);
	}
}