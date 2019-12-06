package br.unb.cic.iris.model.simple.internal;

import java.util.ArrayList;
import java.util.List;

import br.unb.cic.iris.model.EmailMessage;
import br.unb.cic.iris.model.IrisFolder;

public class IrisFolderSimple implements IrisFolder<EmailMessage> {
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
		messages = new ArrayList<>();
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public void addMessage(EmailMessage e) {
		messages.add(e);
	}

	@Override
	public List<EmailMessage> getMessages() {
		return messages;
	}

	@Override
	public EmailMessage getMessage(int index) {
		return messages.get(index);
	}

}