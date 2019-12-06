module iris.mail.secure.smime {	
	requires bcprov.jdk15on;
	requires bcpkix.jdk15on;
	requires bcmail.jdk15on;
	
	requires iris.mail.secure;	
	
	provides br.unb.cic.iris.mail.IEmailClient with br.unb.cic.iris.mail.secure.smime.EmailClientSmime;
}