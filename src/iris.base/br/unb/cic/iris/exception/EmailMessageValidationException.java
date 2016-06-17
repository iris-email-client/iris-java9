package br.unb.cic.iris.exception;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/***
 * added by dBaseException
 */
@SuppressWarnings("serial")
public class EmailMessageValidationException extends EmailException {
	private List<String> messages = new ArrayList<String>();

	public EmailMessageValidationException(String message) {
		this(message, null);
	}

	public EmailMessageValidationException(String message, Exception error) {
		super(message, error);
		addMessage(message);
	}

	public EmailMessageValidationException(List<String> messages) {
		this(messages, null);
	}

	public EmailMessageValidationException(List<String> messages, Exception error) {
		super(toString(messages), error);
		this.messages = messages;
	}

	public List<String> getMessages() {
		return Collections.unmodifiableList(messages);
	}

	private void addMessage(String msg) {
		messages.add(msg);
	}

	private static String toString(List<String> messages) {
		StringBuilder sb = new StringBuilder();
		for (String msg : messages) {
			sb.append(msg + "; ");
		}
		return sb.toString();
	}
}