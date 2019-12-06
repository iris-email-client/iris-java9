module iris.search.persistence.jdbc {
	requires iris.persistence.jdbc;
	requires iris.search.api;		
		
	exports br.unb.cic.iris.search.persistence.jdbc;		
	
	provides br.unb.cic.iris.search.persistence.SearchDAOFactory with br.unb.cic.iris.search.persistence.jdbc.SearchDAOFactoryJdbc;
}
