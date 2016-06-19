package br.unb.cic.iris.persistence.lucene;

import br.unb.cic.iris.persistence.DAOFactory;
import br.unb.cic.iris.persistence.IEmailDAO;
import br.unb.cic.iris.persistence.IFolderDAO;
import br.unb.cic.iris.persistence.lucene.internal.EmailDAOLucene;
import br.unb.cic.iris.persistence.lucene.internal.FolderDAOLucene;

public class DaoFactoryLucene implements DAOFactory {

	@Override
	public IEmailDAO createEmailDAO() {		
		return EmailDAOLucene.instance();
	}

	@Override
	public IFolderDAO createFolderDAO() {		
		return FolderDAOLucene.instance();
	}

}
