package br.unb.cic.iris.cli.command;

import java.util.Scanner;

import br.unb.cic.iris.core.SystemFacade;
import br.unb.cic.iris.mail.EmailProvider;
import br.unb.cic.iris.mail.provider.ProviderManager;

/***
 * added by dConsole
 */
public class ConsoleConnectCommand extends ConsoleAbstractMailCommand {
	public static final String COMMAND_NAME = "connect";

	@Override
	public String explain() {
		return String.format("(%s) - %s %n", COMMAND_NAME, message("command.connect.explain"));
	}

	@Override
	public void handleExecute() {
		Scanner sc = new Scanner(System.in);
		String providerStr = read(sc, "command.connect.label.provider");
		EmailProvider provider = ProviderManager.instance().getProvider(providerStr);
		System.out.println("Prodiver recovered: " + provider.getName());
		String username = read(sc, "command.connect.label.username");
		String password = read(sc, "command.connect.label.password");
		provider.setUsername(username);
		provider.setPassword(password);
		SystemFacade.instance().defineEmailProvider(provider);
	}

	private static String read(Scanner sc, String question) {
		askQuestion(question);
		return sc.nextLine();
	}

	private static void askQuestion(String message) {
		System.out.printf("%s: ", message(message));
	}

	@Override
	public String getCommandName() {
		return COMMAND_NAME;
	}
}