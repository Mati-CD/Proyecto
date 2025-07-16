package org.example.GUI;

import org.example.CodigoLogico.Torneo;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.List;
import java.util.function.Function;

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

    public static void setLabelTextColor(JLabel label, Color color) {
        if (label != null) {
            label.setForeground(color);
        }
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

    public static class ComboBoxRenderer<T> extends JLabel implements ListCellRenderer<T> {
        private final Function<T, String> textExtractor;

        /**
         * Constructor para el renderer genérico.
         * @param textExtractor Una función que toma un objeto T y devuelve la String a mostrar.
         */
        public ComboBoxRenderer(Function<T, String> textExtractor) {
            setOpaque(true);
            this.textExtractor = textExtractor;
        }

        @Override
        public Component getListCellRendererComponent(JList<? extends T> list, T value, int index, boolean isSelected, boolean cellHasFocus) {
            if (value != null) {
                setText(textExtractor.apply(value));
            } else {
                setText("");
            }

            if (isSelected) {
                setBackground(list.getSelectionBackground());
                setForeground(list.getSelectionForeground());
            } else {
                setBackground(list.getBackground());
                setForeground(list.getForeground());
            }

            return this;
        }
    }

    public static void cargarTorneosEnComboBox(JComboBox<Torneo> torneosComboBox, List<Torneo> listaDeTorneos) {
        Torneo seleccionAnterior = (Torneo) torneosComboBox.getSelectedItem();
        DefaultComboBoxModel<Torneo> model = new DefaultComboBoxModel<>();

        if (listaDeTorneos.isEmpty()) {
            torneosComboBox.setEnabled(false);
            torneosComboBox.setModel(model);
        }
        else {
            for (Torneo torneo : listaDeTorneos) {
                model.addElement(torneo);
            }
            torneosComboBox.setModel(model);
            torneosComboBox.setEnabled(true);

            if (seleccionAnterior != null && listaDeTorneos.contains(seleccionAnterior)) {
                torneosComboBox.setSelectedItem(seleccionAnterior);
            } else if (torneosComboBox.getItemCount() > 0) {
                torneosComboBox.setSelectedIndex(0);
            }
        }
    }
}