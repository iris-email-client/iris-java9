package br.unb.cic.iris.cli.command.internal;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import br.unb.cic.iris.cli.command.ConsoleAbstractMailCommand;
import br.unb.cic.iris.core.FolderManager;
import br.unb.cic.iris.exception.IrisException;
import br.unb.cic.iris.model.EmailMessage;

public class ConsoleListMessagesCommand extends ConsoleAbstractMailCommand {
	static final String COMMAND_NAME = "ls";
	DateFormat formatter = new SimpleDateFormat("dd/MMM/yy 'at' HH:mm");
	List<EmailMessage> messages;

	@Override
	public String explain() {
		return String.format("(%s) - %s %n", COMMAND_NAME, message("command.list.messages.explain"));
	}

	@Override
	public void handleExecute() throws IrisException {
		messages = FolderManager.instance().listFolderMessages();
		for (int i = 0; i < messages.size(); i++) {
			EmailMessage msg = messages.get(i);
			System.out.printf("%d - %s - %s \t- %s%n", i + 1, formatter.format(msg.getDate()), msg.getFrom(), msg.getSubject());
		}
	}

	@Override
	public String getCommandName() {
		return COMMAND_NAME;
	}
}
