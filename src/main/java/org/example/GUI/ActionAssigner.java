package org.example.GUI;
import java.awt.event.ActionListener;

public interface ActionAssigner {
    /**
     * Obtiene un ActionListener para la acción de UI especificada.
     *
     * @param actionID El ID de la acción (String de ActionGUI).
     * @return Un ActionListener listo para ejecutar la accion correspondiente, o null si el ID no es válido.
     */
    ActionListener getAction(String actionID);
}
