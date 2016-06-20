package br.unb.cic.iris.cli.command.internal;

import br.unb.cic.iris.cli.command.ConsoleAbstractMailCommand;
//import br.unb.cic.iris.core.FolderManager;
import br.unb.cic.iris.core.SystemFacade;
import br.unb.cic.iris.mail.EmailProvider;
import br.unb.cic.iris.model.Status;


/***
 * added by dConsole
 */
public class ConsoleStatusCommand extends ConsoleAbstractMailCommand {
	static final String COMMAND_NAME = "status";

	@Override
	public String explain() {
		return String.format("(%s) - %s %n", COMMAND_NAME, message("command.status.explain"));
	}

	@Override
	public void handleExecute() {
		Status status = SystemFacade.instance().getStatus();
		System.out.println("System status: " + status);
		//System.out.println("Current folder: " + FolderManager.instance().getCurrentFolderName());
		if (Status.CONNECTED == status) {
			EmailProvider provider = SystemFacade.instance().getProvider();
			System.out.println(" - Provider: " + provider.getName());
			System.out.println(" - Username: " + provider.getUsername());
		}
	}

	@Override
	public String getCommandName() {
		return COMMAND_NAME;
	}
}