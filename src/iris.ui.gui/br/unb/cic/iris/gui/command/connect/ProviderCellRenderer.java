package br.unb.cic.iris.gui.command.connect;

import java.awt.Component;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;

import br.unb.cic.iris.mail.EmailProvider;

public class ProviderCellRenderer extends DefaultListCellRenderer {

	@Override
	public Component getListCellRendererComponent(JList<? extends Object> list, Object value, int index,
			boolean isSelected, boolean cellHasFocus) {
		super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

		if (value instanceof EmailProvider) {
			EmailProvider provider = (EmailProvider) value;
			setText(provider.getName());
		}
		return this;
	}
	
}
