module iris.tag.api {
	requires public iris.persistence;
	
	exports br.unb.cic.iris.tag;
	exports br.unb.cic.iris.tag.model;
	exports br.unb.cic.iris.tag.persistence;
	
	uses br.unb.cic.iris.tag.model.TagEntityFactory;
	uses br.unb.cic.iris.tag.persistence.TagDAOFactory;	
}