package br.unb.cic.iris.gui.command;

import java.awt.event.ActionListener;

import br.unb.cic.iris.command.MailCommand;
import br.unb.cic.iris.gui.GuiManager;

public interface GuiMailCommand extends MailCommand, ActionListener {

	public void register(GuiManager manager);
	
}
