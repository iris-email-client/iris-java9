package br.unb.cic.iris.cli.command;

import br.unb.cic.iris.command.AbstractMailCommand;
import br.unb.cic.iris.exception.EmailException;
import br.unb.cic.iris.mail.EmailProvider;
import br.unb.cic.iris.mail.provider.ProviderManager;

public class ConsoleListProvidersCommand extends AbstractMailCommand {
	static final String COMMAND_NAME = "lp";

	@Override
	public String explain() {
		return String.format("(%s) - %s %n", COMMAND_NAME,	message("command.list.providers.explain"));
	}

	@Override
	protected void handleExecute() throws EmailException {
		for (EmailProvider provider : ProviderManager.instance().getProviders()) {
			System.out.printf("%s - %s%n", provider.getName(), provider.getDescription());
		}
	}
	
	@Override
	public String getCommandName() {
		return COMMAND_NAME;
	}

}
