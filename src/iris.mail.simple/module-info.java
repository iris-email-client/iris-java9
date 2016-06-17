module iris.mail.simple {
	requires javax.mail;
	requires java.activation;
	
	requires public iris.core;
	//requires public iris.mail;
	//requires iris.mail.provider;
	
	provides br.unb.cic.iris.mail.IEmailClient with br.unb.cic.iris.mail.simple.EmailClient;
}