package br.unb.cic.iris.base;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

/***
 * added by dBase
 */
public class Configuration {
	public static final String PROVIDER_AUTH = "provider.auth";
	public static final String PROVIDER_NAME = "provider.name";
	public static final String PROVIDER_DESCRIPTION = "provider.description";
	public static final String PROVIDER_TRANSPORT_PROTOCOL = "provider.transport.protocol";
	public static final String PROVIDER_TRANSPORT_HOST = "provider.transport.host";
	public static final String PROVIDER_TRANSPORT_PORT = "provider.transport.port";
	public static final String PROVIDER_STORE_PROTOCOL = "provider.store.protocol";
	public static final String PROVIDER_STORE_HOST = "provider.store.host";
	public static final String PROVIDER_STORE_PORT = "provider.store.port";
	public static final String PROVIDER_USER = "provider.user";
	public static final String PROVIDER_PASSWORD = "provider.password";
	private Properties properties;

	public Configuration() {
		properties = new Properties();
		loadProperties();
	}

	private void loadProperties() {
		System.out.println("Loading properties ...");
		String fileName = "default_provider.properties";
		System.out.println("Load properties: " + fileName);
		try {
			properties.load(getClass().getResourceAsStream("/" + fileName));
			System.out.println("Load properties: " + accountPropertyFile());
			properties.load(new FileInputStream(new File(accountPropertyFile())));
		} catch (Exception e) {
			throw new RuntimeException("Could not open the configuration file " + fileName);
		}
	}

	public Properties getProperties() {
		return properties;
	}

	public static String accountPropertyFile() {
		return System.getProperty("user.home") + "/.iris/account.properties";
	}
}