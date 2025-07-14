package org.example.CodigoLogico;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase encargada de manejar la lista de participantes inscritos en un torneo.
 * Permite agregar nuevos participantes y recuperar la lista de inscritos.
 */
public class Inscripciones {

    private List<Participante> inscritos;

    public Inscripciones() {
        this.inscritos = new ArrayList<>();
    }

    /**
     * Agrega un nuevo participante a la lista de inscritos.
     *
     * @param participante el participante a agregar
     * @throws IllegalArgumentException si el participante es nulo
     */
    public void add(Participante participante) {
        if (participante == null) {
            throw new IllegalArgumentException("El participante no puede ser nulo.");
        }
        inscritos.add(participante);
    }

    /**
     * Devuelve una copia de la lista de participantes inscritos.
     *
     * @return lista de participantes inscritos
     */
    public List<Participante> getInscritos() {
        return new ArrayList<>(inscritos);
    }
}