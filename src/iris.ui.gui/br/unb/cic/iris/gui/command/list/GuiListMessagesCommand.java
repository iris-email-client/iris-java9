package br.unb.cic.iris.gui.command.list;

import java.awt.event.ActionEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import br.unb.cic.iris.exception.IrisException;
import br.unb.cic.iris.gui.GuiManager;
import br.unb.cic.iris.gui.command.AbstractGuiMailCommand;

public class GuiListMessagesCommand extends AbstractGuiMailCommand {
	public static final String COMMAND_NAME = "List";

	@Override
	public String explain() {
		return String.format("(%s) - %s %n", COMMAND_NAME, message("command.list.messages.explain"));
	}

	@Override
	public void handleExecute() throws IrisException {
		ListMessagesPanel panel = new ListMessagesPanel();		
		GuiManager.instance().setCenterPanel(panel);
		GuiManager.instance().setStatusText("List local/downloaded emails");
	}


	@Override
	public String getCommandName() {
		return COMMAND_NAME;
	}

	@Override
	public void register(GuiManager manager) {
		ImageIcon icon = createImageIcon("/images/list.png", getCommandName());

		JButton btn = new JButton(icon);
		//btn.setVerticalTextPosition(SwingConstants.BOTTOM);
	    //btn.setHorizontalTextPosition(SwingConstants.CENTER);
	    //btn.setText(getCommandName());
		btn.addActionListener(this);
		btn.setToolTipText(getCommandName() + " - " + explain());

		manager.addToolbarComponent(btn);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		execute();
	}
	
	
}
