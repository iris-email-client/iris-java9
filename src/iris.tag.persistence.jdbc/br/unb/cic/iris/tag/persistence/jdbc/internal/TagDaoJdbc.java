package br.unb.cic.iris.tag.persistence.jdbc.internal;

import java.util.List;

import br.unb.cic.iris.persistence.IrisPersistenceException;
import br.unb.cic.iris.persistence.jdbc.DbUtil;
import br.unb.cic.iris.tag.model.Tag;
import br.unb.cic.iris.tag.model.TagEntityFactory;
import br.unb.cic.iris.tag.persistence.TagDAO;

public class TagDaoJdbc implements TagDAO {
	private static TagDaoJdbc instance;
	private static final String SELECT_ALL = "SELECT id, nick, address FROM addressbook";
	private static final String SELECT_BY_NAME = "SELECT id, nick, address FROM addressbook WHERE nick = ?";
	private static final String SELECT_BY_ID = "SELECT id, nick, address FROM addressbook WHERE id = ?";
	private static final String INSERT = "INSERT INTO addressbook (id, nick, address) VALUES (?,?,?)";
	private static final String DELETE = "DELETE FROM addressbook WHERE nick = ?";

	private TagEntityFactory entityFactory;

	private TagDaoJdbc() throws IrisPersistenceException {
		// getDbUtil().execute(CREATE_TABLE);
	}

	public static TagDaoJdbc instance() throws IrisPersistenceException {
		if (instance == null) {
			instance = new TagDaoJdbc();
		}
		return instance;
	}

	@Override
	public Tag findById(String id) throws IrisPersistenceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Tag findByName(String name) throws IrisPersistenceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Tag saveOrUpdate(Tag tag) throws IrisPersistenceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Tag save(Tag tag, String messageId) throws IrisPersistenceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(Tag t) throws IrisPersistenceException {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Tag> findAll() throws IrisPersistenceException {
		// TODO Auto-generated method stub
		return null;
	}

	private DbUtil getDbUtil() throws IrisPersistenceException {
		return DbUtil.instance();
	}

}
