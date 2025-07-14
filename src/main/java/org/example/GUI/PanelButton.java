package org.example.GUI;

import javax.swing.*;
import java.awt.*;

/**
 * Botón personalizado utilizado en los paneles de la aplicación.
 */
public class PanelButton extends JButton {

    /**
     * @param label el texto que se mostrará en el botón
     * @param font la fuente que se aplicará al texto del botón
     */
    public PanelButton(String label, Font font) {
        super(label);
        this.setFont(font);
    }

    /**
     * @param preferredSize el tamaño preferido que se desea asignar al botón
     */
    public void setButtonPreferredSize(Dimension preferredSize) {
        setPreferredSize(preferredSize);
    }
}