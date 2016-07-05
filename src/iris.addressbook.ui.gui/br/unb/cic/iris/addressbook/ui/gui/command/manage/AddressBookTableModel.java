package br.unb.cic.iris.addressbook.ui.gui.command.manage;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import br.unb.cic.iris.addressbook.model.AddressBookEntry;

public class AddressBookTableModel extends AbstractTableModel {
	private List<AddressBookEntry> entries;

	public AddressBookTableModel() {
		entries = new ArrayList();
	}

	@Override
	public int getColumnCount() {
		return 2;
	}

	@Override
	public int getRowCount() {
		return entries.size();
	}

	@Override
	public Object getValueAt(int line, int col) {
		AddressBookEntry entry = entries.get(line);
		switch (col) {
		case 0:
			return entry.getNick();

		case 1:
			return entry.getAddress();
		}
		return "";
	}

	@Override
	public String getColumnName(int col) {
		switch (col) {
		case 0:
			return "NICK";

		case 1:
			return "ADDRESS";
		}
		return "";
	}

	public void setAddressBookEntries(List<AddressBookEntry> list) {
		this.entries = list;
		fireTableDataChanged();
	}

}
