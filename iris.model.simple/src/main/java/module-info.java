module iris.model.simple {	
	requires transitive iris.model;
	
	exports br.unb.cic.iris.model.simple;
	
	provides br.unb.cic.iris.model.EntityFactory with br.unb.cic.iris.model.simple.EntityFactorySimple;
}