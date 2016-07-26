package br.unb.cic.iris.persistence.lucene;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.StringField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TermQuery;

import br.unb.cic.iris.persistence.IrisPersistenceException;
import br.unb.cic.iris.persistence.lucene.internal.AbstractCollector;
import br.unb.cic.iris.persistence.lucene.internal.IndexManager;

/***
 * added by dPersistenceLucene
 */
public abstract class AbstractDAO<T> {
	protected String type;

	protected class TCollector extends AbstractCollector {
		private IndexSearcher searcher;
		private List<T> result;

		public TCollector(IndexSearcher searcher, List<T> result) {
			this.searcher = searcher;
			this.result = result;
		}

		@Override
		public void tryCollect(int doc) throws Exception {
			Document d = searcher.doc(doc);
			result.add(fromDocument(d));
		}
	}


	public List<T> findAll() throws IrisPersistenceException {
		List<T> result = new ArrayList<>();
		try {
			Query query = new TermQuery(new Term("type", type));
			IndexSearcher searcher = IndexManager.getSearcher();
			searcher.search(query, new TCollector(searcher, result));
		} catch (IOException e) {
			throw new IrisPersistenceException("An error occurred while retrieving all " + type + "s", e);
		}
		return result;
	}

	public T findById(String id) throws IrisPersistenceException {
		Query nameQuery = new TermQuery(new Term("id", id));
		List<T> result = findByTerms(new Query[] { nameQuery });
		if (result.isEmpty()) {
			throw new IrisPersistenceException(String.format("%s not found", type), null);
		}
		return result.iterator().next();
	}

	public Document saveOrUpdate(T t) throws IrisPersistenceException {
		try {
			return saveDocument(toDocument(t));
		} catch (Exception e) {
			throw new IrisPersistenceException("An error occurred while saving " + type + ".", e);
		}
	}

	public void delete(T t) throws IrisPersistenceException {
		try {
			Document d = toDocument(t);
			BooleanQuery q = new BooleanQuery();
			q.add(new TermQuery(new Term("type", type)), Occur.MUST);
			q.add(new TermQuery(new Term("id", d.get("id"))), Occur.MUST);
			IndexWriter writer = IndexManager.getWriter();
			writer.deleteDocuments(q);
			writer.commit();
		} catch (Exception e) {
			throw new IrisPersistenceException("An error occured while deleting " + type, e);
		}
	}

	protected Document saveDocument(Document doc) throws IrisPersistenceException {
		try {
			doc.add(new StringField("type", type, Store.YES));
			IndexWriter writer = IndexManager.getWriter();
			String id = doc.get("id");
			if ("null".equals(id) || "0".equals(id) || id == null) {
				doc.removeField("id");
				doc.add(new StringField("id", java.util.UUID.randomUUID().toString(), Store.YES));
				writer.addDocument(doc);
			} else {
				writer.updateDocument(new Term("id", id), doc);
			}
			writer.commit();
			/*
			 * Commits all changes to an index and closes all associated files.
			 * Note that this may be a costly operation, so, try to re-use a
			 * single writer instead of closing and opening a new one.
			 */
			//TODO
			//writer.close();
			//IndexManager.closeIndex();
			return doc;
		} catch (IOException e) {
			throw new IrisPersistenceException("An error occurred while saving " + type + ".", e);
		}
	}

	public List<T> findByTerms(Query[] queries) throws IrisPersistenceException {
		List<T> result = new ArrayList<>();
		try {
			BooleanQuery query = new BooleanQuery();
			Query typeQuery = new TermQuery(new Term("type", type));
			query.add(typeQuery, Occur.MUST);
			for (Query q : queries) {
				query.add(q, Occur.MUST);
			}
			IndexSearcher searcher = IndexManager.getSearcher();
			searcher.search(query, new TCollector(searcher, result));
		} catch (IOException e) {
			throw new IrisPersistenceException(e.getMessage(), e);
		}
		return result;
	}

	// TODO PersistenceException
	protected abstract T fromDocument(Document doc) throws IrisPersistenceException;

	protected abstract Document toDocument(T t) throws IrisPersistenceException;
}