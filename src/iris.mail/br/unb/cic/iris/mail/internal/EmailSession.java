package br.unb.cic.iris.mail.internal;

import static br.unb.cic.iris.mail.i18n.MessageBundle.message;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Service;
import javax.mail.Session;
import javax.mail.event.ConnectionEvent;
import javax.mail.event.ConnectionListener;

import br.unb.cic.iris.mail.EmailProvider;
import br.unb.cic.iris.mail.EmailStatusManager;
import br.unb.cic.iris.mail.IEmailClient;

/***
 * added by dBaseMail
 */
public class EmailSession implements ConnectionListener {
	private final String encoding;
	private final Session session;
	private final EmailProvider provider;

	public EmailSession(EmailProvider provider) {
		this(provider, IEmailClient.CHARACTER_ENCODING);
	}

	public EmailSession(EmailProvider provider, String encoding) {
		this.provider = provider;
		this.encoding = encoding;
		this.session = createMailSession();
	}

	private Session createMailSession() {
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
		EmailStatusManager.instance().notifyListener(message("email.status.session.connecting"));
		service.connect(host, port, getProvider().getUsername(), getProvider().getPassword());
	}

	public Session getSession() {
		return session;
	}

	public EmailProvider getProvider() {
		return provider;
	}

	public String getEncoding() {
		return encoding;
	}

	@Override
	public void opened(ConnectionEvent e) {
		EmailStatusManager.instance().notifyListener(message("email.status.session.connected"));
	}

	@Override
	public void disconnected(ConnectionEvent e) {
		EmailStatusManager.instance().notifyListener(message("email.status.session.disconnected"));
	}

	@Override
	public void closed(ConnectionEvent e) {
		EmailStatusManager.instance().notifyListener(message("email.status.session.connection.closed"));
	}
}