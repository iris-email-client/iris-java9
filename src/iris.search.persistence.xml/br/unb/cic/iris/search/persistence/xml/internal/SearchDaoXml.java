package br.unb.cic.iris.search.persistence.xml.internal;

import java.util.List;

import br.unb.cic.iris.model.EmailMessage;
import br.unb.cic.iris.persistence.IrisPersistenceException;
import br.unb.cic.iris.search.persistence.SearchDAO;

public class SearchDaoXml implements SearchDAO {
	private static SearchDaoXml instance;

	private SearchDaoXml() {
	}

	public static SearchDaoXml instance() {
		if (instance == null) {
			instance = new SearchDaoXml();
		}
		return instance;
	}

	@Override
	public List<EmailMessage> search(String text) throws IrisPersistenceException {
		// TODO Auto-generated method stub
		return null;
	}

}
