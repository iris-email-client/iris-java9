package br.unb.cic.iris.util;

import java.util.List;
/*** added by dBase
 */
public class StringUtil {
	public static boolean notEmpty(String s) {
		return s != null && ! s.isEmpty();
	}
	public static boolean isEmpty(String s) {
		return s == null || s.isEmpty();
	}
	public static boolean notEmpty(List<String> parameters) {
		boolean retValue = true;
		for(String str : parameters) {
			if(isEmpty(str)) {
				retValue = false;
				break;
			}
		}
		return retValue;
	}
	public static String paddle(String s, int size) {
		String tmp = null;
		if(s.length() < size) {
			for(int i = 0;
				i < size - s.length();
				i ++) {
				tmp = s.concat(" ");
			}
		}
		return tmp;
	}
}