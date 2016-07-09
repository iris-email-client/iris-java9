package br.unb.cic.iris.tag.model.xml.internal;

import java.util.Set;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import br.unb.cic.iris.model.EmailMessage;
import br.unb.cic.iris.tag.model.Tag;

@XmlRootElement
@XmlType(propOrder = { "id", "name" })
public class TagXml implements Tag {
	private String id;
	private String name;
	// TODO save only the IDs
	private Set<EmailMessage> messages;

	public TagXml() {
	}

	public TagXml(String name) {
		this.name = name;
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

	@Override
	public Set<EmailMessage> getMessages() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setMessages(Set<EmailMessage> messages) {
		// TODO Auto-generated method stub

	}

}
