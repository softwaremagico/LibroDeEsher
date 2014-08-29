package com.softwaremagico.librodeesher.basics;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.google.gson.annotations.Expose;
import com.softwaremagico.persistence.StorableObject;

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

@Entity
@Table(name = "T_ROLL")
public class Roll extends StorableObject {
	@Expose
	private Integer firstDice;
	@Expose
	private Integer secondDice;

	public Roll() {
		initialize(10);
	}

	public Roll(Integer faces) {
		initialize(faces);
	}
	
	@Override
	public void resetIds(){
		resetIds(this);
	}

	private void initialize(Integer diceFaces) {
		firstDice = Dice.getRoll(diceFaces);
		secondDice = Dice.getRoll(diceFaces);
	}

	public Integer getFirstDice() {
		return firstDice;
	}

	public Integer getSecondDice() {
		return secondDice;
	}

	public void setFirstDice(Integer firstDice) {
		this.firstDice = firstDice;
	}

	public void setSecondDice(Integer secondDice) {
		this.secondDice = secondDice;
	}

	@Override
	public String toString() {
		return "(" + firstDice + "," + secondDice + ")";
	}

}
