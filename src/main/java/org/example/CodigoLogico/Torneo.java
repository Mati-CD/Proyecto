package org.example.CodigoLogico;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Clase principal que representa un torneo, incluyendo participantes, fases y lógica de desarrollo.
 * Extiende {@link ObserverController} para enviar notificaciones a los observadores.
 */
public class Torneo extends ObserverController {
    private final List<FaseTorneo> fases = new ArrayList<>();
    private final List<Participante> participantes;
    private String campeon;
    private String nombre;
    private String disciplina;
    private String tipoDeInscripcion;
    private int numParticipantes;

    /**
     * Constructor para crear un nuevo torneo.
     *
     * @param nombre nombre del torneo
     * @param disciplina disciplina o deporte del torneo
     * @param tipoDeInscripcion tipo de inscripción (ej: "Individual", "Grupal")
     */

    public Torneo(String nombre, String disciplina, String tipoDeInscripcion, int numParticipantes) {
        this.nombre = nombre;
        this.disciplina = disciplina;
        this.tipoDeInscripcion = tipoDeInscripcion;
        this.numParticipantes = numParticipantes;
        participantes = new ArrayList<>();
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

    public int getNumParticipantes() {
        return numParticipantes;
    }

    public List<Participante> getParticipantes() {
        return new ArrayList<>(participantes);
    }

    public List<FaseTorneo> getFases() {
        return new ArrayList<>(fases);
    }

    public FaseTorneo getFaseActual() {
        return fases.isEmpty() ? null : fases.get(fases.size() - 1);
    }

    public String getCampeon() {
        return campeon;
    }

    public boolean tieneCampeon() {
        return campeon != null;
    }

    /**
     * Retorna los partidos de la fase actual del torneo.
     *
     * @return lista de partidos activos
     */
    public List<Partido> getPartidosActuales() {
        List<Partido> partidos = new ArrayList<>();
        if (!fases.isEmpty()) {
            FaseTorneo faseActual = getFaseActual();
            for (TorneoComponent componente : faseActual.getComponentes()) {
                partidos.add((Partido) componente);
            }
        }
        return partidos;
    }

    /**
     * Agrega un participante al torneo si no está ya inscrito.
     *
     * @param participante participante a agregar
     */
    void addParticipante(Participante participante) {
        if (!participantes.contains(participante)) {
            participantes.add(participante);
        }
    }

    public boolean removeParticipante(Participante participante) {
        return participantes.remove(participante);
    }

    public List<Participante> sorteoParticipantesRandom(List<Participante> participantesActuales) {
        if (!fases.isEmpty()) {
            notificarObservers("No se puede generar un nuevo bracket porque el torneo '" + nombre + "' ya ha comenzado");
            return null;
        }

        if (participantesActuales.size() != numParticipantes) {
            notificarObservers("Para generar un bracket del torneo '" + nombre + "' se requieren " + numParticipantes + " participantes." +
                    "\nActualmente hay " + participantesActuales.size() + ".");
            return null;
        }

        List<Participante> participantesRandom = new ArrayList<>(participantesActuales);
        Collections.shuffle(participantesRandom);
        return participantesRandom;
    }

    /**
     * Inicia el torneo validando la cantidad de participantes e iniciando la fase inicial.
     */
    public void iniciarTorneo(List<Participante> participantesSorteados) {
        if (participantesSorteados.size() != numParticipantes) {
            notificarObservers("La lista de participantes para iniciar el torneo tiene una cantidad incorrecta. Se esperaban " + numParticipantes + " y se recibieron " + participantesSorteados.size() + ".");
            return;
        }

        fases.clear();
        campeon = null;
        this.participantes.clear();
        this.participantes.addAll(participantesSorteados);

        crearFaseInicial();
        notificarObservers("Torneo '" + nombre + "' iniciado con " + this.participantes.size() + " participantes.");
    }

    // Gestión de fases

    /**
     * Crea la primera fase del torneo emparejando a los participantes.
     */
    private void crearFaseInicial() {
        int numParticipantes = this.participantes.size();
        String nombreFase = obtenerNombreFase(numParticipantes);
        FaseTorneo fase = new FaseTorneo(nombreFase);

        for (int i = 0; i < numParticipantes; i += 2) {
            String player1 = this.participantes.get(i).getNombre();
            String player2 = this.participantes.get(i + 1).getNombre();
            Partido partido = new Partido(player1, player2, nombreFase);
            fase.agregar(partido);
        }

        fases.add(fase);
        notificarObservers(nombreFase + " programada");
    }

    /**
     * Devuelve el nombre adecuado para una fase según el número de participantes.
     *
     * @param numParticipantes cantidad de jugadores en la fase
     * @return nombre de la fase
     */
    private String obtenerNombreFase(int numParticipantes) {
        return switch (numParticipantes) {
            case 2 -> "Final";
            case 4 -> "Semifinales";
            case 8 -> "Cuartos de Final";
            case 16 -> "Octavos de Final";
            default -> "Fase con " + numParticipantes + " participantes";
        };
    }

    // Registro de resultados

    /**
     * Registra el resultado de un partido y avanza el torneo si es necesario.
     *
     * @param partido partido cuyo resultado se registrará
     * @param resultado resultado del partido
     */
    public void registrarResultado(Partido partido, String resultado) {
        partido.registrarResultado(resultado);
        notificarObservers("Resultado registrado: " + partido);

        if (todosLosPartidosCompletados()) {
            if (esFinal()) {
                declararCampeon();
            } else {
                crearSiguienteFase();
            }
        }
    }

    /**
     * Verifica si todos los partidos de la fase actual tienen un ganador.
     *
     * @return true si todos los partidos están completados
     */
    public boolean todosLosPartidosCompletados() {
        if (fases.isEmpty()) return false;

        for (TorneoComponent componente : getFaseActual().getComponentes()) {
            if (((Partido) componente).getGanador() == null) {
                return false;
            }
        }
        return true;
    }

    /**
     * Verifica si la fase actual corresponde a la final.
     *
     * @return true si la fase actual es la final
     */
    public boolean esFinal() {
        return !fases.isEmpty() && getFaseActual().getNombre().equals("Final");
    }

    public void crearSiguienteFase() {
        List<String> ganadores = obtenerGanadoresUltimaFase();
        String nombreFase = obtenerNombreFase(ganadores.size());

        FaseTorneo nuevaFase = new FaseTorneo(nombreFase);
        for (int i = 0; i < ganadores.size(); i += 2) {
            Partido partido = new Partido(ganadores.get(i), ganadores.get(i + 1), nombreFase);
            nuevaFase.agregar(partido);
        }

        fases.add(nuevaFase);
        notificarObservers(nuevaFase.getNombre() + " programada");
    }

    /**
     * Obtiene los ganadores de la última fase jugada.
     *
     * @return lista de nombres de los ganadores
     */
    private List<String> obtenerGanadoresUltimaFase() {
        List<String> ganadores = new ArrayList<>();
        for (TorneoComponent componente : getFaseActual().getComponentes()) {
            ganadores.add(((Partido) componente).getGanador());
        }
        return ganadores;
    }

    /**
     * Declara al campeón del torneo con base en el último partido ganado.
     */
    private void declararCampeon() {
        campeon = obtenerGanadoresUltimaFase().get(0);
        notificarObservers("¡Campeón declarado: " + campeon + "!");
    }

    // Representación textual

    /**
     * Devuelve una representación textual completa del torneo, incluyendo fases y campeón.
     *
     * @return estructura textual del torneo
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("=== ").append(getNombre()).append(" (").append(") ===\n\n");

        for (FaseTorneo fase : fases) {
            sb.append("=== ").append(fase.getNombre().toUpperCase()).append(" ===\n");
            for (TorneoComponent componente : fase.getComponentes()) {
                sb.append("- ").append(componente).append("\n");
            }
            sb.append("\n");
        }

        if (campeon != null) {
            sb.append("=== ¡¡¡ CAMPEÓN: ").append(campeon).append(" !!! ===\n");
        }

        return sb.toString();
    }

    /**
     * Retorna la estructura del torneo como cadena de texto.
     *
     * @return resumen completo del torneo
     */
    public String getEstructuraTexto() {
        return this.toString();
    }
}