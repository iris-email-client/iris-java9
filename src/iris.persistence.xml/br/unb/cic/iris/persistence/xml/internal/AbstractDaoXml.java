package br.unb.cic.iris.persistence.xml.internal;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import br.unb.cic.iris.exception.IrisUncheckedException;
import br.unb.cic.iris.model.xml.internal.IrisMessageStoreXml;
import br.unb.cic.iris.persistence.EmailDAO;
import br.unb.cic.iris.persistence.FolderDAO;
import br.unb.cic.iris.persistence.IrisPersistenceException;
import br.unb.cic.iris.persistence.xml.DaoFactoryXml;

public abstract class AbstractDaoXml {
	private static String XML_PATH = System.getProperty("user.home") + "/.iris/iris_store.xml";
	private static File XML_FILE = new File(XML_PATH);
	
	private DaoFactoryXml daoFactory;
	

	public AbstractDaoXml() {
		if(! XML_FILE.exists()){
			try {
				persistIrisStore(new IrisMessageStoreXml());
			} catch (IrisPersistenceException e) {				
				throw new IrisUncheckedException("Error while creating XML store file: "+e.getMessage(), e);
			}
		}
		
		daoFactory = new DaoFactoryXml();
	}


	public EmailDAO getEmailDAO() {
		return daoFactory.createEmailDAO();
	}

	public FolderDAO getFolderDAO() {
		return daoFactory.createFolderDAO();
	}

	public File getXmlFile() throws IrisPersistenceException {
		return XML_FILE;
	}
	
	public IrisMessageStoreXml getIrisXmlStore() throws IrisPersistenceException {
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(IrisMessageStoreXml.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			return (IrisMessageStoreXml) jaxbUnmarshaller.unmarshal(getXmlFile());
		} catch (JAXBException e) {
			throw new IrisPersistenceException("Error reading XML file", e);
		}
	}

	public void persistIrisStore(IrisMessageStoreXml store) throws IrisPersistenceException {
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(IrisMessageStoreXml.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

			jaxbMarshaller.marshal(store, getXmlFile());
		} catch (JAXBException e) {
			throw new IrisPersistenceException("Error writing XML file", e);
		}
	}

}
