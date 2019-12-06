module iris.mail.secure.pgp {	
	requires bcprov.jdk15on;	
	requires bcpg.jdk15on;
	requires javamail.crypto;
	
	requires iris.mail.secure;	
	
	provides br.unb.cic.iris.mail.IEmailClient with br.unb.cic.iris.mail.secure.pgp.EmailClientPgp;
}