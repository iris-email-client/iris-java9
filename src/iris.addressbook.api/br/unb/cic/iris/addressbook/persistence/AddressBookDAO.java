package br.unb.cic.iris.addressbook.persistence;

import java.util.List;

import br.unb.cic.iris.addressbook.model.AddressBookEntry;
import br.unb.cic.iris.persistence.IrisPersistenceException;

public interface AddressBookDAO {
	public AddressBookEntry saveOrUpdate(AddressBookEntry entry) throws IrisPersistenceException;

	public AddressBookEntry findByNick(String nick) throws IrisPersistenceException;

	public AddressBookEntry findById(String id) throws IrisPersistenceException;

	public List<AddressBookEntry> findAll() throws IrisPersistenceException;

	public void delete(String nick) throws IrisPersistenceException;

}
