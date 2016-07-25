module iris.tag.ui.gui {
	requires iris.core;
	requires iris.tag.api;	
	requires iris.ui.gui;	
	
	provides br.unb.cic.iris.command.MailCommand with br.unb.cic.iris.tag.ui.gui.command.TagGuiCommand;
	
}