module iris.mail.secure {
	//requires javax.mail;
	//requires java.activation;
	
	requires public iris.mail;
	//requires public iris.mail;
	//requires iris.mail.provider;
	
	exports br.unb.cic.iris.mail.secure;
	//TODO rever esse export ... talvez seja melhor botar como publico mesmo
	exports br.unb.cic.iris.mail.secure.internal;// to iris.mail.secure.smime;
	
	//provides br.unb.cic.iris.mail.IEmailClient with br.unb.cic.iris.mail.simple.EmailClient;
}