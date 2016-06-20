package br.unb.cic.iris.addressbook.persistence;

public interface AddressBookDAOFactory {

	public IAddressBookDAO createAddressBookDAO();	
	
}
