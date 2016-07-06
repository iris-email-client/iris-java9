package br.unb.cic.iris.persistence.jdbc.internal;

import br.unb.cic.iris.core.IrisServiceLocator;
import br.unb.cic.iris.model.EntityFactory;
import br.unb.cic.iris.persistence.EmailDAO;
import br.unb.cic.iris.persistence.FolderDAO;
import br.unb.cic.iris.persistence.IrisPersistenceException;
import br.unb.cic.iris.persistence.jdbc.DaoFactoryJDBC;
import br.unb.cic.iris.persistence.jdbc.DbUtil;

public abstract class AbstractDaoJdbc {
	private DaoFactoryJDBC daoFactory;

	public AbstractDaoJdbc() {
		daoFactory = new DaoFactoryJDBC();
	}

	public EmailDAO getEmailDAO() {
		return daoFactory.createEmailDAO();
	}

	public FolderDAO getFolderDAO() {
		return daoFactory.createFolderDAO();
	}

	public EntityFactory getEntityFactory() {
		return IrisServiceLocator.instance().getEntityFactory();
	}

	public DbUtil getDbUtil() throws IrisPersistenceException {		
		return DbUtil.instance();
	}
}
