package br.unb.cic.iris.model.xml.internal;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import br.unb.cic.iris.model.IrisFolder;

public class TesteRead {
	static String FILE = "/home/pedro/tmp/iris_store.xml";

	static IrisFolderXml getFolderByName(IrisMessageStoreXml store, String folderName){
		return store.getFolders().stream()
			.filter(f -> folderName.equals(f.getName()))
			.findAny()
			.orElse(null);
	}
	
	static void testeConvertList(IrisMessageStoreXml store){
		List<IrisFolder> lista = new ArrayList<>(store.getFolders());
		lista.forEach(f -> System.out.println(f.getName()));
	}
	
	public static void main(String[] args) {
		try {

			File file = new File(FILE);
			JAXBContext jaxbContext = JAXBContext.newInstance(IrisMessageStoreXml.class);

			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			IrisMessageStoreXml store = (IrisMessageStoreXml) jaxbUnmarshaller.unmarshal(file);
			System.out.println(store);

			
			System.out.println("INBOX="+getFolderByName(store, "INBOX"));
			System.out.println("NULL="+getFolderByName(store, "nao"));
			
			testeConvertList(store);
		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}
}
