package br.unb.cic.iris.model.simple.internal;

import java.util.ArrayList;
import java.util.List;

import br.unb.cic.iris.model.EmailMessage;
import br.unb.cic.iris.model.IrisFolder;

public class IrisFolderSimple implements IrisFolder<EmailMessage> {
	public static final String INBOX = "INBOX";
	public static final String OUTBOX = "OUTBOX";

	private String id;
	private String name;
	private List<EmailMessage> messages;

	public IrisFolderSimple() {
		this(null, "");
	}

	public IrisFolderSimple(String name) {
		this(null, name);
	}

	public IrisFolderSimple(String id, String name) {
		this.id = id;
		this.name = name;
		messages = new ArrayList<EmailMessage>();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void addMessage(EmailMessage e) {
		messages.add(e);
	}

	public List<EmailMessage> getMessages() {
		return messages;
	}

	public EmailMessage getMessage(int index) {
		return messages.get(index);
	}

}