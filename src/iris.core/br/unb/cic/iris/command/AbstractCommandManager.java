package br.unb.cic.iris.command;

import java.util.Iterator;
import java.util.ServiceLoader;

import br.unb.cic.iris.exception.EmailException;
import br.unb.cic.iris.exception.EmailUncheckedException;

public abstract class AbstractCommandManager extends BaseCommandManager {

	// disponibilizar esse metodo para os deltas se registrarem,
	// e outros (possiveis) "plugins" (via merge de codigo)
	// e acesso atraves do singleton
	// protected abstract void initialize();

	public AbstractCommandManager() {
		try {
			loadCommands();
		} catch (EmailException e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}

	public void reload() throws Exception {
		loadCommands();
	}

	protected void loadCommands() throws EmailException {
		System.out.println("Loading commands ...");
		ServiceLoader<MailCommand> sl = ServiceLoader.load(MailCommand.class);
		Iterator<MailCommand> it = sl.iterator();

		if (!it.hasNext())
			throw new EmailUncheckedException("No mail commands found!");

		while (it.hasNext()) {
			MailCommand command = it.next();
			addCommand(command);			
		}

		System.out.println("Total commands found: " + listAll().size());
	}

}
