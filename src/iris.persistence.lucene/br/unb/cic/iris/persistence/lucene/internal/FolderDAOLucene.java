package br.unb.cic.iris.persistence.lucene.internal;

import java.text.ParseException;
import java.util.List;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.StringField;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TermQuery;

import br.unb.cic.iris.core.SystemFacade;
import br.unb.cic.iris.model.IrisFolder;
import br.unb.cic.iris.persistence.IFolderDAO;
import br.unb.cic.iris.persistence.PersistenceException;

/***
 * added by dPersistenceLucene
 */
public class FolderDAOLucene extends AbstractDAO<IrisFolder> implements IFolderDAO {

	public FolderDAOLucene() {
		this.type = "folder";

		try {
			ensureIsCreated(IrisFolder.INBOX);
			ensureIsCreated(IrisFolder.OUTBOX);
		} catch (PersistenceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void ensureIsCreated(String folderName) throws PersistenceException {
		List<IrisFolder> folders = doFindByName(folderName);
		if (folders.isEmpty()) {
			createFolder(folderName);
		}
	}

	public IrisFolder findByName(String folderName) throws PersistenceException {
		List<IrisFolder> result = doFindByName(folderName);
		if (result.isEmpty()) {
			throw new PersistenceException(String.format("Folder '%s' not found", folderName), null);
		}
		return result.iterator().next();
	}

	public List<IrisFolder> doFindByName(String folderName) throws PersistenceException {
		Query nameQuery = new TermQuery(new Term("name", folderName));
		return findByTerms(new Query[] { nameQuery });
	}

	protected IrisFolder fromDocument(Document d) throws ParseException {
		IrisFolder f = SystemFacade.instance().getEntityFactory().createIrisFolder();
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
	public IrisFolder createFolder(String folderName) throws PersistenceException {
		IrisFolder inbox = SystemFacade.instance().getEntityFactory().createIrisFolder();
		inbox.setName(folderName);
		saveOrUpdate(inbox);
		System.out.println(String.format("%s folder created.", folderName));
		return inbox;
	}
}