package org.example.CodigoLogico;

public class ParticipanteIndividual extends Participante{
    private String pais;
    private String edad;

    public ParticipanteIndividual(String nombre, String edad, String pais) {
        super(nombre);
        this.pais = pais;
        this.edad = edad;
    }

    public String getPais() {
        return pais;
    }

    public String getEdad() {
        return edad;
    }

    @Override
    public String getNombreForTorneo() {
        return getNombre();
    }

    @Override
    public String getDatos() {
        return getNombre() + " (Individual - " + pais + " - " + edad + " años - " + getCorreo() + ")";
    }

    @Override
    public String toString() {
        return getNombre() + " (" + pais + ")";
    }
}
