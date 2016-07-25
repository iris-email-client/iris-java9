module iris.search.ui.cli {
	requires iris.core;
	requires iris.search.api;
	requires iris.ui.cli;
	
	provides br.unb.cic.iris.command.MailCommand with br.unb.cic.iris.search.ui.cli.command.SearchConsoleCommand;
	
}