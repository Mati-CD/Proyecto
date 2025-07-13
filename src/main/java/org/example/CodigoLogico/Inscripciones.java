package org.example.CodigoLogico;

import java.util.ArrayList;
import java.util.List;

public class Inscripciones {
    private List<Participante> inscritos;

    public Inscripciones() {
        this.inscritos = new ArrayList<>();
    }

    public void add(Participante participante) {
        if (participante == null) {
            throw new IllegalArgumentException("El participante no puede ser nulo.");
        }
        inscritos.add(participante);
    }

    public List<Participante> getInscritos() {
        return new ArrayList<>(inscritos);
    }
}
