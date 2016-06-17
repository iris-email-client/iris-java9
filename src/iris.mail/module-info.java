module iris.mail {
	requires public iris.base;	
	
	exports br.unb.cic.iris.mail;
	
	uses br.unb.cic.iris.mail.IEmailClient;
	uses br.unb.cic.iris.mail.EmailProvider;
}