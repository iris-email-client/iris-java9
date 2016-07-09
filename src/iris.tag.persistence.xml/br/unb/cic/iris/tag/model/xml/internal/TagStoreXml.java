package br.unb.cic.iris.tag.model.xml.internal;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "TagsStore")
public class TagStoreXml {
	private List<TagXml> tags;

	public TagStoreXml() {
		tags = new ArrayList<>();
	}

	public List<TagXml> getEntries() {
		return tags;
	}

	@XmlElement(name = "Tag")
	public void setEntries(List<TagXml> tags) {
		this.tags = tags;
	}

	public TagXml findTagByPredicate(Predicate<TagXml> p) {
		return getEntries()
				.stream()
				.filter(p)
				.findAny()
				.orElse(null);
	}

}
