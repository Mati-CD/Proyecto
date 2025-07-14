package org.example.CodigoLogico;

import java.util.ArrayList;
import java.util.List;

public class GestorTorneos extends ObserverController {
    private List<Torneo> torneosCreados;
    private boolean creadoConExito;
    private boolean inscritoConExito;

    public GestorTorneos() {
        this.torneosCreados = new ArrayList<>();
        this.creadoConExito = false;
        this.inscritoConExito = false;
    }

    public boolean torneoExiste(String nombre) {
        for (Torneo t : torneosCreados) {
            if (t.getNombre().equals(nombre)) {
                return true;
            }
        }
        return false;
    }

    public boolean getCreadoConExito() {
        return creadoConExito;
    }
    public boolean getInscritoConExito() {
        return inscritoConExito;
    }

    public void addTorneo(Torneo torneo) {
        if (torneoExiste(torneo.getNombre())) {
            notificarObservers("ERROR: Ya existe un torneo con el nombre '" + torneo.getNombre() + "'.");
            creadoConExito = false;
            return;
        }
        torneosCreados.add(torneo);
        String mensaje = "Torneo creado exitosamente:\nNombre: " + torneo.getNombre() +
                "\nDisciplina: " + torneo.getDisciplina() +
                "\nTipo de Inscripción: " + torneo.getTipoDeInscripcion();
        notificarObservers(mensaje);
        creadoConExito = true;
    }

    public void addParticipanteATorneo(String nombreTorneo, Participante participante) {
        inscritoConExito = false;

        Torneo torneo = buscarTorneoPorNombre(nombreTorneo);
        if (torneo == null) {
            notificarObservers("ERROR: No se encontró el torneo '" + nombreTorneo + "' para la inscripción.");
            return;
        }
        if (participante == null) {
            notificarObservers("ERROR: El participante a inscribir no puede ser nulo.");
            return;
        }
        if (torneo.getParticipantes().contains(participante)) {
            notificarObservers("ERROR: El participante '" + participante.getNombre() + "' ya está inscrito en el torneo '" + nombreTorneo + "'.");
            return;
        }

        torneo.addParticipante(participante);
        String mensaje = "Participante inscrito exitosamente: \nNombre: " + participante.getNombre();;
        notificarObservers(mensaje);
        inscritoConExito = true;
    }

    private Torneo buscarTorneoPorNombre(String nombre) {
        for (Torneo t : torneosCreados) {
            if (t.getNombre().equals(nombre)) {
                return t;
            }
        }
        return null;
    }

    public List<Participante> getParticipantesDeTorneo(String nombreTorneo) {
        Torneo torneo = buscarTorneoPorNombre(nombreTorneo);
        if (torneo != null) {
            return new ArrayList<>(torneo.getParticipantes());
        }
        return new ArrayList<>();
    }

    public List<Torneo> getTorneosCreados() {
        return new ArrayList<>(torneosCreados);
    }
}
