package br.unb.cic.iris.tag.model;

public interface TagEntityFactory {
	public Tag createTag();

	public Tag createTag(String name);
}
