package org.example.GUI;

public interface PanelConfigurable {
    /**
     * Inicializa el panel.
     *
     * @param actionAssigner El objeto de tipo ActionAssigner que el panel usará para obtener y configurar sus ActionListeners.
     */
    void inicializar(ActionAssigner actionAssigner);
}

