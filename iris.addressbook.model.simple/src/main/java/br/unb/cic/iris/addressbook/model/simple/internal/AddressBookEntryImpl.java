package br.unb.cic.iris.addressbook.model.simple.internal;

import br.unb.cic.iris.addressbook.model.AddressBookEntry;

public class AddressBookEntryImpl implements AddressBookEntry {
	private String id;
	private String nick;
	private String address;

	public AddressBookEntryImpl() {
	}

	public AddressBookEntryImpl(String nick, String address) {
		this.nick = nick;
		this.address = address;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
}
