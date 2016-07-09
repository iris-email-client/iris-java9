package br.unb.cic.iris.tag.persistence.jdbc;

import br.unb.cic.iris.exception.IrisUncheckedException;
import br.unb.cic.iris.persistence.IrisPersistenceException;
import br.unb.cic.iris.tag.persistence.TagDAO;
import br.unb.cic.iris.tag.persistence.TagDAOFactory;
import br.unb.cic.iris.tag.persistence.jdbc.internal.TagDaoJdbc;

public class TagDAOFactoryJdbc implements TagDAOFactory {

	@Override
	public TagDAO createTagDAO() {
		try {
			return TagDaoJdbc.instance();
		} catch (IrisPersistenceException e) {
			throw new IrisUncheckedException("Could not create TagDaoJdbc: " + e.getMessage(), e);
		}
	}

}
