package br.unb.cic.iris.addressbook.model.xml.internal;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "AddressBookStore")
public class AddressBookStoreXml {
	private List<AddressBookEntryXml> entries;

	public AddressBookStoreXml() {
		entries = new ArrayList<>();
	}

	public List<AddressBookEntryXml> getEntries() {
		return entries;
	}

	@XmlElement(name = "AddressBookEntry")
	public void setEntries(List<AddressBookEntryXml> entries) {
		this.entries = entries;
	}

	public AddressBookEntryXml findAddressBookEntryByPredicate(Predicate<AddressBookEntryXml> p) {
		return getEntries()
				.stream()
				.filter(p)
				.findAny()
				.orElse(null);
	}

}
