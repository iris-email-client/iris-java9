module iris.search.persistence.xml {
	requires iris.persistence.xml;
	requires iris.search.api;		
		
	exports br.unb.cic.iris.search.persistence.xml;		
	
	provides br.unb.cic.iris.search.persistence.SearchDAOFactory with br.unb.cic.iris.search.persistence.xml.SearchDAOFactoryXml;
}
