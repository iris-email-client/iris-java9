package br.unb.cic.iris.mail.provider.gmail;

import java.util.Properties;

import br.unb.cic.iris.mail.EmailProvider;

/***
 * added by dGmailProvider
 */
public class GmailProvider implements EmailProvider {
	private static final String NAME = "gmail";
	private static final String DESCRIPTION = "connect to gmail accounts";
	private final static Properties properties = new Properties();
	private final static String transportProtocol = "smtp";
	private final static String storeProtocol = "imap";
	private final static boolean transportAuth = true;
	private String username;
	private String password;

	public GmailProvider() {
	}

	public GmailProvider(String username, String password) {
		this.username = username;
		this.password = password;
	}

	static {
		properties.put(String.format("mail.%s.host", transportProtocol), "smtp.gmail.com");
		properties.put(String.format("mail.%s.port", transportProtocol), "587");
		properties.put(String.format("mail.%s.auth", transportProtocol), String.valueOf(transportAuth));
		properties.put(String.format("mail.%s.starttls.enable", transportProtocol), String.valueOf(transportAuth));
		properties.put(String.format("mail.%s.host", storeProtocol), "imap.gmail.com");
		properties.put(String.format("mail.%s.port", storeProtocol), "993");
		properties.setProperty(String.format("mail.%s.socketFactory.class", storeProtocol),	"javax.net.ssl.SSLSocketFactory");
		properties.setProperty(String.format("mail.%s.socketFactory.fallback", storeProtocol), "false");
		properties.setProperty(String.format("mail.%s.socketFactory.port", storeProtocol), String.valueOf("993"));
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
		return "smtp.gmail.com";
	}

	@Override
	public int getTransportPort() {
		return 587;
	}

	@Override
	public String getStoreHost() {
		return "imap.gmail.com";
	}

	@Override
	public int getStorePort() {
		return 993;
	}
}