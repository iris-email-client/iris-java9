module iris.addressbook.api {
	requires transitive iris.persistence;
	
	exports br.unb.cic.iris.addressbook;
	exports br.unb.cic.iris.addressbook.model;
	exports br.unb.cic.iris.addressbook.persistence;
	
	uses br.unb.cic.iris.addressbook.model.AddressBookEntityFactory;
	uses br.unb.cic.iris.addressbook.persistence.AddressBookDAOFactory;
	
}