package br.unb.cic.iris.mail.secure.pgp.internal;

import br.unb.cic.iris.mail.EmailProvider;
import br.unb.cic.iris.mail.secure.EmailSecurityManager;
import br.unb.cic.iris.mail.secure.internal.EmailReceiverSecure;

public class EmailReceiverPgp extends EmailReceiverSecure{	

	public EmailReceiverPgp(EmailProvider provider, String encoding) {
		super(provider, encoding);
	}

	@Override
	protected EmailSecurityManager getEmailSecurityManager() {	
		return PgpSecurityManager.instance();
	}
}
