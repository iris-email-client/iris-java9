module iris.ui.gui {
	requires java.desktop;
	
	requires iris.core;		
	
	exports br.unb.cic.iris.gui.command;
	
	provides br.unb.cic.iris.command.MailCommand with br.unb.cic.iris.gui.command.GuiHelpCommand;
	provides br.unb.cic.iris.command.MailCommand with br.unb.cic.iris.gui.command.status.GuiStatusCommand;
	provides br.unb.cic.iris.command.MailCommand with br.unb.cic.iris.gui.command.connect.GuiConnectCommand;	
}
