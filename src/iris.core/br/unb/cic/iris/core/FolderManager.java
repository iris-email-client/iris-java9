package br.unb.cic.iris.core;

import java.util.List;

import br.unb.cic.iris.exception.EmailException;
import br.unb.cic.iris.model.EmailMessage;
import br.unb.cic.iris.model.EntityFactory;
import br.unb.cic.iris.model.IrisFolder;
import br.unb.cic.iris.persistence.DAOFactory;
import br.unb.cic.iris.persistence.IEmailDAO;
import br.unb.cic.iris.persistence.IFolderDAO;

public class FolderManager {
	private static final FolderManager instance = new FolderManager();
	private static final String ROOT_FOLDER = "ROOT";

	private IrisFolder currentFolder;
	private List<EmailMessage> currentMessages;

	private FolderManager() {	
		currentFolder = getEntityFactory().createIrisFolder(ROOT_FOLDER);
		currentMessages = new java.util.ArrayList<EmailMessage>();
	}

	public static FolderManager instance() {
		return instance;
	}

	public String getCurrentFolderName() {
		return currentFolder.getName();
	}

	public List<EmailMessage> getCurrentMessages() {
		return currentMessages;
	}

	public void changeToFolder(String folderName) throws EmailException {
		IrisFolder folder = getFolderDAO().findByName(folderName);
		if (folder != null) {
			currentFolder = folder;
			currentMessages = new java.util.ArrayList<EmailMessage>();
		}
	}

	public List<IrisFolder> listFolders() throws EmailException {
		return getFolderDAO().findAll();
	}

	public List<EmailMessage> listFolderMessages() throws EmailException {
		if (currentFolder == null || currentFolder.getId() == null) {
			return new java.util.ArrayList<EmailMessage>();
		}
		currentMessages = getEmailDAO().listMessages(currentFolder.getId());
		return currentMessages;
	}

	public EmailMessage getMessage(String id) throws EmailException {
		return getEmailDAO().findById(id);
	}

	private EntityFactory getEntityFactory() {
		return IrisServiceLocator.instance().getEntityFactory();
	}

	private IEmailDAO getEmailDAO() {
		return getDaoFactory().createEmailDAO();
	}

	private IFolderDAO getFolderDAO() {
		return getDaoFactory().createFolderDAO();
	}

	private DAOFactory getDaoFactory() {
		return IrisServiceLocator.instance().getDaoFactory();
	}
}
