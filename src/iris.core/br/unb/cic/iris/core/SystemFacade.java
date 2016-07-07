package br.unb.cic.iris.core;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Properties;

import br.unb.cic.iris.core.i18n.MessageBundle;
import br.unb.cic.iris.exception.IrisException;
import br.unb.cic.iris.exception.IrisUncheckedException;
import br.unb.cic.iris.mail.EmailProvider;
import br.unb.cic.iris.mail.EmailStatusListener;
import br.unb.cic.iris.mail.EmailStatusManager;
import br.unb.cic.iris.mail.IEmailClient;
import br.unb.cic.iris.mail.provider.ProviderManager;
import br.unb.cic.iris.model.EmailMessage;
import br.unb.cic.iris.model.IrisFolder;
import br.unb.cic.iris.model.Status;
import br.unb.cic.iris.persistence.DAOFactory;
import br.unb.cic.iris.persistence.EmailDAO;
import br.unb.cic.iris.persistence.FolderDAO;
import br.unb.cic.iris.persistence.IrisPersistenceException;
import br.unb.cic.iris.util.StringUtil;

/***
 * added by dBaseFacade* modified by dPersistenceRelational
 */
public final class SystemFacade {
	private static final SystemFacade instance = new SystemFacade();

	private EmailProvider provider;
	private Status status = Status.NOT_CONNECTED;

	private Locale locale;
	private Properties irisProperties;

	private SystemFacade() {
		System.out.println("Starting system ...");
		initIrisProperties();
		initLocale();

		IrisServiceLocator.instance();
		ProviderManager.instance();
		
		initDefaultProvider();
	}

	public static SystemFacade instance() {
		return instance;
	}

	public void defineEmailProvider(EmailProvider newProvider) {
		System.out.println("Defining email provider: "+newProvider.getName()+", user="+newProvider.getUsername());
		setStatus(Status.NOT_CONNECTED);
		this.provider = newProvider;
		getEmailClient().setProvider(newProvider);
		setStatus(Status.CONNECTED);
	}

	// TODO refactoring candidate
	public void send(EmailMessage message, EmailStatusListener listener) throws IrisException {
		EmailStatusManager.instance().setListener(listener);
		send(message);
	}

	public void send(EmailMessage message) throws IrisException {
		verifyConnection();
		getEmailClient().send(message);
		message.setDate(new Date());
		saveMessage(message);
	}

	private void saveMessage(EmailMessage message) throws IrisException {
		saveMessage(message, IrisFolder.OUTBOX);
	}

	private void saveMessage(EmailMessage message, String folderName) throws IrisException {
		EmailDAO emailDAO = getDaoFactory().createEmailDAO();
		IrisFolder folder = checkFolder(folderName);
		message.setFolder(folder);
		emailDAO.saveMessage(message);
	}

	public List<IrisFolder> listRemoteFolders(EmailStatusListener listener) throws IrisException {
		EmailStatusManager.instance().setListener(listener);
		return listRemoteFolders();
	}
	public List<IrisFolder> listRemoteFolders() throws IrisException {
		verifyConnection();
		return getEmailClient().listFolders();
	}

	public void downloadMessages(String folder) throws IrisException {
		verifyConnection();
		EmailDAO dao = getDaoFactory().createEmailDAO();
		checkFolder(folder);
		Date lastMessageReceived = dao.lastMessageReceived(folder);
		List<EmailMessage> messages = getEmailClient().getMessagesAfterDate(folder, lastMessageReceived);
		for (EmailMessage message : messages) {
			saveMessage(message, folder);
		}
	}

	private IrisFolder checkFolder(String folderName) throws IrisPersistenceException {
		FolderDAO folderDAO = getDaoFactory().createFolderDAO();
		IrisFolder folder = folderDAO.findByName(folderName);
		if (folder == null) {
			folder = folderDAO.createFolder(folderName);
		}
		return folder;
	}

	private void verifyConnection() {
		if (!isConnected()) {
			throw new IrisUncheckedException(MessageBundle.message("error.not.connected"));
		}
	}

	public boolean isConnected() {
		return Status.CONNECTED == getStatus();
	}

	private void setStatus(Status status) {
		this.status = status;
	}

	public Status getStatus() {
		return status;
	}

	public EmailProvider getProvider() {
		return provider;
	}

	private IEmailClient getEmailClient() {
		return IrisServiceLocator.instance().getEmailClient();
	}

	private DAOFactory getDaoFactory() {
		return IrisServiceLocator.instance().getDaoFactory();
	}

	private void initLocale() {
		String language = getIrisProperties().getProperty("language");
		if (!StringUtil.isEmpty(language)) {
			locale = new Locale(getIrisProperties().getProperty("language"));
		} else {
			locale = Locale.ENGLISH;
		}
	}

	public Locale getLocale() {
		return locale;
	}

	private void initIrisProperties() {
		String irisPropertiesFile = System.getProperty("user.home") + "/.iris/iris.properties";
		irisProperties = new Properties();
		try (InputStream is = new FileInputStream(irisPropertiesFile)) {
			irisProperties.load(is);
		} catch (IOException e) {						
			System.err.println(MessageBundle.message("Unable to read iris properties file: "+irisPropertiesFile+". "+e.getLocalizedMessage()));
			e.printStackTrace();
		}
	}

	public Properties getIrisProperties() {
		return irisProperties;
	}
	
	private void initDefaultProvider(){
		if(getIrisProperties().containsKey("provider")){
			String providerStr = getIrisProperties().getProperty("provider");
			EmailProvider defaultProvider = ProviderManager.instance().getProvider(providerStr.trim());
			if(defaultProvider != null){
				String username = getIrisProperties().getProperty("username").trim();
				String password = getIrisProperties().getProperty("password").trim();
				if(StringUtil.notEmpty(username) && StringUtil.notEmpty(password)){
					defaultProvider.setUsername(username);
					defaultProvider.setPassword(password);
					defineEmailProvider(defaultProvider);
				}
			}
		}
	}
	
}