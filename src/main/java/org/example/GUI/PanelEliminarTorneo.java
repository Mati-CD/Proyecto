package org.example.GUI;

import org.example.CodigoLogico.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;
import java.io.IOException;
import java.util.List;

/**
 * PanelEliminarTorneo permite al usuario seleccionar y eliminar un torneo desde una lista desplegable.
 */
public class PanelEliminarTorneo extends JPanel implements PanelConfigurable, TorneoObserver {
    private GestorTorneos gestorTorneos;
    private PanelButton irAtrasBtn;
    private PanelButton eliminarBtn;
    private JComboBox<Torneo> torneosComboBox;
    private boolean listenersActivos = false;

    private BufferedImage backgroundImage;
    private final Dimension size2 = new Dimension(120, 30);
    private final Font componentFont1 = new Font("SansSerif", Font.BOLD, 14);
    private final Font componentFont2 = new Font("Arial", Font.BOLD, 12);

    /**
     * Constructor que inicializa el panel, sus componentes visuales,
     * carga la imagen de fondo y configura la interfaz.
     */
    public PanelEliminarTorneo() {
        super(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        try {
            backgroundImage = ImageIO.read(getClass().getResource("/images/image1.png"));
            if (backgroundImage == null) {
                System.err.println("La imagen no se encontró en la ruta: /images/image1.png");
                setBackground(new Color(195, 0, 0));
            }
            else {
                float darkFactor = 0.6f;
                float[] scales = {darkFactor, darkFactor, darkFactor, 1.0f};
                float[] offsets = {0f, 0f, 0f, 0f};

                RescaleOp op = new RescaleOp(scales, offsets, null);
                backgroundImage = op.filter(backgroundImage, null);
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error al cargar la imagen de fondo: " + e.getMessage());
            setBackground(new Color(195, 0, 0));
        }

        Font fontTitulo = new Font("Arial", Font.BOLD, 24);

        irAtrasBtn = new PanelButton("Volver atrás", componentFont2);
        irAtrasBtn.setButtonPreferredSize(size2);
        irAtrasBtn.setButtonColor(new Color(50, 50, 50),
                Color.WHITE,
                Color.GRAY,
                1
        );

        eliminarBtn = new PanelButton("Eliminar Torneo", componentFont1);
        eliminarBtn.setButtonPreferredSize(new Dimension(200, 50));
        eliminarBtn.setButtonColor(
                new Color(231, 76, 60), // rojo
                Color.WHITE,
                new Color(192, 57, 43), 2
        );

        // ComboBox
        torneosComboBox = new JComboBox<>();
        torneosComboBox.setFont(componentFont1);
        torneosComboBox.setRenderer(new GuiUtils.ComboBoxRenderer<>(Torneo::getNombre));
        torneosComboBox.setForeground(Color.BLACK);
        torneosComboBox.setBackground(Color.WHITE);

        // Título y encabezado
        JPanel topPanel = GuiUtils.crearPanelDeEncabezado(
                irAtrasBtn,
                "",
                fontTitulo,
                null
        );
        add(topPanel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel();
        centerPanel.setOpaque(false);
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setBackground(Color.WHITE);
        centerPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        JPanel comboPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        comboPanel.setOpaque(false);
        JLabel labelSeleccionarTorneo = new JLabel("Seleccione torneo:");
        labelSeleccionarTorneo.setFont(componentFont1);
        GuiUtils.setLabelTextColor(labelSeleccionarTorneo, Color.WHITE);
        comboPanel.add(labelSeleccionarTorneo);
        comboPanel.add(torneosComboBox);
        centerPanel.add(comboPanel, BorderLayout.NORTH);

        JPanel botonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        botonPanel.setOpaque(false);
        botonPanel.setBackground(Color.WHITE);
        botonPanel.add(eliminarBtn);

        centerPanel.add(comboPanel);
        centerPanel.add(Box.createVerticalStrut(20));
        centerPanel.add(botonPanel);

        add(centerPanel, BorderLayout.CENTER);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            Graphics2D g2d = (Graphics2D) g;

            g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
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
        SwingUtilities.invokeLater(() -> {
            if (mensaje.startsWith("ERROR_TORNEO_NO_ELIMINABLE:")) {
                GuiUtils.showMessageOnce(this, GuiUtils.formatearMensajeTorneo(mensaje), "Error", JOptionPane.ERROR_MESSAGE);
            } else if (mensaje.startsWith("ERROR_TORNEO_NO_ENCONTRADO:")) {
                GuiUtils.showMessageOnce(this, GuiUtils.formatearMensajeTorneo(mensaje), "Error", JOptionPane.ERROR_MESSAGE);
            } else if (mensaje.startsWith("EXITO_TORNEO_ELIMINADO:")) {
                GuiUtils.showMessageOnce(this, GuiUtils.formatearMensajeTorneo(mensaje), "Éxito", JOptionPane.INFORMATION_MESSAGE);
            }
        });
    }

    /**
     * Carga en el combo box los torneos actualmente creados disponibles
     * a través del gestor de torneos.
     */
    private void cargarTorneosEnComboxBox() {
        List<Torneo> torneos = gestorTorneos.getTorneosCreados();
        GuiUtils.cargarTorneosEnComboBox(torneosComboBox, torneos);
    }

    /**
     * Muestra una confirmación antes de proceder con la eliminación.
     * Si el usuario acepta, elimina el torneo seleccionado y actualiza la lista.
     */
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
