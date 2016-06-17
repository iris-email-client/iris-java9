module iris.mail.provider.outlook {	
	requires iris.mail.provider;
	
	provides br.unb.cic.iris.mail.EmailProvider with br.unb.cic.iris.mail.provider.outlook.OutlookProvider;
}