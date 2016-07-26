module iris.persistence.lucene {	
	requires public lucene.core;
	requires public lucene.analyzers.common;	
	requires public lucene.queryparser;
	
	requires iris.core;
	requires iris.model.simple;
	requires public iris.persistence;
		
	exports br.unb.cic.iris.persistence.lucene;	
	
	uses br.unb.cic.iris.model.EntityFactory;
		
	provides br.unb.cic.iris.persistence.DAOFactory with br.unb.cic.iris.persistence.lucene.DaoFactoryLucene;
}
