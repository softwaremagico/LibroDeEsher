package com.softwaremagico.librodeesher.pj.magic;

public enum Element {
	FIRE("fuego"), ICE("hielo"), LIGHT("luz"), WATER("agua"), EARTH("tierra"), AIR("aire");

	private String tag;

	Element(String tag) {
		this.tag = tag;
	}
	
	public static Element getElement(String profession){
		for(Element element : Element.values()){
			if(profession.toLowerCase().contains(element.tag)){
				return element;
			}
		}
		return null;
	}
}
