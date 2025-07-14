package org.example.CodigoLogico;

import java.util.ArrayList;
import java.util.List;

/**
 * Representa a un equipo cualquiera como participante en el torneo.
 * Cada equipo tiene un nombre, un número máximo de miembros y una lista de integrantes.
 */
public class ParticipanteEquipo extends Participante {


    private List<String> miembros;
    private int cantidadMiembros;

    /**
     * Crea un nuevo equipo con nombre y cantidad máxima de integrantes.
     *
     * @param nombreEquipo nombre del equipo
     * @param cantidadMiembros cantidad máxima de miembros permitidos
     */
    public ParticipanteEquipo(String nombreEquipo, int cantidadMiembros) {
        super(nombreEquipo);
        this.cantidadMiembros = cantidadMiembros;
        this.miembros = new ArrayList<>();
    }

    /**
     * Agrega un miembro al equipo si no ha alcanzado el límite y no está repetido.
     *
     * @param nombre nombre del nuevo integrante
     */
    public void addMiembro(String nombre) {
        if (miembros.size() < cantidadMiembros && !miembros.contains(nombre)) {
            miembros.add(nombre);
        }
    }

    /**
     * Devuelve la cantidad máxima de miembros del equipo.
     *
     * @return cantidad máxima de integrantes
     */
    public int getCantidadMiembros() {
        return cantidadMiembros;
    }

    /**
     * Retorna una copia de la lista de miembros actuales del equipo.
     *
     * @return lista de nombres de integrantes
     */
    public List<String> getEquipo() {
        return new ArrayList<>(miembros);
    }

    /**
     * Devuelve el nombre que se mostrará en el torneo para este equipo.
     *
     * @return nombre del equipo
     */
    @Override
    public String getNombreForTorneo() {
        return getNombre();
    }

    /**
     * Devuelve un resumen con la información del equipo y sus miembros.
     *
     * @return descripción textual del equipo
     */
    @Override
    public String getDatos() {
        return getNombre() + " (Equipo - " + miembros.size() + "/" + cantidadMiembros +
                " miembros: " + String.join(", ", miembros) +
                " - Correo: " + getCorreo() + ")";
    }

    /**
     * Devuelve una representación simple del equipo para propósitos generales.
     *
     * @return texto con el nombre del equipo seguido de "(Equipo)"
     */
    @Override
    public String toString() {
        return getNombre() + " (Equipo)";
    }
}