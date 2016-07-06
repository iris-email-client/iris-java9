package br.unb.cic.iris.mail.provider;

import java.util.Iterator;
import java.util.List;
import java.util.ServiceLoader;

import br.unb.cic.iris.base.BaseManager;
import br.unb.cic.iris.exception.IrisUncheckedException;
import br.unb.cic.iris.mail.EmailProvider;

/***
 * added by dBaseMail* modified by dGmailProvider* modified by dYahooProvider*
 * modified by dOutlookProvider
 */
public class ProviderManager {
	private BaseManager<EmailProvider> manager = new BaseManager<>();
	private static ProviderManager instance = new ProviderManager();

	private ProviderManager() {
		loadProviders();
	}

	public static ProviderManager instance() {
		return instance;
	}

	private void loadProviders() {
		System.out.println("Loading providers ...");
		ServiceLoader<EmailProvider> sl = ServiceLoader.load(EmailProvider.class);
		Iterator<EmailProvider> it = sl.iterator();

		if (!it.hasNext())
			throw new IrisUncheckedException("No mail providers found!");

		while (it.hasNext()) {
			EmailProvider provider = it.next();
			addProvider(provider);
		}

		System.out.println("Total providers found: " + manager.getAll().size());
	}

	@SuppressWarnings("boxing")
	public void addProvider(EmailProvider provider) {
		System.out.println("Adding provider: "+provider.getName()+" - "+provider.getDescription());
		manager.add(provider.getName().trim(), provider);
	}

	@SuppressWarnings("boxing")
	public EmailProvider getProvider(String name) {
		return manager.get(name);
	}

	public List<EmailProvider> getProviders() {
		return manager.getAll();
	}

}