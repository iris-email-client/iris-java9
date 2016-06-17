package br.unb.cic.iris.model;

import java.util.List;

public interface IrisFolder<T extends EmailMessage> {
	public static final String INBOX = "INBOX";
	public static final String OUTBOX = "OUTBOX";

	public String getId();
	public void setId(String id);
	
	public String getName();
	public void setName(String name);

	public void addMessage(T msg);
	public T getMessage(int idx);
	public List<T> getMessages();
}
