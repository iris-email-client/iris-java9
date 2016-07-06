package br.unb.cic.iris.addressbook.ui.gui.command.manage;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import br.unb.cic.iris.addressbook.AddressBookManager;
import br.unb.cic.iris.addressbook.model.AddressBookEntry;
import br.unb.cic.iris.exception.IrisValidationException;
import br.unb.cic.iris.gui.GuiManager;
import br.unb.cic.iris.persistence.IrisPersistenceException;
import br.unb.cic.iris.util.EmailValidator;
import br.unb.cic.iris.util.StringUtil;

public class AddressBookManagerPanel extends JPanel {
	private static final long serialVersionUID = -858948301748853786L;
	private AddressBookTableModel tableModel;

	public AddressBookManagerPanel() {
		initComponents();
		initData();
	}

	private void initData() {
		Runnable runnable = () -> {
			textFieldNick.setText("");
			textFieldAddress.setText("");
			try {
				List<AddressBookEntry> list = AddressBookManager.instance().findAll();
				tableModel.setAddressBookEntries(list);
				if (list.isEmpty()) {
					btnDelete.setEnabled(false);
				}
			} catch (IrisPersistenceException e) {
				GuiManager.instance().showException(e);
			}
		};
		runnable.run();
	}

	private void initComponents() {
		setLayout(new BorderLayout(0, 0));

		JPanel panelNorth = new JPanel();
		add(panelNorth, BorderLayout.NORTH);
		panelNorth.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		JPanel panelNorthAux = new JPanel();
		panelNorthAux.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelNorth.add(panelNorthAux);
		GridBagLayout gbl_panelNorthAux = new GridBagLayout();
		gbl_panelNorthAux.columnWidths = new int[] { 0, 0, 0 };
		gbl_panelNorthAux.rowHeights = new int[] { 0, 0, 0, 0, 0 };
		gbl_panelNorthAux.columnWeights = new double[] { 1.0, 0.0, Double.MIN_VALUE };
		gbl_panelNorthAux.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		panelNorthAux.setLayout(gbl_panelNorthAux);

		JLabel labelNick = new JLabel("Nick");
		GridBagConstraints gbc_labelNick = new GridBagConstraints();
		gbc_labelNick.anchor = GridBagConstraints.EAST;
		gbc_labelNick.insets = new Insets(10, 10, 5, 5);
		gbc_labelNick.gridx = 0;
		gbc_labelNick.gridy = 0;
		panelNorthAux.add(labelNick, gbc_labelNick);

		textFieldNick = new JTextField();
		GridBagConstraints gbc_textFieldNick = new GridBagConstraints();
		gbc_textFieldNick.gridwidth = 2;
		gbc_textFieldNick.insets = new Insets(10, 0, 5, 10);
		gbc_textFieldNick.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldNick.gridx = 1;
		gbc_textFieldNick.gridy = 0;
		panelNorthAux.add(textFieldNick, gbc_textFieldNick);
		textFieldNick.setColumns(25);

		JLabel labelAddress = new JLabel("Address");
		GridBagConstraints gbc_labelAddress = new GridBagConstraints();
		gbc_labelAddress.anchor = GridBagConstraints.EAST;
		gbc_labelAddress.insets = new Insets(0, 10, 5, 5);
		gbc_labelAddress.gridx = 0;
		gbc_labelAddress.gridy = 1;
		panelNorthAux.add(labelAddress, gbc_labelAddress);

		textFieldAddress = new JTextField();
		GridBagConstraints gbc_textFieldAddress = new GridBagConstraints();
		gbc_textFieldAddress.insets = new Insets(0, 0, 5, 10);
		gbc_textFieldAddress.gridwidth = 2;
		gbc_textFieldAddress.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldAddress.gridx = 1;
		gbc_textFieldAddress.gridy = 1;
		panelNorthAux.add(textFieldAddress, gbc_textFieldAddress);
		textFieldAddress.setColumns(25);

		btnPersist = new JButton("     Persist     ");
		btnPersist.addActionListener(al -> {
			persistAddressBookEntry();
		});
		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.gridwidth = 2;
		gbc_btnNewButton.insets = new Insets(0, 0, 10, 5);
		gbc_btnNewButton.gridx = 1;
		gbc_btnNewButton.gridy = 2;
		panelNorthAux.add(btnPersist, gbc_btnNewButton);

		JPanel panelCenter = new JPanel();
		panelCenter.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		add(panelCenter, BorderLayout.CENTER);
		panelCenter.setLayout(new BorderLayout(0, 0));

		tableModel = new AddressBookTableModel();
		table = new JTable(tableModel);
		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int row = table.getSelectedRow();
				if (row >= 0) {
					btnDelete.setEnabled(true);
				}
			}
		});
		panelCenter.add(new JScrollPane(table), BorderLayout.CENTER);

		JPanel panelSouth = new JPanel();
		add(panelSouth, BorderLayout.SOUTH);

		btnDelete = new JButton("Delete");
		btnDelete.addActionListener(al -> {
			deleteAddressBookEntry();
		});
		btnDelete.setEnabled(false);
		panelSouth.add(btnDelete);
	}

	private void persistAddressBookEntry() {
		String nick = textFieldNick.getText();
		String address = textFieldAddress.getText();
		try {
			validateFields(nick, address);

			AddressBookManager.instance().save(nick, address);
			
			initData();
		} catch (IrisPersistenceException ex) {			
			GuiManager.instance().showException(ex);
		} catch (IrisValidationException ive) {
			GuiManager.instance().showException(ive);
		}
	}

	private void deleteAddressBookEntry() {
		int row = table.getSelectedRow();
		AddressBookEntry entry = tableModel.getAddressBookEntry(row);
		try {
			AddressBookManager.instance().delete(entry.getNick());			
			initData();
		} catch (IrisPersistenceException e) {
			GuiManager.instance().showException(e);
		}
	}
			

	private void validateFields(String nick, String address) throws IrisValidationException {
		String requiredField = "Field required: %s";
		List<String> errors = new LinkedList<>();
		if (StringUtil.isEmpty(nick)) {
			errors.add(String.format(requiredField, "nick"));
		}
		if (StringUtil.isEmpty(address)) {
			errors.add(String.format(requiredField, "address"));
		} else if (!EmailValidator.validate(address)) {
			errors.add("Invalid email: " + address);
		}
		
		if (!errors.isEmpty()) {
			throw new IrisValidationException(errors);
		}
	}

	private JTextField textFieldNick;
	private JTextField textFieldAddress;
	private JTable table;
	private JButton btnDelete;
	private JButton btnPersist;

}
