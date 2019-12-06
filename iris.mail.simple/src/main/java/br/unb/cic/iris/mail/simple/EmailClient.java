package br.unb.cic.iris.mail.simple;

import br.unb.cic.iris.mail.EmailProvider;
import br.unb.cic.iris.mail.internal.AbstractEmailClient;

public class EmailClient extends AbstractEmailClient {

	public void setProvider(EmailProvider provider, String encoding) {
		sender = new EmailSenderSimple(provider, encoding);
		receiver = new EmailReceiverSimple(provider, encoding);
	}

}