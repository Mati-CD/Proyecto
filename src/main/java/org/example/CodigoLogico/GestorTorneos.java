package org.example.CodigoLogico;

import java.util.ArrayList;
import java.util.List;

public class GestorTorneos {
    private List<Torneo> torneosCreados;

    public GestorTorneos() {
        this.torneosCreados = new ArrayList<>();
    }

    public boolean torneoExiste(String nombre) {
        for (Torneo t : torneosCreados) {
            if (t.getNombre().equals(nombre)) {
                return true;
            }
        }
        return false;
    }

    public void add(Torneo torneo) {
        torneosCreados.add(torneo);
    }

    public List<Torneo> getTorneosCreados() {
        return new ArrayList<>(torneosCreados);
    }
}
