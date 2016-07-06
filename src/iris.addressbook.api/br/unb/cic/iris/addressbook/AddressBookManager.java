package br.unb.cic.iris.addressbook;

import java.util.Iterator;
import java.util.List;
import java.util.ServiceLoader;

import br.unb.cic.iris.addressbook.model.AddressBookEntityFactory;
import br.unb.cic.iris.addressbook.model.AddressBookEntry;
import br.unb.cic.iris.addressbook.persistence.AddressBookDAOFactory;
import br.unb.cic.iris.exception.IrisUncheckedException;
import br.unb.cic.iris.persistence.IrisPersistenceException;

public class AddressBookManager {
	private static AddressBookManager instance = new AddressBookManager();

	private AddressBookEntityFactory entityFactory;
	private AddressBookDAOFactory daoFactory;

	private AddressBookManager() {
		initEntityFactory();
		initDaoFactory();
	}

	public static AddressBookManager instance() {
		return instance;
	}

	public void save(String nick, String address) throws IrisPersistenceException {
		daoFactory.createAddressBookDAO().saveOrUpdate(createAddressBookEntity(nick, address));
	}

	public void saveOrUpdate(AddressBookEntry entry) throws IrisPersistenceException {
		daoFactory.createAddressBookDAO().saveOrUpdate(entry);
	}

	public List<AddressBookEntry> findAll() throws IrisPersistenceException {
		return daoFactory.createAddressBookDAO().findAll();
	}

	public AddressBookEntry findByNick(String nick) throws IrisPersistenceException {
		return daoFactory.createAddressBookDAO().findByNick(nick);
	}

	public AddressBookEntry findById(String id) throws IrisPersistenceException {
		return daoFactory.createAddressBookDAO().findById(id);
	}

	public void delete(String nick) throws IrisPersistenceException {
		daoFactory.createAddressBookDAO().delete(nick);
	}

	public AddressBookEntry createAddressBookEntity() {
		return entityFactory.createAddressBook();
	}

	public AddressBookEntry createAddressBookEntity(String nick, String address) {
		return entityFactory.createAddressBook(nick, address);
	}

	private void initEntityFactory() {
		ServiceLoader<AddressBookEntityFactory> sl = ServiceLoader.load(AddressBookEntityFactory.class);
		Iterator<AddressBookEntityFactory> it = sl.iterator();

		if (!it.hasNext())
			throw new IrisUncheckedException("No AddressBook Entity Factory found!");

		entityFactory = it.next();
		System.out.println("AddressBook Entity Factory: " + entityFactory.getClass().getCanonicalName());
	}

	private void initDaoFactory() {
		ServiceLoader<AddressBookDAOFactory> sl = ServiceLoader.load(AddressBookDAOFactory.class);
		Iterator<AddressBookDAOFactory> it = sl.iterator();

		if (!it.hasNext())
			throw new IrisUncheckedException("No AddressBook DAO Factory found!");

		daoFactory = it.next();
		System.out.println("DAO Factory: " + daoFactory.getClass().getCanonicalName());
	}
}
