package br.unb.cic.iris.persistence.test;

import static br.unb.cic.iris.model.IrisFolder.INBOX;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import br.unb.cic.iris.core.IrisServiceLocator;
import br.unb.cic.iris.model.EntityFactory;
import br.unb.cic.iris.model.IrisFolder;
import br.unb.cic.iris.persistence.FolderDAO;
import br.unb.cic.iris.persistence.IrisPersistenceException;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public abstract class FolderDaoTest {
	protected static FolderDAO dao;
	protected static EntityFactory entityFactory;
	
	static String ID;

	@BeforeClass
	public static void runOnceBeforeClass() {
		dao = IrisServiceLocator.instance().getDaoFactory().createFolderDAO();
		entityFactory = IrisServiceLocator.instance().getEntityFactory();
		assertNotNull("Folder DAO shouldn't be null", dao);
	}

	@Test
	public void test01SaveFolder() throws IrisPersistenceException{
		System.out.println("testSaveFolder");
		IrisFolder folder = dao.createFolder(INBOX);
		System.out.println("Folder created: "+folder.getId());
		assertNotNull("Folder shouldn't be null", folder);
		assertNotNull("Folder ID shouldn't be null", folder.getId());
		ID = folder.getId();
	}
	
	@Test(expected=IrisPersistenceException.class)
	public void test01SaveFolderFailBlankName() throws IrisPersistenceException{
		System.out.println("testSaveFolderFailBlankName");
		IrisFolder folder = dao.createFolder("");
		assertNull("Folder should be null", folder);		
	}
	
	@Test(expected=IrisPersistenceException.class)
	public void test01SaveFolderFailNullName() throws IrisPersistenceException{
		System.out.println("testSaveFolderFailNullName");
		IrisFolder folder = dao.createFolder(null);
		assertNull("Folder should be null", folder);		
	}	
	
	@Test(expected=IrisPersistenceException.class)
	public void test01SaveFolderFailSpaceName() throws IrisPersistenceException{
		System.out.println("testSaveFolderFailSpaceName");
		IrisFolder folder = dao.createFolder(" ");
		assertNull("Folder should be null", folder);		
	}
	
	@Test
	public void test02FindByName() throws IrisPersistenceException {
		System.out.println("testFindByName");
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
	
	//@Test(expected=IrisPersistenceException.class)
	@Test(expected=NullPointerException.class)
	public void test02FindByNameFailNullName() throws IrisPersistenceException {
		System.out.println("testFindByNameFailNullName");
		IrisFolder folder = dao.findByName(null);
		assertNull("Folder should be null", folder);			
	}
	
	@Test
	public void test03FindByID() throws IrisPersistenceException {
		System.out.println("testFindByID");
		IrisFolder folder = dao.findById(ID);
		assertNotNull("Folder shouldn't be null", folder);
		assertEquals("Folder ID should be: "+ID, ID, folder.getId());		
		assertEquals("Folder name should be: "+INBOX, INBOX, folder.getName());				
	}
}
