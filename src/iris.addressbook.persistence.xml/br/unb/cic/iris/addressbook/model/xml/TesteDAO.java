package br.unb.cic.iris.addressbook.model.xml;

import br.unb.cic.iris.addressbook.model.AddressBookEntry;
import br.unb.cic.iris.addressbook.model.xml.internal.AddressBookEntryXml;
import br.unb.cic.iris.addressbook.persistence.xml.internal.AddressBookDaoXml;
import br.unb.cic.iris.persistence.PersistenceException;

public class TesteDAO {

	public static void main(String[] args) {
		AddressBookDaoXml dao = new AddressBookDaoXml();
		
		try {
			dao.saveOrUpdate(new AddressBookEntryXml("teste", "teste@gmail.com"));
			System.out.println("saved ...");
			dao.saveOrUpdate(new AddressBookEntryXml("teste2", "teste2@gmail.com"));
			dao.saveOrUpdate(new AddressBookEntryXml("teste3", "teste3@gmail.com"));
			dao.saveOrUpdate(new AddressBookEntryXml("teste4", "teste4@gmail.com"));
			AddressBookEntry byNick = dao.findByNick("teste");
			System.out.println("NICK: "+byNick);
		} catch (PersistenceException e) {		
			e.printStackTrace();
		}
	}
	
}
