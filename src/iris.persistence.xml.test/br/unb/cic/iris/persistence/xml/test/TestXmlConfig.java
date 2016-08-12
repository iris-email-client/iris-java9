package br.unb.cic.iris.persistence.xml.test;

import br.unb.cic.iris.base.Configuration;
import br.unb.cic.iris.persistence.PersistenceConfiguration;

public class TestXmlConfig implements PersistenceConfiguration {
	private static String XML_PATH = Configuration.IRIS_HOME + Configuration.FILE_SEPARATOR + "iris_store_test.xml";

	@Override
	public String getPath() {		
		return XML_PATH;
	}

	@Override
	public boolean reset() {		
		return true;
	}

}
