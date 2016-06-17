package br.unb.cic.iris.model.xml;

import br.unb.cic.iris.model.EmailMessage;
import br.unb.cic.iris.model.EntityFactory;
import br.unb.cic.iris.model.IrisFolder;
import br.unb.cic.iris.model.xml.internal.EmailMessageXml;
import br.unb.cic.iris.model.xml.internal.IrisFolderXml;

public class EntityFactoryXml implements EntityFactory {

	public EntityFactoryXml() {
	}
	

	@Override
	public EmailMessage createEmailMessage() {		
		return new EmailMessageXml();
	}
	@Override
	public EmailMessage createEmailMessage(String from, String to, String cc, String bcc, String subject, String message) {		
		return new EmailMessageXml(from, to, cc, bcc, subject, message);
	}

	@Override
	public IrisFolder createIrisFolder() {		
		return new IrisFolderXml();
	}

	@Override
	public IrisFolder createIrisFolder(String name) {
		return new IrisFolderXml(name);
	}

	

}
