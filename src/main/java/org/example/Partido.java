package org.example;

import java.util.Collections;
import java.util.List;

public class Partido extends TorneoComponent {
    private final String jugador1;
    private final String jugador2;
    private final String ronda;
    private String ganador;
    private String resultado;
    private boolean resultadoRegistrado = false; // Nuevo campo

    public Partido(String jugador1, String jugador2, String ronda) {
        this.jugador1 = jugador1;
        this.jugador2 = jugador2;
        this.ronda = ronda;
    }

    public void registrarResultado(String resultado) {
        if (resultadoRegistrado) {
            throw new IllegalStateException("No se puede modificar un resultado ya registrado");
        }
        this.resultado = resultado;
        this.ganador = resultado.startsWith("1") ? jugador1 : jugador2;
        this.resultadoRegistrado = true;
    }

    public boolean tieneResultado() {
        return resultadoRegistrado;
    }

    @Override
    public String getNombre() {
        return jugador1 + " vs " + jugador2;
    }

    @Override
    public List<TorneoComponent> getComponentes() {
        return Collections.emptyList();
    }

    @Override
    public void agregar(TorneoComponent component) {
        throw new UnsupportedOperationException();
    }

    // Getters
    public String getJugador1() { return jugador1; }
    public String getJugador2() { return jugador2; }
    public String getGanador() { return ganador; }


    @Override
    public String toString() {
        return getNombre() + (ganador != null ? " -> Ganador: " + ganador : "");
    }
}