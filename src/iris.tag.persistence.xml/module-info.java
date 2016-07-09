module iris.tag.persistence.xml {
	requires java.xml.bind;	
	requires iris.tag.api;
	
	exports br.unb.cic.iris.tag.model.xml;
	exports br.unb.cic.iris.tag.persistence.xml;
	
	exports br.unb.cic.iris.tag.model.xml.internal to java.xml.bind;
	
	provides br.unb.cic.iris.tag.model.TagEntityFactory with br.unb.cic.iris.tag.model.xml.TagEntityFactoryXml;
	provides br.unb.cic.iris.tag.persistence.TagDAOFactory with br.unb.cic.iris.tag.persistence.xml.TagDAOFactoryXml;
}
