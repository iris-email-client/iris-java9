module iris.mail.provider.gmail {	
	requires iris.mail.provider;
	
	provides br.unb.cic.iris.mail.EmailProvider with br.unb.cic.iris.mail.provider.gmail.GmailProvider;
}