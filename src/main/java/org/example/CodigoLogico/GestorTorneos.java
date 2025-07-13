package org.example.CodigoLogico;

import java.util.ArrayList;
import java.util.List;

public class GestorTorneos extends ObserverController {
    private List<Torneo> torneosCreados;
    private boolean creadoConExito;

    public GestorTorneos() {
        this.torneosCreados = new ArrayList<>();
        this.creadoConExito = false;
    }

    public boolean torneoExiste(String nombre) {
        for (Torneo t : torneosCreados) {
            if (t.getNombre().equals(nombre)) {
                return true;
            }
        }
        return false;
    }

    public boolean creadoConExito() {
        return creadoConExito;
    }

    public void add(Torneo torneo) {
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

    public List<Torneo> getTorneosCreados() {
        return new ArrayList<>(torneosCreados);
    }
}
