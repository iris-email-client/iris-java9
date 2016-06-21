package br.unb.cic.iris.cli.command.internal;

import java.util.List;

import br.unb.cic.iris.cli.command.ConsoleAbstractMailCommand;
import br.unb.cic.iris.core.SystemFacade;
import br.unb.cic.iris.exception.EmailException;
import br.unb.cic.iris.model.IrisFolder;

public class ConsoleListRemoteFoldersCommand extends ConsoleAbstractMailCommand {
	static final String COMMAND_NAME = "lr";

	@Override
	public String explain() {
		return String.format("(%s) - %s %n", COMMAND_NAME, message("command.list.remote.folders.explain"));
	}

	@Override
	public void handleExecute() throws EmailException {
		List<IrisFolder> irisFolders = SystemFacade.instance().listRemoteFolders();
		for (IrisFolder folder : irisFolders) {
			System.out.printf(" + %s%n", folder.getName());
		}
	}

	@Override
	public String getCommandName() {
		return COMMAND_NAME;
	}
}
