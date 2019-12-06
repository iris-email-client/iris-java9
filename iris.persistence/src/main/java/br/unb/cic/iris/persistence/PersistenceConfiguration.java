package br.unb.cic.iris.persistence;

import java.io.File;

public interface PersistenceConfiguration {

	public String getPath();
	public boolean reset();
	
	public default void doReset() throws IrisPersistenceException {		
		File persistenceFile = new File(getPath());
		if(persistenceFile.exists()){
			delete(persistenceFile);
		}
	}
	
	default void delete(File file){
		if(file.isDirectory()){
			for(File f: file.listFiles()){
				delete(f);
			}			
		}
		file.delete();		
	}
	
}
