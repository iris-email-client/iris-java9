module iris.addressbook.model.simple {	
	requires iris.addressbook.api;
	
	exports br.unb.cic.iris.addressbook.model.simple;
	
	provides br.unb.cic.iris.addressbook.model.AddressBookEntityFactory with br.unb.cic.iris.addressbook.model.simple.AddressBookEntityFactorySimple;
}