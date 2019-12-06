package br.unb.cic.iris.search;

import java.util.Iterator;
import java.util.List;
import java.util.ServiceLoader;

import br.unb.cic.iris.exception.IrisUncheckedException;
import br.unb.cic.iris.model.EmailMessage;
import br.unb.cic.iris.persistence.IrisPersistenceException;
import br.unb.cic.iris.search.persistence.SearchDAOFactory;

public class SearchManager {
	private static SearchManager instance = new SearchManager();
	
	private SearchDAOFactory daoFactory;

	private SearchManager() {
		initDaoFactory();
	}

	public static SearchManager instance() {
		return instance;
	}
	
	
	public List<EmailMessage> search(String text) throws IrisPersistenceException{		
		return daoFactory.createSearchDAO().search(text);
	}
	
	
	private void initDaoFactory() {		
		//daoFactory = (SearchDAOFactory) IrisServiceLocator.instance().getService(SearchDAOFactory.class, getClass().getModule().getClassLoader());
		//daoFactory = (SearchDAOFactory) IrisServiceLocator.getService2(SearchDAOFactory.class, getClass().getClassLoader()).get();
				//.orElseThrow(() -> new IrisUncheckedException("No Search DAO Factory found!"));
		
		ServiceLoader<SearchDAOFactory> sl = ServiceLoader.load(SearchDAOFactory.class);
		Iterator<SearchDAOFactory> it = sl.iterator();

		if (!it.hasNext())
			throw new IrisUncheckedException("No Search DAO Factory found!");

		daoFactory = it.next();
		System.out.println("Search DAO Factory: " + daoFactory.getClass().getCanonicalName());
	}
}
