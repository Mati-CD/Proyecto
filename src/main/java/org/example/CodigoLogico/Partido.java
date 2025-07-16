package org.example.CodigoLogico;

import java.util.Collections;
import java.util.List;

/**
 * Representa un partido entre dos participantes dentro de una fase del torneo.
 * Cada partido pertenece a una ronda y puede registrar un resultado con un ganador.
 * Forma parte del patrón Composite como una hoja (no puede tener hijos).
 */
public class Partido extends TorneoComponent {

    private final String jugador1;
    private final String jugador2;
    private final String ronda;
    private String ganador;
    private boolean resultadoRegistrado = false;

    /**
     * Crea un nuevo partido entre dos jugadores en una ronda determinada.
     *
     * @param jugador1 nombre del primer jugador
     * @param jugador2 nombre del segundo jugador
     * @param ronda nombre de la ronda en que se disputa el partido
     */
    public Partido(String jugador1, String jugador2, String ronda) {
        this.jugador1 = jugador1;
        this.jugador2 = jugador2;
        this.ronda = ronda;
    }

    /**
     * Registra el ganador del partido.
     * El nombre del ganador debe coincidir con uno de los jugadores del partido.
     *
     * @param nombreGanador el nombre del jugador que ganó el partido
     * @throws IllegalStateException si el resultado ya ha sido registrado previamente
     * @throws IllegalArgumentException si el nombre del ganador no corresponde a ninguno de los jugadores del partido
     */
    public void registrarResultado(String nombreGanador) {
        if (resultadoRegistrado) {
            throw new IllegalStateException("No se puede modificar un resultado ya registrado");
        }

        if (!nombreGanador.equals(jugador1) && !nombreGanador.equals(jugador2)) {
            throw new IllegalArgumentException("El ganador seleccionado (" + nombreGanador + ") no es un jugador válido para este partido.");
        }

        this.ganador = nombreGanador;
        this.resultadoRegistrado = true;
    }

    /**
     * Verifica si el partido ya tiene un resultado registrado.
     *
     * @return true si se ha registrado un resultado, false en caso contrario
     */
    public boolean tieneResultado() {
        return resultadoRegistrado;
    }

    /**
     * Devuelve el nombre del partido como "Jugador1 vs Jugador2".
     *
     * @return nombre del partido
     */
    @Override
    public String getNombre() {
        return jugador1 + " vs " + jugador2;
    }

    /**
     * Este componente no tiene hijos, por lo que retorna una lista vacía.
     *
     * @return lista vacía
     */
    @Override
    public List<TorneoComponent> getComponentes() {
        return Collections.emptyList();
    }

    /**
     * Los partidos no admiten subcomponentes. Llama a este método lanzará una excepción.
     *
     * @param component componente a agregar
     * @throws UnsupportedOperationException siempre
     */
    @Override
    public void agregar(TorneoComponent component) {
        throw new UnsupportedOperationException();
    }

    /**
     * Devuelve el nombre del primer jugador.
     *
     * @return nombre del jugador1
     */
    public String getJugador1() {
        return jugador1;
    }

    /**
     * Devuelve el nombre del segundo jugador.
     *
     * @return nombre del jugador2
     */
    public String getJugador2() {
        return jugador2;
    }

    /**
     * Devuelve el nombre del jugador ganador si se ha registrado un resultado.
     *
     * @return nombre del ganador o null si no hay resultado aún
     */
    public String getGanador() {
        return ganador;
    }

    /**
     * Devuelve una representación textual del partido, incluyendo al ganador si ya se registró.
     *
     * @return representación en texto del partido
     */
    @Override
    public String toString() {
        return getNombre() + (ganador != null ? " -> Ganador: " + ganador : "");
    }
}