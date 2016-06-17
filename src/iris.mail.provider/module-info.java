module iris.mail.provider {
	requires iris.base;
	requires public iris.mail;
		
	exports br.unb.cic.iris.mail.provider;
	
	uses br.unb.cic.iris.mail.EmailProvider;
	
}