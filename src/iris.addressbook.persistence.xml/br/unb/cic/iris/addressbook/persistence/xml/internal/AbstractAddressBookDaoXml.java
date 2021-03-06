package br.unb.cic.iris.addressbook.persistence.xml.internal;

import java.io.File;
import java.io.IOException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import br.unb.cic.iris.addressbook.model.xml.internal.AddressBookStoreXml;
import br.unb.cic.iris.exception.IrisUncheckedException;
import br.unb.cic.iris.persistence.IrisPersistenceException;

public abstract class AbstractAddressBookDaoXml {
	private static String XML_PATH = System.getProperty("user.home") + "/.iris/iris_addressbook_store.xml";
	private static File XML_FILE = new File(XML_PATH);

	protected File getXmlFile() throws IrisPersistenceException {
		if(! XML_FILE.exists()){
			try {
				XML_FILE.createNewFile();
			} catch (IOException e) {
				throw new IrisUncheckedException("Could not create AddressBook XML store file", e);				
			}
			persistStore(new AddressBookStoreXml());
		}
		return XML_FILE;
	}
	
	protected AddressBookStoreXml getXmlStore() throws IrisPersistenceException {
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(AddressBookStoreXml.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			return (AddressBookStoreXml) jaxbUnmarshaller.unmarshal(getXmlFile());
		} catch (JAXBException e) {
			throw new IrisPersistenceException("Error reading XML file", e);
		}
	}

	protected void persistStore(AddressBookStoreXml store) throws IrisPersistenceException {
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(AddressBookStoreXml.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

			jaxbMarshaller.marshal(store, getXmlFile());
		} catch (JAXBException e) {
			throw new IrisPersistenceException("Error writing XML file", e);
		}
	}

}
