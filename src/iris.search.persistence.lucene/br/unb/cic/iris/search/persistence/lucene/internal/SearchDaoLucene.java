package br.unb.cic.iris.search.persistence.lucene.internal;

import java.util.List;

import br.unb.cic.iris.model.EmailMessage;
import br.unb.cic.iris.persistence.IrisPersistenceException;
import br.unb.cic.iris.search.persistence.SearchDAO;

public class SearchDaoLucene implements SearchDAO {
	private static SearchDaoLucene instance;

	private SearchDaoLucene() {
	}

	public static SearchDaoLucene instance() {
		if (instance == null) {
			instance = new SearchDaoLucene();
		}
		return instance;
	}

	@Override
	public List<EmailMessage> search(String text) throws IrisPersistenceException {
		// TODO Auto-generated method stub
		return null;
	}

}
