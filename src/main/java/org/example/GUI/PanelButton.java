package org.example.GUI;

import javax.swing.*;
import javax.swing.border.Border;
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
        this.setOpaque(true);
        this.setContentAreaFilled(true);
        this.setBorderPainted(true);
    }

    /**
     * @param preferredSize el tamaño preferido que se desea asignar al botón
     */
    public void setButtonPreferredSize(Dimension preferredSize) {
        setPreferredSize(preferredSize);
    }

    /**
     * @param backgroundColor El color de fondo del botón.
     * @param foregroundColor El color del texto del botón.
     * @param borderColor     El color del borde del botón (null para sin borde).
     * @param borderWidth     El grosor del borde (borderColor no debe ser null).
     */
    public void setButtonColor(Color backgroundColor, Color foregroundColor, Color borderColor, int borderWidth) {
        this.setBackground(backgroundColor);
        this.setForeground(foregroundColor);
        if (borderColor != null) {
            Border lineBorder = BorderFactory.createLineBorder(borderColor, borderWidth);
            this.setBorder(BorderFactory.createCompoundBorder(lineBorder, BorderFactory.createEmptyBorder(5, 15, 5, 15)));
        } else {
            this.setBorder(null);
        }
    }
}