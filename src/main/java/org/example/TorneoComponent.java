package org.example;

import java.util.List;

public abstract class TorneoComponent {
    public abstract String getNombre();
    public abstract List<TorneoComponent> getComponentes();
    public abstract void agregar(TorneoComponent component);
}