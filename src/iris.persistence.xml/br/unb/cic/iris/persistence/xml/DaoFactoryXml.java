package br.unb.cic.iris.persistence.xml;

import br.unb.cic.iris.persistence.DAOFactory;
import br.unb.cic.iris.persistence.EmailDAO;
import br.unb.cic.iris.persistence.FolderDAO;
import br.unb.cic.iris.persistence.xml.internal.EmailDaoXml;
import br.unb.cic.iris.persistence.xml.internal.FolderDaoXml;

public class DaoFactoryXml implements DAOFactory {

	@Override
	public EmailDAO createEmailDAO() {		
		return new EmailDaoXml();
	}

	@Override
	public FolderDAO createFolderDAO() {		
		return new FolderDaoXml();
	}

}
