module iris.mail.simple {	
	requires public iris.mail;
	
	provides br.unb.cic.iris.mail.IEmailClient with br.unb.cic.iris.mail.simple.EmailClient;
}