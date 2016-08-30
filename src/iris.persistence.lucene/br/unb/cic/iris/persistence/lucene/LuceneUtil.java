package br.unb.cic.iris.persistence.lucene;

import java.io.IOException;

import br.unb.cic.iris.persistence.IrisPersistenceException;
import br.unb.cic.iris.persistence.PersistenceConfiguration;
import br.unb.cic.iris.persistence.lucene.internal.DefaultLuceneConfig;
import br.unb.cic.iris.persistence.lucene.internal.IndexManager;

public class LuceneUtil {
	private static LuceneUtil instance;
	private PersistenceConfiguration config;

	private LuceneUtil() throws IrisPersistenceException {
		setConfig(new DefaultLuceneConfig());
	}

	public static LuceneUtil instance() throws IrisPersistenceException {
		if (instance == null) {
			instance = new LuceneUtil();
		}
		return instance;
	}

	public void setConfig(PersistenceConfiguration config) throws IrisPersistenceException {
		this.config = config;		
		try {
			IndexManager.closeIndex();
			if (config.reset()) {
				config.doReset();
			}
			IndexManager.createIndex(config.getPath());
		} catch (IOException e) {
			throw new IrisPersistenceException("Error creating lucene index: "+e.getMessage(), e);
		}
	}
	
	public PersistenceConfiguration getConfig(){
		return config;
	}
	
}
