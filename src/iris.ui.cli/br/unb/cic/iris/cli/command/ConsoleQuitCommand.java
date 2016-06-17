package br.unb.cic.iris.cli.command;

import br.unb.cic.iris.command.AbstractMailCommand;

/***
 * added by dConsole
 */
public class ConsoleQuitCommand extends AbstractMailCommand {
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