package br.unb.cic.iris.cli.command.internal;

import java.util.List;

import br.unb.cic.iris.cli.command.ConsoleAbstractMailCommand;
import br.unb.cic.iris.core.FolderManager;
import br.unb.cic.iris.exception.IrisException;
import br.unb.cic.iris.model.IrisFolder;

public class ConsoleListFoldersCommand extends ConsoleAbstractMailCommand {
	static final String COMMAND_NAME = "lf";

	@Override
	public String explain() {
		return String.format("(%s) - %s %n", COMMAND_NAME, message("command.list.folders.explain"));
	}

	@Override
	public void handleExecute() throws IrisException {
		List<IrisFolder> irisFolders = FolderManager.instance().listFolders();
		for (IrisFolder folder : irisFolders) {
			System.out.printf("%s - %s%n", folder.getId(), folder.getName());
		}
	}

	@Override
	public String getCommandName() {
		return COMMAND_NAME;
	}
}
