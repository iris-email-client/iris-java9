package br.unb.cic.iris.cli.command.internal;

import br.unb.cic.iris.cli.command.ConsoleAbstractMailCommand;
import br.unb.cic.iris.exception.IrisException;
import br.unb.cic.iris.mail.EmailProvider;
import br.unb.cic.iris.mail.provider.ProviderManager;

public class ConsoleListProvidersCommand extends ConsoleAbstractMailCommand {
	static final String COMMAND_NAME = "lp";

	@Override
	public String explain() {
		return String.format("(%s) - %s %n", COMMAND_NAME,	message("command.list.providers.explain"));
	}

	@Override
	protected void handleExecute() throws IrisException {
		for (EmailProvider provider : ProviderManager.instance().getProviders()) {
			System.out.printf("%s - %s%n", provider.getName(), provider.getDescription());
		}
	}
	
	@Override
	public String getCommandName() {
		return COMMAND_NAME;
	}

}
