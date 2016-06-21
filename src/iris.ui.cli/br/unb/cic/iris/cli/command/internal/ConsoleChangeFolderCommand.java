package br.unb.cic.iris.cli.command.internal;

import br.unb.cic.iris.cli.command.ConsoleAbstractMailCommand;
import br.unb.cic.iris.core.FolderManager;
import br.unb.cic.iris.exception.EmailException;

public class ConsoleChangeFolderCommand extends ConsoleAbstractMailCommand {
	static final String COMMAND_NAME = "cd";

	@Override
	public String explain() {
		return String.format("(cd <folder_name>) - change current folder)");
	}

	@Override
	public void handleExecute() throws EmailException {
		if (validParameters()) {
			FolderManager.instance().changeToFolder(parameters.get(0));
			String folder = FolderManager.instance().getCurrentFolderName();
			System.out.println(folder);
		}
	}

	@Override
	public String getCommandName() {
		return COMMAND_NAME;
	}
}
