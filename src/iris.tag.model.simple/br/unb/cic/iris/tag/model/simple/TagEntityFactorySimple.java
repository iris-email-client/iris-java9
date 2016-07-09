package br.unb.cic.iris.tag.model.simple;

import br.unb.cic.iris.tag.model.TagEntityFactory;
import br.unb.cic.iris.tag.model.simple.internal.Tag;

public class TagEntityFactorySimple implements TagEntityFactory {

	public TagEntityFactorySimple() {
	}

	@Override
	public br.unb.cic.iris.tag.model.Tag createTag() {		
		return new Tag();
	}

	@Override
	public br.unb.cic.iris.tag.model.Tag createTag(String name) {
		return new Tag(name);
	}

}
