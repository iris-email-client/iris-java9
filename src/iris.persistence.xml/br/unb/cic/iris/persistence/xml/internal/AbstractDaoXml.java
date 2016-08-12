package br.unb.cic.iris.persistence.xml.internal;

import br.unb.cic.iris.exception.IrisUncheckedException;
import br.unb.cic.iris.model.xml.internal.IrisMessageStoreXml;
import br.unb.cic.iris.persistence.EmailDAO;
import br.unb.cic.iris.persistence.FolderDAO;
import br.unb.cic.iris.persistence.IrisPersistenceException;
import br.unb.cic.iris.persistence.xml.DaoFactoryXml;
import br.unb.cic.iris.persistence.xml.XmlUtil;

public abstract class AbstractDaoXml {
	private DaoFactoryXml daoFactory;

	public AbstractDaoXml() {
		try {
			XmlUtil.instance().setConfig(new DefaultXmlConfig());
		} catch (IrisPersistenceException e) {
			throw new IrisUncheckedException("Error while configuring DAO: " + e.getMessage(), e);
		}
		daoFactory = new DaoFactoryXml();
	}

	protected EmailDAO getEmailDAO() {
		return daoFactory.createEmailDAO();
	}

	protected FolderDAO getFolderDAO() {
		return daoFactory.createFolderDAO();
	}

	protected IrisMessageStoreXml getIrisXmlStore() throws IrisPersistenceException {
		return XmlUtil.instance().getIrisXmlStore();
	}

	protected void persistIrisStore(IrisMessageStoreXml store) throws IrisPersistenceException {
		XmlUtil.instance().persistIrisStore(store);		
	}

}
