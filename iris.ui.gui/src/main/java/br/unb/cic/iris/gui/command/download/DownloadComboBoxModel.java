package br.unb.cic.iris.gui.command.download;

import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractListModel;
import javax.swing.ComboBoxModel;

import br.unb.cic.iris.model.IrisFolder;

public class DownloadComboBoxModel extends AbstractListModel<Object> implements ComboBoxModel<Object> {
	private static final long serialVersionUID = -7038576176965687257L;
	private List<IrisFolder> folders;
	private IrisFolder selectedFolder;
	private final static int FIRSTINDEX = 0;

	public DownloadComboBoxModel() {
		this.folders = new ArrayList<>();
	}

	public DownloadComboBoxModel(List<IrisFolder> folders) {
		this();
		this.folders.addAll(folders);
		if (getSize() > 0) {
			setSelectedItem(this.folders.get(FIRSTINDEX));
		}
	}

	@Override
	public Object getElementAt(int index) {
		return folders.get(index);
	}

	@Override
	public int getSize() {
		return folders.size();
	}

	@Override
	public Object getSelectedItem() {
		return selectedFolder;
	}

	@Override
	public void setSelectedItem(Object anItem) {
		selectedFolder = (IrisFolder) anItem;
	}

	public void addFolder(IrisFolder folder) {
		folders.add(folder);
		fireIntervalAdded(this, getSize() - 1, getSize() - 1);
		setSelectedItem(folders.get(getSize() - 1));
	}

	public void addFolders(List<IrisFolder> foldersToAdd) {
		int firstLine = getSize();
		folders.addAll(foldersToAdd);
		fireIntervalAdded(this, firstLine, firstLine + foldersToAdd.size());
		if (getSize() > 0) {
			setSelectedItem(this.folders.get(FIRSTINDEX));
		}
	}

	public void setFolders(List<IrisFolder> newFolders){
		this.folders = new ArrayList<>();
		addFolders(newFolders);
	}
	
	public void removeFolder() {
		folders.remove(getSelectedItem());
		fireIntervalRemoved(this, FIRSTINDEX, getSize() - 1);
		setSelectedItem(folders.get(FIRSTINDEX));
	}

	public void clear() {
		folders.clear();
		fireContentsChanged(this, FIRSTINDEX, getSize() - 1);
	}

}
