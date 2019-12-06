package br.unb.cic.iris.model.simple.internal;

import java.util.Date;

import br.unb.cic.iris.model.EmailMessage;
import br.unb.cic.iris.model.IrisFolder;

public class EmailMessageSimple implements EmailMessage {
	private String id;
	private String from;
	private String to;
	private String cc;
	private String bcc;
	private String subject;
	private String message;
	private Date date;
	private IrisFolder folder;

	public EmailMessageSimple() {
	}

	public EmailMessageSimple(String to, String subject, String message) {
		this(null, to, subject, message);
	}

	public EmailMessageSimple(String from, String to, String subject, String message, Date date, IrisFolder folder) {
		this(from, to, null, null, subject, message, date, folder);
	}

	public EmailMessageSimple(String from, String to, String subject, String message) {
		this(from, to, null, null, subject, message);
	}

	public EmailMessageSimple(String from, String to, String cc, String bcc, String subject, String message) {
		this(from, to, cc, bcc, subject, message, null, null);
	}

	public EmailMessageSimple(String from, String to, String cc, String bcc, String subject, String message, Date date, IrisFolder folder) {
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

	public void setId(String id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public void setCc(String cc) {
		this.cc = cc;
	}

	public void setBcc(String bcc) {
		this.bcc = bcc;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
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

	public void setFolder(IrisFolder folder) {
		this.folder = folder;
	}

	@Override
	public String toString() {
		return String.format("%s - %s - %s - %s", getId(), getFrom(), getSubject(), getDate());
	}
}