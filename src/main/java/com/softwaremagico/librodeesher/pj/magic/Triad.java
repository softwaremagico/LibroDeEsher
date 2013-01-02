package com.softwaremagico.librodeesher.pj.magic;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class Triad {
	private static Hashtable<Element, List<String>> first_triad;
	private static Hashtable<Element, List<String>> second_triad;

	static {
		defineTriads();
	}
	
	private static void defineTriads(){
		first_triad = new Hashtable<>();
		second_triad = new Hashtable<>();

		first_triad.put(Element.FIRE, new ArrayList<String>());
		first_triad.put(Element.ICE, new ArrayList<String>());
		first_triad.put(Element.WATER, new ArrayList<String>());

		second_triad.put(Element.EARTH, new ArrayList<String>());
		second_triad.put(Element.AIR, new ArrayList<String>());
		second_triad.put(Element.LIGHT, new ArrayList<String>());
	}
	
	private static void defineElementalist(){
		first_triad.get(Element.FIRE).add("mago de fuego");
	}
	
	public boolean isSameTriad(Element element){
		
	}
	
	
}
