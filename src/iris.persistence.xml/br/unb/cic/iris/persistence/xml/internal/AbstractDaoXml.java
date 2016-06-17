package br.unb.cic.iris.persistence.xml.internal;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import br.unb.cic.iris.model.xml.internal.IrisMessageStoreXml;
import br.unb.cic.iris.persistence.IEmailDAO;
import br.unb.cic.iris.persistence.IFolderDAO;
import br.unb.cic.iris.persistence.PersistenceException;
import br.unb.cic.iris.persistence.xml.DaoFactoryXml;

public abstract class AbstractDaoXml {
	private static String XML_PATH = System.getProperty("user.home") + "/.iris/iris_store.xml";
	private static File XML_FILE = new File(XML_PATH);
	
	private DaoFactoryXml daoFactory;
	

	public AbstractDaoXml() {
		daoFactory = new DaoFactoryXml();
	}


	public IEmailDAO getEmailDAO() {
		return daoFactory.createEmailDAO();
	}

	public IFolderDAO getFolderDAO() {
		return daoFactory.createFolderDAO();
	}

	public File getXmlFile() throws PersistenceException {
		if(! XML_FILE.exists()){
			persistIrisStore(new IrisMessageStoreXml());
		}
		return XML_FILE;
	}
	
	public IrisMessageStoreXml getIrisXmlStore() throws PersistenceException {
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(IrisMessageStoreXml.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			return (IrisMessageStoreXml) jaxbUnmarshaller.unmarshal(getXmlFile());
		} catch (JAXBException e) {
			throw new PersistenceException("Error reading XML file", e);
		}
	}

	public void persistIrisStore(IrisMessageStoreXml store) throws PersistenceException {
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(IrisMessageStoreXml.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

			jaxbMarshaller.marshal(store, getXmlFile());
		} catch (JAXBException e) {
			throw new PersistenceException("Error writing XML file", e);
		}
	}

}
