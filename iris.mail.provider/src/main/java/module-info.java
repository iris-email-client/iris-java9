module iris.mail.provider {	
	requires transitive iris.mail;
		
	exports br.unb.cic.iris.mail.provider;
	
	uses br.unb.cic.iris.mail.EmailProvider;
	
}