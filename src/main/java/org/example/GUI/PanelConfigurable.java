package org.example.GUI;

public interface PanelConfigurable {
    /**
     * Inicializa el diseño del panel.
     *
     * @param asignarAction El objeto de tipo AsignarAction que el panel usará para configurar sus acciones.
     */
    void inicializar(AsignarAction asignarAction);
}
