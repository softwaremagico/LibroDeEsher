package com.softwaremagico.librodeesher.basics;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

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

@Table(name = "T_ROLL")
public class Roll {
	@Id
	@GeneratedValue
	private Long id; // database id.
	
	@Column
	private Integer firstDice;
	@Column
	private Integer secondDice;

	public Roll() {
		initialize(10);
	}

	public Roll(Integer faces) {
		initialize(faces);
	}

	private void initialize(Integer faces) {
		firstDice = Dice.getRoll(faces);
		secondDice = Dice.getRoll(faces);
	}

	public Integer getFirstDice() {
		return firstDice;
	}

	public Integer getSecondDice() {
		return secondDice;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setFirstDice(Integer firstDice) {
		this.firstDice = firstDice;
	}

	public void setSecondDice(Integer secondDice) {
		this.secondDice = secondDice;
	}

}