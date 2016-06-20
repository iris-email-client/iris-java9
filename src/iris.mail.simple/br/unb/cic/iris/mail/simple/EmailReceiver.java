package br.unb.cic.iris.mail.simple;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Store;
import javax.mail.event.FolderEvent;
import javax.mail.event.FolderListener;
import javax.mail.event.StoreEvent;
import javax.mail.event.StoreListener;
import javax.mail.internet.MimeMessage;
import javax.mail.search.SearchTerm;

import br.unb.cic.iris.core.SystemFacade;
import br.unb.cic.iris.exception.EmailException;
import br.unb.cic.iris.i18n.MessageBundle;
import br.unb.cic.iris.mail.EmailProvider;
import br.unb.cic.iris.mail.EmailStatusManager;
import br.unb.cic.iris.model.EmailMessage;
import br.unb.cic.iris.model.EntityFactory;
import br.unb.cic.iris.model.IrisFolder;

/***
 * added by dBaseMail
 */
public class EmailReceiver implements StoreListener, FolderListener {
	private Store store;
	private EmailSession session;
	private EmailProvider provider;
	private EntityFactory entityFactory;

	public EmailReceiver(EmailProvider provider, String encoding) {
		entityFactory = SystemFacade.instance().getEntityFactory();
		this.provider = provider;
		session = new EmailSession(provider, encoding);
	}

	public List<IrisFolder> listFolders() throws EmailException {
		List<IrisFolder> folders = new ArrayList<IrisFolder>();
		try {
			Store store = getStore();
			Folder defaultFolder = store.getDefaultFolder();
			Folder[] externalFolders = defaultFolder.list();
			for (Folder f : externalFolders) {
				folders.add(entityFactory.createIrisFolder(f.getName()));
			}
		} catch (MessagingException e) {
			throw new EmailException(MessageBundle.message("error.list.folder"), e);
		}
		return folders;
	}

	public List<EmailMessage> getMessages(String folderName, SearchTerm searchTerm) throws EmailException {
		List<EmailMessage> messages = new ArrayList<EmailMessage>();
		Folder folder = openFolder(folderName);
		try {
			Message messagesRetrieved[] = null;
			if (searchTerm == null) {
				messagesRetrieved = folder.getMessages();
			} else {
				messagesRetrieved = folder.search(searchTerm);
			}
			messages = convertToIrisMessage(messagesRetrieved);
		} catch (MessagingException e) {
			throw new EmailException(e.getMessage(), e);
		}
		return messages;
	}

	public List<EmailMessage> getMessages(String folderName, int begin, int end) throws EmailException {
		List<EmailMessage> messages = new ArrayList<EmailMessage>();
		Folder folder = openFolder(folderName);
		try {
			Message messagesRetrieved[] = folder.getMessages(begin, end);
			messages = convertToIrisMessage(messagesRetrieved);
		} catch (MessagingException e) {
			throw new EmailException(e.getMessage(), e);
		}
		return messages;
	}

	public List<EmailMessage> getMessages(String folderName, int seqnum) throws EmailException {
		List<EmailMessage> messages = new ArrayList<EmailMessage>();
		Folder folder = openFolder(folderName);
		try {
			List<Message> messagesList = new ArrayList<Message>();
			int messageCount = folder.getMessageCount();
			for (int i = seqnum; i <= messageCount; i++) {
				Message message = folder.getMessage(i);
				messagesList.add(message);
			}
			Message[] messagesRetrieved = toArray(messagesList);
			messages = convertToIrisMessage(messagesRetrieved);
		} catch (MessagingException e) {
			throw new EmailException(e.getMessage(), e);
		}
		return messages;
	}

	private Message[] toArray(List<Message> messagesList) {
		return messagesList.toArray(new Message[messagesList.size()]);
	}

	private List<EmailMessage> convertToIrisMessage(Message[] messagesRetrieved) throws EmailException {
		List<EmailMessage> messages = new ArrayList<EmailMessage>();
		int cont = 0;
		int total = messagesRetrieved.length;
		for (Message m : messagesRetrieved) {
			try {
				//TODO
				messages.add(convertToIrisMessage(m));
				if (total != 0) {
					for (int i = 0; i < 15; i++) {
						System.out.print('\b');
					}
					cont++;
					int tmp = 100 * cont;
					System.out.print((tmp / total) + "% completed");
					
					EmailStatusManager.instance().notifyMessageDownloadProgress((tmp / total));
				}
			} catch (IOException e) {
				throw new EmailException(e.getMessage(), e);
			} catch (MessagingException e) {
				throw new EmailException(e.getMessage(), e);
			}
		}
		System.out.println();
		return messages;
	}

	private Folder openFolder(String folderName) throws EmailException {
		return openFolder(folderName, Folder.READ_ONLY);
	}

	private Folder openFolder(String folderName, int openType) throws EmailException {
		try {
			Folder folder = getStore().getFolder(folderName);
			folder.open(openType);
			return folder;
		} catch (MessagingException e) {
			throw new EmailException(e.getMessage(), e);
		} catch (EmailException e) {
			throw new EmailException(e.getMessage(), e);
		}
	}

	private EmailMessage convertToIrisMessage(Message message) throws IOException, MessagingException {
		MimeMessage m = (MimeMessage) message;
		EmailMessage msg = entityFactory.createEmailMessage();
		msg.setBcc(convertAddressToString(m.getRecipients(RecipientType.BCC)));
		msg.setCc(convertAddressToString(m.getRecipients(RecipientType.CC)));
		msg.setTo(convertAddressToString(m.getRecipients(RecipientType.TO)));
		msg.setFrom(convertAddressToString(m.getFrom()));
		msg.setMessage(getText(m));
		msg.setSubject(m.getSubject());
		msg.setDate(m.getReceivedDate());
		return msg;
	}

	private String getText(Message message) throws IOException, MessagingException {
		String result = message.getContent().toString();
		if (message instanceof MimeMessage) {
			MimeMessage m = (MimeMessage) message;
			Object contentObject = m.getContent();
			if (contentObject instanceof Multipart) {
				BodyPart clearTextPart = null;
				BodyPart htmlTextPart = null;
				Multipart content = (Multipart) contentObject;
				int count = content.getCount();
				for (int i = 0; i < count; i++) {
					BodyPart part = content.getBodyPart(i);
					if (part.isMimeType("text/plain")) {
						clearTextPart = part;
						break;
					} else if (part.isMimeType("text/html")) {
						htmlTextPart = part;
					}
				}
				if (clearTextPart != null) {
					result = (String) clearTextPart.getContent();
				} else if (htmlTextPart != null) {
					String html = (String) htmlTextPart.getContent();
					result = html;
				}
			} else if (contentObject instanceof String) {
				result = (String) contentObject;
			} else {
				System.out.printf("WARNING: not a mime part or multipart %s", message.toString());
				result = null;
			}
		}
		return result;
	}

	private String convertAddressToString(Address[] recipients) {
		StringBuilder sb = new StringBuilder("");
		if (recipients != null) {
			for (Address a : recipients) {
				sb.append(a.toString() + ", ");
			}
		}
		return sb.toString();
	}

	private Store createStoreAndConnect() throws MessagingException {
		EmailStatusManager.instance().notifyListener("Creating store ...");
		//System.out.println("Creating store ...");
		Store store = session.getSession().getStore(provider.getStoreProtocol());
		store.addStoreListener(this);
		store.addConnectionListener(session);
		session.connect(store, provider.getStoreHost(), provider.getStorePort());
		return store;
	}

	public Store getStore() throws EmailException {
		if (store == null) {
			try {
				store = createStoreAndConnect();
			} catch (MessagingException e) {
				throw new EmailException(e.getMessage(), e);
			}
		}
		return store;
	}

	public Store renew() throws EmailException {
		if (store != null) {
			try {
				store.close();
			} catch (MessagingException e) {
				throw new EmailException(e.getMessage(), e);
			}
			store = null;
		}
		return getStore();
	}

	@Override
	public void notification(StoreEvent e) {
		EmailStatusManager.instance().notifyListener("Notification: " + e.getMessage());
		//System.out.println("Notification: " + e.getMessage());
	}

	@Override
	public void folderCreated(FolderEvent e) {
	}

	@Override
	public void folderDeleted(FolderEvent e) {
	}

	@Override
	public void folderRenamed(FolderEvent e) {
	}
}