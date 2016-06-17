package br.unb.cic.iris.model.xml.internal;

import java.io.File;
import java.util.Date;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import br.unb.cic.iris.model.IrisFolder;

public class Teste {
	static String FILE = "/home/pedro/tmp/iris_store.xml";

	static EmailMessageXml createMessage(String id, String from, String to, String cc, String bcc, String subject,
			String message) {
		EmailMessageXml msg = new EmailMessageXml(from, to, cc, bcc, subject, message);
		msg.setDate(new Date());
		msg.setId(id);
		return msg;
	}

	public static void main(String[] args) {
		IrisMessageStoreXml store = new IrisMessageStoreXml();

		IrisFolderXml folder = new IrisFolderXml("1", IrisFolder.INBOX);
		folder.addMessage(createMessage("1", "from@outlook.com", "to@gmail.com", "", "", "Teste 1", "Mensagem 1 ..."));
		folder.addMessage(createMessage("2", "from@outlook.com", "to@gmail.com", "", "", "Teste 2", "Mensagem 2 ..."));
		folder.addMessage(createMessage("3", "from@outlook.com", "to@gmail.com", "", "", "Teste 3", "Mensagem 3 ..."));
		store.addFolder(folder);

		store.addFolder(new IrisFolderXml("2", "Folder 2"));

		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(IrisMessageStoreXml.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

			// output pretty printed
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

			//jaxbMarshaller.marshal(store, new File(FILE));
			jaxbMarshaller.marshal(store, System.out);

		} catch (JAXBException e) {
			e.printStackTrace();
		}

	}

}
