package br.unb.cic.iris.tag;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.ServiceLoader;

import br.unb.cic.iris.exception.IrisUncheckedException;
import br.unb.cic.iris.model.EmailMessage;
import br.unb.cic.iris.persistence.IrisPersistenceException;
import br.unb.cic.iris.tag.model.Tag;
import br.unb.cic.iris.tag.model.TagEntityFactory;
import br.unb.cic.iris.tag.persistence.TagDAO;
import br.unb.cic.iris.tag.persistence.TagDAOFactory;

/***
 * added by dTagBase* modified by dTagRelational
 */
public final class TagManager {
	private static final TagManager instance = new TagManager();

	private TagEntityFactory entityFactory;
	private TagDAOFactory daoFactory;

	private TagManager() {
		initEntityFactory();
		initDaoFactory();
	}

	public static TagManager instance() {
		return instance;
	}

	public TagDAO getTagDAO() {
		return daoFactory.createTagDAO();
	}

	public Tag findById(String id) throws IrisPersistenceException {
		return getTagDAO().findById(id);
	}

	public Tag findByName(String name) throws IrisPersistenceException {
		return getTagDAO().findByName(name);
	}

	public java.util.List<Tag> findAll() throws IrisPersistenceException {
		return getTagDAO().findAll();
	}

	public void saveOrUpdate(Tag tag) throws IrisPersistenceException {
		getTagDAO().saveOrUpdate(tag);
	}

	public void delete(Tag tag) throws IrisPersistenceException {
		getTagDAO().delete(tag);
	}

	public void saveTags(String messageId, String tagsStr) throws IrisPersistenceException {
		String[] tags = tagsStr.trim().split(",");
		for (String str : tags) {
			Tag tag = createTagEntity(str.trim());
			getTagDAO().save(tag, messageId);
		}
	}

	public java.util.List<EmailMessage> listMessagesByTag(String tag) throws IrisPersistenceException {
		//IrisServiceLocator.instance().getDaoFactory().createEmailDAO().li
		//return EmailDAO.instance().listMessagesByTag(tag);
		//TODO implement .....
		return new ArrayList<>();
	}

	public Tag createTagEntity() {
		return entityFactory.createTag();
	}

	public Tag createTagEntity(String name) {
		return entityFactory.createTag(name);
	}

	private void initEntityFactory() {
		ServiceLoader<TagEntityFactory> sl = ServiceLoader.load(TagEntityFactory.class);
		Iterator<TagEntityFactory> it = sl.iterator();

		if (!it.hasNext())
			throw new IrisUncheckedException("No Tag Entity Factory found!");

		entityFactory = it.next();
		System.out.println("Tag Entity Factory: " + entityFactory.getClass().getCanonicalName());
	}

	private void initDaoFactory() {
		ServiceLoader<TagDAOFactory> sl = ServiceLoader.load(TagDAOFactory.class);
		Iterator<TagDAOFactory> it = sl.iterator();

		if (!it.hasNext())
			throw new IrisUncheckedException("No Tag DAO Factory found!");

		daoFactory = it.next();
		System.out.println("Tag DAO Factory: " + daoFactory.getClass().getCanonicalName());
	}
}