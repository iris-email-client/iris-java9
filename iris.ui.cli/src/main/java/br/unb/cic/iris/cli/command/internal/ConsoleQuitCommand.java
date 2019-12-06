package br.unb.cic.iris.cli.command.internal;

import br.unb.cic.iris.cli.command.ConsoleAbstractMailCommand;

/***
 * added by dConsole
 */
public class ConsoleQuitCommand extends ConsoleAbstractMailCommand {
	public static final String COMMAND_QUIT = "quit";

	@Override
	public String explain() {
		return String.format("(%s) - %s %n", COMMAND_QUIT, message("command.quit.explain"));
	}

	@Override
	public void handleExecute() {
		System.out.println(message("quit"));
		System.exit(0);
	}

	@Override
	public String getCommandName() {
		return COMMAND_QUIT;
	}
}