module iris.mail {
	requires public javax.mail;
	requires public java.activation;
	requires public iris.base;	
	requires public iris.model;
	
	exports br.unb.cic.iris.mail;
	exports br.unb.cic.iris.mail.i18n;
	exports br.unb.cic.iris.mail.internal;// to iris.mail.simple, iris.mail.secure, iris.mail.secure.smime;
	
	uses br.unb.cic.iris.model.EntityFactory;
		
	uses br.unb.cic.iris.mail.IEmailClient;
	uses br.unb.cic.iris.mail.EmailProvider;
}
