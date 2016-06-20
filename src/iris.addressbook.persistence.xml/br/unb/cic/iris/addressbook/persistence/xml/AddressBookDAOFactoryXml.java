package br.unb.cic.iris.addressbook.persistence.xml;

import br.unb.cic.iris.addressbook.persistence.AddressBookDAOFactory;
import br.unb.cic.iris.addressbook.persistence.IAddressBookDAO;
import br.unb.cic.iris.addressbook.persistence.xml.internal.AddressBookDaoXml;

public class AddressBookDAOFactoryXml implements AddressBookDAOFactory {

	@Override
	public IAddressBookDAO createAddressBookDAO() {
		return new AddressBookDaoXml();
	}

}
