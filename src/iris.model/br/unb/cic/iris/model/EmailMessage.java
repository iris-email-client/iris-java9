package br.unb.cic.iris.model;

import java.util.Date;

public interface EmailMessage {
	public String getId();
	public void setId(String id);
	
	public void setTo(String to);	
	public void setFrom(String from);
	public void setCc(String cc);
	public void setBcc(String bcc);
	public void setSubject(String subject);
	public void setMessage(String message);

	public String getFrom();
	public String getTo();
	public String getCc();
	public String getBcc();
	public String getSubject();
	public String getMessage();

	public Date getDate();
	public void setDate(Date date);
	
	public IrisFolder getFolder();
	public void setFolder(IrisFolder folder);
}
