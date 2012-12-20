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
	BASIC("lista básica"), OPEN("lista abierta"), CLOSED("lista cerrada"), OTHER_PROFESSION(
			"listas básicas de otras profesiones"), OTHER_REALM_OPEN("listas abiertas de otros reinos"), OTHER_REALM_CLOSED(
			"listas cerradas de otros reinos"), OTHER_REALM_OTHER_PROFESSION("listas básicas de otros reinos"), ARCHANUM(
			"listas abiertas arcanas"), TRIAD("listas básicas de la tríada"), COMPLEMENTARY_TRIAD(
			"listas básicas elementales complementarias"), TRAINING("listas propias de adiestramiento"), OTHER_TRAINING(
			"listas de otros adiestramientos");

	private String tag;

	MagicListType(String tag) {
		this.tag = tag;
	}
	
	public String getTag(){
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
}
