package br.unb.cic.iris.persistence;

import java.io.File;

public interface PersistenceConfiguration {

	public String getPath();
	public boolean reset();
	
	public default void doReset() throws IrisPersistenceException {
		//System.out.println("Deleting: "+getPath());
		File persistenceFile = new File(getPath());
		if(persistenceFile.exists()){
			if(!persistenceFile.delete()){
				throw new IrisPersistenceException("Could not delete file: "+getPath());
			}
		}
	}
	
}
