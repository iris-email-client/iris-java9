package br.unb.cic.iris.persistence.lucene.test;

import br.unb.cic.iris.base.Configuration;
import br.unb.cic.iris.persistence.PersistenceConfiguration;

public class TestLuceneConfig implements PersistenceConfiguration {
	private static String LUCENE_IDX_PATH = Configuration.IRIS_HOME + Configuration.FILE_SEPARATOR + "lucene_idx_test";

	@Override
	public String getPath() {
		return LUCENE_IDX_PATH;
	}

	@Override
	public boolean reset() {
		return true;
	}
}
