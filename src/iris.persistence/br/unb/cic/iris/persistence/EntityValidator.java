package br.unb.cic.iris.persistence;

import br.unb.cic.iris.util.StringUtil;

public class EntityValidator {

	public static void checkFolderBeforeCreate(FolderDAO dao, String folderName) throws IrisPersistenceException {
		if(StringUtil.isEmpty(folderName)){
			throw new IrisPersistenceException("Folder name is required");
		}
		
		if(dao.findByName(folderName) != null){
			throw new IrisPersistenceException("Folder '"+folderName+"' already exists");
		}			
	}


}
