package br.unb.cic.iris.gui.command;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import br.unb.cic.iris.command.AbstractCommandManager;
import br.unb.cic.iris.command.MailCommand;

public class GuiCommandManager extends AbstractCommandManager {
	private static GuiCommandManager singleton = new GuiCommandManager();

	private GuiCommandManager() {
	}

	public static GuiCommandManager singleton() {
		return singleton;
	}

	@Override
	protected void handleAddCommand(MailCommand command) {	
		// TODO Auto-generated method stub

	}

	public List<GuiCommand> listGuiCommands() {
		Comparator<MailCommand> byName = (MailCommand c1, MailCommand c2) -> c1.getCommandName().compareTo(c2.getCommandName());
		return listAll().stream()
				.filter(c -> c instanceof GuiCommand)
				.sorted(byName)
				.map(c -> (GuiCommand) c)
				.collect(Collectors.toList());
	}

}
