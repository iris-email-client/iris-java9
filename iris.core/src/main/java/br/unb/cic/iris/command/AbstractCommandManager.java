package br.unb.cic.iris.command;

import static br.unb.cic.iris.core.i18n.MessageBundle.message;

import java.util.Iterator;
import java.util.ServiceLoader;

import br.unb.cic.iris.exception.IrisException;
import br.unb.cic.iris.exception.IrisUncheckedException;

public abstract class AbstractCommandManager extends BaseCommandManager {

	public AbstractCommandManager() {
		try {
			loadCommands();
		} catch (IrisException e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}

	public void reload() throws Exception {
		loadCommands();
	}

	protected void loadCommands() throws IrisException {
		System.out.println(message("command.abstract.manager.loading"));
		ServiceLoader<MailCommand> sl = ServiceLoader.load(MailCommand.class);
		Iterator<MailCommand> it = sl.iterator();

		if (!it.hasNext())
			throw new IrisUncheckedException(message("command.abstract.manager.no.commands.found"));

		while (it.hasNext()) {
			MailCommand command = it.next();
			addCommand(command);
		}

		System.out.println(message("command.abstract.manager.total", listAll().size()));
	}

}
