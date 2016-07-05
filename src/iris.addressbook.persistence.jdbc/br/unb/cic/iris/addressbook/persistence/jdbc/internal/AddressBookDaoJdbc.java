package br.unb.cic.iris.addressbook.persistence.jdbc.internal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ServiceLoader;
import java.util.UUID;

import br.unb.cic.iris.addressbook.model.AddressBookEntityFactory;
import br.unb.cic.iris.addressbook.model.AddressBookEntry;
import br.unb.cic.iris.addressbook.persistence.IAddressBookDAO;
import br.unb.cic.iris.exception.EmailUncheckedException;
import br.unb.cic.iris.persistence.PersistenceException;
import br.unb.cic.iris.persistence.jdbc.DbUtil;

public class AddressBookDaoJdbc implements IAddressBookDAO {
	private static final String SELECT_ALL = "SELECT id, nick, address FROM addressbook";
	private static final String SELECT_BY_NICK = "SELECT id, nick, address FROM addressbook WHERE nick = ?";
	private static final String SELECT_BY_ID = "SELECT id, nick, address FROM addressbook WHERE id = ?";
	private static final String INSERT = "INSERT INTO addressbook (id, nick, address) VALUES (?,?,?)";
	private static final String DELETE = "DELETE FROM addressbook WHERE nick = ?";

	private AddressBookEntityFactory entityFactory;
	
	
	public AddressBookDaoJdbc() throws PersistenceException {
		getDbUtil().execute(CREATE_TABLE);
	}
	

	@Override
	public AddressBookEntry saveOrUpdate(AddressBookEntry entry) throws PersistenceException {
		// TODO Auto-generated method stub
		entry.setId(UUID.randomUUID().toString());		
		
		try (Connection conn = getDbUtil().connect();
                PreparedStatement pstmt = conn.prepareStatement(INSERT)) {
            pstmt.setString(1, entry.getId());
            pstmt.setString(2, entry.getNick());
            pstmt.setString(3, entry.getAddress());
            pstmt.executeUpdate();
        } catch (SQLException e) {
        	throw new PersistenceException("Could not create address book entry: "+e.getMessage(), e);
        }
		
		return entry;
	}

	@Override
	public AddressBookEntry findByNick(String nick) throws PersistenceException {
		return executeQuery(SELECT_BY_NICK, nick);
	}

	@Override
	public AddressBookEntry findById(String id) throws PersistenceException {
		return executeQuery(SELECT_BY_ID, id);
	}

	@Override
	public List<AddressBookEntry> findAll() throws PersistenceException {
		List<AddressBookEntry> entries = new LinkedList<>();
		try (Connection conn = getDbUtil().connect();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(SELECT_ALL)) {			
			while (rs.next()) {
				entries.add(toAddressBookEntry(rs));
			}
		} catch (SQLException e) {
			throw new PersistenceException("Could not list all address book entries: "+e.getMessage(), e);
		}
		return entries;
	}

	@Override
	public void delete(String nick) throws PersistenceException {
		try (Connection conn = getDbUtil().connect(); 
				PreparedStatement pstmt = conn.prepareStatement(DELETE)) {
			pstmt.setString(1, nick);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new PersistenceException("Could not delete address book entry ("+nick+"): " + e.getMessage(), e);
		}
	}

	private AddressBookEntry executeQuery(String sql, String value) throws PersistenceException {
		try (Connection conn = getDbUtil().connect(); 
				PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, value);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				return toAddressBookEntry(rs);
			}
		} catch (SQLException e) {
			throw new PersistenceException("Could not execute query: " + e.getMessage(), e);
		}
		return null;
	}

	private AddressBookEntry toAddressBookEntry(ResultSet rs) throws SQLException {
		AddressBookEntry addressBookEntry = getEntityFactory().createAddressBook();
		addressBookEntry.setId(rs.getString("id"));
		addressBookEntry.setNick(rs.getString("nick"));
		addressBookEntry.setAddress(rs.getString("address"));		
		return addressBookEntry;
	}

	private DbUtil getDbUtil() throws PersistenceException {
		return DbUtil.instance();
	}

	private AddressBookEntityFactory getEntityFactory() {		
		if (entityFactory == null) {
			ServiceLoader<AddressBookEntityFactory> sl = ServiceLoader.load(AddressBookEntityFactory.class);
			Iterator<AddressBookEntityFactory> it = sl.iterator();

			if (!it.hasNext())
				throw new EmailUncheckedException("No address book entity factory found!");
			
			entityFactory = it.next();
			System.out.println("Address Book Entity Factory: "+entityFactory.getClass().getCanonicalName());
		}
		return entityFactory;
	}
	
	
	private static final String CREATE_TABLE = 
		"CREATE TABLE IF NOT EXISTS addressbook ("
		+"  id    TEXT PRIMARY KEY," 
		+"  nick  TEXT UNIQUE,"
		+"  address  TEXT NOT NULL"
		+");";
}
