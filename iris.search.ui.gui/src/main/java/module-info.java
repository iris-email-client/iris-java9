module iris.search.ui.gui {
	requires java.desktop;
	
	requires iris.core;
	requires iris.search.api;	
	requires iris.ui.gui;	
	
	provides br.unb.cic.iris.command.MailCommand with br.unb.cic.iris.search.ui.gui.command.SearchGuiCommand;
	
}