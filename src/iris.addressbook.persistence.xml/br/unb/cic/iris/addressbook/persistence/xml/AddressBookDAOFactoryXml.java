package br.unb.cic.iris.addressbook.persistence.xml;

import br.unb.cic.iris.addressbook.persistence.AddressBookDAOFactory;
import br.unb.cic.iris.addressbook.persistence.AddressBookDAO;
import br.unb.cic.iris.addressbook.persistence.xml.internal.AddressBookDaoXml;

public class AddressBookDAOFactoryXml implements AddressBookDAOFactory {

	@Override
	public AddressBookDAO createAddressBookDAO() {
		return new AddressBookDaoXml();
	}

}
