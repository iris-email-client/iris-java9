module iris.addressbook.persistence.xml {
	requires java.xml.bind;	
	requires iris.addressbook.api;
	
	exports br.unb.cic.iris.addressbook.model.xml;
	exports br.unb.cic.iris.addressbook.persistence.xml;
	
	exports br.unb.cic.iris.addressbook.model.xml.internal to java.xml.bind;
	
	provides br.unb.cic.iris.addressbook.model.AddressBookEntityFactory with br.unb.cic.iris.addressbook.model.xml.AddressBookEntityFactoryXml;
	provides br.unb.cic.iris.addressbook.persistence.AddressBookDAOFactory with br.unb.cic.iris.addressbook.persistence.xml.AddressBookDAOFactoryXml;
}
