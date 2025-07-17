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
     * Verifica si el nombre del participante individual ya existe en el torneo.
     */
    public boolean nombreIndividualExiste(String nombreTorneo, String nombreParticipante) {
        Torneo torneo = buscarTorneoPorNombre(nombreTorneo);
        if (torneo == null) return false;

        for (Participante p : torneo.getParticipantes()) {
            if (p instanceof ParticipanteIndividual && p.getNombre().equalsIgnoreCase(nombreParticipante)) {
                return true;
            }
            if (p instanceof ParticipanteEquipo) {
                ParticipanteEquipo equipo = (ParticipanteEquipo) p;
                if (equipo.getMiembros().stream().anyMatch(m -> m.equalsIgnoreCase(nombreParticipante))) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Verifica si el nombre del equipo ya existe en el torneo.
     */
    public boolean nombreEquipoExiste(String nombreTorneo, String nombreEquipo) {
        Torneo torneo = buscarTorneoPorNombre(nombreTorneo);
        if (torneo == null) return false;

        return torneo.getParticipantes().stream()
                .anyMatch(p -> p.getNombre().equalsIgnoreCase(nombreEquipo));
    }

    public boolean getCreadoConExito() {
        return creadoConExito;
    }

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
            notificarObservers("ERROR_TORNEO_EXISTE:Ya existe un torneo con el nombre '" + torneo.getNombre() + "'.");
            creadoConExito = false;
            return;
        }
        torneosCreados.add(torneo);
        notificarObservers("EXITO_TORNEO_CREADO:Torneo creado exitosamente: " + torneo.getNombre());
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
            notificarObservers("ERROR_TORNEO_NO_ENCONTRADO:No se encontró el torneo " + nombreTorneo);
            return;
        }
        if (participante == null) {
            notificarObservers("ERROR_PARTICIPANTE_NULO:El participante a inscribir no puede ser nulo.");
            return;
        }

        boolean agregadoExitosamentePorTorneo = torneo.addParticipante(participante);

        if (agregadoExitosamentePorTorneo) {
            notificarObservers("EXITO_PARTICIPANTE_INSCRITO:Participante inscrito exitosamente: \nNombre: "
                    + participante.getNombreForTorneo() +
                    "\nTorneo: " + nombreTorneo);
            inscritoConExito = true;
        } else {
            if (!torneo.getFases().isEmpty()) {
                notificarObservers("ERROR_TORNEO_INICIADO:No se puede inscribir a '" + participante.getNombreForTorneo() + "' porque el torneo '" + nombreTorneo + "' ya ha iniciado.");
            } else if (torneo.getParticipantes().contains(participante)) {
                notificarObservers("ERROR_PARTICIPANTE_DUPLICADO:El participante '" + participante.getNombreForTorneo() + "' ya está inscrito en el torneo '" + nombreTorneo + "'.");
            } else if (torneo.getParticipantes().size() >= torneo.getNumParticipantes()) {
                notificarObservers("ERROR_TORNEO_LLENO:Ha alcanzado el máximo de inscritos para el torneo '" + nombreTorneo + "' (" + torneo.getNumParticipantes() + " participantes).");
            } else {
                notificarObservers("ERROR_INSCRIPCION_FALLIDA:No se pudo inscribir al participante '" + participante.getNombreForTorneo() + "' en el torneo '" + nombreTorneo + "'.");
            }
        }
    }

    /**
     * Busca un torneo por su nombre exacto.
     *
     * @param nombreTorneo el nombre del torneo
     * @return el torneo encontrado o null si no existe
     */
    public void removeParticipanteDeTorneo(String nombreTorneo, Participante participante) {
        Torneo torneo = buscarTorneoPorNombre(nombreTorneo);

        if (torneo == null) {
            notificarObservers("ERROR_TORNEO_NO_ENCONTRADO:No se encontró el torneo '" + nombreTorneo + "' para eliminar al participante.");
            return;
        }
        if (participante == null) {
            notificarObservers("ERROR_PARTICIPANTE_NULO:El participante a eliminar no existe.");
            return;
        }

        boolean removidoExitosamentePorTorneo = torneo.removeParticipante(participante);

        if (removidoExitosamentePorTorneo) {
            notificarObservers("EXITO_PARTICIPANTE_ELIMINADO:Participante '" + participante.getNombreForTorneo() + "' eliminado exitosamente del torneo '" + nombreTorneo + "'.");
        }
        else {
            if (!torneo.getFases().isEmpty()) {
                notificarObservers("ERROR_TORNEO_INICIADO:No se puede eliminar participantes de un torneo que ya ha iniciado.");
            }
            else {
                notificarObservers("ERROR_PARTICIPANTE_NO_ENCONTRADO:El participante '" + participante.getNombreForTorneo() + "' no se encontró en el torneo '" + nombreTorneo + "'.");
            }
        }
    }

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

    public List<Torneo> getTorneosCreados() {
        return new ArrayList<>(torneosCreados);
    }

    /**
     * Elimina un torneo del sistema si existe.
     *
     * @param nombreTorneo el nombre del torneo a eliminar
     */
    public void eliminarTorneo(String nombreTorneo) {
        Torneo torneo = buscarTorneoPorNombre(nombreTorneo);
        if (torneo == null) {
            notificarObservers("ERROR_TORNEO_NO_ENCONTRADO:No se encontró el torneo " + nombreTorneo);
            return;
        }

        if (!torneo.getFases().isEmpty()) {
            notificarObservers("ERROR_TORNEO_NO_ELIMINABLE:No se puede eliminar un torneo que ya ha comenzado.");
            return;
        }

        torneosCreados.remove(torneo);
        notificarObservers("EXITO_TORNEO_ELIMINADO:Torneo '" + nombreTorneo + "' eliminado exitosamente.");
    }
}