package br.unb.cic.iris.model.simple;

import br.unb.cic.iris.model.EntityFactory;
import br.unb.cic.iris.model.EmailMessage;
import br.unb.cic.iris.model.IrisFolder;
import br.unb.cic.iris.model.simple.internal.EmailMessageSimple;
import br.unb.cic.iris.model.simple.internal.IrisFolderSimple;

public class EntityFactorySimple implements EntityFactory {

	public EntityFactorySimple() {
	}
	

	@Override
	public EmailMessage createEmailMessage() {		
		return new EmailMessageSimple();
	}
	@Override
	public EmailMessage createEmailMessage(String from, String to, String cc, String bcc, String subject, String message) {		
		return new EmailMessageSimple(from, to, cc, bcc, subject, message);
	}

	@Override
	public IrisFolder createIrisFolder() {		
		return new IrisFolderSimple();
	}

	@Override
	public IrisFolder createIrisFolder(String name) {
		return new IrisFolderSimple(name);
	}

	

}
