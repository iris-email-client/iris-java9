package br.unb.cic.iris.gui.command;

import java.awt.event.ActionEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import br.unb.cic.iris.gui.GuiManager;

public class GuiHelpCommand extends AbstractGuiMailCommand {
	static final String COMMAND_HELP = "Help";

	@Override
	public String explain() {
		return String.format("(%s) - %s %n", COMMAND_HELP, message("command.help.explain"));
	}

	@Override
	public void handleExecute() {
		StringBuilder sb = new StringBuilder();
				
		GuiCommandManager.instance().listAll().stream()
				.sorted(COMPARE_BY_NAME)
				.forEach(c -> sb.append(c.explain()));
		
		showInfoMessage(sb.toString());		
	}

	@Override
	public String getCommandName() {
		return COMMAND_HELP;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		execute();
	}

	@Override
	public void register(GuiManager manager) {
		ImageIcon icon = createImageIcon("/images/help.png", getCommandName());

		JButton btn = new JButton(icon);
		btn.addActionListener(this);
		btn.setToolTipText(getCommandName() + " - " + explain());

		manager.addToolbarComponent(btn);
	}

}
