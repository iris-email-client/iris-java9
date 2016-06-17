package br.unb.cic.iris.persistence;

import java.util.List;

import br.unb.cic.iris.model.IrisFolder;
/*** added by dBasePersistence
 */
public interface IFolderDAO {
	public IrisFolder createFolder(String folderName) throws PersistenceException;
	public IrisFolder findByName(String folderName) throws PersistenceException;
	public IrisFolder findById(String id) throws PersistenceException;
	public List<IrisFolder> findAll() throws PersistenceException;
}