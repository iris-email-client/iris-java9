package br.unb.cic.iris.persistence.jdbc;

import br.unb.cic.iris.persistence.DAOFactory;
import br.unb.cic.iris.persistence.IEmailDAO;
import br.unb.cic.iris.persistence.IFolderDAO;
import br.unb.cic.iris.persistence.jdbc.internal.EmailDaoJdbc;
import br.unb.cic.iris.persistence.jdbc.internal.FolderDaoJdbc;

public class DaoFactoryJDBC implements DAOFactory {

	@Override
	public IEmailDAO createEmailDAO() {		
		return new EmailDaoJdbc();
	}

	@Override
	public IFolderDAO createFolderDAO() {		
		return new FolderDaoJdbc();
	}

}
