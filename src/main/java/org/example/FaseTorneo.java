package org.example;

import java.util.ArrayList;
import java.util.List;

public class FaseTorneo extends TorneoComponent {
    private final String nombre;
    private final List<TorneoComponent> partidos = new ArrayList<>();

    public FaseTorneo(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String getNombre() {
        return nombre;
    }

    @Override
    public List<TorneoComponent> getComponentes() {
        return new ArrayList<>(partidos);
    }

    @Override
    public void agregar(TorneoComponent component) {
        partidos.add(component);
    }
}