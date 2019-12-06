package br.unb.cic.iris.search.persistence.jdbc;

import br.unb.cic.iris.search.persistence.SearchDAO;
import br.unb.cic.iris.search.persistence.SearchDAOFactory;
import br.unb.cic.iris.search.persistence.jdbc.internal.SearchDaoJdbc;

public class SearchDAOFactoryJdbc implements SearchDAOFactory {

	@Override
	public SearchDAO createSearchDAO() {
		return SearchDaoJdbc.instance();
	}

}
