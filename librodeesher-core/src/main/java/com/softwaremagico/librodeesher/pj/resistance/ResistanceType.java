package com.softwaremagico.librodeesher.pj.resistance;
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

public enum ResistanceType {
	CANALIZATION("Canalización", "Cn"), ESSENCE("Esencia", "Es"), MENTALISM("Mentalismo", "Me"), PSIONIC("Psiónico", "Ps"), POISON("Veneno", "Vn"), DISEASE("Enfermedad", "Ef"), COLD("Frío", "Fr"), HOT("Calor", "Ca"), FEAR("Miedo", "Mi");
	
	private String tag;
	private String abbreviature;
	
	ResistanceType(String tag, String abbreviature){
		this.tag = tag;
		this.abbreviature = abbreviature;
	}
	
	public String getTag(){
		return tag;
	}
	
	public String getAbbreviature(){
		return abbreviature;
	}
	
    public static ResistanceType getResistancesType(String tag){
        for(ResistanceType type : ResistanceType.values()){
            if(type.tag.equals(tag)){
                return type;
            }
        }
        return null;
    }
}
