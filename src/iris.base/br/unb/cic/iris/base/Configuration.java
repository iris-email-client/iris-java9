package br.unb.cic.iris.base;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;
import java.util.Properties;

import br.unb.cic.iris.util.StringUtil;

public class Configuration {
	public static final String FILE_SEPARATOR = System.getProperty("file.separator");
	public static final String IRIS_HOME = System.getProperty("user.home") + FILE_SEPARATOR + ".iris";	
	
	private static final Configuration instance = new Configuration();	

	private Locale locale;
	private Properties irisProperties;

	private Configuration() {
		initIrisProperties();
		initLocale();
	}

	public static Configuration instance() {
		return instance;
	}

	private void initLocale() {
		String language = getIrisProperties().getProperty("language");
		if (!StringUtil.isEmpty(language)) {
			locale = new Locale(getIrisProperties().getProperty("language"));
		} else {
			locale = Locale.ENGLISH;
		}
	}

	public Locale getLocale() {
		return locale;
	}

	private void initIrisProperties() {
		String irisPropertiesFile = IRIS_HOME + FILE_SEPARATOR + "iris.properties";
		irisProperties = new Properties();
		try (InputStream is = new FileInputStream(irisPropertiesFile)) {
			irisProperties.load(is);
		} catch (IOException e) {
			System.err.println("Unable to read iris properties file: " + irisPropertiesFile + ". " + e.getLocalizedMessage());
			e.printStackTrace();
		}
	}

	public Properties getIrisProperties() {
		return irisProperties;
	}

	public static void main(String[] args) {
		System.out.println(IRIS_HOME + FILE_SEPARATOR + "iris.properties");
	}
}
