module iris.mail.provider.yahoo {	
	requires iris.mail.provider;
	
	provides br.unb.cic.iris.mail.EmailProvider with br.unb.cic.iris.mail.provider.yahoo.YahooProvider;
}