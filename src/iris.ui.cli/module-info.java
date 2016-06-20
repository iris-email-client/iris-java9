module iris.ui.cli {
	//requires JavaMailAPI;
	//requires iris.base;
	requires iris.core;
	//requires iris.mail;
	//requires iris.mail.simple;
	//requires iris.mail.provider;
	
	exports br.unb.cic.iris.cli.command;
	
	provides br.unb.cic.iris.command.MailCommand with br.unb.cic.iris.cli.command.internal.ConsoleConnectCommand;
	provides br.unb.cic.iris.command.MailCommand with br.unb.cic.iris.cli.command.internal.ConsoleHelpCommand;
	provides br.unb.cic.iris.command.MailCommand with br.unb.cic.iris.cli.command.internal.ConsoleListProvidersCommand;
	provides br.unb.cic.iris.command.MailCommand with br.unb.cic.iris.cli.command.internal.ConsoleQuitCommand;
	provides br.unb.cic.iris.command.MailCommand with br.unb.cic.iris.cli.command.internal.ConsoleSendMessageCommand;
	provides br.unb.cic.iris.command.MailCommand with br.unb.cic.iris.cli.command.internal.ConsoleStatusCommand;
}