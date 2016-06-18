module iris.persistence.lucene {	
	requires lucene.core;
	requires lucene.analyzers.common;	
	requires lucene.queryparser;
	
	requires iris.core;
	requires iris.model.simple;
	requires public iris.persistence;
		
	exports br.unb.cic.iris.persistence.lucene;	
	
	uses br.unb.cic.iris.model.EntityFactory;
		
	provides br.unb.cic.iris.persistence.DAOFactory with br.unb.cic.iris.persistence.lucene.DaoFactoryLucene;
}
