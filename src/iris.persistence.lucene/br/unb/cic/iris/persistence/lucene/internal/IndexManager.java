package br.unb.cic.iris.persistence.lucene.internal;

import java.io.File;
import java.io.IOException;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.FieldType;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.Version;

/***
 * added by dPersistenceLucene
 */
public class IndexManager {	
	public static final FieldType TYPE_STORED = new FieldType();

	static {
		TYPE_STORED.setIndexed(true);
		TYPE_STORED.setTokenized(true);
		TYPE_STORED.setStored(true);
		TYPE_STORED.setStoreTermVectors(true);
		TYPE_STORED.setStoreTermVectorPositions(true);
		TYPE_STORED.freeze();
	}

	private static Directory index;
	private static IndexWriter writer;
	private static IndexReader reader;

	private IndexManager() {
	}

	public static Directory createIndex(String filepath) throws IOException {
		File path = null;
		if (filepath != null && !filepath.isEmpty()) {
			path = new File(filepath);
		}
		if (path == null) {
			index = new RAMDirectory();
			IndexWriter writer = getWriter();
			writer.commit();
		} else {
			if (!path.exists()) {
				path.mkdir();
				index = FSDirectory.open(path);
				IndexWriter writer = getWriter();
				writer.commit();
			}
			index = FSDirectory.open(path);
		}
		return index;
	}

	public static void setIndex(String filepath) throws IOException {
		File path = null;
		if (filepath != null && !filepath.isEmpty()) {
			path = new File(filepath);
		}
		if (path == null || !path.exists())
			throw new IOException("Couldn't open the specified Lucene index.");
		index = FSDirectory.open(path);
	}

	public static Directory getIndex() throws IOException {
		if (index == null) {
			String path = System.getProperty("user.home") + "/.iris/lucene_idx";
			if ("true".equals(System.getProperty("iris.lucene.ram"))) {
				path = null;
			}
			index = createIndex(path);
		}
		return index;
	}

	public static void closeIndex() throws IOException {
		index.close();
		writer = null;
		index = null;
		reader = null;
	}

	public static IndexWriter getWriter() throws IOException {
		if (writer == null) {
			Analyzer analyzer = new StandardAnalyzer();
			IndexWriterConfig config = new IndexWriterConfig(Version.LATEST, analyzer);
			writer = new IndexWriter(getIndex(), config);
		}
		return writer;
	}

	static IndexReader getReader() throws IOException {
		if (reader == null) {
			reader = DirectoryReader.open(getIndex());
		} else {
			IndexReader r = DirectoryReader.openIfChanged((DirectoryReader) reader);
			if (r != null)
				reader = r;
		}
		return reader;
	}

	public static IndexSearcher getSearcher() throws IOException {
		return new IndexSearcher(getReader());
	}
}