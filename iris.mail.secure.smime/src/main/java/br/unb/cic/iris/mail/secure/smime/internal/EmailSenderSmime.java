package br.unb.cic.iris.mail.secure.smime.internal;

import br.unb.cic.iris.mail.EmailProvider;
import br.unb.cic.iris.mail.secure.EmailSecurityManager;
import br.unb.cic.iris.mail.secure.internal.EmailSenderSecure;

public class EmailSenderSmime extends EmailSenderSecure {

	public EmailSenderSmime(EmailProvider provider, String encoding) {
		super(provider, encoding);
	}

	@Override
	protected EmailSecurityManager getEmailSecurityManager() {
		return EmailSecurityManagerSmime.instance();
	}

}
