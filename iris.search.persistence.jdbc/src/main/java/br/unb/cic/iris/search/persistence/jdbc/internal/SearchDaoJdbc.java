package br.unb.cic.iris.search.persistence.jdbc.internal;

import java.util.List;

import br.unb.cic.iris.model.EmailMessage;
import br.unb.cic.iris.persistence.IrisPersistenceException;
import br.unb.cic.iris.persistence.jdbc.DbUtil;
import br.unb.cic.iris.search.persistence.SearchDAO;

public class SearchDaoJdbc implements SearchDAO {
	private static SearchDaoJdbc instance;

	private SearchDaoJdbc() {
	}

	public static SearchDaoJdbc instance() {
		if (instance == null) {
			instance = new SearchDaoJdbc();
		}
		return instance;
	}

	@Override
	public List<EmailMessage> search(String text) throws IrisPersistenceException {
		// TODO Auto-generated method stub
		return null;
	}

	private DbUtil getDbUtil() throws IrisPersistenceException {
		return DbUtil.instance();
	}

}
