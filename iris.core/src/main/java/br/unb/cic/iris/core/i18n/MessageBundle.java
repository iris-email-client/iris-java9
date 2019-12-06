package br.unb.cic.iris.core.i18n;

import java.util.Locale;
import java.util.ResourceBundle;

import br.unb.cic.iris.core.SystemFacade;

/***
 * added by dBase
 */
public class MessageBundle {
	ResourceBundle rb;
	private static MessageBundle instance;

	
	public MessageBundle(Locale locale) {		
		rb = ResourceBundle.getBundle("MessageBundle", locale);
	}

	public static MessageBundle instance() {
		if (instance == null) {
			instance = new MessageBundle(SystemFacade.instance().getLocale());
		}
		return instance;
	}

	public String getMessage(String key) {
		return rb.getString(key);
	}

	public String getMessage(String key, String param) {
		return getMessage(key, new String[] { param });
	}

	public String getMessage(String key, Object[] args) {
		return String.format(getMessage(key), args);
	}

	public static String message(String key) {
		return instance().getMessage(key);
	}

	public static String message(String key, int param) {
		return instance().getMessage(key, param+"");
	}
	
	public static String message(String key, String param) {
		return instance().getMessage(key, param);
	}
	
	public static String message(String key, String... params) {
		return instance().getMessage(key, params);
	}

	public static String message(String key, Object[] args) {
		return instance().getMessage(key, args);
	}
	
	public ResourceBundle getBundle(){
		return rb;
	}

}