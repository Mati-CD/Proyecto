package org.example.GUI;

import org.example.CodigoLogico.*;
import javax.swing.*;
import java.awt.*;
import java.util.List;

public class PanelEliminarTorneo extends JPanel implements PanelConfigurable, TorneoObserver {
    private GestorTorneos gestorTorneos;
    private PanelButton irAtrasBtn;
    private PanelButton eliminarBtn;
    private JComboBox<Torneo> torneosComboBox;
    private boolean listenersActivos = false;

    public PanelEliminarTorneo() {
        super(new BorderLayout());
        setBackground(new Color(200, 50, 50));

        Font font = new Font("SansSerif", Font.BOLD, 14);
        Font font1 = new Font("Arial", Font.BOLD, 12);
        Font titleFont = new Font("Arial", Font.BOLD, 24);

        // Inicializar Componentes
        irAtrasBtn = new PanelButton("Volver atrás", font1);
        irAtrasBtn.setButtonPreferredSize(new Dimension(120, 30));
        eliminarBtn = new PanelButton("Eliminar Torneo", font);
        eliminarBtn.setButtonPreferredSize(new Dimension(200, 50));
        eliminarBtn.setBackground(Color.RED);
        eliminarBtn.setForeground(Color.WHITE);
        torneosComboBox = new JComboBox<>();
        torneosComboBox.setFont(font);
        torneosComboBox.setRenderer(new GuiUtils.TorneoComboBoxRenderer());

        // Panel superior
        JPanel topPanel = GuiUtils.crearPanelDeEncabezado(irAtrasBtn,
                "Eliminar Torneo",
                titleFont,
                null
        );
        add(topPanel, BorderLayout.NORTH);

        // Panel central
        JPanel centerPanel = new JPanel(new GridLayout(2, 1, 10, 10));
        centerPanel.setOpaque(false);
        centerPanel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));

        JPanel comboPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        comboPanel.setOpaque(false);
        comboPanel.add(new JLabel("Seleccione torneo a eliminar:"));
        comboPanel.add(torneosComboBox);
        centerPanel.add(comboPanel);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setOpaque(false);
        buttonPanel.add(eliminarBtn);
        centerPanel.add(buttonPanel);

        add(centerPanel, BorderLayout.CENTER);
    }

    @Override
    public void inicializar(ActionAssigner actionAssigner, GestorTorneos gestorTorneos) {
        this.gestorTorneos = gestorTorneos;
        this.gestorTorneos.registrarObserver(this);

        if (!listenersActivos) {
            irAtrasBtn.addActionListener(actionAssigner.getAction(ActionGUI.IR_A_ORGANIZADOR.getID()));
            eliminarBtn.addActionListener(e -> eliminarTorneo());
            listenersActivos = true;
        }

        cargarTorneosEnComboBox();

        this.revalidate();
        this.repaint();
    }

    @Override
    public void actualizar(String mensaje) {
        if (mensaje.contains("No se puede eliminar un torneo")) {
            GuiUtils.showMessageOnce(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
        } else if (mensaje.contains("No se encontró el torneo")) {
            GuiUtils.showMessageOnce(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
        }
        else if (mensaje.contains("eliminado exitosamente")) {
            GuiUtils.showMessageOnce(this, mensaje, "Éxito", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void cargarTorneosEnComboBox() {
        Torneo seleccionAnterior = (Torneo) torneosComboBox.getSelectedItem();
        torneosComboBox.removeAllItems();
        List<Torneo> torneos = gestorTorneos.getTorneosCreados();

        if (torneos.isEmpty()) {
            torneosComboBox.setEnabled(false);
        } else {
            for (Torneo torneo : torneos) {
                torneosComboBox.addItem(torneo);
            }
            torneosComboBox.setEnabled(true);

            if (seleccionAnterior != null && torneos.contains(seleccionAnterior)) {
                torneosComboBox.setSelectedItem(seleccionAnterior);
            } else if (torneosComboBox.getItemCount() > 0) {
                torneosComboBox.setSelectedIndex(0);
            }
        }
    }

    private void eliminarTorneo() {
        Torneo torneoSeleccionado = (Torneo) torneosComboBox.getSelectedItem();
        if (torneoSeleccionado == null) {
            GuiUtils.showMessageOnce(this, "Por favor seleccione un torneo", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int confirmacion = JOptionPane.showConfirmDialog(
                this,
                "¿Está seguro que desea eliminar el torneo '" + torneoSeleccionado.getNombre() + "'?\nEsta acción no se puede deshacer.",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
        );

        if (confirmacion == JOptionPane.YES_OPTION) {
            gestorTorneos.eliminarTorneo(torneoSeleccionado.getNombre());
            cargarTorneosEnComboBox();
        }
    }
}