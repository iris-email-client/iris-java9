package br.unb.cic.iris.persistence.jdbc.test;

import org.junit.Before;

import br.unb.cic.iris.persistence.IrisPersistenceException;
import br.unb.cic.iris.persistence.jdbc.DbUtil;
import br.unb.cic.iris.persistence.test.FolderDaoTest;

public class FolderDaoJdbcTest extends FolderDaoTest {

	@Before
	public void runOnceBeforeEachMethod() {
		try {
			DbUtil.instance().setConfig(new TestJdbcConfig());
		} catch (IrisPersistenceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}

}
