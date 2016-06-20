package br.unb.cic.iris.gui.command.status;

import java.awt.event.ActionEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;

//import br.unb.cic.iris.core.FolderManager;
import br.unb.cic.iris.core.SystemFacade;
import br.unb.cic.iris.gui.GuiManager;
import br.unb.cic.iris.gui.command.AbstractGuiMailCommand;
import br.unb.cic.iris.mail.EmailProvider;
import br.unb.cic.iris.model.Status;



public class GuiStatusCommand extends AbstractGuiMailCommand {
	static final String COMMAND_NAME = "Status";

	@Override
	public String explain() {
		return String.format("(%s) - %s %n", COMMAND_NAME, message("command.status.explain"));
	}

	@Override
	public void handleExecute() {		
		StatusPanel panel = new StatusPanel();
		panel.update(createStatusText());
		GuiManager.instance().setCenterPanel(panel);
		GuiManager.instance().setStatusText("Executing: " + getCommandName());
	}

	private String createStatusText(){
		StringBuilder sb = new StringBuilder();
		Status status = SystemFacade.instance().getStatus();
		sb.append("System status: " + status + "\n");
		//TODO sb.append("Current folder: " + FolderManager.instance().getCurrentFolderName());
		if (Status.CONNECTED == status) {
			EmailProvider provider = SystemFacade.instance().getProvider();
			sb.append("Provider: " + provider.getName() + "\n");
			sb.append("Username: " + provider.getUsername() + "\n");
		}
		return sb.toString();
	}
	
	@Override
	public String getCommandName() {
		return COMMAND_NAME;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		execute();
	}

	@Override
	public void register(GuiManager manager) {		
		ImageIcon icon = createImageIcon("/images/status.png", getCommandName());

		JButton btn = new JButton(icon);
		btn.addActionListener(this);
		btn.setToolTipText(getCommandName() + " - " + explain());

		manager.addToolbarComponent(btn);
	}

}