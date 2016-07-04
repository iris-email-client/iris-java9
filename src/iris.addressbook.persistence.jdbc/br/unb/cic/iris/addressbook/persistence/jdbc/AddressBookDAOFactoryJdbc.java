package br.unb.cic.iris.addressbook.persistence.jdbc;

import br.unb.cic.iris.addressbook.persistence.AddressBookDAOFactory;
import br.unb.cic.iris.addressbook.persistence.IAddressBookDAO;
import br.unb.cic.iris.addressbook.persistence.jdbc.internal.AddressBookDaoJdbc;
import br.unb.cic.iris.exception.EmailUncheckedException;
import br.unb.cic.iris.persistence.PersistenceException;

public class AddressBookDAOFactoryJdbc implements AddressBookDAOFactory {

	@Override
	public IAddressBookDAO createAddressBookDAO() {		
		try {
			return new AddressBookDaoJdbc();
		} catch (PersistenceException e) {			
			throw new EmailUncheckedException("Could not create AddressBookDaoJdbc: "+e.getMessage(), e);
		}
	}

}
