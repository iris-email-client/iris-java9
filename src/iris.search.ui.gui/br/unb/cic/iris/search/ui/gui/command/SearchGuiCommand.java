package br.unb.cic.iris.search.ui.gui.command;

import java.awt.Image;
import java.awt.event.ActionEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import br.unb.cic.iris.exception.IrisException;
import br.unb.cic.iris.gui.GuiManager;
import br.unb.cic.iris.gui.command.AbstractGuiMailCommand;
import br.unb.cic.iris.search.ui.gui.i18n.MessageBundle;


public class SearchGuiCommand extends AbstractGuiMailCommand {
	public static final String COMMAND_NAME = "Search";

	@Override
	public String explain() {
		return String.format("(%s) - %s %n", COMMAND_NAME, MessageBundle.message("command.search.explain"));
	}

	@Override
	public void handleExecute() throws IrisException {
		showInfoMessage("Not yet implemented");
	}


	@Override
	public String getCommandName() {
		return COMMAND_NAME;
	}

	@Override
	public void register(GuiManager manager) {
		ImageIcon icon = this.createImageIcon("/images/search.png", getCommandName());		
		JButton btn = new JButton(icon);	
		btn.addActionListener(al -> {
			execute();
		});
		btn.setToolTipText(getCommandName() + " - " + explain());
		manager.addToolbarComponent(btn);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		execute();
	}
	
	protected ImageIcon createImageIcon(String path, String description) {
		java.net.URL imgURL = getClass().getResource(path);
		if (imgURL != null) {
			Image img = new ImageIcon(imgURL, description).getImage();
			ImageIcon icon = new ImageIcon(img.getScaledInstance(32, 32, java.awt.Image.SCALE_SMOOTH));
			return icon;
		}
		System.err.println("Couldn't find file: " + path);
		return null;
	}
}