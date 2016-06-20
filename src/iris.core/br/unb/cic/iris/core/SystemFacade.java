package br.unb.cic.iris.core;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.ServiceLoader;

import br.unb.cic.iris.exception.EmailException;
import br.unb.cic.iris.exception.EmailUncheckedException;
import br.unb.cic.iris.i18n.MessageBundle;
import br.unb.cic.iris.mail.EmailProvider;
import br.unb.cic.iris.mail.EmailStatusListener;
import br.unb.cic.iris.mail.EmailStatusManager;
import br.unb.cic.iris.mail.IEmailClient;
import br.unb.cic.iris.mail.provider.ProviderManager;
import br.unb.cic.iris.model.EmailMessage;
import br.unb.cic.iris.model.EntityFactory;
import br.unb.cic.iris.model.IrisFolder;
import br.unb.cic.iris.model.Status;
import br.unb.cic.iris.persistence.DAOFactory;
import br.unb.cic.iris.persistence.IEmailDAO;
import br.unb.cic.iris.persistence.IFolderDAO;


/***
 * added by dBaseFacade* modified by dPersistenceRelational
 */
public final class SystemFacade {
	private static final SystemFacade instance = new SystemFacade();
	private IEmailClient client;
	private EmailProvider provider;
	private EntityFactory entityFactory;
	private DAOFactory daoFactory;
	private Status status = Status.NOT_CONNECTED;

	private SystemFacade() {
		System.out.println("Starting system ...");
		initEntityFactory();
		initDaoFactory();
		initEmailClient();
		ProviderManager.instance();
		//Configuration config = new Configuration();
		//provider = new DefaultProvider(config.getProperties());
		//provider = new GmailProvider();
		//ProviderManager.instance().addProvider(provider);
		//connect(provider);
	}

	public static SystemFacade instance() {
		return instance;
	}	

	public void defineEmailProvider(EmailProvider provider) {
		setStatus(Status.NOT_CONNECTED);
		this.provider = provider;
		client.setProvider(provider);
		setStatus(Status.CONNECTED);		
	}
	
	//TODO refactoring candidate
	public void send(EmailMessage message, EmailStatusListener listener) throws EmailException {
		EmailStatusManager.instance().setListener(listener);
		send(message);
	}

	public void send(EmailMessage message) throws EmailException {
		verifyConnection();
		client.send(message);
		message.setDate(new Date());
		saveMessage(message);
	}

	private void saveMessage(EmailMessage message) throws EmailException {
		saveMessage(message, IrisFolder.OUTBOX);
	}

	private void saveMessage(EmailMessage message, String folderName) throws EmailException {
		IEmailDAO emailDAO = daoFactory.createEmailDAO();
		IFolderDAO folderDAO = daoFactory.createFolderDAO();
		
		IrisFolder folder = folderDAO.findByName(folderName);
		if(folder == null){
			folder = folderDAO.createFolder(folderName);
		}
		
		message.setFolder(folder);
		emailDAO.saveMessage(message);
	}
	
	public List<IrisFolder> listRemoteFolders() throws EmailException {
		verifyConnection();
		return client.listFolders();
	}

	public void downloadMessages(String folder) throws EmailException {
		/*verifyConnection();
		SearchTerm searchTerm = null;
		IEmailDAO dao = EmailDAO.instance();
		Date lastMessageReceived = dao.lastMessageReceived();
		System.out.println("**************************** lastMessageReceived=" + lastMessageReceived);
		if (lastMessageReceived != null) {
			searchTerm = new ReceivedDateTerm(ComparisonTerm.GT, lastMessageReceived);
		}
		List<EmailMessage> messages = client.getMessages(folder, searchTerm);
		for (EmailMessage message : messages) {
			saveMessage(message, folder);
		}*/
	}

	private void verifyConnection() {
		if (!isConnected()) {
			throw new EmailUncheckedException(MessageBundle.message("error.not.connected"));
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
	
	public EntityFactory getEntityFactory(){
		return entityFactory;
	}
	
	
	
	private void initEmailClient(){		
		ServiceLoader<IEmailClient> sl = ServiceLoader.load(IEmailClient.class);
		Iterator<IEmailClient> it = sl.iterator();

		if (!it.hasNext())
			throw new EmailUncheckedException("No mail client found!");
		
		client = it.next();
		System.out.println("Email client: "+client.getClass().getCanonicalName());
	}
	
	private void initEntityFactory(){		
		ServiceLoader<EntityFactory> sl = ServiceLoader.load(EntityFactory.class);
		Iterator<EntityFactory> it = sl.iterator();

		if (!it.hasNext())
			throw new EmailUncheckedException("No entity factory found!");
		
		entityFactory = it.next();
		System.out.println("Entity Factory: "+entityFactory.getClass().getCanonicalName());
	}
	
	private void initDaoFactory() {		
		ServiceLoader<DAOFactory> sl = ServiceLoader.load(DAOFactory.class);
		Iterator<DAOFactory> it = sl.iterator();

		if (!it.hasNext())
			throw new EmailUncheckedException("No DAO factory found!");
		
		daoFactory = it.next();
		System.out.println("DAO Factory: "+daoFactory.getClass().getCanonicalName());
	}
	
}