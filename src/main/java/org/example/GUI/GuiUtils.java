package org.example.GUI;

import org.example.CodigoLogico.Torneo;

import javax.swing.*;
import java.awt.*;

/**
 * Clase usada para mostrar mensajes en la interfaz gráfica.
 * Nos permite trabajar con métodos reutilizables que encapsulan la lógica de diálogos con el usuario.
 */
public class GuiUtils {

    /**
     * Muestra un mensaje emergente (JOptionPane) de forma asíncrona en el hilo de eventos de Swing.
     * Este metodo garantiza que el mensaje se muestre correctamente desde cualquier hilo.
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

    public static JPanel crearPanelDeEncabezado(PanelButton leftButton,
            String titleText,
            Font titleFont,
            PanelButton rightButton) {

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setOpaque(false);

        // Panel para el botón izquierdo
        JPanel topLeftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topLeftPanel.setOpaque(false);
        if (leftButton != null) {
            topLeftPanel.add(leftButton);
        }
        topPanel.add(topLeftPanel, BorderLayout.WEST);

        // Etiqueta para título central
        JLabel titleLabel = new JLabel(titleText, SwingConstants.CENTER);
        titleLabel.setFont(titleFont);
        topPanel.add(titleLabel, BorderLayout.CENTER);

        // Panel para el botón derecho
        JPanel topRightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        topRightPanel.setOpaque(false);
        if (rightButton != null) {
            topRightPanel.add(rightButton);
        }
        topPanel.add(topRightPanel, BorderLayout.EAST);

        return topPanel;
    }

    public static class TorneoComboBoxRenderer extends JLabel implements ListCellRenderer<Torneo> {

        public TorneoComboBoxRenderer() {
            setOpaque(true);
        }

        @Override
        public Component getListCellRendererComponent(JList<? extends Torneo> list, Torneo value, int index, boolean isSelected, boolean cellHasFocus) {
            // Solo mostrar el nombre del torneo
            if (value != null) {
                setText(value.getNombre());
            }
            else {
                setText("");
            }

            if (isSelected) {
                setBackground(list.getSelectionBackground());
                setForeground(list.getSelectionForeground());
            }
            else {
                setBackground(list.getBackground());
                setForeground(list.getForeground());
            }

            return this;
        }
    }
}