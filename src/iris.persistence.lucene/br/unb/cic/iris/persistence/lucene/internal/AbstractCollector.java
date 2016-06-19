package br.unb.cic.iris.persistence.lucene.internal;

import java.io.IOException;

import org.apache.lucene.index.AtomicReaderContext;
import org.apache.lucene.search.Collector;
import org.apache.lucene.search.Scorer;

/***
 * added by dPersistenceLucene
 */
public abstract class AbstractCollector extends Collector {
	private int base;

	public void setScorer(Scorer scorer) {
	}

	public boolean acceptsDocsOutOfOrder() {
		return true;
	}

	public void collect(int doc) {
		try {
			tryCollect(doc + base);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	abstract void tryCollect(int doc) throws Exception;

	public void setNextReader(AtomicReaderContext context) throws IOException {
		this.base = context.docBase;
	}
}