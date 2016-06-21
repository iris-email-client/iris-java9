package br.unb.cic.iris.cli.command.internal;

import br.unb.cic.iris.cli.command.ConsoleAbstractMailCommand;
import br.unb.cic.iris.core.SystemFacade;
import br.unb.cic.iris.exception.EmailException;
import br.unb.cic.iris.model.IrisFolder;

public class ConsoleDownloadMessagesCommand extends ConsoleAbstractMailCommand {
	static final String COMMAND_NAME = "download";

	@Override
	public String explain() {
		return String.format("(%s) - %s %n", COMMAND_NAME, message("command.download.explain"));
	}

	@Override
	public void handleExecute() throws EmailException {
		String folder = IrisFolder.INBOX;
		if (validParameters()) {
			folder = parameters.get(0);
		}
		System.out.println("Downloading messages from/to folder: " + folder);
		SystemFacade.instance().downloadMessages(folder);
	}

	@Override
	public String getCommandName() {
		return COMMAND_NAME;
	}
	
}
