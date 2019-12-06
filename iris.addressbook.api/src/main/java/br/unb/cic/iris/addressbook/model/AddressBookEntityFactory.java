package br.unb.cic.iris.addressbook.model;

public interface AddressBookEntityFactory {
	public AddressBookEntry createAddressBook();

	public AddressBookEntry createAddressBook(String nick, String address);
}
