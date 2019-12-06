package br.unb.cic.iris.tag.model;

import java.util.Set;

import br.unb.cic.iris.model.EmailMessage;

public interface Tag {

	public String getId();

	public void setId(String id);

	public String getName();

	public void setName(String name);

	public Set<EmailMessage> getMessages();

	public void setMessages(Set<EmailMessage> messages);

}