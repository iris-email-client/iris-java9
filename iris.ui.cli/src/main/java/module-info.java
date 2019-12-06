module iris.ui.cli {
	requires iris.core;
	
	exports br.unb.cic.iris.cli.command;
		
	provides br.unb.cic.iris.command.MailCommand with br.unb.cic.iris.cli.command.internal.ConsoleChangeFolderCommand	
					, br.unb.cic.iris.cli.command.internal.ConsoleConnectCommand
					, br.unb.cic.iris.cli.command.internal.ConsoleCurrentFolderCommand
					, br.unb.cic.iris.cli.command.internal.ConsoleDownloadMessagesCommand
					, br.unb.cic.iris.cli.command.internal.ConsoleHelpCommand
					, br.unb.cic.iris.cli.command.internal.ConsoleListFoldersCommand
					, br.unb.cic.iris.cli.command.internal.ConsoleListMessagesCommand
					, br.unb.cic.iris.cli.command.internal.ConsoleListProvidersCommand
					, br.unb.cic.iris.cli.command.internal.ConsoleListRemoteFoldersCommand
					, br.unb.cic.iris.cli.command.internal.ConsoleQuitCommand
					, br.unb.cic.iris.cli.command.internal.ConsoleSendMessageCommand
					, br.unb.cic.iris.cli.command.internal.ConsoleStatusCommand;	
}