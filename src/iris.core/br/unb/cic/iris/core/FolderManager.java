package br.unb.cic.iris.core;

import java.util.List;

import br.unb.cic.iris.exception.IrisException;
import br.unb.cic.iris.model.EmailMessage;
import br.unb.cic.iris.model.EntityFactory;
import br.unb.cic.iris.model.IrisFolder;
import br.unb.cic.iris.persistence.DAOFactory;
import br.unb.cic.iris.persistence.EmailDAO;
import br.unb.cic.iris.persistence.FolderDAO;

public class FolderManager {
	private static final FolderManager instance = new FolderManager();
	private static final String ROOT_FOLDER = "ROOT";

	private IrisFolder currentFolder;
	private List<EmailMessage> currentMessages;

	private FolderManager() {	
		currentFolder = getEntityFactory().createIrisFolder(ROOT_FOLDER);
		currentMessages = new java.util.ArrayList<>();
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

	public void changeToFolder(String folderName) throws IrisException {
		IrisFolder folder = getFolderDAO().findByName(folderName);
		if (folder != null) {
			currentFolder = folder;
			currentMessages = new java.util.ArrayList<>();
		}
	}

	public List<IrisFolder> listFolders() throws IrisException {
		return getFolderDAO().findAll();
	}

	public List<EmailMessage> listFolderMessages() throws IrisException {
		if (currentFolder == null || currentFolder.getId() == null) {
			return new java.util.ArrayList<>();
		}
		currentMessages = getEmailDAO().listMessages(currentFolder.getId());
		return currentMessages;
	}

	public EmailMessage getMessage(String id) throws IrisException {
		return getEmailDAO().findById(id);
	}

	private EntityFactory getEntityFactory() {
		return IrisServiceLocator.instance().getEntityFactory();
	}

	private EmailDAO getEmailDAO() {
		return getDaoFactory().createEmailDAO();
	}

	private FolderDAO getFolderDAO() {
		return getDaoFactory().createFolderDAO();
	}

	private DAOFactory getDaoFactory() {
		return IrisServiceLocator.instance().getDaoFactory();
	}
}
