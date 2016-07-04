package br.unb.cic.iris.persistence.jdbc.internal;

import br.unb.cic.iris.core.IrisServiceLocator;
import br.unb.cic.iris.model.EntityFactory;
import br.unb.cic.iris.persistence.IEmailDAO;
import br.unb.cic.iris.persistence.IFolderDAO;
import br.unb.cic.iris.persistence.PersistenceException;
import br.unb.cic.iris.persistence.jdbc.DaoFactoryJDBC;
import br.unb.cic.iris.persistence.jdbc.DbUtil;

public abstract class AbstractDaoJdbc {
	private DaoFactoryJDBC daoFactory;

	public AbstractDaoJdbc() {
		daoFactory = new DaoFactoryJDBC();
	}

	public IEmailDAO getEmailDAO() {
		return daoFactory.createEmailDAO();
	}

	public IFolderDAO getFolderDAO() {
		return daoFactory.createFolderDAO();
	}

	public EntityFactory getEntityFactory() {
		return IrisServiceLocator.instance().getEntityFactory();
	}

	public DbUtil getDbUtil() throws PersistenceException {		
		return DbUtil.instance();
	}
}
