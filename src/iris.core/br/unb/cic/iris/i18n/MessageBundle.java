package br.unb.cic.iris.i18n;

import java.util.ResourceBundle;

/***
 * added by dBase
 */
public class MessageBundle {
	ResourceBundle rb;
	private static MessageBundle instance;

	public MessageBundle() {
		rb = ResourceBundle.getBundle("MessageBundle");
	}

	public static MessageBundle instance() {
		if (instance == null) {
			instance = new MessageBundle();
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
		return MessageBundle.instance().getMessage(key);
	}

	public static String message(String key, String param) {
		return MessageBundle.instance().getMessage(key, param);
	}

	public static String message(String key, Object[] args) {
		return MessageBundle.instance().getMessage(key, args);
	}
}