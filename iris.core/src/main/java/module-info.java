module iris.core {
	requires transitive iris.mail;
	requires transitive iris.mail.provider;
	requires transitive iris.persistence;
	
	exports br.unb.cic.iris.command;
	exports br.unb.cic.iris.core;	
	exports br.unb.cic.iris.core.i18n;
	
	uses br.unb.cic.iris.command.MailCommand;
	uses br.unb.cic.iris.mail.IEmailClient;
	uses br.unb.cic.iris.model.EntityFactory;
	uses br.unb.cic.iris.persistence.DAOFactory;
}