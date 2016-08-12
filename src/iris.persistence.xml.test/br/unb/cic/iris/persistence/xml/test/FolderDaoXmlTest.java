package br.unb.cic.iris.persistence.xml.test;

import org.junit.Before;

import br.unb.cic.iris.persistence.IrisPersistenceException;
import br.unb.cic.iris.persistence.test.FolderDaoTest;
import br.unb.cic.iris.persistence.xml.XmlUtil;

public class FolderDaoXmlTest extends FolderDaoTest {

	@Before
	public void runOnceBeforeEachMethod() {
		try {
			XmlUtil.instance().setConfig(new TestXmlConfig());
		} catch (IrisPersistenceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}

}
