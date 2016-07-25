package br.unb.cic.iris.search.persistence.xml;

import br.unb.cic.iris.search.persistence.SearchDAO;
import br.unb.cic.iris.search.persistence.SearchDAOFactory;
import br.unb.cic.iris.search.persistence.xml.internal.SearchDaoXml;

public class SearchDAOFactoryXml implements SearchDAOFactory {

	@Override
	public SearchDAO createSearchDAO() {
		return SearchDaoXml.instance();
	}

}
