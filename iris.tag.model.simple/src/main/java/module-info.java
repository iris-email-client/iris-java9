module iris.tag.model.simple {	
	requires iris.tag.api;
	
	exports br.unb.cic.iris.tag.model.simple;
	
	provides br.unb.cic.iris.tag.model.TagEntityFactory with br.unb.cic.iris.tag.model.simple.TagEntityFactorySimple;
}