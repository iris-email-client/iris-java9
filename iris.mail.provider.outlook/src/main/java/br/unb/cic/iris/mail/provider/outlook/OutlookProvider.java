package br.unb.cic.iris.mail.provider.outlook;

import java.util.Properties;

import br.unb.cic.iris.mail.EmailProvider;

/***
 * added by dOutlookProvider
 */
public class OutlookProvider implements EmailProvider {
	private static final String NAME = "outlook";
	private static final String DESCRIPTION = "connect to outlook accounts";
	private final static Properties properties = new Properties();
	private final static String transportProtocol = "smtp";
	private final static String storeProtocol = "imaps";
	private final static boolean transportAuth = true;
	private String username;
	private String password;

	public OutlookProvider() {
	}

	public OutlookProvider(String username, String password) {
		this.username = username;
		this.password = password;
	}

	static {
		properties.put(String.format("mail.%s.host", transportProtocol), "smtp-mail.outlook.com");
		properties.put(String.format("mail.%s.auth", transportProtocol), String.valueOf(transportAuth));
		properties.put(String.format("mail.%s.starttls.enable", transportProtocol), String.valueOf(transportAuth));
		properties.put(String.format("mail.%s.host", storeProtocol), "imap-mail.outlook.com");
	}

	public Properties getProperties() {
		return properties;
	}

	public boolean isAuthenticationEnabled() {
		return transportAuth;
	}

	@Override
	public EmailProvider clone() throws CloneNotSupportedException {
		return (EmailProvider) super.clone();
	}

	@Override
	public String getName() {
		return NAME;
	}

	@Override
	public String getDescription() {
		return DESCRIPTION;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public void setUsername(String username) {
		this.username = username;
	}

	@Override
	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String getTransportProtocol() {
		return transportProtocol;
	}

	@Override
	public String getStoreProtocol() {
		return storeProtocol;
	}

	@Override
	public String getTransportHost() {
		return "smtp-mail.outlook.com";
	}

	@Override
	public int getTransportPort() {
		return -1;
	}

	@Override
	public String getStoreHost() {
		return "imap-mail.outlook.com";
	}

	@Override
	public int getStorePort() {
		return -1;
	}
}