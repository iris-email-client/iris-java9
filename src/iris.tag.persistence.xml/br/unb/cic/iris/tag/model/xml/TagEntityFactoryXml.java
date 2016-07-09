package br.unb.cic.iris.tag.model.xml;

import br.unb.cic.iris.tag.model.Tag;
import br.unb.cic.iris.tag.model.TagEntityFactory;
import br.unb.cic.iris.tag.model.xml.internal.TagXml;

public class TagEntityFactoryXml implements TagEntityFactory {

	@Override
	public Tag createTag() {
		return new TagXml();
	}

	@Override
	public Tag createTag(String name) {
		return new TagXml(name);
	}

}
