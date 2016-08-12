package br.unb.cic.iris.persistence.xml.internal;

import br.unb.cic.iris.base.Configuration;
import br.unb.cic.iris.persistence.PersistenceConfiguration;

public class DefaultXmlConfig implements PersistenceConfiguration {
	private static String XML_PATH = Configuration.IRIS_HOME + Configuration.FILE_SEPARATOR + "iris_store.xml";

	@Override
	public String getPath() {		
		return XML_PATH;
	}

	@Override
	public boolean reset() {		
		return false;
	}

}
