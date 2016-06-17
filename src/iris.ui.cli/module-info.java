module iris.ui.cli {
	//requires JavaMailAPI;
	//requires iris.base;
	requires iris.core;
	//requires iris.mail;
	//requires iris.mail.simple;
	//requires iris.mail.provider;
	
	provides br.unb.cic.iris.command.MailCommand with br.unb.cic.iris.cli.command.ConsoleConnectCommand;
	provides br.unb.cic.iris.command.MailCommand with br.unb.cic.iris.cli.command.ConsoleHelpCommand;
	provides br.unb.cic.iris.command.MailCommand with br.unb.cic.iris.cli.command.ConsoleListProvidersCommand;
	provides br.unb.cic.iris.command.MailCommand with br.unb.cic.iris.cli.command.ConsoleQuitCommand;
	provides br.unb.cic.iris.command.MailCommand with br.unb.cic.iris.cli.command.ConsoleSendMessageCommand;
	provides br.unb.cic.iris.command.MailCommand with br.unb.cic.iris.cli.command.ConsoleStatusCommand;
}