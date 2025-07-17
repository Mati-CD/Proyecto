package org.example.GUI;

import org.example.CodigoLogico.Torneo;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.List;
import java.util.function.Function;

/**
 * Clase de utilidades comunes para la interfaz gráfica.
 */
public class GuiUtils {

    /**
     * Muestra un mensaje emergente (JOptionPane) de forma asíncrona a los de eventos.
     * Este metodo garantiza que el mensaje se muestre correctamente.
     *
     * @param parentComponent el componente padre del diálogo
     * @param message         el mensaje que se desea mostrar
     * @param title           el título del cuadro de diálogo
     * @param messageType     el tipo de mensaje (e.g., JOptionPane.ERROR_MESSAGE)
     */
    public static void showMessageOnce(Component parentComponent, String message, String title, int messageType) {
        SwingUtilities.invokeLater(() -> {
            JOptionPane.showMessageDialog(parentComponent, message, title, messageType);
        });
    }

    /**
     * Cambia el color del texto de un JLabel si el label no es nulo.
     *
     * @param label JLabel al que se le cambiará el color
     * @param color nuevo color para el texto
     */
    public static void setLabelTextColor(JLabel label, Color color) {
        if (label != null) {
            label.setForeground(color);
        }
    }

    /**
     * Crea un panel de encabezado con botones opcionales a la izquierda y derecha, y un título centrado.
     *
     * @param leftButton  botón opcional a la izquierda
     * @param titleText   texto opcional del título central
     * @param titleFont   fuente opcional del título
     * @param rightButton botón opcional a la derecha
     * @return el panel de encabezado
     */
    public static JPanel crearPanelDeEncabezado(PanelButton leftButton,
            String titleText,
            Font titleFont,
            PanelButton rightButton) {

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setOpaque(false);

        JPanel topLeftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topLeftPanel.setOpaque(false);
        if (leftButton != null) {
            topLeftPanel.add(leftButton);
        }
        topPanel.add(topLeftPanel, BorderLayout.WEST);

        JLabel titleLabel = new JLabel(titleText, SwingConstants.CENTER);
        titleLabel.setFont(titleFont);
        topPanel.add(titleLabel, BorderLayout.CENTER);

        JPanel topRightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        topRightPanel.setOpaque(false);
        if (rightButton != null) {
            topRightPanel.add(rightButton);
        }
        topPanel.add(topRightPanel, BorderLayout.EAST);

        return topPanel;
    }

    /**
     * Renderizador personalizado para JComboBox que permite mostrar una representación del textode los objetos.
     *
     * @param <T> tipo de los elementos en la lista
     */
    public static class ComboBoxRenderer<T> extends JLabel implements ListCellRenderer<T> {
        private final Function<T, String> textExtractor;

        /**
         * Constructor que recibe una función para extraer texto de cada objeto.
         *
         * @param textExtractor función que define cómo mostrar cada objeto
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

    /**
     * Carga una lista de torneos en un JComboBox y mantiene la selección anterior si es posible.
     *
     * @param torneosComboBox el combo box donde se cargarán los torneos
     * @param listaDeTorneos lista de torneos disponibles
     */
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

    /**
     * Extrae el contenido de un mensaje en formato TAG:mensaje.
     * Si no contiene ":", retorna el mensaje tal cual.
     *
     * @param mensaje El mensaje completo con tag opcional.
     * @return El contenido del mensaje sin el tag.
     */
    public static String formatearMensajeTorneo(String mensaje) {
        if (mensaje == null || !mensaje.contains(":")) return mensaje;
        return mensaje.substring(mensaje.indexOf(':') + 1);
    }
}