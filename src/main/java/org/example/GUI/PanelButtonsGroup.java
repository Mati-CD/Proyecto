package org.example.GUI;

public interface PanelButtonsGroup {
    /**
     * Asigna los ActionListeners a los botones de este grupo.
     * Esta asignacion utiliza un catálogo de acciones predefinidas (ActionGUI)
     * para obtener y entregar el ActionListener correspondiente segun su ID.
     *
     * @param actionAssigner El objeto de tipo ActionAssigner que los botones usarán para solicitar y registrar sus ActionListeners.     */
    void setButtonActions(ActionAssigner actionAssigner);
}