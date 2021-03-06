package br.unb.cic.iris.gui.command.connect;

import java.awt.event.ActionEvent;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import br.unb.cic.iris.core.SystemFacade;
import br.unb.cic.iris.gui.GuiManager;
import br.unb.cic.iris.gui.command.AbstractGuiMailCommand;
import br.unb.cic.iris.mail.EmailProvider;
import br.unb.cic.iris.mail.provider.ProviderManager;
import br.unb.cic.iris.model.Status;

public class GuiConnectCommand extends AbstractGuiMailCommand {
	public static final String COMMAND_NAME = "Connect";

	@Override
	public String explain() {
		return String.format("(%s) - %s %n", COMMAND_NAME, message("command.connect.explain"));
	}

	@Override
	public void handleExecute() {
		List<EmailProvider> providers = ProviderManager.instance().getProviders();
		ConnectPanel panel = new ConnectPanel(providers);
		GuiManager.instance().setCenterPanel(panel);
		GuiManager.instance().setStatusText(message("command.connect.status"));
		
		if(SystemFacade.instance().getStatus() == Status.CONNECTED){
			EmailProvider provider = SystemFacade.instance().getProvider();			
			GuiManager.instance().appendStatusText(message("command.connect.already.connected", provider.getName(), provider.getUsername()));
		}
	}

	@Override
	public String getCommandName() {
		return COMMAND_NAME;
	}

	@Override
	public void register(GuiManager manager) {
		ImageIcon icon = createImageIcon("/images/connect.png", getCommandName());

		JButton btn = new JButton(icon);
		btn.addActionListener(this);
		btn.setToolTipText(getCommandName() + " - " + explain());

		manager.addToolbarComponent(btn);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		execute();
	}
	
}
