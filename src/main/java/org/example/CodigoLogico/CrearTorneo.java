package org.example.CodigoLogico;

public class CrearTorneo {
    private String nombre;
    private String disciplina;
    private String tipoDeInscripcion;

    public CrearTorneo(String nombre, String disciplina, String tipoDeInscripcion) {
        this.nombre = nombre;
        this.disciplina = disciplina;
        this.tipoDeInscripcion = tipoDeInscripcion;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDisciplina() {
        return disciplina;
    }

    public String getTipoDeInscripcion() {
        return tipoDeInscripcion;
    }
}
