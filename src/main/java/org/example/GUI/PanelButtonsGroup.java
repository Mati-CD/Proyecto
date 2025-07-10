package org.example.GUI;

public interface PanelButtonsGroup {
    /**
     * Asignacion de ActionListeners a los botones de este grupo.
     * Esta asignacion de ActionListeners utiliza un catálogo de acciones predefinidas (ActionGUI)
     * para generar y entregar el ActionListener correspondiente segun su ID.
     *
     * @param asignarAction El objeto de tipo AsignarAction que los botones usarán para registrar sus listeners.
     */
    void setButtonActions(AsignarAction asignarAction);
}