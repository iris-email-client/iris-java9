module iris.core {
	requires public iris.mail;
	requires public iris.mail.provider;
	requires public iris.persistence;
	
	exports br.unb.cic.iris.command;
	exports br.unb.cic.iris.core;	
	exports br.unb.cic.iris.core.i18n;
	
	uses br.unb.cic.iris.command.MailCommand;
	uses br.unb.cic.iris.mail.IEmailClient;
	uses br.unb.cic.iris.model.EntityFactory;
	uses br.unb.cic.iris.persistence.DAOFactory;
}