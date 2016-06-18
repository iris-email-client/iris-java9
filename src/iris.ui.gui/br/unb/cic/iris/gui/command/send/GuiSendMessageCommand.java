package br.unb.cic.iris.gui.command.send;

import java.awt.event.ActionEvent;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import br.unb.cic.iris.exception.EmailException;
import br.unb.cic.iris.gui.GuiManager;
import br.unb.cic.iris.gui.command.AbstractGuiMailCommand;

public class GuiSendMessageCommand extends AbstractGuiMailCommand {
	public static final String COMMAND_SEND = "Send";

	@Override
	public String explain() {
		return String.format("(%s) - %s %n", COMMAND_SEND, message("command.send.explain"));
	}

	@Override
	public void handleExecute() throws EmailException {
		SendPanel panel = new SendPanel();		
		GuiManager.instance().setCenterPanel(panel);
		GuiManager.instance().setStatusText("Send email");
	}


	@Override
	public String getCommandName() {
		return COMMAND_SEND;
	}

	@Override
	public void register(GuiManager manager) {
		ImageIcon icon = createImageIcon("/images/status.png", getCommandName());

		JButton btn = new JButton(icon);
		btn.addActionListener(this);
		btn.setToolTipText(getCommandName() + " - " + explain());

		manager.addToolbarComponent(btn);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		execute();
	}
	
	
	
	
	@Override
	public void setParameters(List<String> parameters) {
		// TODO Auto-generated method stub
		
	}
}
