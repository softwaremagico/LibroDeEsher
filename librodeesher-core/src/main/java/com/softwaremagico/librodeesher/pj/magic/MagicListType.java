package com.softwaremagico.librodeesher.pj.magic;

/*
 * #%L
 * Libro de Esher
 * %%
 * Copyright (C) 2007 - 2012 Softwaremagico
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

public enum MagicListType {
	BASIC("lista básica", "Listas Básicas de Hechizos"), OPEN("lista abierta", "Listas Abiertas de Hechizos"), CLOSED(
			"lista cerrada", "Listas Cerradas de Hechizos"), OTHER_PROFESSION(
			"listas básicas de otras profesiones", "Listas Básicas de Otras Profesiones"), OTHER_REALM_OPEN(
			"listas abiertas de otros reinos", "Listas Abiertas de Otros Reinos"), OTHER_REALM_CLOSED(
			"listas cerradas de otros reinos", "Listas Cerradas de Otros Reinos"), OTHER_REALM_OTHER_PROFESSION(
			"listas básicas de otros reinos", "Listas Básicas de Otros Reinos"), ARCHANUM(
			"listas abiertas arcanas", "Listas Abiertas Arcanas"), TRIAD("listas básicas de la tríada",
			"Listas Básicas de la Tríada"), COMPLEMENTARY_TRIAD("listas básicas elementales complementarias",
			"Listas Básicas Elementales Complementarias"), TRAINING("listas propias de adiestramiento",
			"Listas Hechizos de Adiestramiento"), OTHER_REALM_TRAINING("listas de otros adiestramientos",
			"Listas Hechizos de Adiestramientos de Otros Reinos");

	private String tag;
	private String categoryName;

	MagicListType(String tag, String categoryName) {
		this.tag = tag;
		this.categoryName = categoryName;
	}

	public String getTag() {
		return tag;
	}

	public static MagicListType getMagicType(String tag) {
		tag = tag.toLowerCase().trim();
		for (MagicListType type : MagicListType.values()) {
			if (type.tag.equals(tag)) {
				return type;
			}
		}
		return null;
	}

	public static MagicListType getMagicTypeOfCategory(String categoryName) {
		for (MagicListType type : MagicListType.values()) {
			if (type.categoryName.equals(categoryName)) {
				return type;
			}
		}
		return null;
	}

	public String getCategoryName() {
		return categoryName;
	}
}
