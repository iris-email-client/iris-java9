package br.unb.cic.iris.persistence;

import java.util.List;

import br.unb.cic.iris.model.IrisFolder;
/*** added by dBasePersistence
 */
public interface FolderDAO {
	public IrisFolder createFolder(String folderName) throws IrisPersistenceException;
	public IrisFolder findByName(String folderName) throws IrisPersistenceException;
	public IrisFolder findById(String id) throws IrisPersistenceException;
	public List<IrisFolder> findAll() throws IrisPersistenceException;
}