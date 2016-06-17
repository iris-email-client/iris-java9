package br.unb.cic.iris.gui.command;

import java.awt.event.ActionEvent;

import javax.swing.JOptionPane;

//import br.unb.cic.iris.core.FolderManager;
import br.unb.cic.iris.core.SystemFacade;
import br.unb.cic.iris.mail.EmailProvider;
import br.unb.cic.iris.model.Status;



public class GuiStatusCommand extends GuiCommand {
	static final String COMMAND_NAME = "Status";

	@Override
	public String explain() {
		return String.format("(%s) - %s %n", COMMAND_NAME, message("command.status.explain"));
	}

	@Override
	public void handleExecute() {
		StringBuilder sb = new StringBuilder();
		Status status = SystemFacade.instance().getStatus();
		sb.append("System status: " + status + "\n");
		//System.out.println("Current folder: " + FolderManager.instance().getCurrentFolderName());
		if (Status.CONNECTED == status) {
			EmailProvider provider = SystemFacade.instance().getProvider();
			sb.append("Provider: " + provider.getName() + "\n");
			sb.append("Username: " + provider.getUsername() + "\n");
		}
		JOptionPane.showMessageDialog(null, sb.toString());
	}

	@Override
	public String getCommandName() {
		return COMMAND_NAME;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		execute();
	}
}