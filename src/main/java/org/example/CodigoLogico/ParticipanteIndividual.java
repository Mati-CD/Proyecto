package org.example.CodigoLogico;

public class ParticipanteIndividual extends Participante {
    private String pais;
    private int edad;

    public ParticipanteIndividual(String nombre, int edad, String pais) {
        super(nombre);
        this.pais = pais;
        this.edad = edad;
    }

    public String getPais() {
        return pais;
    }

    public int getEdad() {
        return edad;
    }

    @Override
    public String getNombreForTorneo() {
        return getNombre();
    }

    @Override
    public String getDatos() {
        String paisStr = (pais == null || pais.isEmpty()) ? "Sin país" : pais;
        return getNombre() + " (Individual - " + paisStr + " - " + edad + " años - " + getCorreo() + ")";
    }

    @Override
    public String toString() {
        return getNombre() + " (" + (pais != null ? pais : "Sin país") + ")";
    }
}