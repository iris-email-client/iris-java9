package br.unb.cic.iris.search.persistence;

import java.util.List;

import br.unb.cic.iris.model.EmailMessage;
import br.unb.cic.iris.persistence.IrisPersistenceException;

public interface SearchDAO {
	public List<EmailMessage> search(String text) throws IrisPersistenceException;
}
