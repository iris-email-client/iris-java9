module iris.search.persistence.lucene {
	requires iris.persistence.lucene;
	requires iris.search.api;		
		
	exports br.unb.cic.iris.search.persistence.lucene;		
	
	provides br.unb.cic.iris.search.persistence.SearchDAOFactory with br.unb.cic.iris.search.persistence.xml.SearchDAOFactoryXml;
}
