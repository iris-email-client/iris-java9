package br.unb.cic.iris.addressbook.model;

public interface AddressBookEntry {
	public String getId();
	public void setId(String id);
	public String getNick();
	public void setNick(String nick);
	public String getAddress();
	public void setAddress(String address);
}
