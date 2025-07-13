package org.example.GUI;

import org.example.CodigoLogico.GestorTorneos;

public interface PanelConfigurable {
    /**
     * Inicializa el panel.
     *
     * @param actionAssigner El objeto de tipo ActionAssigner que el panel usará para obtener y configurar sus ActionListeners.
     */
    void inicializar(ActionAssigner actionAssigner, GestorTorneos gestorTorneos);
}

