package br.unb.cic.iris.cli.command.internal;

import br.unb.cic.iris.cli.command.ConsoleAbstractMailCommand;
import br.unb.cic.iris.core.FolderManager;
import br.unb.cic.iris.exception.EmailException;

public class ConsoleCurrentFolderCommand extends ConsoleAbstractMailCommand {
	static final String COMMAND_NAME = "pwd";

	@Override
	public String explain() {
		return String.format("(%s) - %s %n", COMMAND_NAME, message("command.current.folder.explain"));
	}

	@Override
	public void handleExecute() throws EmailException {
		String folderName = FolderManager.instance().getCurrentFolderName();
		System.out.printf("%s%n", folderName);
	}

	@Override
	public String getCommandName() {
		return COMMAND_NAME;
	}
}
