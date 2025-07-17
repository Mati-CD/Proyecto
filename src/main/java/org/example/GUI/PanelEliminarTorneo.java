package org.example.GUI;

import org.example.CodigoLogico.*;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * Panel que permite eliminar torneos existentes desde una lista desplegable.
 */
public class PanelEliminarTorneo extends JPanel implements PanelConfigurable, TorneoObserver {
    private GestorTorneos gestorTorneos;
    private PanelButton irAtrasBtn;
    private PanelButton eliminarBtn;
    private JComboBox<Torneo> torneosComboBox;
    private boolean listenersActivos = false;

    public PanelEliminarTorneo() {
        super(new BorderLayout());
        setBackground(Color.WHITE); // Fondo unificado
        setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        Font fontBoton = new Font("SansSerif", Font.BOLD, 14);
        Font fontTitulo = new Font("Arial", Font.BOLD, 24);

        // Botón Volver atrás con estilo personalizado
        irAtrasBtn = new PanelButton("Volver atrás", fontBoton);
        irAtrasBtn.setButtonPreferredSize(new Dimension(150, 40));
        aplicarEstiloVolverAtras(irAtrasBtn);

        // Botón Eliminar con estilo original (rojo)
        eliminarBtn = new PanelButton("Eliminar Torneo", fontBoton);
        eliminarBtn.setButtonPreferredSize(new Dimension(200, 50));
        eliminarBtn.setButtonColor(
                new Color(231, 76, 60), // rojo
                Color.WHITE,
                new Color(192, 57, 43), 2
        );

        // ComboBox
        torneosComboBox = new JComboBox<>();
        torneosComboBox.setFont(fontBoton);
        torneosComboBox.setRenderer(new GuiUtils.ComboBoxRenderer<>(Torneo::getNombre));
        torneosComboBox.setPreferredSize(new Dimension(300, 35));

        // Título y encabezado
        JPanel topPanel = GuiUtils.crearPanelDeEncabezado(
                irAtrasBtn,
                "",
                fontTitulo,
                null
        );
        add(topPanel, BorderLayout.NORTH);

        // Panel central
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setBackground(Color.WHITE);
        centerPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        // Línea: combo y label
        JPanel comboPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        comboPanel.setBackground(Color.WHITE);
        JLabel label = new JLabel("Seleccione torneo a eliminar:");
        label.setFont(fontBoton);
        comboPanel.add(label);
        comboPanel.add(torneosComboBox);

        // Línea: botón eliminar
        JPanel botonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        botonPanel.setBackground(Color.WHITE);
        botonPanel.add(eliminarBtn);

        centerPanel.add(comboPanel);
        centerPanel.add(Box.createVerticalStrut(20));
        centerPanel.add(botonPanel);

        add(centerPanel, BorderLayout.CENTER);
    }

    /**
     * Aplica estilo personalizado solo al botón "Volver atrás"
     */
    private void aplicarEstiloVolverAtras(PanelButton boton) {
        boton.setButtonColor(
                new Color(220, 220, 220),  // Fondo gris claro
                Color.BLACK,               // Texto negro
                new Color(200, 200, 200),  // Fondo hover gris más oscuro
                0                         // Sin borde
        );
    }

    @Override
    public void inicializar(ActionAssigner actionAssigner, GestorTorneos gestorTorneos) {
        this.gestorTorneos = gestorTorneos;
        this.gestorTorneos.registrarObserver(this);

        if (!listenersActivos) {
            irAtrasBtn.addActionListener(actionAssigner.getAction(ActionGUI.IR_A_ORGANIZADOR.getID()));
            eliminarBtn.addActionListener(e -> clickEliminarTorneo());
            listenersActivos = true;
        }

        cargarTorneosEnComboxBox();

        revalidate();
        repaint();
    }

    @Override
    public void actualizar(String mensaje) {
        if (mensaje.contains("No se puede eliminar un torneo")) {
            GuiUtils.showMessageOnce(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
        } else if (mensaje.contains("No se encontró el torneo")) {
            GuiUtils.showMessageOnce(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
        } else if (mensaje.contains("eliminado exitosamente")) {
            GuiUtils.showMessageOnce(this, mensaje, "Éxito", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void cargarTorneosEnComboxBox() {
        List<Torneo> torneos = gestorTorneos.getTorneosCreados();
        GuiUtils.cargarTorneosEnComboBox(torneosComboBox, torneos);
    }

    private void clickEliminarTorneo() {
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
            cargarTorneosEnComboxBox();
        }
    }
}
