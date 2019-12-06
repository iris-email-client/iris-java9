module iris.ui.gui {
	requires java.desktop;
	
	requires iris.core;		
	
	exports br.unb.cic.iris.gui;
	exports br.unb.cic.iris.gui.command;
	
	provides br.unb.cic.iris.command.MailCommand with br.unb.cic.iris.gui.command.GuiHelpCommand	
					, br.unb.cic.iris.gui.command.connect.GuiConnectCommand
					, br.unb.cic.iris.gui.command.download.GuiDownloadMessagesCommand
					, br.unb.cic.iris.gui.command.list.GuiListMessagesCommand
					, br.unb.cic.iris.gui.command.send.GuiSendMessageCommand
					, br.unb.cic.iris.gui.command.status.GuiStatusCommand;
}
