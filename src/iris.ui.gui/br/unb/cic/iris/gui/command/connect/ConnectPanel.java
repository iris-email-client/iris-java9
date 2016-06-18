package br.unb.cic.iris.gui.command.connect;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import br.unb.cic.iris.core.SystemFacade;
import br.unb.cic.iris.gui.GuiManager;
import br.unb.cic.iris.mail.EmailProvider;

public class ConnectPanel extends JPanel {
	private JComboBox<Object> comboBox;
	private JTextField usernameField;
	private JPasswordField passwordField;
	private JButton btnDefineProvider;

	public ConnectPanel(List<EmailProvider> providers) {
		initComponents(providers);
	}

	private void initComponents(List<EmailProvider> providers) {
		JPanel centerPanel = new JPanel();
		centerPanel.setLayout(new GridLayout(3, 2));
		centerPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Select Provider"));
		
		centerPanel.add(new JLabel("Provider"));
		
		comboBox = new JComboBox<>();
		comboBox.setModel(new ConnectComboBoxModel(providers));
		comboBox.setRenderer(new ProviderCellRenderer());
		centerPanel.add(comboBox);
				
		centerPanel.add(new JLabel("User"));
		usernameField = new JTextField(15);
		centerPanel.add(usernameField);
		
		centerPanel.add(new JLabel("Password"));
		passwordField = new JPasswordField(15);
		centerPanel.add(passwordField);
				
		JPanel southPanel = new JPanel();
		btnDefineProvider = new JButton("Define Provider");
		btnDefineProvider.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {				
				EmailProvider provider = (EmailProvider) comboBox.getModel().getSelectedItem();		
				provider.setUsername(usernameField.getText().trim());
				provider.setPassword(new String(passwordField.getPassword()).trim());
				SystemFacade.instance().connect(provider);	
				GuiManager.instance().setStatusText("Provider defined: "+provider.getName());
			}
		});
		southPanel.add(btnDefineProvider);
				
		JPanel contentPanel = new JPanel();
		contentPanel.setLayout(new BorderLayout());			
		contentPanel.add(centerPanel, BorderLayout.NORTH);
		contentPanel.add(southPanel, BorderLayout.SOUTH);
		
		add(contentPanel);
	}
	
	

}
