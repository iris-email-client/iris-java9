package br.unb.cic.iris.cli.command.internal;

import br.unb.cic.iris.cli.command.ConsoleAbstractMailCommand;
import br.unb.cic.iris.cli.command.ConsoleCommandManager;

/***
 * added by dConsole
 */
public class ConsoleHelpCommand extends ConsoleAbstractMailCommand {
	static final String COMMAND_NAME = "help";

	@Override
	public String explain() {		
		return String.format("(%s) - %s %n", COMMAND_NAME, message("command.help.explain"));
	}

	@Override
	public void handleExecute() {				
		ConsoleCommandManager.instance().listAll().stream()
				//.filter(c -> c instanceof MailCommand)
				.sorted(COMPARE_BY_NAME).forEach(c -> System.out.print(c.explain()));
	}

	@Override
	public String getCommandName() {
		return COMMAND_NAME;
	}
}