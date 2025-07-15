package org.example.CodigoLogico;

/**
 * Representa a un participante individual en el torneo.
 * Extiende la clase Participante e incluye información adicional como edad y país.
 */
public class ParticipanteIndividual extends Participante {
    private final int edad;
    private final String pais;

    /**
     * Crea un nuevo participante individual con todos sus datos.
     * @param nombre Nombre completo del participante
     * @param edad Edad del participante (debe ser mayor a 0)
     * @param pais País de origen del participante
     */
    public ParticipanteIndividual(String nombre, int edad, String pais) {
        super(nombre);
        this.edad = edad;
        this.pais = pais;
    }

    /**
     * Obtiene la edad del participante.
     * @return Edad del participante
     */
    public int getEdad() {
        return edad;
    }

    /**
     * Obtiene el país del participante.
     * @return País de origen del participante
     */
    public String getPais() {
        return pais;
    }

    /**
     * Devuelve el nombre que se mostrará en el torneo para este participante.
     * Incluye el país entre paréntesis.
     * @return Nombre para mostrar en formato "Nombre (País)"
     */
    @Override
    public String getNombreForTorneo() {
        return getNombre() + " (" + pais + ")";
    }

    /**
     * Devuelve una descripción detallada del participante.
     * @return Información formateada con nombre, edad, país y correo
     */
    @Override
    public String getDatos() {
        return String.format("%s (Individual - %d años, %s - %s)",
                getNombre(), edad, pais, getCorreo());
    }

    /**
     * Devuelve una representación básica del participante.
     * @return Nombre y país del participante
     */
    @Override
    public String toString() {
        return getNombreForTorneo();
    }
}