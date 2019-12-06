package br.unb.cic.iris.persistence.jdbc;

import br.unb.cic.iris.persistence.DAOFactory;
import br.unb.cic.iris.persistence.EmailDAO;
import br.unb.cic.iris.persistence.FolderDAO;
import br.unb.cic.iris.persistence.jdbc.internal.EmailDaoJdbc;
import br.unb.cic.iris.persistence.jdbc.internal.FolderDaoJdbc;

public class DaoFactoryJDBC implements DAOFactory {

	@Override
	public EmailDAO createEmailDAO() {		
		return new EmailDaoJdbc();
	}

	@Override
	public FolderDAO createFolderDAO() {		
		return new FolderDaoJdbc();
	}

}
