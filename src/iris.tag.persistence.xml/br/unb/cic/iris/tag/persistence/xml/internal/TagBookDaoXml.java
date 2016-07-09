package br.unb.cic.iris.tag.persistence.xml.internal;

import java.io.File;
import java.io.IOException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import br.unb.cic.iris.exception.IrisUncheckedException;
import br.unb.cic.iris.persistence.IrisPersistenceException;
import br.unb.cic.iris.tag.model.xml.internal.TagStoreXml;

public abstract class TagBookDaoXml {
	private static String XML_PATH = System.getProperty("user.home") + "/.iris/iris_tags.xml";
	private static File XML_FILE = new File(XML_PATH);

	protected File getXmlFile() throws IrisPersistenceException {
		if (!XML_FILE.exists()) {
			try {
				XML_FILE.createNewFile();
			} catch (IOException e) {
				throw new IrisUncheckedException("Could not create Tags XML store file", e);
			}
			persistStore(new TagStoreXml());
		}
		return XML_FILE;
	}

	protected TagStoreXml getXmlStore() throws IrisPersistenceException {
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(TagStoreXml.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			return (TagStoreXml) jaxbUnmarshaller.unmarshal(getXmlFile());
		} catch (JAXBException e) {
			throw new IrisPersistenceException("Error reading XML file", e);
		}
	}

	protected void persistStore(TagStoreXml store) throws IrisPersistenceException {
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(TagStoreXml.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

			jaxbMarshaller.marshal(store, getXmlFile());
		} catch (JAXBException e) {
			throw new IrisPersistenceException("Error writing XML file", e);
		}
	}

}
