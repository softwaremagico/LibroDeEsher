package com.softwaremagico.librodeesher.pj.characteristic;

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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.softwaremagico.librodeesher.basics.Dice;
import com.softwaremagico.persistence.StorableObject;

@Entity
@Table(name = "T_APPEARANCE")
public class Appearance extends StorableObject {

	@Column
	private int dicesResult;

	public Appearance() {
		dicesResult = Dice.getRoll(5, 10);
	}

	public int getTotal(int presencePotentialValue) {
		return presencePotentialValue - 25 + dicesResult;
	}

	public int getDicesResult() {
		return dicesResult;
	}

	public void setDicesResult(int dicesResult) {
		this.dicesResult = dicesResult;
	}
}
