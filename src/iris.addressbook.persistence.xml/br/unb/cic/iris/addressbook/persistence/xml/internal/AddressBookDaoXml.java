package br.unb.cic.iris.addressbook.persistence.xml.internal;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;

import br.unb.cic.iris.addressbook.model.AddressBookEntry;
import br.unb.cic.iris.addressbook.model.xml.internal.AddressBookEntryXml;
import br.unb.cic.iris.addressbook.model.xml.internal.AddressBookStoreXml;
import br.unb.cic.iris.addressbook.persistence.AddressBookDAO;
import br.unb.cic.iris.persistence.IrisPersistenceException;

public class AddressBookDaoXml extends AbstractAddressBookDaoXml implements AddressBookDAO {

	@Override
	public AddressBookEntry saveOrUpdate(AddressBookEntry entry) throws IrisPersistenceException {
		AddressBookStoreXml store = getXmlStore();
		if(entry.getId() == null){
			//save
			checkExistingNick(entry.getNick());
			entry.setId(UUID.randomUUID().toString());			
			store.getEntries().add((AddressBookEntryXml)entry);
			persistStore(store);
			return entry;
		}else{
			//update			
			AddressBookEntry addressBookEntry = findById(entry.getId());
			addressBookEntry.setNick(entry.getNick());
			addressBookEntry.setAddress(entry.getAddress());
			//TODO
			
			
			return null;
		}
		
	}

	private void checkExistingNick(String nick) throws IrisPersistenceException{
		if(findByNick(nick) != null){
			throw new IrisPersistenceException("Nick '"+nick+"' already exists");
		}
	}
	
	@Override
	public AddressBookEntry findByNick(String nick) throws IrisPersistenceException {
		Predicate<AddressBookEntryXml> p = getFindByNickPredicate(nick);
		return findByPredicate(p);
	}

	@Override
	public AddressBookEntry findById(String id) throws IrisPersistenceException {
		Predicate<AddressBookEntryXml> p = f -> id.equals(f.getId());
		return findByPredicate(p);
	}
	
	private Predicate<AddressBookEntryXml> getFindByNickPredicate(String nick){
		return f -> nick.equals(f.getNick());
	}

	@Override
	public void delete(String nick) throws IrisPersistenceException {
		AddressBookStoreXml store = getXmlStore();
		boolean removeIf = store.getEntries().removeIf(getFindByNickPredicate(nick));
		if(removeIf){
			persistStore(store);
		}
	}


	private AddressBookEntryXml findByPredicate(Predicate<AddressBookEntryXml> p) throws IrisPersistenceException {
		return getXmlStore().findAddressBookEntryByPredicate(p);
	}

	@Override
	public List<AddressBookEntry> findAll() throws IrisPersistenceException {
		AddressBookStoreXml store = getXmlStore();
		List<AddressBookEntry> list = new ArrayList<>(store.getEntries());
		return list;
	}

}
