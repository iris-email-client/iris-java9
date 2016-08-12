package br.unb.cic.iris.persistence.jdbc.internal;

import br.unb.cic.iris.base.Configuration;
import br.unb.cic.iris.persistence.PersistenceConfiguration;

public class DefaultJdbcConfig implements PersistenceConfiguration {
	private static String DB_PATH = Configuration.IRIS_HOME + Configuration.FILE_SEPARATOR + "iris.db";

	@Override
	public String getPath() {
		return DB_PATH;
	}

	@Override
	public boolean reset() {
		return false;
	}

}
