module iris.search.ui.gui {
	requires iris.core;
	requires iris.tag.api;	
	requires iris.ui.gui;	
	
	provides br.unb.cic.iris.command.MailCommand with br.unb.cic.iris.search.ui.gui.command.SearchGuiCommand;
	
}