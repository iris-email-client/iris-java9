package br.unb.cic.iris.addressbook.model.xml;

import br.unb.cic.iris.addressbook.model.AddressBookEntityFactory;
import br.unb.cic.iris.addressbook.model.AddressBookEntry;
import br.unb.cic.iris.addressbook.model.xml.internal.AddressBookEntryXml;

public class AddressBookEntityFactoryXml implements AddressBookEntityFactory {

	public AddressBookEntry createAddressBook() {
		return new AddressBookEntryXml();
	}

	@Override
	public AddressBookEntry createAddressBook(String nick, String address) {
		return new AddressBookEntryXml(nick, address);
	}

}
