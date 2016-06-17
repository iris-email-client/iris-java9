module iris.persistence.xml {
	requires java.xml.bind;	
	requires iris.model;
	requires public iris.persistence;
	
	exports br.unb.cic.iris.model.xml;
	exports br.unb.cic.iris.persistence.xml;
	
	exports br.unb.cic.iris.model.xml.internal to java.xml.bind;
	
	provides br.unb.cic.iris.model.EntityFactory with br.unb.cic.iris.model.xml.EntityFactoryXml;
	provides br.unb.cic.iris.persistence.DAOFactory with br.unb.cic.iris.persistence.xml.DaoFactoryXml;
}
