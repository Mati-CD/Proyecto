package org.example.CodigoLogico;

import java.util.Objects;

public abstract class Participante {
    private String nombre;
    private String correo;

    public Participante(String nombre) {
        this.nombre = nombre;
        this.correo = nombre.toLowerCase().replace(" ", "") + "@gmail.com";
    }

    public String getNombre() {
        return nombre;
    }

    public String getCorreo() {
        return correo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Participante otroParticipante = (Participante) o;
        return Objects.equals(nombre, otroParticipante.nombre);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nombre);
    }

    public abstract String getNombreForTorneo();
    public abstract String getDatos();
}
