package br.unb.cic.iris.gui.command;

import java.awt.event.ActionEvent;

import javax.swing.JOptionPane;

import br.unb.cic.iris.command.MailCommand;

public class GuiHelpCommand extends GuiCommand {
	static final String COMMAND_HELP = "Help";

	@Override
	public String explain() {
		return String.format("(%s) - %s %n", COMMAND_HELP, message("command.help.explain"));
	}

	@Override
	public void handleExecute() {
		StringBuilder sb = new StringBuilder();
		for (MailCommand c : GuiCommandManager.singleton().listAll()) {
			sb.append(c.explain());
		}
		JOptionPane.showMessageDialog(null, sb.toString());
	}

	@Override
	public String getCommandName() {
		return COMMAND_HELP;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		execute();
	}

}
