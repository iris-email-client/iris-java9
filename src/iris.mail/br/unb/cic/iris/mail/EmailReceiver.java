package br.unb.cic.iris.mail;

import java.util.List;

import javax.mail.Store;
import javax.mail.event.FolderListener;
import javax.mail.event.StoreListener;
import javax.mail.search.SearchTerm;

import br.unb.cic.iris.exception.IrisException;
import br.unb.cic.iris.model.EmailMessage;
import br.unb.cic.iris.model.IrisFolder;

public interface EmailReceiver extends StoreListener, FolderListener{
	public List<IrisFolder> listFolders() throws IrisException;

	public List<EmailMessage> getMessages(String folderName, SearchTerm searchTerm) throws IrisException;

	public List<EmailMessage> getMessages(String folderName, int begin, int end) throws IrisException;

	public List<EmailMessage> getMessages(String folderName, int seqnum) throws IrisException;

	

	public Store getStore() throws IrisException;
	public Store renew() throws IrisException;
}
