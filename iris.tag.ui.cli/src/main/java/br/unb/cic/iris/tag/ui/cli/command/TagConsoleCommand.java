package br.unb.cic.iris.tag.ui.cli.command;

import java.security.InvalidParameterException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import br.unb.cic.iris.cli.command.ConsoleAbstractMailCommand;
import br.unb.cic.iris.core.FolderManager;
import br.unb.cic.iris.exception.IrisException;
import br.unb.cic.iris.model.EmailMessage;
import br.unb.cic.iris.tag.TagManager;
import br.unb.cic.iris.tag.model.Tag;

public class TagConsoleCommand extends ConsoleAbstractMailCommand {
	public static final String COMMAND_NAME = "tag";
	DateFormat formatter = new SimpleDateFormat("dd/MMM/yy 'at' HH:mm");

	@Override
	public String explain() {
		StringBuilder sb = new StringBuilder();
		sb.append("(tag list) - show existing tags");
		sb.append("\n(tag list <tag>) - messages containing specified tag");
		sb.append("\n(tag add <messageID> <tags>) - add tag(s) to a message. EX: tag add 10 tag1, tag2 \n");
		return sb.toString();
	}

	@Override
	public String getCommandName() {
		return "tag";
	}

	@Override
	protected void handleExecute() throws IrisException {
		switch (parameters.get(0)) {
			case "list":
				list();
				break;
			case "add":
				add();
				break;
			default:
				throw new IrisException(parameters.get(0) + " is an invalid command");
		}
	}

	private void list() throws IrisException {
		if (parameters.size() == 2) {
			String tag = parameters.get(1);
			List<EmailMessage> messages = TagManager.instance().listMessagesByTag(tag);
			for (EmailMessage msg : messages) {
				System.out.printf("%s - %s - %s \t- %s%n", msg.getId(), formatter.format(msg.getDate()), msg.getFrom(),	msg.getSubject());
			}
		} else {
			listAll();
		}
	}

	private void add() throws IrisException {
		if (parameters.size() == 3) {
			int idx = Integer.parseInt(parameters.get(1));
			EmailMessage message = FolderManager.instance().getCurrentMessages().get(idx - 1);
			String tags = parameters.get(2);
			TagManager.instance().saveTags(message.getId(), tags);
		} else {
			throw new InvalidParameterException();
		}
	}

	private void listAll() throws IrisException {
		List<Tag> tags = TagManager.instance().findAll();
		System.out.println("-----------------------------------------------------");
		System.out.println("TAGS:");
		System.out.println("-----------------------------------------------------");
		for (Tag t : tags) {
			System.out.print(t.getName() + " ");
		}
		System.out.println("\n-----------------------------------------------------");
	}
}