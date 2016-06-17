package br.unb.cic.iris.model.xml.internal;

import java.util.Date;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import br.unb.cic.iris.model.EmailMessage;
import br.unb.cic.iris.model.IrisFolder;

@XmlRootElement(name = "Message")
@XmlType(propOrder = {"id", "date", "from", "to", "cc", "bcc", "subject", "message"})
public class EmailMessageXml implements EmailMessage {
	private String id;
	private String from;
	private String to;
	private String cc;
	private String bcc;
	private String subject;
	private String message;
	private Date date;
	private IrisFolder folder;

	public EmailMessageXml() {
	}

	public EmailMessageXml(String to, String subject, String message) {
		this(null, to, subject, message);
	}

	public EmailMessageXml(String from, String to, String subject, String message, Date date, IrisFolder folder) {
		this(from, to, null, null, subject, message, date, folder);
	}

	public EmailMessageXml(String from, String to, String subject, String message) {
		this(from, to, null, null, subject, message);
	}

	public EmailMessageXml(String from, String to, String cc, String bcc, String subject, String message) {
		this(from, to, cc, bcc, subject, message, null, null);
	}

	public EmailMessageXml(String from, String to, String cc, String bcc, String subject, String message, Date date, IrisFolder folder) {
		this.from = from;
		this.to = to;
		this.cc = cc;
		this.bcc = bcc;
		this.subject = subject;
		this.message = message;
		this.date = date;
		this.folder = folder;
	}

	public String getId() {
		return id;
	}
	@XmlElement(name = "id")
	public void setId(String id) {
		this.id = id;
	}
	public Date getDate() {
		return date;
	}
	@XmlElement(name = "date")
	public void setDate(Date date) {
		this.date = date;
	}
	@XmlElement(name = "to")
	public void setTo(String to) {
		this.to = to;
	}
	@XmlElement(name = "cc")
	public void setCc(String cc) {
		this.cc = cc;
	}
	@XmlElement(name = "bcc")
	public void setBcc(String bcc) {
		this.bcc = bcc;
	}
	@XmlElement(name = "subject")
	public void setSubject(String subject) {
		this.subject = subject;
	}
	@XmlElement(name = "message")
	public void setMessage(String message) {
		this.message = message;
	}
	@XmlElement(name = "from")
	public void setFrom(String from) {
		this.from = from;
	}
	
	public String getFrom() {
		return from;
	}	
	public String getTo() {
		return to;
	}
	public String getCc() {
		return cc;
	}
	public String getBcc() {
		return bcc;
	}
	public String getSubject() {
		return subject;
	}
	public String getMessage() {
		return message;
	}
	
	public IrisFolder getFolder() {
		return folder;
	}
	@XmlTransient
	public void setFolder(IrisFolder folder) {
		this.folder = folder;
	}

	@Override
	public String toString() {
		return String.format("%s - %s - %s - %s", getId(), getFrom(), getSubject(), getDate());
	}
}