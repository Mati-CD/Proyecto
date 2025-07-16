package org.example.CodigoLogico;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Representa a un equipo cualquiera como participante en el torneo.
 * Cada equipo tiene un nombre, un número máximo de miembros y una lista de integrantes.
 */
public class ParticipanteEquipo extends Participante {
    private List<String> miembros;

    public ParticipanteEquipo(String nombreEquipo,  List<String> miembrosIniciales) {
        super(nombreEquipo);
        this.miembros = new ArrayList<>(miembrosIniciales);
    }

    public void addMiembro(String nombre) {
        if (!miembros.contains(nombre)) {
            miembros.add(nombre);
        }
    }

    public void removeMiembro(String nombre) {
        miembros.remove(nombre);
    }

    public int getCantidadMiembrosActual() {
        return miembros.size();
    }

    public List<String> getMiembros() {
        return Collections.unmodifiableList(miembros);
    }

    @Override
    public String getNombreForTorneo() {
        return getNombre();
    }

    @Override
    public String getDatos() {
        String correoDisplay = (getCorreo() != null && !getCorreo().isEmpty()) ? " - Correo: " + getCorreo() : "";
        return getNombre() + " (Equipo - " + miembros.size() + " miembros: " +
                String.join(", ", miembros) + correoDisplay + ")";
    }

    @Override
    public String toString() {
        return getNombre() + " (Equipo)";
    }
}