package org.example.CodigoLogico;

import java.util.ArrayList;
import java.util.List;

/**
 * Representa una fase dentro de un torneo, que puede contener múltiples componentes de torneo,
 * como participantes individuales, grupos u otras fases.
 * Esta clase implementa el patrón Composite, permitiendonos estructurar el torneo en una jerarquía
 * de fases y elementos individuales de forma flexible.
 */
public class FaseTorneo extends TorneoComponent {

    private final String nombre;
    private final List<TorneoComponent> componentes = new ArrayList<>();

    /**
     * Crea una nueva fase del torneo con el nombre especificado.
     * @param nombre el nombre de la fase del torneo
     */
    public FaseTorneo(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Retorna el nombre de esta fase del torneo.
     *
     * @return el nombre de la fase
     */
    @Override
    public String getNombre() {
        return nombre;
    }

    /**
     * Retorna una copia de la lista de componentes que conforman esta fase.
     *
     * @return lista de componentes del torneo
     */
    @Override
    public List<TorneoComponent> getComponentes() {
        return new ArrayList<>(componentes);
    }

    /**
     * Agrega un nuevo componente (participante, grupo o subfase) a esta fase del torneo.
     * @param componente el componente a agregar
     */
    @Override
    public void agregar(TorneoComponent componente) {
        componentes.add(componente);
    }
}