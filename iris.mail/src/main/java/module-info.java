module iris.mail {
	requires transitive iris.base;	
//	requires public iris.model;
	
	requires transitive javax.mail;
//	requires transitive javax.mail.api;
//	requires transitive java.activation;
	requires transitive activation;
	
	exports br.unb.cic.iris.mail;
	exports br.unb.cic.iris.mail.i18n;
	
	exports br.unb.cic.iris.mail.internal;//TODO to iris.mail.simple, iris.mail.secure, iris.mail.secure.smime;
	
	uses br.unb.cic.iris.model.EntityFactory;
		
	uses br.unb.cic.iris.mail.IEmailClient;
	uses br.unb.cic.iris.mail.EmailProvider;
}
