package com.softwaremagico.librodeesher.pj.magic;
/*
 * #%L
 * Libro de Esher
 * %%
 * Copyright (C) 2007 - 2013 Softwaremagico
 * %%
 * This software is designed by Jorge Hortelano Otero. Jorge Hortelano Otero
 * <softwaremagico@gmail.com> C/Quart 89, 3. Valencia CP:46008 (Spain).
 *  
 * This program is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later
 * version.
 *  
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *  
 * You should have received a copy of the GNU General Public License along with
 * this program; If not, see <http://www.gnu.org/licenses/gpl-3.0.html>.
 * #L%
 */

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class Triad {
	private static Hashtable<Element, List<String>> first_triad;
	private static Hashtable<Element, List<String>> second_triad;

	static {
		defineTriads();
		defineElementalist();
	}

	private static void defineTriads() {
		first_triad = new Hashtable<>();
		second_triad = new Hashtable<>();

		first_triad.put(Element.FIRE, new ArrayList<String>());
		first_triad.put(Element.ICE, new ArrayList<String>());
		first_triad.put(Element.WATER, new ArrayList<String>());

		second_triad.put(Element.EARTH, new ArrayList<String>());
		second_triad.put(Element.AIR, new ArrayList<String>());
		second_triad.put(Element.LIGHT, new ArrayList<String>());
	}

	private static void defineElementalist() {
		first_triad.get(Element.FIRE).add("mago del fuego");
		first_triad.get(Element.ICE).add("mago del hielo");
		first_triad.get(Element.WATER).add("mago del agua");

		second_triad.get(Element.EARTH).add("mago de la tierra");
		second_triad.get(Element.AIR).add("mago del aire");
		second_triad.get(Element.LIGHT).add("mago de la luz");
	}

	public static List<Element> getSameTriadElements(Element element) {
		List<Element> list = new ArrayList<>();
		if (first_triad.keySet().contains(element)) {
			list.addAll(first_triad.keySet());
		} else {
			list.addAll(second_triad.keySet());
		}
		list.remove(element);
		return list;
	}

	public static List<Element> getOtherTriadElements(Element element) {
		List<Element> list = new ArrayList<>();
		if (first_triad.keySet().contains(element)) {
			list.addAll(second_triad.keySet());
		} else {
			list.addAll(first_triad.keySet());
		}
		return list;
	}

	public static List<String> getSameTriadTrainings(Element element, String characterTraining) {
		List<String> list = new ArrayList<>();
		if (first_triad.keySet().contains(element)) {
			for (Element triadElement : first_triad.keySet()) {
				list.addAll(first_triad.get(triadElement));
			}
		} else {
			for (Element triadElement : second_triad.keySet()) {
				list.addAll(second_triad.get(triadElement));
			}
		}
		list.remove(characterTraining);
		return list;
	}

	public static List<String> getOtherTriadTrainings(Element element, String characterTraining) {
		List<String> list = new ArrayList<>();
		if (first_triad.keySet().contains(element)) {
			for (Element triadElement : second_triad.keySet()) {
				list.addAll(second_triad.get(triadElement));
			}
		} else {
			for (Element triadElement : first_triad.keySet()) {
				list.addAll(first_triad.get(triadElement));
			}
		}
		list.remove(characterTraining);
		return list;
	}

}
