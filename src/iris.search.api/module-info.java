module iris.search.api {
	requires public iris.persistence;
	
	exports br.unb.cic.iris.search;
	exports br.unb.cic.iris.search.persistence;	
	
	uses br.unb.cic.iris.search.persistence.SearchDAOFactory;
}