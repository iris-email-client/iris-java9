module iris.ui.gui {
	requires java.desktop;
	
	requires iris.core;	
	//requires iris.mail.simple;
	
	provides br.unb.cic.iris.command.MailCommand with br.unb.cic.iris.gui.command.GuiHelpCommand;
	provides br.unb.cic.iris.command.MailCommand with br.unb.cic.iris.gui.command.GuiStatusCommand;
	//provides br.unb.cic.iris.command.MailCommand with br.unb.cic.iris.gui.command.GuiSendMesssageCommand;	
}
