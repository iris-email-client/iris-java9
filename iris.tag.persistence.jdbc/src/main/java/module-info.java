module iris.tag.persistence.jdbc {
	requires iris.persistence.jdbc;
	requires iris.tag.api;
	requires iris.tag.model.simple;		
		
	exports br.unb.cic.iris.tag.persistence.jdbc;	
	
	uses br.unb.cic.iris.tag.model.TagEntityFactory;
	
	provides br.unb.cic.iris.tag.persistence.TagDAOFactory with br.unb.cic.iris.tag.persistence.jdbc.TagDAOFactoryJdbc;
}
