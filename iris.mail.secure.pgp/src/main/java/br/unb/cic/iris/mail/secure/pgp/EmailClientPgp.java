package br.unb.cic.iris.mail.secure.pgp;

import br.unb.cic.iris.mail.EmailProvider;
import br.unb.cic.iris.mail.internal.AbstractEmailClient;
import br.unb.cic.iris.mail.secure.pgp.internal.EmailReceiverPgp;
import br.unb.cic.iris.mail.secure.pgp.internal.EmailSenderPgp;

public class EmailClientPgp extends AbstractEmailClient {
	public void setProvider(EmailProvider provider, String encoding) {
		sender = new EmailSenderPgp(provider, encoding);
		receiver = new EmailReceiverPgp(provider, encoding);
	}
}
