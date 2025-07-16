package org.example.CodigoLogico;

import java.util.Objects;

/**
 * Clase abstracta que representa a un participante en el torneo.
 * Puede ser implementada por clases concretas como participante individual o grupo.
 */
public abstract class Participante {

    private String nombre;
    private String correo;

    /**
     * Constructor que inicializa el nombre y genera un correo por defecto.
     *
     * @param nombre el nombre del participante
     */
    public Participante(String nombre) {
        this.nombre = nombre;
        this.correo = nombre.toLowerCase().replace(" ", "") + "@gmail.com";
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getNombre() {
        return nombre;
    }

    public String getCorreo() {
        return correo;
    }

    /**
     * Compara si dos participantes son iguales según su nombre.
     *
     * @param o el objeto con el que se compara
     * @return true si tienen el mismo nombre, false en caso contrario
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Participante otroParticipante = (Participante) o;
        return Objects.equals(nombre, otroParticipante.nombre);
    }

    /**
     * Calcula el hash code basado en el nombre.
     *
     * @return el código hash del participante
     */
    @Override
    public int hashCode() {
        return Objects.hash(nombre);
    }

    /**
     * Devuelve el nombre que debe mostrarse en el torneo (puede diferir del nombre interno).
     *
     * @return nombre representativo en el torneo
     */
    public abstract String getNombreForTorneo();

    /**
     * Devuelve los datos detallados del participante.
     *
     * @return información en formato de texto
     */
    public abstract String getDatos();
}