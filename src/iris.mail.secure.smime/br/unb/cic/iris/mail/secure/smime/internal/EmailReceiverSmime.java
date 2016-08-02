package br.unb.cic.iris.mail.secure.smime.internal;

import br.unb.cic.iris.mail.EmailProvider;
import br.unb.cic.iris.mail.secure.EmailSecurityManager;
import br.unb.cic.iris.mail.secure.internal.EmailReceiverSecure;

public class EmailReceiverSmime extends EmailReceiverSecure {

	public EmailReceiverSmime(EmailProvider provider, String encoding) {
		super(provider, encoding);		
	}

	@Override
	protected EmailSecurityManager getEmailSecurityManager() {
		return EmailSecurityManagerSmime.instance();
	}

}
