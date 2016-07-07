package br.unb.cic.iris.addressbook.ui.gui.command;

import java.awt.Component;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import br.unb.cic.iris.addressbook.ui.gui.command.manage.AddressBookManagerPanel;
import br.unb.cic.iris.exception.IrisException;
import br.unb.cic.iris.gui.GuiManager;
import br.unb.cic.iris.gui.command.AbstractGuiMailCommand;

/***
 * added by dAddressBookConsole
 */
public class AddressBookGuiCommand extends AbstractGuiMailCommand {
	public static final String COMMAND_NAME = "Address Book";

	@Override
	public String explain() {
		//return String.format("(%s) - %s %n", COMMAND_NAME, message("command.download.messages.explain"));
		return String.format("(%s) - %s %n", COMMAND_NAME, "Address book ...");
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
		ImageIcon icon = this.createImageIcon("/images/addressbook.png", getCommandName());	
		
		JButton btn = new JButton(icon);
		btn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent evt) {
				// Create a Popup menu
			    JPopupMenu popup = new JPopupMenu("Popup");
			    JMenuItem menuItemManage = new JMenuItem("Manage Address Book");
			    menuItemManage.addActionListener(al -> {
						AddressBookManagerPanel panel = new AddressBookManagerPanel();
						GuiManager.instance().setCenterPanel(panel);
						GuiManager.instance().setStatusText("Manage address book entries");					
				});			   
			    popup.add(menuItemManage);
			    popup.add(new JMenuItem("Send Email using adb"));
			    // show on the button?
			    Component source = (Component)evt.getSource();
			    popup.show(source, 0, source.getHeight());
			}
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