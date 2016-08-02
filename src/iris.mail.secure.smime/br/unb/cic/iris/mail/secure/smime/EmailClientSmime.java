package br.unb.cic.iris.mail.secure.smime;

import br.unb.cic.iris.mail.EmailProvider;
import br.unb.cic.iris.mail.internal.AbstractEmailClient;
import br.unb.cic.iris.mail.secure.smime.internal.EmailReceiverSmime;
import br.unb.cic.iris.mail.secure.smime.internal.EmailSenderSmime;

public class EmailClientSmime extends AbstractEmailClient {

	@Override
	public void setProvider(EmailProvider provider, String encoding) {		
	sender = new EmailSenderSmime(provider, encoding);
	receiver = new EmailReceiverSmime(provider, encoding);
}

}
