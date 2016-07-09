package br.unb.cic.iris.tag.model.simple.internal;

import java.util.Set;

import br.unb.cic.iris.model.EmailMessage;

public class Tag implements br.unb.cic.iris.tag.model.Tag {
	private String id;
	private String name;
	private Set<EmailMessage> messages;

	public Tag() {
		this(null);
	}

	public Tag(String name) {
		this(null, name);
	}

	public Tag(String id, String name) {
		super();
		this.id = id;
		this.name = name;
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

	public Set<EmailMessage> getMessages() {
		return messages;
	}

	public void setMessages(Set<EmailMessage> messages) {
		this.messages = messages;
	}
}