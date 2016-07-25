package br.unb.cic.iris.search.persistence.lucene;

import br.unb.cic.iris.search.persistence.SearchDAO;
import br.unb.cic.iris.search.persistence.SearchDAOFactory;
import br.unb.cic.iris.search.persistence.lucene.internal.SearchDaoLucene;

public class SearchDAOFactoryLucene implements SearchDAOFactory {

	@Override
	public SearchDAO createSearchDAO() {
		return SearchDaoLucene.instance();
	}

}
