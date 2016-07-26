package br.unb.cic.iris.addressbook.persistence.lucene.internal;

import java.util.List;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.StringField;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TermQuery;

import br.unb.cic.iris.addressbook.AddressBookManager;
import br.unb.cic.iris.addressbook.model.AddressBookEntry;
import br.unb.cic.iris.addressbook.persistence.AddressBookDAO;
import br.unb.cic.iris.persistence.IrisPersistenceException;
import br.unb.cic.iris.persistence.lucene.AbstractDAO;
/*** added by dAddressBookLucene
 */
public class AddressBookDaoLucene extends AbstractDAO<AddressBookEntry> implements AddressBookDAO {
	private static AddressBookDaoLucene instance = new AddressBookDaoLucene();

	private AddressBookDaoLucene() {
		this.type = "address_book";
	}

	public static AddressBookDaoLucene instance() {
		return instance;
	}

	@Override
	public AddressBookEntry persist(AddressBookEntry entry) throws IrisPersistenceException {
		return fromDocument(saveOrUpdate(entry));
	}

	@Override
	public void delete(String nick) throws IrisPersistenceException {
		delete(findByNick(nick));
	}

	@Override
	public AddressBookEntry findByNick(String nick) throws IrisPersistenceException {
		Query nameQuery = new TermQuery(new Term("nick", nick));
		List<AddressBookEntry> result = findByTerms(new Query[] { nameQuery });
		if (result.isEmpty()) {
			throw new IrisPersistenceException(String.format("Address book entry for nick '%s' not found", nick), null);
		}
		return result.iterator().next();
	}

	@Override
	protected AddressBookEntry fromDocument(Document doc) throws IrisPersistenceException {
		AddressBookEntry entry = AddressBookManager.instance().createAddressBookEntity();
		entry.setId(doc.get("id"));
		entry.setNick(doc.get("nick"));
		entry.setAddress(doc.get("address"));
		return entry;
	}

	protected Document toDocument(AddressBookEntry e) {
		Document d = new Document();
		d.add(new StringField("id", String.valueOf(e.getId()), Store.YES));
		d.add(new StringField("nick", e.getNick(), Store.YES));
		d.add(new StringField("address", e.getAddress(), Store.YES));
		return d;
	}

}
