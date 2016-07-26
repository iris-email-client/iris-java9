module iris.addressbook.persistence.lucene {
	requires iris.persistence.lucene;
	requires iris.addressbook.api;
	requires iris.addressbook.model.simple;		
		
	exports br.unb.cic.iris.addressbook.persistence.lucene;	
	
	uses br.unb.cic.iris.addressbook.model.AddressBookEntityFactory;
	
	provides br.unb.cic.iris.addressbook.persistence.AddressBookDAOFactory with br.unb.cic.iris.addressbook.persistence.lucene.AddressBookDAOFactoryLucene;
}
