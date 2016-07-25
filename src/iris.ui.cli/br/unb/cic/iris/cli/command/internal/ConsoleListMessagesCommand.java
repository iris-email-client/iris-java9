package br.unb.cic.iris.cli.command.internal;

import java.util.List;

import br.unb.cic.iris.cli.command.AbstractListMessagesCommand;
import br.unb.cic.iris.core.FolderManager;
import br.unb.cic.iris.exception.IrisException;
import br.unb.cic.iris.model.EmailMessage;

public class ConsoleListMessagesCommand extends AbstractListMessagesCommand {
	static final String COMMAND_NAME = "ls";	
	List<EmailMessage> messages;

	@Override
	public String explain() {
		return String.format("(%s) - %s %n", COMMAND_NAME, message("command.list.messages.explain"));
	}

	@Override
	public void handleExecute() throws IrisException {
		messages = FolderManager.instance().listFolderMessages();
		print(messages);
	}

	@Override
	public String getCommandName() {
		return COMMAND_NAME;
	}
}
