package br.unb.cic.iris.persistence.lucene.test;

import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.BeforeClass;

import br.unb.cic.iris.core.IrisServiceLocator;
import br.unb.cic.iris.persistence.IrisPersistenceException;
import br.unb.cic.iris.persistence.lucene.LuceneUtil;
import br.unb.cic.iris.persistence.test.FolderDaoTest;

public class FolderDaoLuceneTest extends FolderDaoTest {

	@BeforeClass
	public static void runOnceBeforeClass() {		
		dao = IrisServiceLocator.instance().getDaoFactory().createFolderDAO();
		entityFactory = IrisServiceLocator.instance().getEntityFactory();
		assertNotNull("Folder DAO shouldn't be null", dao);
		
		try {
			LuceneUtil.instance().setConfig(new TestLuceneConfig());
		} catch (IrisPersistenceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
//	@Before
//	public void runOnceBeforeEachMethod() {
//		System.out.println("runOnceBeforeEachMethod ...");
//		try {
//			LuceneUtil.instance().setConfig(new TestLuceneConfig());
//		} catch (IrisPersistenceException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}		
//	}

}
