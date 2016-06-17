package br.unb.cic.iris.model.xml.internal;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "IrisStore")
public class IrisMessageStoreXml {
	private List<IrisFolderXml> folders;
	
	public IrisMessageStoreXml(){
		folders = new ArrayList<>(2);
	}

	public IrisMessageStoreXml(List<IrisFolderXml> folders) {		
		this.folders = folders;
	}

	public List<IrisFolderXml> getFolders() {
		return folders;
	}
	
	@XmlElement(name = "Folder")
	public void setFolders(List<IrisFolderXml> folders) {
		this.folders = folders;
	}
	
	public void addFolder(IrisFolderXml folder){
		this.folders.add(folder);
	}
	
	public IrisFolderXml findFolderByPredicate(Predicate<IrisFolderXml> p) {	
		return getFolders().stream()
				.filter(p)
				.findAny()
				.orElse(null);
	}
}
