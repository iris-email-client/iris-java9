package br.unb.cic.iris.gui.command.download;

import java.awt.event.ActionEvent;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import br.unb.cic.iris.core.SystemFacade;
import br.unb.cic.iris.exception.IrisException;
import br.unb.cic.iris.exception.IrisUncheckedException;
import br.unb.cic.iris.gui.GuiManager;
import br.unb.cic.iris.gui.command.AbstractGuiMailCommand;
import br.unb.cic.iris.model.IrisFolder;

public class GuiDownloadMessagesCommand extends AbstractGuiMailCommand {
	public static final String COMMAND_NAME = "Download";

	@Override
	public String explain() {
		return String.format("(%s) - %s %n", COMMAND_NAME, message("command.download.messages.explain"));
	}

	@Override
	public void handleExecute() throws IrisException {		
		DownloadPanel panel = new DownloadPanel();		
		GuiManager.instance().setCenterPanel(panel);
		GuiManager.instance().setStatusText("Downloading emails");
		
		Runnable runnable = () -> {
			try {
				List<IrisFolder> folders = SystemFacade.instance().listRemoteFolders();
				panel.setFolders(folders);
			} catch (IrisException e) {
				throw new IrisUncheckedException("Error while listing remote folders: "+e.getMessage(), e);
			}
		};
		runnable.run();
		
	}


	@Override
	public String getCommandName() {
		return COMMAND_NAME;
	}

	@Override
	public void register(GuiManager manager) {
		ImageIcon icon = createImageIcon("/images/download.png", getCommandName());

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
