package br.unb.cic.iris.gui.command.connect;

import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractListModel;
import javax.swing.ComboBoxModel;

import br.unb.cic.iris.mail.EmailProvider;

public class ConnectComboBoxModel extends AbstractListModel<Object>implements ComboBoxModel<Object> {
	private List<EmailProvider> providers;
	private EmailProvider selectedProvider;
	private final static int FIRSTINDEX = 0;

	public ConnectComboBoxModel() {
		this.providers = new ArrayList<>();
	}

	public ConnectComboBoxModel(List<EmailProvider> providers) {
		this();
		this.providers.addAll(providers);
		if (getSize() > 0) {
			setSelectedItem(this.providers.get(FIRSTINDEX));
		}
	}

	@Override
	public Object getElementAt(int index) {
		return providers.get(index);
	}

	@Override
	public int getSize() {
		return providers.size();
	}

	@Override
	public Object getSelectedItem() {
		return selectedProvider;
	}

	@Override
	public void setSelectedItem(Object anItem) {
		selectedProvider = (EmailProvider) anItem;		
	}

	public void addProvider(EmailProvider provider) {
		providers.add(provider);
		fireIntervalAdded(this, getSize() - 1, getSize() - 1);
		setSelectedItem(providers.get(getSize() - 1));
	}

	public void addProviders(List<EmailProvider> providersList) {
		int primeiraLinha = getSize();
		providers.addAll(providersList);
		fireIntervalAdded(this, primeiraLinha, primeiraLinha + providersList.size());
		setSelectedItem(providers.get(getSize() - 1));
	}

	public void removeProvider() {
		providers.remove(getSelectedItem());
		fireIntervalRemoved(this, FIRSTINDEX, getSize() - 1);
		setSelectedItem(providers.get(FIRSTINDEX));
	}

	public void clear() {
		providers.clear();
		fireContentsChanged(this, FIRSTINDEX, getSize() - 1);
	}

}
