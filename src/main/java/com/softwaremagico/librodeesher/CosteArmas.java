/*
This software is designed by Jorge Hortelano Otero.
softwaremagico@gmail.com
Copyright (C) 2007 Jorge Hortelano Otero.
C/Botanico 12, 1. Valencia CP:46008 (Spain).
This program is free software; you can redistribute it and/or
modify it under the terms of the GNU General Public License
as published by the Free Software Foundation; either version 2
of the License, or (at your option) any later version.
This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.
You should have received a copy of the GNU General Public License
along with this program; if not, write to the Free Software
Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
Created on march of 2008.
 */

package com.softwaremagico.librodeesher;
/*
 * #%L
 * Libro de Esher
 * %%
 * Copyright (C) 2008 - 2012 Softwaremagico
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

import com.softwaremagico.files.DirectorioRolemaster;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author jorge
 */
class CosteArmas implements Serializable {
    List<int[]> costeRango;
    Esher esher;
    //DirectorioRolemaster directorioRolemaster;

    CosteArmas(Esher tmp_esher) {
        //directorioRolemaster = tmp_directorioRolemaster;
        esher = tmp_esher;
        costeRango = new ArrayList<>();
    }

    private void ResetearCostesRangos() throws Exception {
        costeRango = new ArrayList<>();
        for (int i = 0; i < TiposArmasDisponibles(); i++) {
            int[] costes = new int[3];
            costeRango.add(costes);
        }
    }
    
    public void AñadirCosteRango(int[] coste) {
        costeRango.add(coste);
    }

    public void CambiarCosteRango(int orden, int[] coste) {
        if(orden<costeRango.size()) costeRango.set(orden, coste);
        else AñadirCosteRango(coste);
    }

    public int[] DevolverCosteRango(int orden) {
        return costeRango.get(orden);
    }

    /**
     * Muestra aquellas armas que existen en ficheros de armas y también son categorias.
     */
    private int TiposArmasDisponibles() throws Exception {
        List<String> ficherosArmas = DirectorioRolemaster.TiposArmasDisponibles();
        return ficherosArmas.size();
    }
}
