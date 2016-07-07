package br.unb.cic.iris.gui.command.download;

import java.awt.Component;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;

import br.unb.cic.iris.model.IrisFolder;

public class IrisFolderCellRenderer extends DefaultListCellRenderer {
	private static final long serialVersionUID = 2469560191232933610L;

	@Override
	public Component getListCellRendererComponent(JList<? extends Object> list, Object value, int index,
			boolean isSelected, boolean cellHasFocus) {
		super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

		if (value instanceof IrisFolder) {
			IrisFolder folder = (IrisFolder) value;
			setText(folder.getName());
		}
		return this;
	}

}
