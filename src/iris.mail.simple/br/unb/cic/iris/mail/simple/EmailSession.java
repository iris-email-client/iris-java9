package br.unb.cic.iris.mail.simple;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Service;
import javax.mail.Session;
import javax.mail.event.ConnectionEvent;
import javax.mail.event.ConnectionListener;

import br.unb.cic.iris.mail.EmailProvider;

/***
 * added by dBaseMail
 */
public class EmailSession implements ConnectionListener {
	// private static Logger logger =
	// Logger.getLogger(EmailSession.class.getName());
	private final String encoding;
	private final Session session;
	private final EmailProvider provider;

	public EmailSession(EmailProvider provider) {
		this(provider, EmailClient.CHARACTER_ENCODING);
	}

	public EmailSession(EmailProvider provider, String encoding) {
		this.provider = provider;
		this.encoding = encoding;
		this.session = createMailSession();
	}

	private Session createMailSession() {
		// logger.log(Level.FINE, "Creating mail session");
		Properties props = getProvider().getProperties();
		if (getProvider().isAuthenticationEnabled()) {
			return Session.getInstance(props, new Authenticator() {
				@Override
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(getProvider().getUsername(), getProvider().getPassword());
				}
			});
		}
		return Session.getInstance(props);
	}

	protected final void connect(Service service, String host, int port) throws MessagingException {
		System.out.println("Connecting ...");
		service.connect(host, port, getProvider().getUsername(), getProvider().getPassword());
	}

	protected Session getSession() {
		return session;
	}

	protected EmailProvider getProvider() {
		return provider;
	}

	protected String getEncoding() {
		return encoding;
	}

	@Override
	public void opened(ConnectionEvent e) {
		System.out.println("Connected ...");
	}

	@Override
	public void disconnected(ConnectionEvent e) {
		System.out.println("Disconnected ...");
	}

	@Override
	public void closed(ConnectionEvent e) {
		System.out.println("Connection closed ...");
	}
}