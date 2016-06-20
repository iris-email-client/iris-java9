package br.unb.cic.iris.addressbook.model.xml;

import java.util.UUID;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import br.unb.cic.iris.addressbook.model.xml.internal.AddressBookEntryXml;
import br.unb.cic.iris.addressbook.model.xml.internal.AddressBookStoreXml;

public class Teste {
	static String FILE = "/home/pedro/tmp/iris__addressbook_store.xml";

	static AddressBookEntryXml createAddressBookEntry(String nick, String address){
		AddressBookEntryXml entry = new AddressBookEntryXml(nick, address);
		entry.setId(UUID.randomUUID().toString());
		return entry;
	}
	
	public static void main(String[] args) {				
		AddressBookStoreXml store = new AddressBookStoreXml();
		
		store.getEntries().add(createAddressBookEntry("teste", "teste@gmail.com"));
		store.getEntries().add(createAddressBookEntry("teste2", "teste2@gmail.com"));
		store.getEntries().add(createAddressBookEntry("teste3", "teste3@gmail.com"));
		store.getEntries().add(createAddressBookEntry("teste4", "teste4@gmail.com"));

		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(AddressBookStoreXml.class);
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
