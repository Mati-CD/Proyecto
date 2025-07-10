package org.example.GUI;
import java.awt.event.ActionListener;

public abstract class AbstractAsignarAction implements AsignarAction {
    protected final Navegador navegador;

    public AbstractAsignarAction(Navegador navegador) {
        this.navegador = navegador;
    }

    @Override
    public ActionListener getAction(String actionID) {
        ActionGUI accionEnum = ActionGUI.getById(actionID);
        if (accionEnum != null) {
            return e -> accionEnum.ejecutar(navegador);
        }
        return null;
    }
}
