package org.example.GUI;

import javax.swing.*;
import java.awt.*;

/**
 * Clase usada para mostrar mensajes en la interfaz gráfica.
 * Nos permite trabajar con métodos reutilizables que encapsulan la lógica de diálogos con el usuario.
 */
public class GuiUtilidades {

    /**
     * Muestra un mensaje emergente (JOptionPane) de forma asíncrona en el hilo de eventos de Swing.
     * Este método garantiza que el mensaje se muestre correctamente desde cualquier hilo.
     *
     * @param parentComponent el componente padre del diálogo;
     * @param message el mensaje que se desea mostrar
     * @param title el título del cuadro de diálogo
     * @param messageType el tipo de mensaje
     */
    public static void showMessageOnce(Component parentComponent, String message, String title, int messageType) {
        SwingUtilities.invokeLater(() -> {
            JOptionPane.showMessageDialog(parentComponent, message, title, messageType);
        });
    }
}