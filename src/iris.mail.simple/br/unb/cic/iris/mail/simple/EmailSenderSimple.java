package br.unb.cic.iris.mail.simple;

import br.unb.cic.iris.mail.EmailProvider;
import br.unb.cic.iris.mail.internal.AbstractEmailSender;

public class EmailSenderSimple extends AbstractEmailSender {

	public EmailSenderSimple(EmailProvider provider, String encoding) {
		super(provider, encoding);
	}

}