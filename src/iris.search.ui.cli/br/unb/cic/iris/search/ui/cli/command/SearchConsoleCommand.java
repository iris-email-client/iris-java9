package br.unb.cic.iris.search.ui.cli.command;

import java.util.List;

import br.unb.cic.iris.cli.command.AbstractListMessagesCommand;
import br.unb.cic.iris.exception.IrisException;
import br.unb.cic.iris.model.EmailMessage;
import br.unb.cic.iris.search.SearchManager;
import br.unb.cic.iris.search.ui.cli.i18n.MessageBundle;


public class SearchConsoleCommand extends AbstractListMessagesCommand {
	public static final String COMMAND_SEARCH = "search";	

	@Override
	public String explain() {
		return String.format("(%s <query>) - %s %n", COMMAND_SEARCH, MessageBundle.message("command.search.explain"));
	}

	@Override
	public String getCommandName() {
		return COMMAND_SEARCH;
	}

	@Override
	protected void handleExecute() throws IrisException {
		List<EmailMessage> messages = SearchManager.instance().search("");
		System.out.println("Not yet implemented!!!");
		
		/*if (validParameters()) {
			String query = "";
			for (String parameter : parameters) {
				if (query.length() > 0)
					query += " ";
				query += parameter;
			}
			List<EmailMessage> messages = SearchManager.instance().search(query);
			print(messages);
		}*/
	}

}