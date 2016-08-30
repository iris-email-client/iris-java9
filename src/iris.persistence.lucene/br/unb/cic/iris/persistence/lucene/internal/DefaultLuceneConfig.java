package br.unb.cic.iris.persistence.lucene.internal;

import br.unb.cic.iris.base.Configuration;
import br.unb.cic.iris.persistence.PersistenceConfiguration;

public class DefaultLuceneConfig implements PersistenceConfiguration {	
	private static String LUCENE_IDX_PATH = Configuration.IRIS_HOME + Configuration.FILE_SEPARATOR + "lucene_idx";

	@Override
	public String getPath() {
		return LUCENE_IDX_PATH;
	}

	@Override
	public boolean reset() {
		return false;
	}

}
