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
	private Map<String, Integer> map = Collections.synchronizedMap(new HashMap<String, Integer>());
	private List<T> list = new Vector<T>();

	public void add(String key, T value) {
		synchronized (list) {
			map.put(key, list.size());
			list.add(value);
		}
	}

	public T get(String key) {
		return list.get(map.get(key));
	}

	public List<T> getAll() {
		return Collections.unmodifiableList(list);
	}

	public void clear() {
		map = Collections.synchronizedMap(new HashMap<String, Integer>());
		list = new Vector<T>();
	}

	public void print() {
		for (T t : list) {
			System.out.println(t);
		}
	}
}