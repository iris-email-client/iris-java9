package br.unb.cic.iris.tag.persistence.lucene.internal;

import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.StringField;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TermQuery;

import br.unb.cic.iris.core.IrisServiceLocator;
import br.unb.cic.iris.persistence.EmailDAO;
import br.unb.cic.iris.persistence.IrisPersistenceException;
import br.unb.cic.iris.persistence.lucene.AbstractDAO;
import br.unb.cic.iris.tag.TagManager;
import br.unb.cic.iris.tag.model.Tag;
import br.unb.cic.iris.tag.persistence.TagDAO;

/***
 * added by dTagLucene
 */
public class TagDaoLucene extends AbstractDAO<Tag> implements TagDAO {
	private static final TagDaoLucene instance = new TagDaoLucene();
	private EmailDAO emailDAO;

	private TagDaoLucene() {
		this.type = "tag";
		emailDAO = IrisServiceLocator.instance().getDaoFactory().createEmailDAO();
	}

	public static TagDaoLucene instance() {
		return instance;
	}

	public Tag findByName(String tagName) throws IrisPersistenceException {
		List<Tag> result = new ArrayList<>();
		Query nameQuery = new TermQuery(new Term("name", tagName));
		result = findByTerms(new Query[] { nameQuery });
		if (result.isEmpty()) {
			throw new IrisPersistenceException("Tag name not found", null);
		}
		return result.iterator().next();
	}

	public Tag save(Tag tagToSave, String messageId) throws IrisPersistenceException {
		// TODO
		// EmailMessage email = emailDAO.findById(messageId);
		// email.addTag(tagToSave);
		// emailDAO.saveOrUpdate(email);
		return null;
	}
	
	@Override
	public Tag persist(Tag tag) throws IrisPersistenceException {
		// TODO Auto-generated method stub
		return null;
	}

	protected Tag fromDocument(Document d) throws IrisPersistenceException {
		Tag t = TagManager.instance().createTagEntity();
		t.setId(d.get("id"));
		t.setName(d.get("name"));
		return t;
	}

	protected Document toDocument(Tag t) throws IrisPersistenceException {
		Document doc = new Document();
		doc.add(new StringField("id", String.valueOf(t.getId()), Store.YES));
		doc.add(new StringField("name", t.getName(), Store.YES));
		return doc;
	}


}
