package br.unb.cic.iris.persistence.xml.internal;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;

import br.unb.cic.iris.model.IrisFolder;
import br.unb.cic.iris.model.xml.internal.IrisFolderXml;
import br.unb.cic.iris.model.xml.internal.IrisMessageStoreXml;
import br.unb.cic.iris.persistence.EntityValidator;
import br.unb.cic.iris.persistence.FolderDAO;
import br.unb.cic.iris.persistence.IrisPersistenceException;

public class FolderDaoXml extends AbstractDaoXml implements FolderDAO {

	@Override
	public IrisFolderXml findByName(String folderName) throws IrisPersistenceException {				
		Predicate<IrisFolderXml> p = f -> folderName.equals(f.getName());
		return findByPredicate(p);
	}

	@Override
	public IrisFolderXml findById(String id) throws IrisPersistenceException {
		Predicate<IrisFolderXml> p = f -> id.equals(f.getId());
		return findByPredicate(p);
	}

	@Override
	public List<IrisFolder> findAll() throws IrisPersistenceException {	
		IrisMessageStoreXml store = getIrisXmlStore();
		List<IrisFolder> list = new ArrayList<>(store.getFolders());
		return list;
	}
	
	private IrisFolderXml findByPredicate(Predicate<IrisFolderXml> p) throws IrisPersistenceException {
		return getIrisXmlStore().findFolderByPredicate(p);
	}

	@Override
	public IrisFolderXml createFolder(String folderName) throws IrisPersistenceException {
		EntityValidator.checkFolderBeforeCreate(this, folderName);
		
		IrisFolderXml folder = new IrisFolderXml(folderName);
		folder.setId(UUID.randomUUID().toString());
		
		IrisMessageStoreXml store = getIrisXmlStore();
		store.addFolder(folder);
		
		persistIrisStore(store);	
		
		return folder;
	}

}
