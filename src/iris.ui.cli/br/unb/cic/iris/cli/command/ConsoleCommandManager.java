package br.unb.cic.iris.cli.command;

import static br.unb.cic.iris.core.i18n.MessageBundle.message;

import java.util.ArrayList;
import java.util.List;

import br.unb.cic.iris.command.AbstractCommandManager;
import br.unb.cic.iris.command.MailCommand;
import br.unb.cic.iris.core.i18n.MessageBundle;
import br.unb.cic.iris.exception.IrisException;
import br.unb.cic.iris.exception.IrisValidationException;
import br.unb.cic.iris.exception.IrisUncheckedException;
import br.unb.cic.iris.util.StringUtil;

/***
 * added by dConsole
 */
public class ConsoleCommandManager extends AbstractCommandManager {
	private static ConsoleCommandManager instance = new ConsoleCommandManager();

	private ConsoleCommandManager() {
	}

	public static ConsoleCommandManager instance() {
		return instance;
	}

	public static void run(String cmd) {
		ConsoleCommandManager.instance().runCommand(cmd);
	}

	public void runCommand(String cmd) {
		if (StringUtil.notEmpty(cmd)) {
			try {
				MailCommand command = createCommand(cmd.trim());
				command.execute();
			} catch (IrisUncheckedException eux) {
				System.err.printf("%s: %s\n", message("error"), eux.getLocalizedMessage());
			} catch (IrisValidationException emvx) {
				System.err.println(MessageBundle.message("error.validation"));
				for (String msg : emvx.getMessages()) {
					System.err.println(" - " + msg);
				}
			} catch (IrisException ex) {
				System.err.printf("%s: %s\n", message("error"), ex.getMessage());
			} catch (NullPointerException npe) {
				System.err.println(message("error.command.not.found", cmd));
			} catch (Exception e) {
				e.printStackTrace();
			} catch (Throwable t) {
				t.printStackTrace();
			}
		}
	}

	private MailCommand createCommand(String cmd) throws IrisException {
		String[] split = cmd.split(" ");
		MailCommand command = null;
		if (split.length > 1) {
			command = getCommand(split[0].trim());
			((ConsoleMailCommand)command).setParameters(getParameters(split));
		} else {
			command = getCommand(cmd);
		}
		return command;
	}

	private static List<String> getParameters(String[] split) {
		List<String> parameters = new ArrayList<String>();
		for (int i = 1; i < split.length; i++) {
			parameters.add(split[i].trim());
		}
		return parameters;
	}

	@Override
	protected void handleAddCommand(MailCommand command) {
	}
}