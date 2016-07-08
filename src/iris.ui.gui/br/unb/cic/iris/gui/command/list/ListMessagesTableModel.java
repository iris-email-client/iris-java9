package br.unb.cic.iris.gui.command.list;

import java.util.LinkedList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import br.unb.cic.iris.model.EmailMessage;

public class ListMessagesTableModel extends AbstractTableModel {
	private static final String[] COLUMN_NAMES = { "FROM", "SUBJECT", "DATE" };
	private List<EmailMessage> messages;

	public ListMessagesTableModel() {
		messages = new LinkedList<>();
	}

	public String getColumnName(int col) {
		return COLUMN_NAMES[col].toString();
	}

	@Override
	public int getColumnCount() {
		return COLUMN_NAMES.length;
	}

	@Override
	public int getRowCount() {
		return messages.size();
	}

	@Override
	public Object getValueAt(int row, int col) {
		EmailMessage message = messages.get(row);

		switch (col) {
			case 0:
				return ListMessagesUtil.parseEmail(message.getFrom());
			case 1:
				return message.getSubject();
			case 2:				
				return ListMessagesUtil.parseDateSimple(message.getDate());
			default:
				return "";
		}
	}

	public void setMessages(List<EmailMessage> newMessages) {
		System.out.println("messages=" + newMessages.size());
		this.messages = newMessages;
		fireTableDataChanged();
	}

	public EmailMessage getMessage(int idx) {
		return messages.get(idx);
	}

}
