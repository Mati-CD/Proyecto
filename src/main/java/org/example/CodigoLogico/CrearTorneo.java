package org.example.CodigoLogico;

import java.util.Date;

public class CrearTorneo {
    private String nombre;
    private String disciplina;
    private String tipoDeInscripcion;
    private Date fecha;

    public CrearTorneo(String nombre, String disciplina, String tipoDeInscripcion) {
        this.nombre = nombre;
        this.disciplina = disciplina;
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
