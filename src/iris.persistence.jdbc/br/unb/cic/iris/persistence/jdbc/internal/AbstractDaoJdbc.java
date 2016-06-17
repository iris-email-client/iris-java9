package br.unb.cic.iris.persistence.jdbc.internal;

import java.util.Iterator;
import java.util.ServiceLoader;

import br.unb.cic.iris.exception.EmailUncheckedException;
import br.unb.cic.iris.model.EntityFactory;
import br.unb.cic.iris.persistence.IEmailDAO;
import br.unb.cic.iris.persistence.IFolderDAO;
import br.unb.cic.iris.persistence.PersistenceException;
import br.unb.cic.iris.persistence.jdbc.DaoFactoryJDBC;

public abstract class AbstractDaoJdbc {
	private DaoFactoryJDBC daoFactory;
	private EntityFactory entityFactory;
	private DbUtil dbUtil;

	public AbstractDaoJdbc() {
		daoFactory = new DaoFactoryJDBC();
		initEntityFactory();
	}

	public IEmailDAO getEmailDAO() {
		return daoFactory.createEmailDAO();
	}

	public IFolderDAO getFolderDAO() {
		return daoFactory.createFolderDAO();
	}

	public EntityFactory getEntityFactory() {
		return entityFactory;
	}

	public DbUtil getDbUtil() throws PersistenceException {
		if(dbUtil == null){
			dbUtil = new DbUtil();
		}
		return dbUtil;
	}

	private void initEntityFactory() {
		ServiceLoader<EntityFactory> sl = ServiceLoader.load(EntityFactory.class);
		Iterator<EntityFactory> it = sl.iterator();

		if (!it.hasNext())
			throw new EmailUncheckedException("No entity factory found!");

		entityFactory = it.next();
	}
}
