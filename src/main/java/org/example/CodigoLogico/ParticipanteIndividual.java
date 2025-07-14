package org.example.CodigoLogico;

/**
 * Representa a un participante individual en el torneo.
 * Incluye información adicional como país y edad.
 */
public class ParticipanteIndividual extends Participante {

    /**
     * Crea un participante individual con su nombre, edad y país.
     *
     * @param nombre nombre del participante
     */
    public ParticipanteIndividual(String nombre) {
        super(nombre);

    }

    /**
     * Devuelve el nombre que se mostrará en el torneo para este participante.
     *
     * @return nombre del participante
     */
    @Override
    public String getNombreForTorneo() {
        return getNombre();
    }

    /**
     * Devuelve una descripción detallada del participante.
     *
     * @return información formateada con nombre, país, edad y correo
     */
    @Override
    public String getDatos() {
        return getNombre() + " (Individual - " + getCorreo() + ")";
    }

    /**
     * Devuelve una representación básica del participante para interfaces gráficas.
     *
     * @return nombre seguido del país entre paréntesis
     */
    @Override
    public String toString() {
        return getNombre();
    }
}