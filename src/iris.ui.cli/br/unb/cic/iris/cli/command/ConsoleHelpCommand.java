package br.unb.cic.iris.cli.command;

/***
 * added by dConsole
 */
public class ConsoleHelpCommand extends ConsoleAbstractMailCommand {
	static final String COMMAND_HELP = "help";

	@Override
	public String explain() {		
		return String.format("(%s) - %s %n", COMMAND_HELP, message("command.help.explain"));
	}

	@Override
	public void handleExecute() {				
		ConsoleCommandManager.singleton().listAll().stream()
				//.filter(c -> c instanceof MailCommand)
				.sorted(COMPARE_BY_NAME).forEach(c -> System.out.print(c.explain()));
	}

	@Override
	public String getCommandName() {
		return COMMAND_HELP;
	}
}