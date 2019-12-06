package br.unb.cic.iris.mail;

import java.util.List;

import javax.mail.event.TransportListener;

import br.unb.cic.iris.exception.IrisException;
import br.unb.cic.iris.model.EmailMessage;

public interface EmailSender extends TransportListener {
	public void send(EmailMessage email) throws IrisException;

	public List<String> validateEmailMessage(EmailMessage message);
	
	public default void notifyListeners(String message){
		EmailStatusManager.instance().notifyListener(message);	
	}
}
