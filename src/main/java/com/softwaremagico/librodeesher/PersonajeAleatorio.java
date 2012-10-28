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

import com.softwaremagico.librodeesher.gui.MostrarMensaje;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PersonajeAleatorio {

    private Random generator = new Random();
    private final int INTENTOS = 5;
    private int nivel_final;
    private int adiestramientos_escogidos_en_nivel = 0;

    PersonajeAleatorio() {
    }

    /**
     * Obtiene todas los datos del personaje de forma aleatoria.
     */
    public void ObtenerPersonajeAleatorio(int tmp_nivel_final) {
        Esher.aleatorio = true;
        nivel_final = tmp_nivel_final;

        try {
            new LeerRaza();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        Personaje.getInstance().HacerFijoEspecialesRaza();
        new Magia();
        try {
            new LeerProfesion();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        //Caracteristicas
        ObtenerCaracteristicasAleatorias();
        Personaje.getInstance().caracteristicas.ObtenerPotenciales();
        Personaje.getInstance().caracteristicas.ObtenerApariencia();
        Personaje.getInstance().CalcularPuntosDesarrollo();

        //Asignamos reino magia;
        Personaje.getInstance().reino = SeleccionarReinoMagia();
        Personaje.getInstance().ObtenerMagia();

        //Cultura
        ObtenerCulturaAleatoria();
        Personaje.getInstance().ActualizarOrdenCostesArmas();

        //Repartimos los puntos de desarrollo.
        while (Personaje.getInstance().nivel < nivel_final) {
            ObtenerRangosSugeridos();
            GastarPuntosDesarrolloDeFormaAleatoria(true);
            Personaje.getInstance().SubirUnNivel();
            adiestramientos_escogidos_en_nivel = 0;
        }
        //El ultimo nivel.
        ObtenerRangosSugeridos();
        GastarPuntosDesarrolloDeFormaAleatoria(true);
        AsignarPuntosHistorialAleatoriamente();

        //Asignamos los talentos al personaje.
        if (Esher.talentosAleatorio) {
            AsignarTalentosPj();
        }
    }

    /**
     * Selecciona el reino de magia más adecuado al personaje.
     */
    public String SeleccionarReinoMagia() {
        int maxCar = 0;
        int value = 0;
        String reinoElegido = "";
        String reino;
        for (int i = 0; i < Personaje.getInstance().reinosDeProfesion.size(); i++) {
            reino = Personaje.getInstance().reinosDeProfesion.get(i);
            if (reino.equals("Esencia")) {
                value = Personaje.getInstance().caracteristicas.DevolverCaracteristicaDeAbreviatura("Em").Total();
            }
            if (reino.equals("Mentalismo")) {
                value = Personaje.getInstance().caracteristicas.DevolverCaracteristicaDeAbreviatura("Pr").Total();
            }
            if (reino.equals("Canalización")) {
                value = Personaje.getInstance().caracteristicas.DevolverCaracteristicaDeAbreviatura("In").Total();
            }
            if (value >= maxCar) {
                maxCar = value;
                reinoElegido = Personaje.getInstance().reinosDeProfesion.get(i);
            }
        }
        return reinoElegido;
    }

    /**
     * Distribuye los puntos de caracteristicas de forma aleatoria.
     */
    public void ObtenerCaracteristicasAleatorias() {
        List<Caracteristica> listaCaracteristicas = Personaje.getInstance().caracteristicas.ObtenerListaAleatoriaDeCaracteristicas();

        while (Personaje.getInstance().ObtenerPuntosCaracteristicasGastados() < Personaje.getInstance().caracteristicas.totalCaracteristicas) {
            for (int i = 0; i < listaCaracteristicas.size(); i++) {
                Caracteristica car = listaCaracteristicas.get(i);
                if ((generator.nextInt(100) + 1) < (car.ObtenerPuntosTemporal() - Esher.especializacion * 10)
                        && car.ObtenerPuntosTemporal() < (Math.min(90 + Esher.especializacion * 4, 101))
                        && Personaje.getInstance().ObtenerPuntosCaracteristicasGastados(car, 1) <= Personaje.getInstance().caracteristicas.totalCaracteristicas) {
                    car.IncrementarPuntosTemporalSinContemplarPotencial(1);
                }
            }
        }
    }

    public void ObtenerCulturaAleatoria() {
        try {
            new LeerCultura();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        if (Esher.inteligencia) {
            Personaje.getInstance().armas.BarajarInteligentementeTiposArmas();
        } else {
            Personaje.getInstance().armas.BarajarTiposArmas();
        }
        AsignarRangosArmasAleatoriamente();
        AsignarAficionesAleatoriamente();
        AsignarIdiomasAleatoriamente();
        AsignarHechizosAleatoriamente();
    }

    private void AsignarRangosArmasAleatoriamente() {
        List<String> listadoArmas;
        Habilidad hab;
        for (int i = 0; i < Personaje.getInstance().armas.DevolverTotalTiposDeArmas(); i++) {
            Categoria cat = Personaje.getInstance().DevolverCategoriaDeNombre("Armas·" + Personaje.getInstance().armas.DevolverTipoDeArma(i));
            try {
                listadoArmas = Personaje.getInstance().armas.SeleccionarNombreArmasValidasPorCategoriaDeTipo(Personaje.getInstance().armas.DevolverTipoDeArma(i));
                hab = cat.DevolverHabilidadDeNombre(listadoArmas.get(generator.nextInt(listadoArmas.size())));
                if (!hab.noElegirAleatorio) {
                    hab.rangosCultura = Personaje.getInstance().armas.DevolverRangosCulturaTipoArma(Personaje.getInstance().armas.DevolverTipoDeArma(i));
                }
            } catch (Exception npe) {
            }
        }
    }

    void GastarPuntosDesarrolloDeFormaAleatoria(boolean obtenerAdiestramientos) {
        List<Categoria> listaCategoriasBarajada = Esher.BarajarCategorias();
        while (Personaje.getInstance().PuntosDesarrolloNoGastados() > 0 && IntentosAsignarPD() <= INTENTOS) {
            if (obtenerAdiestramientos) {
                ObtenerAdiestramientosSugeridos();
                ObtenerAdiestramientoAleatorio();
            }
            ObtenerRangosAleatorios(listaCategoriasBarajada);
        }
    }

    private void ObtenerRangosSugeridos() {
        for (int i = 0; i < Personaje.getInstance().categorias.size(); i++) {
            Categoria cat = Personaje.getInstance().categorias.get(i);
            for (int k = 0; k < cat.NumeroRangosIncrementables(); k++) {
                if (cat.DevolverRangos() < cat.rangosSugeridos) {
                    if (k == 0) {
                        cat.nuevosRangos++;
                    } else {
                        if ((cat.rangosSugeridos - cat.DevolverRangos()) > (nivel_final - Personaje.getInstance().nivel)) {
                            cat.nuevosRangos++;
                        }
                    }
                }
            }

            for (int j = 0; j < cat.listaHabilidades.size(); j++) {
                Habilidad hab = cat.listaHabilidades.get(j);
                for (int k = 0; k < hab.NumeroRangosIncrementables(); k++) {
                    if (hab.DevolverRangos() < hab.rangosSugeridos) {
                        if (k == 0) {
                            hab.IncrementarNuevosRangos(1);
                        } else {
                            if ((hab.rangosSugeridos - hab.DevolverRangos()) > (nivel_final - Personaje.getInstance().nivel)) {
                                hab.IncrementarNuevosRangos(1);
                            }
                        }
                    }
                }
            }
        }
    }

    private void ObtenerRangosAleatorios(List<Categoria> listaCategoriasBarajada) {
        //Lo barajamos para que haya más aleatoriedad.
        for (int i = 0; i < listaCategoriasBarajada.size(); i++) {
            Categoria cat = listaCategoriasBarajada.get(i);
            if (generator.nextInt(100) + 1 < cat.ProbabilidadSubida()) {
                cat.nuevosRangos++;
            }
            List<Habilidad> habilidadesBarajadas = BarajarHabilidadesDeCategoria(cat);
            for (int j = 0; j < habilidadesBarajadas.size(); j++) {
                Habilidad hab = habilidadesBarajadas.get(j);
                if (generator.nextInt(100) + 1 < hab.ProbabilidadSubida()) {
                    //Contar los hechizos subidos para aplicar el multiplicador por mas de 5 listas.
                    if (hab.nuevosRangos == 0) {
                        hab.multiplicadorCosteHechizos = Personaje.getInstance().DevolverMultiplicadoCosteHechizos();
                    }
                    hab.IncrementarNuevosRangos(1);
                    //Si da opciones de nuevas habilidades, se incluyen ahora.
                    if (hab.habilidadesNuevasPosibles.size() > 0 && cat.NumeroHabilidadesExistes(hab.habilidadesNuevasPosibles) == 0) {
                        cat.AddHabilidad(Habilidad.getSkill(cat, hab.habilidadesNuevasPosibles.get(generator.nextInt(hab.habilidadesNuevasPosibles.size()))));
                    }
                    //Permitimos que una habilidad tenga posibilidades de subir dos rangos.
                    if (Esher.especializacion > 0) {
                        j--;
                    }
                    //Permitimos que el PNJ pueda coger alguna especialización.
                    for (int k = 0; k < hab.especializacionPosible.size(); k++) {
                        //Si no existe ya...
                        if (!hab.especializacion.contains(hab.especializacionPosible.get(k)) && !hab.EsGeneralizada()) {
                            //Se le da una posibilidad de añadirse.
                            if (generator.nextInt(100) < Esher.especializacion) {
                                hab.AñadirEspecializacion(true, hab.especializacionPosible.get(k), "");
                            }
                        }
                    }
                    //O una generalización.
                    if (!hab.EsRestringida() && !hab.especializada) {
                        if (generator.nextInt(100) < -Esher.especializacion) {
                            hab.HacerGeneralizada();
                        }
                    }
                }
            }
        }
        Esher.bucleHabilidades++;
    }

    int IntentosAsignarPD() {
        return Esher.bucleHabilidades;
    }

    private int ProbabilidadCogerAficion(Habilidad hab, int loop) throws NullPointerException {
        if (Esher.inteligencia) {
            if (hab.DevolverCategoria().DevolverRangos() == 0 || hab.DevolverCategoria().DevolverHabilidadesConRangos() > -Esher.especializacion + 1) {
                return 5 * loop;
            }
            return hab.rangosAficiones * (2 + Esher.especializacion + loop) + hab.rangosCultura * (2 + Esher.especializacion + loop)
                    + hab.DevolverCategoria().rangos * 5 + Math.min(Personaje.getInstance().CosteCategoriaYHabilidad(hab.categoriaPadre, 0, hab), 15) - hab.DevolverCategoria().DevolverHabilidadesConRangos() * 5 + 5;
        } else {
            return hab.rangosAficiones * (Esher.especializacion + loop + 2) + hab.rangosCultura * (Esher.especializacion + loop + 2)
                    + hab.DevolverCategoria().rangos + 5;
        }
    }

    private void AsignarAficionesAleatoriamente() {
        int loop = 0;
        while (Personaje.getInstance().DevolverPuntosAficiones() > 0) {
            loop++;
            Personaje.getInstance().listaAficiones = Esher.BarajarLista(Personaje.getInstance().listaAficiones);
            for (int i = Personaje.getInstance().listaAficiones.size() - 1; i >= 0; i--) {
                String af = Personaje.getInstance().listaAficiones.get(i);
                Habilidad hab = Personaje.getInstance().DevolverHabilidadDeNombre(af);
                try {
                    if (generator.nextInt(100) + 1 < ProbabilidadCogerAficion(hab, loop) && Personaje.getInstance().DevolverPuntosAficiones() > 0 && !hab.noElegirAleatorio) {
                        hab.rangosAficiones++;
                    }
                } catch (NullPointerException npe) {
                    MostrarMensaje.showErrorMessage("Afición " + af + " no encontrada", "Personaje Aleatorio");
                }
            }
        }
    }

    private int ProbabilidadCogerIdiomaHablado(IdiomaCultura id) {
        if ((id.rangosNuevosHablado + id.hablado + 1 > id.maxHabladoCultura)
                || (id.rangosNuevosHablado + id.hablado + 1 > 10)) {
            return 0;
        }
        if (Esher.inteligencia) {
            return (id.rangosNuevosHablado + id.hablado + id.rangosNuevosEscritos + id.escrito) * (Esher.especializacion) + 15;
        } else {
            return (id.rangosNuevosHablado + id.hablado + id.rangosNuevosEscritos + id.escrito) * (3 + Esher.especializacion) + 5;
        }
    }

    private int ProbabilidadCogerIdiomaEscrito(IdiomaCultura id) {
        if ((id.rangosNuevosEscritos + id.escrito + 1 > id.maxEscritoCultura)
                || (id.rangosNuevosEscritos + id.escrito + 1 > 10)) {
            return 0;
        }
        if (Esher.inteligencia) {
            return (id.rangosNuevosHablado + id.hablado + id.rangosNuevosEscritos + id.escrito) * (Esher.especializacion) + 15;
        } else {
            return (id.rangosNuevosHablado + id.hablado + id.rangosNuevosEscritos + id.escrito) * (3 + Esher.especializacion) + 5;
        }
    }

    private void AsignarIdiomasAleatoriamente() {
        while (Personaje.getInstance().DevolverPuntosIdiomaCultura() > 0) {
            IdiomaCultura id = Personaje.getInstance().idiomasCultura.Get(generator.nextInt(Personaje.getInstance().idiomasCultura.Size()));
            if (generator.nextInt(100) + 1 < ProbabilidadCogerIdiomaHablado(id) && Personaje.getInstance().DevolverPuntosIdiomaCultura() > 0) {
                id.rangosNuevosHablado++;
            }
            if (generator.nextInt(100) + 1 < ProbabilidadCogerIdiomaEscrito(id) && Personaje.getInstance().DevolverPuntosIdiomaCultura() > 0) {
                id.rangosNuevosEscritos++;
            }
        }

        //Se copian los rangos a la habilidad correspondiente.
        Habilidad hab;
        Categoria cat = Personaje.getInstance().DevolverCategoriaDeNombre("Comunicación");
        for (int i = 0; i < Personaje.getInstance().idiomasCultura.Size(); i++) {
            IdiomaCultura idi = Personaje.getInstance().idiomasCultura.Get(i);
            if (idi.DevolverValorHablado() > 0) {
                try {
                    hab = cat.DevolverHabilidadDeNombre("Hablar " + idi.nombre);
                    hab.rangos = idi.DevolverValorHablado();
                } catch (NullPointerException npe) {
                    hab = Habilidad.getSkill(cat, "Hablar " + idi.nombre);
                    hab.rangos = idi.DevolverValorHablado();
                    cat.AddHabilidad(hab);
                }
            }
            if (idi.DevolverValorEscrito() > 0) {
                try {
                    hab = cat.DevolverHabilidadDeNombre("Escribir " + idi.nombre);
                    hab.rangos = idi.DevolverValorEscrito();
                } catch (NullPointerException npe) {
                    hab = Habilidad.getSkill(cat, "Escribir " + idi.nombre);
                    hab.rangos = idi.DevolverValorHablado();
                    cat.AddHabilidad(hab);
                }
            }
        }
    }

    private void AsignarHechizosAleatoriamente() {
        Categoria cat = Personaje.getInstance().DevolverCategoriaDeNombre("Listas Abiertas de Hechizos");
        int n = cat.listaHabilidades.size();
        if (n < 1) {
            n = 1;
        }
        int hechizoSeleccionado = generator.nextInt(n);
        try {
            Habilidad hab = cat.listaHabilidades.get(hechizoSeleccionado);
            Personaje.getInstance().AsignarListaHechizosCultura(hab);
        } catch (IndexOutOfBoundsException iobe) {
        }
    }

    private int ProbabilidadCogerAdiestramiento(String tmp_adiestramiento) {
        LeerAdiestramientos ad = new LeerAdiestramientos(tmp_adiestramiento, false);
        int cost = ad.DevolverCosteDeAdiestramiento();
        //No queremos adiestramientos de reinos de magia distintos al nuestro.
        if (!ad.reino) {
            return 0;
        }
        if (cost > Personaje.getInstance().PuntosDesarrolloNoGastados()) {
            return 0;
        }

        int probabilidad = ((28 - cost) * 2 + Personaje.getInstance().nivel - ((Personaje.getInstance().adiestramientosAntiguos.size()
                + (Esher.especializacion)) * 20));

        if (Personaje.getInstance().costesAdiestramientos.EsAdiestramientoPreferido(tmp_adiestramiento)) {
            probabilidad += 25;
        }

        if (probabilidad < 1 && (Personaje.getInstance().adiestramientosAntiguos.size() < Personaje.getInstance().nivel / 10)) {
            probabilidad = 1;
        }

        if (Personaje.getInstance().costesAdiestramientos.EsAdiestramientoProhibido(tmp_adiestramiento)) {
            probabilidad -= 1500;
        }

        //Los elementalistas tienen que elegir una opción de adiestramiento del reino.
        if (Personaje.getInstance().EsProfesionGuiaElemental() && EsAdiestramientoDeElementalista(tmp_adiestramiento)
                && Personaje.getInstance().adiestramientosAntiguos.isEmpty()) {
            probabilidad += 1000;
        }
        return probabilidad / (adiestramientos_escogidos_en_nivel + 1);
    }

    private void AñadirAdiestramientoAleatorio(String tmp_adiestramiento) {
        try {
            new LeerAdiestramientos(tmp_adiestramiento, false, true);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        //Seleccionar aleatoriamente las habilidades.
        Personaje.getInstance().adiestramiento.RepartirRangosEnCategoriasDeFormaAleatoria();

        //Seleccionar aleatoriamente los aumentos de características.
        Personaje.getInstance().adiestramiento.RepartirCaracterísticasParaSubirAleatoriamente();

        //Aceptar los cambios.
        Personaje.getInstance().ConfirmarAdiestramiento();
        adiestramientos_escogidos_en_nivel++;
    }

    private List<String> BarajarAdiestramientos() {
        List<String> adiestramientos = Esher.BarajarLista(Esher.DevolverAdiestramientosPosibles());
        List<String> adAux = new ArrayList<>();
        if (Personaje.getInstance().EsProfesionGuiaElemental()) {
            for (int i = 0; i < adiestramientos.size(); i++) {
                String ad = adiestramientos.get(i);
                if (EsAdiestramientoDeElementalista(ad)) {
                    adAux.add(ad);
                    adiestramientos.remove(i);
                    i--;
                }
            }
        }
        for (int j = 0; j < adiestramientos.size(); j++) {
            adAux.add(adiestramientos.get(j));
        }
        return adAux;
    }

    private boolean EsAdiestramientoDeElementalista(String adiestramiento) {
        return adiestramiento.contains("Mago de") || adiestramiento.contains("Elemental");
    }

    private void ObtenerAdiestramientoAleatorio() {
        List<String> adiestramientos = BarajarAdiestramientos();
        for (int i = 0; i < adiestramientos.size(); i++) {
            String adiest = adiestramientos.get(i);
            int probabilidad = ProbabilidadCogerAdiestramiento(adiest);
            if (generator.nextInt(100) < probabilidad) {
                AñadirAdiestramientoAleatorio(adiest);
            }
        }
    }

    private void ObtenerAdiestramientosSugeridos() {
        for (int i = 0; i < Personaje.getInstance().adiestramientosSugeridos.size(); i++) {
            //Adiestramiento sugerido
            String adiest = Personaje.getInstance().adiestramientosSugeridos.get(i);
            if (!Personaje.getInstance().adiestramientosAntiguos.contains(adiest)) {
                if (adiestramientos_escogidos_en_nivel
                        < Math.ceil(Personaje.getInstance().adiestramientosSugeridos.size()) / nivel_final) {
                    LeerAdiestramientos ad = new LeerAdiestramientos(adiest, false);
                    int cost = ad.DevolverCosteDeAdiestramiento();
                    if (cost <= Personaje.getInstance().PuntosDesarrolloNoGastados()) {
                        AñadirAdiestramientoAleatorio(adiest);
                    }
                }
            }
        }
    }

    private void AsignarPuntosHistorialAleatoriamente() {
        while (Personaje.getInstance().historial > Personaje.getInstance().DevolverPuntosHistorialGastados()) {
            int sig = generator.nextInt(Personaje.getInstance().categorias.size());
            Categoria cat = Personaje.getInstance().categorias.get(sig);
            int prob = generator.nextInt(100);
            if (prob < cat.Total() - cat.costeRango[0]) {
                cat.historial = true;
            } else {
                if (!cat.historial) {
                    if (cat.listaHabilidades.size() > 0) {
                        sig = generator.nextInt(cat.listaHabilidades.size());
                        Habilidad hab = cat.listaHabilidades.get(sig);
                        if (prob < hab.Total() - cat.costeRango[0]) {
                            hab.historial = true;
                        }
                    }
                }
            }
        }
    }

    private int ProbabilidadObtenerTalento(Talento talento) {
        //No puede existir un talento similar pero de otro grado.
        if (Personaje.getInstance().ExisteTalentoSimilar(talento)) {
            return 0;
        }
        //Para talentos;
        if (talento.coste > 0) {
            if (talento.coste > Personaje.getInstance().DevolverPuntosTalentosRestantes()) {
                return 0;
            }
            return Personaje.getInstance().EsTalentoAdecuado(talento) / (Math.max(3 - Esher.especializacion, 1));
        }
        //Para defectos:
        if (Personaje.getInstance().EsDefectoAdecuado(talento) && Personaje.getInstance().DevolverPuntosTalentosRestantes() < 30) {
            return 5;
        }
        //Otros
        return 0;
    }

    private void AsignarTalentosPj() {
        int bucle = 0;
        int next = 0;
        Personaje.getInstance().talentos = new ArrayList<>();
        //esher.talentos = new Talentos(esher);
        List<Talento> talentosBarajados = Esher.talentos.BarajarTalentos();
        while (Personaje.getInstance().DevolverPuntosTalentosRestantes() > 0 && bucle < 20) {
            int prob = generator.nextInt(100);
            if (next < talentosBarajados.size()) {
                //Si tiene que escoger una habilidad, escoge una al azar.
                try {
                    if (talentosBarajados.get(next).bonusCategoriaHabilidadElegir.listadoCategoriasHabilidadesPosiblesAElegir.size() > 1) {
                        talentosBarajados.get(next).bonusCategoriaHabilidadElegir.SeleccionarAlAzar();
                    }
                } catch (NullPointerException npe) {
                }
                if (prob < ProbabilidadObtenerTalento(talentosBarajados.get(next))) {
                    Personaje.getInstance().talentos.add(talentosBarajados.get(next));
                    //Si tiene que escoger una habilidad común de entre una categoría
                    for (int j = 0; j < talentosBarajados.get(next).bonusCategoria.size(); j++) {
                        if (talentosBarajados.get(next).bonusCategoria.get(j).habilidadComun) {
                            talentosBarajados.get(next).bonusCategoria.get(j).habilidadEscogida =
                                    Personaje.getInstance().DevolverCategoriaDeNombre(talentosBarajados.get(next).bonusCategoria.get(j).nombre).DevolverHabilidadAlAzar();
                        }
                    }
                    talentosBarajados.remove(next);
                    next--;
                }
                next++;
            } else {
                next = 0;
                bucle++;
            }
        }
    }

    private List<Habilidad> BarajarHabilidadesDeCategoria(Categoria cat) {
        List<Habilidad> listaBarajada = new ArrayList<>();
        List<Habilidad> listaOriginal = new ArrayList<>();
        listaOriginal.addAll(cat.listaHabilidades);

        int pos = 0;
        while (listaOriginal.size() > 0) {
            int elemento = generator.nextInt(listaOriginal.size());
            listaBarajada.add(listaOriginal.get(elemento));
            listaOriginal.remove(elemento);
            pos++;
        }
        return listaBarajada;
    }
}
