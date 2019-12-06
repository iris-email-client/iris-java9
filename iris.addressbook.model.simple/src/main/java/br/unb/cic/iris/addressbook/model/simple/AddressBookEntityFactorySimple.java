package br.unb.cic.iris.addressbook.model.simple;

import br.unb.cic.iris.addressbook.model.AddressBookEntityFactory;
import br.unb.cic.iris.addressbook.model.AddressBookEntry;
import br.unb.cic.iris.addressbook.model.simple.internal.AddressBookEntryImpl;

public class AddressBookEntityFactorySimple implements AddressBookEntityFactory {

	public AddressBookEntityFactorySimple() {
	}

	@Override
	public AddressBookEntry createAddressBook() {
		return new AddressBookEntryImpl();
	}

	@Override
	public AddressBookEntry createAddressBook(String nick, String address) {
		return new AddressBookEntryImpl(nick, address);
	}

}
