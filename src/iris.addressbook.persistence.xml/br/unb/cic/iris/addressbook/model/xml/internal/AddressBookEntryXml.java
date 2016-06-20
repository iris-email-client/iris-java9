package br.unb.cic.iris.addressbook.model.xml.internal;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import br.unb.cic.iris.addressbook.model.AddressBookEntry;

@XmlRootElement
@XmlType(propOrder = { "id", "nick", "address" })
public class AddressBookEntryXml implements AddressBookEntry {
	private String id;
	private String nick;
	private String address;

	public AddressBookEntryXml() {
	}

	public AddressBookEntryXml(String nick, String address) {
		this.nick = nick;
		this.address = address;
	}

	public String getId() {
		return id;
	}

	@XmlElement(name = "id")
	public void setId(String id) {
		this.id = id;
	}

	public String getNick() {
		return nick;
	}

	@XmlElement(name = "nick")
	public void setNick(String nick) {
		this.nick = nick;
	}

	public String getAddress() {
		return address;
	}

	@XmlElement(name = "address")
	public void setAddress(String address) {
		this.address = address;
	}

}
