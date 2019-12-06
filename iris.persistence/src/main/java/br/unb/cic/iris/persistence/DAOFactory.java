package br.unb.cic.iris.persistence;

public interface DAOFactory {

	public EmailDAO createEmailDAO();
	public FolderDAO createFolderDAO();
	
}
