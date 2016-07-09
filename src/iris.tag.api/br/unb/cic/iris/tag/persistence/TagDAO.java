package br.unb.cic.iris.tag.persistence;

import br.unb.cic.iris.persistence.IrisPersistenceException;
import br.unb.cic.iris.tag.model.Tag;

public interface TagDAO {
	public Tag findById(String id) throws IrisPersistenceException;

	public Tag findByName(String name) throws IrisPersistenceException;

	public java.util.List<Tag> findAll() throws IrisPersistenceException;

	public Tag saveOrUpdate(Tag tag) throws IrisPersistenceException;

	public Tag save(Tag tag, String messageId) throws IrisPersistenceException;

	public void delete(Tag t) throws IrisPersistenceException;
}