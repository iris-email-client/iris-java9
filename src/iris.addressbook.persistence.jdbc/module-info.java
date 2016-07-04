module iris.addressbook.persistence.jdbc {
	requires iris.persistence.jdbc;
	requires iris.addressbook.api;
	requires iris.addressbook.model.simple;		
		
	exports br.unb.cic.iris.addressbook.persistence.jdbc;	
	
	uses br.unb.cic.iris.addressbook.model.AddressBookEntityFactory;
	
	provides br.unb.cic.iris.addressbook.persistence.AddressBookDAOFactory with br.unb.cic.iris.addressbook.persistence.jdbc.AddressBookDAOFactoryJdbc;
}
