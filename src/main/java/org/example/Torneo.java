package org.example;

import java.util.ArrayList;
import java.util.List;

public class Torneo {
    private final String nombre;
    private final String disciplina;
    private final List<FaseTorneo> fases = new ArrayList<>();
    private final List<String> participantes = new ArrayList<>();
    private final List<TorneoObserver> observers = new ArrayList<>();
    private String campeon;

    public Torneo(String nombre, String disciplina) {
        this.nombre = nombre;
        this.disciplina = disciplina;
    }

    public void registrarObserver(TorneoObserver observer) {
        observers.add(observer);
    }

    private void notificarObservers(String mensaje) {
        for (TorneoObserver observer : observers) {
            observer.actualizar(mensaje);
        }
    }

    public void agregarParticipante(String nombre) {
        if (!participantes.contains(nombre)) {
            participantes.add(nombre);
            notificarObservers("Participante agregado: " + nombre);
        }
    }

    public void iniciarTorneo() {
        if (!esPotenciaDeDos(participantes.size())) {
            notificarObservers("El número de participantes debe ser potencia de 2 (2, 4, 8, 16...)");
            return;
        }

        fases.clear();
        campeon = null;
        crearFaseInicial();
    }

    private boolean esPotenciaDeDos(int numero) {
        return numero > 0 && (numero & (numero - 1)) == 0;
    }

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

    private boolean todosLosPartidosCompletados() {
        return fases.get(fases.size()-1).getComponentes().stream()
                .allMatch(c -> ((Partido)c).getGanador() != null);
    }

    private boolean esFinal() {
        return fases.get(fases.size()-1).getNombre().equals("Final");
    }

    private void crearSiguienteFase() {
        List<String> ganadores = obtenerGanadoresUltimaFase();
        String nombreFase;

        if (ganadores.size() == 2) {
            nombreFase = "Final";
        } else if (ganadores.size() == 4) {
            nombreFase = "Semifinales";
        } else if (ganadores.size() == 8) {
            nombreFase = "Cuartos de Final";
        } else {
            nombreFase = "Fase " + (fases.size() + 1);
        }

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
        fases.get(fases.size()-1).getComponentes().forEach(c -> {
            ganadores.add(((Partido)c).getGanador());
        });
        return ganadores;
    }

    private void declararCampeon() {
        campeon = obtenerGanadoresUltimaFase().get(0);
        notificarObservers("¡Campeón declarado: " + campeon + "!");
    }

    public List<Partido> getPartidosActuales() {
        if (fases.isEmpty()) return new ArrayList<>();
        FaseTorneo faseActual = fases.get(fases.size() - 1);
        List<Partido> partidos = new ArrayList<>();
        for (TorneoComponent component : faseActual.getComponentes()) {
            partidos.add((Partido) component);
        }
        return partidos;
    }

    public List<String> getParticipantes() {
        return new ArrayList<>(participantes);
    }

    public boolean tieneCampeon() {
        return campeon != null;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("=== ").append(nombre).append(" (").append(disciplina).append(") ===\n\n");

        for (FaseTorneo fase : fases) {
            sb.append("=== ").append(fase.getNombre().toUpperCase()).append(" ===\n");
            for (TorneoComponent component : fase.getComponentes()) {
                sb.append("- ").append(component).append("\n");
            }
            sb.append("\n");
        }

        if (campeon != null) {
            sb.append("=== ¡¡¡ CAMPEÓN: ").append(campeon).append(" !!! ===\n");
        }

        return sb.toString();
    }
    public FaseTorneo getFaseActual() {
        return fases.isEmpty() ? null : fases.get(fases.size() - 1);
    }

    public String getEstructuraTexto() {
        return this.toString();
    }
}