package br.unb.cic.iris.persistence.xml;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import br.unb.cic.iris.exception.IrisUncheckedException;
import br.unb.cic.iris.model.xml.internal.IrisMessageStoreXml;
import br.unb.cic.iris.persistence.IrisPersistenceException;
import br.unb.cic.iris.persistence.PersistenceConfiguration;
import br.unb.cic.iris.persistence.xml.internal.DefaultXmlConfig;

public class XmlUtil {
	private static File XML_FILE;

	private static XmlUtil instance;

	private PersistenceConfiguration config;

	private XmlUtil() throws IrisPersistenceException {
		setConfig(new DefaultXmlConfig());
	}

	public static XmlUtil instance() throws IrisPersistenceException {
		if (instance == null) {
			instance = new XmlUtil();
		}
		return instance;
	}

	public void setConfig(PersistenceConfiguration config) throws IrisPersistenceException {
		this.config = config;
		if (config.reset()) {
			config.doReset();
		}
		XML_FILE = new File(config.getPath());
		if (!XML_FILE.exists()) {
			try {
				persistIrisStore(new IrisMessageStoreXml());
			} catch (IrisPersistenceException e) {
				throw new IrisUncheckedException("Error while creating XML file: " + e.getMessage(), e);
			}
		}
	}

	public IrisMessageStoreXml getIrisXmlStore() throws IrisPersistenceException {
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(IrisMessageStoreXml.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			return (IrisMessageStoreXml) jaxbUnmarshaller.unmarshal(XML_FILE);
		} catch (JAXBException e) {
			throw new IrisPersistenceException("Error reading XML file", e);
		}
	}

	public void persistIrisStore(IrisMessageStoreXml store) throws IrisPersistenceException {
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(IrisMessageStoreXml.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

			jaxbMarshaller.marshal(store, XML_FILE);
		} catch (JAXBException e) {
			throw new IrisPersistenceException("Error writing XML file", e);
		}
	}
}
