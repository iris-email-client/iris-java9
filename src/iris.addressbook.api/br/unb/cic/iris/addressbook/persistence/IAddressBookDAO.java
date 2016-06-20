package br.unb.cic.iris.addressbook.persistence;

import java.util.List;

import br.unb.cic.iris.addressbook.model.AddressBookEntry;
import br.unb.cic.iris.persistence.PersistenceException;

public interface IAddressBookDAO {
	public AddressBookEntry saveOrUpdate(AddressBookEntry entry) throws PersistenceException;

	public AddressBookEntry findByNick(String nick) throws PersistenceException;

	public AddressBookEntry findById(String id) throws PersistenceException;

	public List<AddressBookEntry> findAll() throws PersistenceException;

	public void delete(String nick) throws PersistenceException;

}
