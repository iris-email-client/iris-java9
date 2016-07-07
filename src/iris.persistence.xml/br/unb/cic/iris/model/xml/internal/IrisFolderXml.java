package br.unb.cic.iris.model.xml.internal;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import br.unb.cic.iris.model.IrisFolder;

@XmlRootElement
@XmlType(propOrder = {"id", "name", "messages"})
public class IrisFolderXml implements IrisFolder<EmailMessageXml> {
	public static final String INBOX = "INBOX";
	public static final String OUTBOX = "OUTBOX";

	private String id;
	private String name;
	private List<EmailMessageXml> messages;

	public IrisFolderXml() {
		this(null, "");
	}

	public IrisFolderXml(String name) {
		this(null, name);
	}

	public IrisFolderXml(String id, String name) {
		this.id = id;
		this.name = name;
		messages = new ArrayList<>();
	}


	public String getId() {
		return id;
	}
	@XmlElement(name = "id")
	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	@XmlElement(name = "name")
	public void setName(String name) {
		this.name = name;
	}

	public List<EmailMessageXml> getMessages() {
		return messages;
	}

	@XmlElement(name = "Message")
	public void setMessages(List<EmailMessageXml> contents) {
		this.messages = contents;
	}

	public void addMessage(EmailMessageXml msg) {
		messages.add(msg);
	}

	public EmailMessageXml getMessage(int idx) {
		return messages.get(idx);
	}

}