package br.unb.cic.iris.persistence;

public interface DAOFactory {

	public IEmailDAO createEmailDAO();
	public IFolderDAO createFolderDAO();
	
}
