package br.unb.cic.iris.persistence.jdbc.test;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import br.unb.cic.iris.core.IrisServiceLocator;
import br.unb.cic.iris.model.EntityFactory;
import br.unb.cic.iris.model.IrisFolder;
import br.unb.cic.iris.persistence.FolderDAO;
import br.unb.cic.iris.persistence.IrisPersistenceException;

public class FolderDaoJdbcTest {
	private static FolderDAO dao;
	private static EntityFactory entityFactory;

	@BeforeClass
	public static void runOnceBeforeClass() {
		dao = IrisServiceLocator.instance().getDaoFactory().createFolderDAO();
		entityFactory = IrisServiceLocator.instance().getEntityFactory();
		assertNotNull("Folder DAO shouldn't be null", dao);
	}

	@Test
	public void testSaveFolder() throws IrisPersistenceException{
		IrisFolder folder = dao.createFolder(IrisFolder.INBOX);
		assertNotNull("Folder shouldn't be null", folder);
		assertNotNull("Folder ID shouldn't be null", folder.getId());
	}
	
	@Test(expected=IrisPersistenceException.class)
	public void testSaveFolderFailNullName() throws IrisPersistenceException{
		IrisFolder folder = dao.createFolder(null);
		assertNull("Folder should be null", folder);		
	}

}
