package org.example.CodigoLogico;

import java.util.ArrayList;
import java.util.List;

public class ParticipanteEquipo extends Participante{
    private List<String> miembros;
    private int cantidadMiembros;

    public ParticipanteEquipo(String nombreEquipo, int cantidadMiembros) {
        super(nombreEquipo);
        this.cantidadMiembros = cantidadMiembros;
        this.miembros = new ArrayList<>();
    }

    public void addMiembro(String nombre) {
        if (miembros.size() < cantidadMiembros && !miembros.contains(nombre)) {
            miembros.add(nombre);
        }
    }

    public int getCantidadMiembros() {
        return cantidadMiembros;
    }

    public List<String> getEquipo() {
        return new ArrayList<>(miembros);
    }

    @Override
    public String getNombreForTorneo() {
        return getNombre();
    }

    @Override
    public String getDatos() {
        return getNombre() + " (Equipo - " + miembros.size() + "/" + cantidadMiembros + " miembros: " + String.join(", ", miembros) + " - Correo: " + getCorreo() + ")";
    }

    @Override
    public String toString() {
        return getNombre() + " (Equipo)";
    }
}
