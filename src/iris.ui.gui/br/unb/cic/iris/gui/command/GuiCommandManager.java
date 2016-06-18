package br.unb.cic.iris.gui.command;

import br.unb.cic.iris.command.AbstractCommandManager;
import br.unb.cic.iris.command.MailCommand;
import br.unb.cic.iris.gui.GuiManager;

public class GuiCommandManager extends AbstractCommandManager {
	private static GuiCommandManager singleton = new GuiCommandManager();

	private GuiCommandManager() {
	}

	public static GuiCommandManager singleton() {
		return singleton;
	}

	@Override
	protected void handleAddCommand(MailCommand command) {			
		if(command instanceof GuiMailCommand){
			GuiMailCommand guiCmd = (GuiMailCommand) command;
			guiCmd.register(GuiManager.instance());
		}
		
	}

}
