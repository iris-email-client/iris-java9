module iris.ui.cli {
	requires iris.core;
	
	exports br.unb.cic.iris.cli.command;
		
	provides br.unb.cic.iris.command.MailCommand with br.unb.cic.iris.cli.command.internal.ConsoleChangeFolderCommand;	
	provides br.unb.cic.iris.command.MailCommand with br.unb.cic.iris.cli.command.internal.ConsoleConnectCommand;
	provides br.unb.cic.iris.command.MailCommand with br.unb.cic.iris.cli.command.internal.ConsoleCurrentFolderCommand;	
	provides br.unb.cic.iris.command.MailCommand with br.unb.cic.iris.cli.command.internal.ConsoleDownloadMessagesCommand;	
	provides br.unb.cic.iris.command.MailCommand with br.unb.cic.iris.cli.command.internal.ConsoleHelpCommand;
	provides br.unb.cic.iris.command.MailCommand with br.unb.cic.iris.cli.command.internal.ConsoleListFoldersCommand;
	provides br.unb.cic.iris.command.MailCommand with br.unb.cic.iris.cli.command.internal.ConsoleListMessagesCommand;	
	provides br.unb.cic.iris.command.MailCommand with br.unb.cic.iris.cli.command.internal.ConsoleListProvidersCommand;
	provides br.unb.cic.iris.command.MailCommand with br.unb.cic.iris.cli.command.internal.ConsoleListRemoteFoldersCommand;	
	provides br.unb.cic.iris.command.MailCommand with br.unb.cic.iris.cli.command.internal.ConsoleQuitCommand;
	provides br.unb.cic.iris.command.MailCommand with br.unb.cic.iris.cli.command.internal.ConsoleSendMessageCommand;
	provides br.unb.cic.iris.command.MailCommand with br.unb.cic.iris.cli.command.internal.ConsoleStatusCommand;	
}