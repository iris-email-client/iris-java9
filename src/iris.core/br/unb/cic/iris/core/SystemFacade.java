package br.unb.cic.iris.core;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Properties;

import javax.mail.search.ComparisonTerm;
import javax.mail.search.ReceivedDateTerm;
import javax.mail.search.SearchTerm;

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
	}

	public static SystemFacade instance() {
		return instance;
	}

	public void defineEmailProvider(EmailProvider provider) {
		setStatus(Status.NOT_CONNECTED);
		this.provider = provider;
		getEmailClient().setProvider(provider);
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

	public List<IrisFolder> listRemoteFolders() throws IrisException {
		verifyConnection();
		return getEmailClient().listFolders();
	}

	public void downloadMessages(String folder) throws IrisException {
		verifyConnection();
		SearchTerm searchTerm = null;
		EmailDAO dao = getDaoFactory().createEmailDAO();
		checkFolder(folder);
		Date lastMessageReceived = dao.lastMessageReceived(folder);
		System.out.println("**************************** lastMessageReceived=" + lastMessageReceived);
		if (lastMessageReceived != null) {
			searchTerm = new ReceivedDateTerm(ComparisonTerm.GT, lastMessageReceived);
		}
		List<EmailMessage> messages = getEmailClient().getMessages(folder, searchTerm);
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
		if(!StringUtil.isEmpty(language)){
			locale = new Locale(getIrisProperties().getProperty("language"));
		}else{
			locale = Locale.ENGLISH;
		}		
	}

	public Locale getLocale() {
		return locale;
	}

	private void initIrisProperties() {
		String irisPropertiesFile = System.getProperty("user.home") + "/.iris/iris.properties";
		irisProperties = new Properties();
		try {
			System.out.println("str="+irisPropertiesFile);
			System.out.println("file="+new File(irisPropertiesFile).exists());
			System.out.println("IRIS_PROPERTIES="+new FileInputStream(irisPropertiesFile));
			irisProperties.load(new FileInputStream(irisPropertiesFile));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Properties getIrisProperties() {
		return irisProperties;
	}
}