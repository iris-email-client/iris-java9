module iris.tag.persistence.lucene {
	requires iris.core;
	requires iris.persistence.lucene;
	requires iris.tag.api;
	requires iris.tag.model.simple;		
		
	exports br.unb.cic.iris.tag.persistence.lucene;	
	
	uses br.unb.cic.iris.tag.model.TagEntityFactory;
	
	provides br.unb.cic.iris.tag.persistence.TagDAOFactory with br.unb.cic.iris.tag.persistence.lucene.TagDAOFactoryLucene;
}
