package br.unb.cic.iris.persistence.test;

import static br.unb.cic.iris.model.IrisFolder.INBOX;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.BeforeClass;
import org.junit.Test;

import br.unb.cic.iris.core.IrisServiceLocator;
import br.unb.cic.iris.model.EntityFactory;
import br.unb.cic.iris.model.IrisFolder;
import br.unb.cic.iris.persistence.FolderDAO;
import br.unb.cic.iris.persistence.IrisPersistenceException;

public abstract class FolderDaoTest {
	protected static FolderDAO dao;
	protected  static EntityFactory entityFactory;

	@BeforeClass
	public static void runOnceBeforeClass() {
		dao = IrisServiceLocator.instance().getDaoFactory().createFolderDAO();
		entityFactory = IrisServiceLocator.instance().getEntityFactory();
		assertNotNull("Folder DAO shouldn't be null", dao);
	}

	@Test
	public void testSaveFolder() throws IrisPersistenceException{
		IrisFolder folder = dao.createFolder(INBOX);
		assertNotNull("Folder shouldn't be null", folder);
		assertNotNull("Folder ID shouldn't be null", folder.getId());
	}
	
	@Test(expected=IrisPersistenceException.class)
	public void testSaveFolderFailNullName() throws IrisPersistenceException{
		IrisFolder folder = dao.createFolder(null);
		assertNull("Folder should be null", folder);		
	}
	
	@Test(expected=IrisPersistenceException.class)
	public void testSaveFolderFailBlankName() throws IrisPersistenceException{
		IrisFolder folder = dao.createFolder("");
		assertNull("Folder should be null", folder);		
	}
	
	@Test(expected=IrisPersistenceException.class)
	public void testSaveFolderFailSpaceName() throws IrisPersistenceException{
		IrisFolder folder = dao.createFolder(" ");
		assertNull("Folder should be null", folder);		
	}
	
	@Test
	public void testFindByName() throws IrisPersistenceException {
		try {
			dao.createFolder(INBOX);
		} catch (IrisPersistenceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		IrisFolder folder = dao.findByName(INBOX);
		assertNotNull("Folder shouldn't be null", folder);
		assertEquals("Folder name should be: "+INBOX, INBOX, folder.getName());		
	}

}
