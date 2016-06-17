package br.unb.cic.iris.model;

public interface EntityFactory {

	public EmailMessage createEmailMessage();
	public EmailMessage createEmailMessage(String from, String to, String cc, String bcc, String subject, String message);
	
	public IrisFolder createIrisFolder();
	public IrisFolder createIrisFolder(String name);
	
}
