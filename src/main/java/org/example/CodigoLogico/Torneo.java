package org.example.CodigoLogico;

import java.util.ArrayList;
import java.util.List;
import java.util.Date;

public class Torneo {
    private final String nombre;
    private final List<FaseTorneo> fases = new ArrayList<>();
    private final List<String> participantes = new ArrayList<>();
    private final List<TorneoObserver> observers = new ArrayList<>();
    private String campeon;
    private String disciplina;
    private Date fecha;

    public Torneo(String nombre, String disciplina, Date fecha) {
        this.nombre = nombre;
        this.disciplina = disciplina;
        this.fecha = fecha;
    }

    // Métodos de acceso
    public String getNombre() {
        return nombre;
    }
    public String getDisciplina() {
        return disciplina; }

    public Date getFecha() {
        return fecha; }

    public List<String> getParticipantes() {
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

    // Gestión de participantes
    public void agregarParticipante(String nombre) {
        if (!participantes.contains(nombre)) {
            participantes.add(nombre);
            notificarObservers("Participante agregado: " + nombre);
        }
    }

    // Inicio del torneo
    public void iniciarTorneo() {
        if (!esPotenciaDeDos(participantes.size())) {
            notificarObservers("El número de participantes debe ser potencia de 2 (2, 4, 8, 16...)");
            return;
        }

        fases.clear();
        campeon = null;
        crearFaseInicial();
        notificarObservers("Torneo iniciado con " + participantes.size() + " participantes");
    }

    private boolean esPotenciaDeDos(int numero) {
        return numero > 0 && (numero & (numero - 1)) == 0;
    }

    // Gestión de fases
    private void crearFaseInicial() {
        int numParticipantes = participantes.size();
        String nombreFase = obtenerNombreFase(numParticipantes);
        FaseTorneo fase = new FaseTorneo(nombreFase);

        for (int i = 0; i < numParticipantes; i += 2) {
            Partido partido = new Partido(participantes.get(i), participantes.get(i + 1), nombreFase);
            fase.agregar(partido);
        }

        fases.add(fase);
        notificarObservers(nombreFase + " programada");
    }

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

    public boolean todosLosPartidosCompletados() {
        if (fases.isEmpty()) return false;

        for (TorneoComponent componente : getFaseActual().getComponentes()) {
            if (((Partido)componente).getGanador() == null) {
                return false;
            }
        }
        return true;
    }

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

    private List<String> obtenerGanadoresUltimaFase() {
        List<String> ganadores = new ArrayList<>();
        for (TorneoComponent componente : getFaseActual().getComponentes()) {
            ganadores.add(((Partido)componente).getGanador());
        }
        return ganadores;
    }

    private void declararCampeon() {
        campeon = obtenerGanadoresUltimaFase().get(0);
        notificarObservers("¡Campeón declarado: " + campeon + "!");
    }

    // Sistema de observadores
    public void registrarObserver(TorneoObserver observer) {
        observers.add(observer);
    }

    private void notificarObservers(String mensaje) {
        for (TorneoObserver observer : observers) {
            observer.actualizar(mensaje);
        }
    }

    // Representación textual
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("=== ").append(nombre).append(" (").append(") ===\n\n");

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

    public String getEstructuraTexto() {
        return this.toString();
    }
}