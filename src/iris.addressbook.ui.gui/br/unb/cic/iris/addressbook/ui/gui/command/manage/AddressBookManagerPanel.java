package br.unb.cic.iris.addressbook.ui.gui.command.manage;

import static java.awt.BorderLayout.CENTER;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import br.unb.cic.iris.addressbook.AddressBookManager;
import br.unb.cic.iris.addressbook.model.AddressBookEntry;
import br.unb.cic.iris.gui.GuiManager;
import br.unb.cic.iris.i18n.MessageBundle;
import br.unb.cic.iris.persistence.PersistenceException;

public class AddressBookManagerPanel extends JPanel {

	private AddressBookTableModel tableModel;

	public AddressBookManagerPanel() {
		initComponents();
		initData();
	}

	private void initData() {
		Runnable runnable = () -> {
			try {
				List<AddressBookEntry> list = AddressBookManager.instance().findAll();
				System.out.println("LIST="+list.size());
				tableModel.setAddressBookEntries(list);
			} catch (PersistenceException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
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
			String nick = textFieldNick.getText();
			String address = textFieldAddress.getText();
			//TODO validate fields
			try {
				AddressBookManager.instance().save(nick, address);
				textFieldNick.setText("");
				textFieldAddress.setText("");
				initData();
			} catch (PersistenceException ex) {
				// TODO Auto-generated catch block
				ex.printStackTrace();
				GuiManager.instance().showErrorMessage(String.format("%s: %s", MessageBundle.message("error"), ex.getMessage()));
			}			
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
				System.out.println("row=" + row);
				btnDelete.setEnabled(true);
			}
		});
		panelCenter.add(new JScrollPane(table), BorderLayout.CENTER);

		JPanel panelSouth = new JPanel();
		add(panelSouth, BorderLayout.SOUTH);

		btnDelete = new JButton("Delete");
		btnDelete.setEnabled(false);
		panelSouth.add(btnDelete);
	}

	private JTextField textFieldNick;
	private JTextField textFieldAddress;
	private JTable table;
	private JButton btnDelete;
	private JButton btnPersist;

}
