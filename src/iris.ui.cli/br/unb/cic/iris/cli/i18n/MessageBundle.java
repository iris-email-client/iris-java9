package br.unb.cic.iris.cli.i18n;

import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.ResourceBundle.Control;

import br.unb.cic.iris.core.SystemFacade;

public class MessageBundle {	
	private static MessageBundle instance;

	ResourceBundle rb;
	
	private MessageBundle(Locale locale) {		
		Control control = ResourceBundle.Control.getControl(ResourceBundle.Control.FORMAT_PROPERTIES);
		
		String defaultFile = "/MessageBundle.properties";
		String baseName = control.toBundleName("MessageBundle", locale);		
		//String baseName = control.toBundleName("MessageBundle", Locale.ENGLISH);		
		try {
			String file = String.format("/%s.properties", baseName);				
			InputStream is = getClass().getResourceAsStream(file);			
			if(is == null){				
				is = getClass().getResourceAsStream(defaultFile);
			}
			rb = new IrisCliResourceBundle(is);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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

	public static String message(String key, String param) {
		return instance().getMessage(key, param);
	}

	public static String message(String key, Object[] args) {
		return instance().getMessage(key, args);
	}
	
	public ResourceBundle getBundle(){
		return rb;
	}
}