package br.unb.cic.iris.tag.persistence.lucene;

import br.unb.cic.iris.tag.persistence.TagDAO;
import br.unb.cic.iris.tag.persistence.TagDAOFactory;
import br.unb.cic.iris.tag.persistence.lucene.internal.TagDaoLucene;

public class TagDAOFactoryLucene implements TagDAOFactory {

	@Override
	public TagDAO createTagDAO() {
		return TagDaoLucene.instance();
	}

}
