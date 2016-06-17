package br.unb.cic.iris.cli.command;

import java.util.Comparator;

import br.unb.cic.iris.command.AbstractMailCommand;
import br.unb.cic.iris.command.MailCommand;

/***
 * added by dConsole
 */
public class ConsoleHelpCommand extends AbstractMailCommand {
	static final String COMMAND_HELP = "help";

	@Override
	public String explain() {		
		return String.format("(%s) - %s %n", COMMAND_HELP, message("command.help.explain"));
	}

	@Override
	public void handleExecute() {
		/*for (MailCommand c : ConsoleCommandManager.singleton().listAll()) {
			System.out.print(c.explain());
		}*/
		
		
		Comparator<MailCommand> byName = (MailCommand c1, MailCommand c2) -> c1.getCommandName().compareTo(c2.getCommandName());
		ConsoleCommandManager.singleton().listAll().stream()
				//.filter(c -> c instanceof MailCommand)
				.sorted(byName).forEach(c -> System.out.print(c.explain()));
				
	}

	@Override
	public String getCommandName() {
		return COMMAND_HELP;
	}
}