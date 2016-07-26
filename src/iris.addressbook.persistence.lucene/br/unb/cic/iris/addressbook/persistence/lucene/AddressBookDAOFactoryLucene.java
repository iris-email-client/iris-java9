package br.unb.cic.iris.addressbook.persistence.lucene;

import br.unb.cic.iris.addressbook.persistence.AddressBookDAO;
import br.unb.cic.iris.addressbook.persistence.AddressBookDAOFactory;
import br.unb.cic.iris.addressbook.persistence.lucene.internal.AddressBookDaoLucene;

public class AddressBookDAOFactoryLucene implements AddressBookDAOFactory {

	@Override
	public AddressBookDAO createAddressBookDAO() {		
		return AddressBookDaoLucene.instance();
	}

}
