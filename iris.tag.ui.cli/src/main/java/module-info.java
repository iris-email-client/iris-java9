module iris.tag.ui.cli {
	requires iris.core;
	requires iris.tag.api;
	requires iris.ui.cli;
	
	provides br.unb.cic.iris.command.MailCommand with br.unb.cic.iris.tag.ui.cli.command.TagConsoleCommand;
	
}