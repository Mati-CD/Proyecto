package org.example.GUI;
import java.awt.event.ActionListener;

public abstract class AbstractActionAssigner implements ActionAssigner {
    protected final Navegador navegador;

    /**
     * Crea un nuevo asignador de acciones.
     *
     * @param navegador La instancia del Navegador que este asignador usará para realizar las acciones (cambiar de panel, etc).
     */
    public AbstractActionAssigner(Navegador navegador) {
        this.navegador = navegador;
    }

    /**
     * Busca la acción por su ID en el catálogo de ActionGUI y crea el ActionListener
     * que será asignado a un botón.
     *
     * @param actionID El código que identifica la acción (por ejemplo, "IR_A_INICIO").
     * @return Un ActionListener con la acción solicitada, o null si el código de acción no existe.
     */
    @Override
    public ActionListener getAction(String actionID) {
        ActionGUI actionEnum = ActionGUI.getByID(actionID);
        if (actionEnum != null) {
            return e -> actionEnum.ejecutar(navegador);
        }
        return null;
    }
}
