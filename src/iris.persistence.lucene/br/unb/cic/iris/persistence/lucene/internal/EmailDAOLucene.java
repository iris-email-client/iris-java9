package br.unb.cic.iris.persistence.lucene.internal;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

import org.apache.lucene.document.DateTools;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.SortField;
import org.apache.lucene.search.SortField.Type;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopFieldDocs;

import br.unb.cic.iris.core.SystemFacade;
import br.unb.cic.iris.model.EmailMessage;
import br.unb.cic.iris.model.IrisFolder;
import br.unb.cic.iris.persistence.IEmailDAO;
import br.unb.cic.iris.persistence.PersistenceException;

/***
 * added by dPersistenceLucene
 */
public final class EmailDAOLucene extends AbstractDAO<EmailMessage>implements IEmailDAO {
	private static EmailDAOLucene instance = new EmailDAOLucene();

	private EmailDAOLucene() {
		this.type = "email";
	}

	public static EmailDAOLucene instance() {
		return instance;
	}

	@Override
	public void saveMessage(EmailMessage m) throws PersistenceException {
		saveOrUpdate(m);
	}

	@Override
	public Date lastMessageReceived() throws PersistenceException {
		Date date = null;
		try {
			Query query = new TermQuery(new Term("type", "email"));
			Sort sort = new Sort(new SortField("date", Type.STRING, true));
			IndexSearcher searcher = IndexManager.getSearcher();
			TopFieldDocs docs = searcher.search(query, 1, sort);
			if (docs.totalHits > 0) {
				Document doc = searcher.doc(docs.scoreDocs[0].doc);
				date = DateTools.stringToDate(doc.get("date"));
			}
		} catch (IOException e) {
			throw new PersistenceException("An error occurred while retrieving last message received", e);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	public List<EmailMessage> listMessages(String idFolder) throws PersistenceException {
		Query folderQuery = new TermQuery(new Term("folderId", idFolder));
		return findByTerms(new Query[] { folderQuery });
	}

	protected Document toDocument(EmailMessage m) throws Exception {
		Document doc = new Document();
		doc.add(new StringField("id", String.valueOf(m.getId()), Store.YES));
		doc.add(new TextField("from", m.getFrom(), Store.YES));
		doc.add(new StringField("to", m.getTo(), Store.YES));
		doc.add(new StringField("cc", m.getCc(), Store.YES));
		doc.add(new StringField("bcc", m.getBcc(), Store.YES));
		doc.add(new TextField("subject", m.getSubject(), Store.YES));
		doc.add(new TextField("message", m.getMessage(), Store.YES));
		doc.add(new StringField("date", DateTools.dateToString(m.getDate(), DateTools.Resolution.SECOND), Store.YES));
		doc.add(new StringField("folderId", m.getFolder().getId(), Store.YES));
		return doc;
	}

	protected EmailMessage fromDocument(Document d) throws ParseException {
		EmailMessage m = SystemFacade.instance().getEntityFactory().createEmailMessage();
		m.setId(d.get("id"));
		m.setFrom(d.get("from"));
		m.setTo(d.get("to"));
		m.setCc(d.get("cc"));
		m.setBcc(d.get("bcc"));
		m.setSubject(d.get("subject"));
		m.setMessage(d.get("message"));
		m.setDate(DateTools.stringToDate(d.get("date")));
		IrisFolder f = SystemFacade.instance().getEntityFactory().createIrisFolder();
		f.setId(d.get("folderId"));
		m.setFolder(f);
		return m;
	}

	// public static void main(String[] args) throws PersistenceException {
	// EmailMessage message = new EmailMessage();
	// message.setFrom("alexandrelucchesi@gmail.com");
	// message.setTo("rbonifacio123@gmail.com");
	// message.setCc("jeremiasmg@gmail.com");
	// message.setBcc("somebcc@gmail.com");
	// message.setSubject("Alexandre Lucchesi");
	// message.setMessage("Testing Lucene. :-)");
	// message.setDate(new Date());
	// message.setFolder(new IrisFolder("19", "UnB"));
	// EmailDAOLucene emailDAO = new EmailDAOLucene();
	// emailDAO.saveMessage(message);
	// System.out.println(emailDAO.listMessages("19"));
	// System.out.println(emailDAO.findAll());
	// System.out.println(emailDAO.findById(emailDAO.findAll().iterator().next().getId()));
	// System.out.println(emailDAO.findById(emailDAO.findAll().iterator().next().getId()).getSubject());
	// }
}