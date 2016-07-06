package br.unb.cic.iris.persistence.lucene.internal;

import java.text.ParseException;
import java.util.List;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.StringField;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TermQuery;

import br.unb.cic.iris.core.IrisServiceLocator;
import br.unb.cic.iris.exception.IrisUncheckedException;
import br.unb.cic.iris.model.IrisFolder;
import br.unb.cic.iris.persistence.FolderDAO;
import br.unb.cic.iris.persistence.IrisPersistenceException;

/***
 * added by dPersistenceLucene
 */
public class FolderDAOLucene extends AbstractDAO<IrisFolder> implements FolderDAO {

	private static FolderDAOLucene instance;

	private FolderDAOLucene() {
		this.type = "folder";
	}

	public static FolderDAOLucene instance() {
		if (instance == null) {
			instance = new FolderDAOLucene();
			try {
				ensureIsCreated(IrisFolder.INBOX);
				ensureIsCreated(IrisFolder.OUTBOX);
			} catch (IrisPersistenceException e) {
				throw new IrisUncheckedException("Error hwile initializing lucene presistence", e);
			}
		}
		return instance;
	}

	private static void ensureIsCreated(String folderName) throws IrisPersistenceException {
		List<IrisFolder> folders = instance.doFindByName(folderName);
		if (folders.isEmpty()) {
			IrisFolder inbox = IrisServiceLocator.instance().getEntityFactory().createIrisFolder();
			inbox.setName(folderName);
			instance.saveOrUpdate(inbox);
			System.out.println(String.format("%s folder created.", folderName));
		}
	}

	public IrisFolder findByName(String folderName) throws IrisPersistenceException {
		List<IrisFolder> result = doFindByName(folderName);
		if (result.isEmpty()) {
			throw new IrisPersistenceException(String.format("Folder '%s' not found", folderName), null);
		}
		return result.iterator().next();
	}

	public List<IrisFolder> doFindByName(String folderName) throws IrisPersistenceException {
		Query nameQuery = new TermQuery(new Term("name", folderName));
		return findByTerms(new Query[] { nameQuery });
	}

	protected IrisFolder fromDocument(Document d) throws ParseException {
		IrisFolder f = IrisServiceLocator.instance().getEntityFactory().createIrisFolder();
		f.setId(d.get("id"));
		f.setName(d.get("name"));
		return f;
	}

	protected Document toDocument(IrisFolder f) throws Exception {
		Document doc = new Document();
		doc.add(new StringField("id", String.valueOf(f.getId()), Store.YES));
		doc.add(new StringField("name", f.getName(), Store.YES));
		return doc;
	}

	@Override
	public IrisFolder createFolder(String folderName) throws IrisPersistenceException {
		IrisFolder folder = IrisServiceLocator.instance().getEntityFactory().createIrisFolder();
		folder.setName(folderName);
		instance.saveOrUpdate(folder);
		System.out.println(String.format("%s folder created.", folderName));
		return folder;
	}
}