package br.unb.cic.iris.persistence.xml;

import br.unb.cic.iris.persistence.DAOFactory;
import br.unb.cic.iris.persistence.IEmailDAO;
import br.unb.cic.iris.persistence.IFolderDAO;
import br.unb.cic.iris.persistence.xml.internal.EmailDaoXml;
import br.unb.cic.iris.persistence.xml.internal.FolderDaoXml;

public class DaoFactoryXml implements DAOFactory {

	@Override
	public IEmailDAO createEmailDAO() {		
		return new EmailDaoXml();
	}

	@Override
	public IFolderDAO createFolderDAO() {		
		return new FolderDaoXml();
	}

}
