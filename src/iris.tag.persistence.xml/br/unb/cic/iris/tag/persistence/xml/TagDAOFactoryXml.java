package br.unb.cic.iris.tag.persistence.xml;

import br.unb.cic.iris.tag.persistence.TagDAO;
import br.unb.cic.iris.tag.persistence.TagDAOFactory;
import br.unb.cic.iris.tag.persistence.xml.internal.TagDaoXml;

public class TagDAOFactoryXml implements TagDAOFactory {

	@Override
	public TagDAO createTagDAO() {
		return new TagDaoXml();
	}

}
