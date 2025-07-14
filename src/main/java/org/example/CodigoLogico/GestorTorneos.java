package org.example.CodigoLogico;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase encargada de gestionar la creación de torneos y la inscripción de participantes.
 * Hereda de ObserverController para notificar eventos relevantes.
 * Permite verificar la existencia de torneos, agregarlos y registrar participantes.
 */
public class GestorTorneos extends ObserverController {

    private List<Torneo> torneosCreados;
    private boolean creadoConExito;
    private boolean inscritoConExito;

    /**
     * Constructor por defecto. Inicializa las estructuras internas.
     */
    public GestorTorneos() {
        this.torneosCreados = new ArrayList<>();
        this.creadoConExito = false;
        this.inscritoConExito = false;
    }

    /**
     * Verifica si un torneo con el nombre dado ya existe.
     *
     * @param nombre el nombre del torneo a buscar
     * @return true si existe, false si no
     */
    public boolean torneoExiste(String nombre) {
        for (Torneo t : torneosCreados) {
            if (t.getNombre().equals(nombre)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Indica si el último torneo fue creado exitosamente.
     *
     * @return true si fue creado con éxito, false si falló
     */
    public boolean getCreadoConExito() {
        return creadoConExito;
    }

    /**
     * Indica si el último participante fue inscrito correctamente.
     *
     * @return true si se inscribió con éxito, false si hubo error
     */
    public boolean getInscritoConExito() {
        return inscritoConExito;
    }

    /**
     * Agrega un nuevo torneo al sistema si su nombre no está duplicado.
     *
     * @param torneo el torneo a agregar
     */
    public void addTorneo(Torneo torneo) {
        if (torneoExiste(torneo.getNombre())) {
            notificarObservers("ERROR: Ya existe un torneo con el nombre '" + torneo.getNombre() + "'.");
            creadoConExito = false;
            return;
        }
        torneosCreados.add(torneo);
        String mensaje = "Torneo creado exitosamente: \nNombre: " + torneo.getNombre() +
                "\nDisciplina: " + torneo.getDisciplina() +
                "\nTipo de Inscripción: " + torneo.getTipoDeInscripcion();
        notificarObservers(mensaje);
        creadoConExito = true;
    }

    /**
     * Agrega un participante a un torneo existente, validando duplicados y errores.
     *
     * @param nombreTorneo el nombre del torneo
     * @param participante el participante a inscribir
     */
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
        String mensaje = "Participante inscrito exitosamente: \nNombre: " + participante.getNombre();
        notificarObservers(mensaje);
        inscritoConExito = true;
    }

    /**
     * Busca un torneo por su nombre exacto.
     *
     * @param nombre el nombre del torneo
     * @return el torneo encontrado o null si no existe
     */
    private Torneo buscarTorneoPorNombre(String nombre) {
        for (Torneo t : torneosCreados) {
            if (t.getNombre().equals(nombre)) {
                return t;
            }
        }
        return null;
    }

    /**
     * Obtiene los participantes inscritos en un torneo específico.
     *
     * @param nombreTorneo nombre del torneo
     * @return lista de participantes, vacía si el torneo no existe
     */
    public List<Participante> getParticipantesDeTorneo(String nombreTorneo) {
        Torneo torneo = buscarTorneoPorNombre(nombreTorneo);
        if (torneo != null) {
            return new ArrayList<>(torneo.getParticipantes());
        }
        return new ArrayList<>();
    }

    /**
     * Retorna todos los torneos creados hasta el momento.
     *
     * @return lista de torneos creados
     */
    public List<Torneo> getTorneosCreados() {
        return new ArrayList<>(torneosCreados);
    }
}
