package br.unb.cic.iris.persistence.lucene;

import br.unb.cic.iris.persistence.DAOFactory;
import br.unb.cic.iris.persistence.EmailDAO;
import br.unb.cic.iris.persistence.FolderDAO;
import br.unb.cic.iris.persistence.lucene.internal.EmailDAOLucene;
import br.unb.cic.iris.persistence.lucene.internal.FolderDAOLucene;

public class DaoFactoryLucene implements DAOFactory {

	@Override
	public EmailDAO createEmailDAO() {		
		return EmailDAOLucene.instance();
	}

	@Override
	public FolderDAO createFolderDAO() {		
		return FolderDAOLucene.instance();
	}

}
