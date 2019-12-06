package br.unb.cic.iris.addressbook.persistence.jdbc;

import br.unb.cic.iris.addressbook.persistence.AddressBookDAOFactory;
import br.unb.cic.iris.addressbook.persistence.AddressBookDAO;
import br.unb.cic.iris.addressbook.persistence.jdbc.internal.AddressBookDaoJdbc;
import br.unb.cic.iris.exception.IrisUncheckedException;
import br.unb.cic.iris.persistence.IrisPersistenceException;

public class AddressBookDAOFactoryJdbc implements AddressBookDAOFactory {

	@Override
	public AddressBookDAO createAddressBookDAO() {		
		try {
			return AddressBookDaoJdbc.instance();
		} catch (IrisPersistenceException e) {			
			throw new IrisUncheckedException("Could not create AddressBookDaoJdbc: "+e.getMessage(), e);
		}
	}

}
