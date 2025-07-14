package org.example.CodigoLogico;

/**
 * Representa a un participante individual en el torneo.
 * Incluye información adicional como país y edad.
 */
public class ParticipanteIndividual extends Participante {
    private String pais;
    private int edad;

    /**
     * Crea un participante individual con su nombre, edad y país.
     *
     * @param nombre nombre del participante
     * @param edad edad del participante
     * @param pais país de origen del participante
     */
    public ParticipanteIndividual(String nombre, int edad, String pais) {
        super(nombre);
        this.pais = pais;
        this.edad = edad;
    }

    /**
     * Devuelve el país del participante.
     *
     * @return país de origen
     */
    public String getPais() {
        return pais;
    }

    /**
     * Devuelve la edad del participante.
     *
     * @return edad del participante
     */
    public int getEdad() {
        return edad;
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
        String paisStr = (pais == null || pais.isEmpty()) ? "Sin país" : pais;
        return getNombre() + " (Individual - " + paisStr + " - " + edad + " años - " + getCorreo() + ")";
    }

    /**
     * Devuelve una representación básica del participante para interfaces gráficas.
     *
     * @return nombre seguido del país entre paréntesis
     */
    @Override
    public String toString() {
        return getNombre() + " (" + (pais != null ? pais : "Sin país") + ")";
    }
}