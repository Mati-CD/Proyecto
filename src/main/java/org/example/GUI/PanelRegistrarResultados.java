package org.example.GUI;

import org.example.CodigoLogico.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Panel que permite al usuario registrar el resultado de un partido en un torneo.
 * Incluye selección de torneo, partido y resultado, además de mostrar información del torneo.
 */
public class PanelRegistrarResultados extends JPanel implements PanelConfigurable, TorneoObserver {
    private GestorTorneos gestorTorneos;
    private PanelButton irAtrasBtn;
    private PanelButton registrarGanadorBtn;
    private JComboBox<Torneo> torneosComboBox;
    private JComboBox<Partido> partidosComboBox;
    private JComboBox<String> resultadoComboBox;
    private JTextArea infoTorneoArea;
    private JLabel faseActualLabel;
    private Torneo actualObservedTorneo = null;
    private boolean listenersAdded = false;

    private BufferedImage backgroundImage;

    /**
     * Crea e inicializa todos los componentes visuales del panel.
     */
    public PanelRegistrarResultados() {
        super(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        try {
            backgroundImage = ImageIO.read(getClass().getResource("/images/image1.png"));
            if (backgroundImage == null) {
                System.err.println("La imagen no se encontró en la ruta: /images/image1.png");
                setBackground(new Color(128, 0, 128));
            }
            else {
                float[] scales = {1.0f, 0.5f, 1.2f, 1.0f};
                float[] offsets = {0f, 0f, 30f, 0f};
                RescaleOp op = new RescaleOp(scales, offsets, null);
                backgroundImage = op.filter(backgroundImage, null);
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error al cargar la imagen de fondo: " + e.getMessage());
            setBackground(new Color(128, 0, 128));
        }

        Font labelFont = new Font("SansSerif", Font.BOLD, 16);
        Font buttonFont = new Font("Arial", Font.BOLD, 12);
        Font titleFont = new Font("Arial", Font.BOLD, 24);

        // Botones con estilo uniforme
        irAtrasBtn = new PanelButton("Volver atrás", buttonFont);
        irAtrasBtn.setButtonPreferredSize(new Dimension(120, 30));
        aplicarEstiloBoton(irAtrasBtn);

        registrarGanadorBtn = new PanelButton("Registrar Resultado", buttonFont);
        registrarGanadorBtn.setButtonPreferredSize(new Dimension(200, 50));
        aplicarEstiloBoton(registrarGanadorBtn);

        torneosComboBox = new JComboBox<>();
        torneosComboBox.setFont(labelFont);
        torneosComboBox.setRenderer(new GuiUtils.ComboBoxRenderer<>(Torneo::getNombre));

        partidosComboBox = new JComboBox<>();
        partidosComboBox.setFont(labelFont);
        partidosComboBox.setRenderer(new GuiUtils.ComboBoxRenderer<>(Partido::getNombre));
        partidosComboBox.setPreferredSize(new Dimension(250, 30));
        partidosComboBox.setMaximumSize(new Dimension(300, 30));

        resultadoComboBox = new JComboBox<>();
        resultadoComboBox.setFont(labelFont);
        resultadoComboBox.setPreferredSize(new Dimension(250, 30));
        resultadoComboBox.setMaximumSize(new Dimension(300, 30));

        infoTorneoArea = new JTextArea(15, 35);
        infoTorneoArea.setEditable(false);
        infoTorneoArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        infoTorneoArea.setBackground(new Color(165, 0, 74));
        infoTorneoArea.setForeground(Color.WHITE);
        infoTorneoArea.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.GRAY), "Información del torneo", 0, 0, labelFont, Color.WHITE));

        faseActualLabel = new JLabel("Fase Actual: N/A");
        faseActualLabel.setForeground(Color.WHITE);
        faseActualLabel.setFont(labelFont);
        faseActualLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Panel Superior
        JPanel topPanel = GuiUtils.crearPanelDeEncabezado(
                irAtrasBtn,
                "",
                titleFont,
                null
        );
        add(topPanel, BorderLayout.NORTH);

        // Panel Central
        JPanel centerPanel = new JPanel(new BorderLayout(20, 0));
        centerPanel.setOpaque(false);
        centerPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

        JPanel comboTorneosPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        comboTorneosPanel.setOpaque(false);
        JLabel seleccionTorneoLabel = new JLabel("Seleccione Torneo:");
        seleccionTorneoLabel.setForeground(Color.WHITE);
        seleccionTorneoLabel.setFont(labelFont);
        comboTorneosPanel.add(seleccionTorneoLabel);
        comboTorneosPanel.add(torneosComboBox);
        centerPanel.add(comboTorneosPanel, BorderLayout.NORTH);

        // Panel central se divide en dos secciones
        JPanel doublePanel = new JPanel(new GridLayout(1, 2, 20, 0));
        doublePanel.setOpaque(false);
        doublePanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setOpaque(false);
        infoTorneoArea.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.GRAY), "Últimas Actualizaciones", 0, 0, labelFont, Color.WHITE));
        leftPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Fase Actual
        JPanel faseActualPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        faseActualPanel.setOpaque(false);
        faseActualPanel.add(faseActualLabel);
        leftPanel.add(faseActualPanel);
        leftPanel.add(Box.createVerticalStrut(10));

        // ComboBox para partidos
        JPanel labelPartidoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        labelPartidoPanel.setOpaque(false);
        JLabel selectPartidoLabel = new JLabel("Seleccione Partido:");
        selectPartidoLabel.setForeground(Color.WHITE);
        selectPartidoLabel.setFont(labelFont);
        labelPartidoPanel.add(selectPartidoLabel);
        leftPanel.add(labelPartidoPanel);

        JPanel comboPartidoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        comboPartidoPanel.setOpaque(false);
        comboPartidoPanel.add(partidosComboBox);
        leftPanel.add(comboPartidoPanel);
        leftPanel.add(Box.createVerticalStrut(10));

        // ComboBox para elegir un ganador
        JPanel labelGanadorPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        labelGanadorPanel.setOpaque(false);
        JLabel elegirGanadorLabel = new JLabel("Elegir Ganador:");
        elegirGanadorLabel.setForeground(Color.WHITE);
        elegirGanadorLabel.setFont(labelFont);
        labelGanadorPanel.add(elegirGanadorLabel);
        leftPanel.add(labelGanadorPanel);

        JPanel comboGanadorPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        comboGanadorPanel.setOpaque(false);
        comboGanadorPanel.add(resultadoComboBox);
        leftPanel.add(comboGanadorPanel);
        leftPanel.add(Box.createVerticalStrut(20));

        // Panel central inferior
        JPanel bottomCenterPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        bottomCenterPanel.setOpaque(false);
        bottomCenterPanel.add(registrarGanadorBtn);
        leftPanel.add(bottomCenterPanel);

        leftPanel.add(Box.createVerticalGlue());

        doublePanel.add(leftPanel);

        // Área de actualizaciones
        JScrollPane scrollPane = new JScrollPane(infoTorneoArea);
        scrollPane.setPreferredSize(new Dimension(350, 300));
        scrollPane.setMinimumSize(new Dimension(300, 200));
        scrollPane.setMaximumSize(new Dimension(400, 500));

        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.setOpaque(false);
        rightPanel.add(scrollPane, BorderLayout.CENTER);
        doublePanel.add(rightPanel);

        centerPanel.add(doublePanel, BorderLayout.CENTER);

        add(centerPanel, BorderLayout.CENTER);
    }

    /**
     * Aplica el estilo de botón sin borde, con texto negro y efecto hover gris claro.
     */
    private void aplicarEstiloBoton(PanelButton boton) {
        boton.setButtonColor(
                new Color(220, 220, 220),  // Color base fondo gris claro
                Color.BLACK,               // Color texto negro
                new Color(200, 200, 200),  // Color fondo hover gris más oscuro
                0                         // Sin borde
        );
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

    /**
     * Inicializa el panel y carga torneos.
     */
    @Override
    public void inicializar(ActionAssigner actionAssigner, GestorTorneos gestorTorneos) {
        this.gestorTorneos = gestorTorneos;

        if (!listenersAdded) {
            irAtrasBtn.addActionListener(actionAssigner.getAction(ActionGUI.IR_A_ORGANIZADOR.getID()));
            registrarGanadorBtn.addActionListener(e -> clickRegistrarResultado());
            torneosComboBox.addActionListener(e -> actualizarObserverDelTorneo());
            partidosComboBox.addActionListener(e -> actualizarGanadorSeleccionComboBox());
            listenersAdded = true;
        }
        cargarTorneosEnComboBox();

        this.revalidate();
        this.repaint();
    }

    @Override
    public void actualizar(String mensaje) {
        SwingUtilities.invokeLater(() -> {
            if (mensaje.startsWith("ERROR_BRACKET_GENERACION:") || mensaje.startsWith("ERROR_BRACKET_PARTICIPANTES:")) {
                GuiUtils.showMessageOnce(this, GuiUtils.formatearMensajeTorneo(mensaje), "Error", JOptionPane.ERROR_MESSAGE);
            } else if (mensaje.startsWith("ADVERTENCIA_TORNEO_INICIADO:") || mensaje.startsWith("ADVERTENCIA_PARTICIPANTES_INCORRECTOS:")) {
                GuiUtils.showMessageOnce(this, GuiUtils.formatearMensajeTorneo(mensaje), "Advertencia", JOptionPane.WARNING_MESSAGE);
            } else if (mensaje.startsWith("EXITO_TORNEO_INICIADO:")) {
                GuiUtils.showMessageOnce(this, GuiUtils.formatearMensajeTorneo(mensaje), "Éxito", JOptionPane.INFORMATION_MESSAGE);
            }
            else {
                if (notificacionVisibleParaOrganizador(mensaje)) {
                    infoTorneoArea.append(GuiUtils.formatearMensajeTorneo(mensaje) + "\n");
                    infoTorneoArea.setCaretPosition(infoTorneoArea.getDocument().getLength());
                }
            }
        });
    }

    private void actualizarObserverDelTorneo() {
        Torneo nuevoTorneoSeleccionado = (Torneo) torneosComboBox.getSelectedItem();

        if (actualObservedTorneo != null && actualObservedTorneo != nuevoTorneoSeleccionado) {
            actualObservedTorneo.removerObserver(this);
        }

        if (nuevoTorneoSeleccionado != null && actualObservedTorneo != nuevoTorneoSeleccionado) {
            nuevoTorneoSeleccionado.registrarObserver(this);
            actualObservedTorneo = nuevoTorneoSeleccionado;
        } else if (nuevoTorneoSeleccionado == null && actualObservedTorneo != null) {
            actualObservedTorneo = null;
        }

        cargarEnfrentamientosDisponibles();
        actualizarUltimasNotificaciones();
    }

    private void actualizarUltimasNotificaciones() {
        infoTorneoArea.setText("");

        if (actualObservedTorneo != null) {
            StringBuilder tempLog = new StringBuilder();
            for (String mensaje : actualObservedTorneo.getHistorialNotificaciones()) {
                if (notificacionVisibleParaOrganizador(mensaje)) {
                    tempLog.append(GuiUtils.formatearMensajeTorneo(mensaje)).append("\n");
                }
            }
            infoTorneoArea.setText(tempLog.toString());
            infoTorneoArea.setCaretPosition(infoTorneoArea.getDocument().getLength());
        } else {
            infoTorneoArea.setText("Seleccione un torneo para ver sus últimas actualizaciones.");
        }
    }

    private boolean notificacionVisibleParaOrganizador(String mensaje) {
        if (mensaje.contains("El torneo '") && mensaje.contains("ya ha sido iniciado.")) {
            return false;
        }
        if (mensaje.contains("La lista de participantes para iniciar el torneo tiene una cantidad incorrecta.")) {
            return false;
        }
        if (mensaje.startsWith("Resultado registrado: ")) {
            return false;
        }
        return true;
    }

    private void cargarEnfrentamientosDisponibles() {
        Torneo torneo = (Torneo) torneosComboBox.getSelectedItem();
        DefaultComboBoxModel<Partido> model = new DefaultComboBoxModel<>();

        actualizarFaseActualLabel(torneo);

        if (torneo != null && !torneo.tieneCampeon()) {
            for (Partido partido : torneo.getPartidosActuales()) {
                if (!partido.tieneResultado()) {
                    model.addElement(partido);
                }
            }
        }
        partidosComboBox.setModel(model);
        actualizarGanadorSeleccionComboBox();
    }

    private void actualizarFaseActualLabel(Torneo torneo) {
        if (torneo == null) {
            faseActualLabel.setText("Fase Actual: N/A");
        } else if (torneo.tieneCampeon()) {
            faseActualLabel.setText("Campeón: " + torneo.getCampeon());
        } else if (torneo.getFaseActual() != null) {
            faseActualLabel.setText("Fase Actual: " + torneo.getFaseActual().getNombre());
        } else {
            faseActualLabel.setText("Fase Actual: N/A");
        }
    }

    private void cargarTorneosEnComboBox() {
        List<Torneo> torneos = new ArrayList<>();
        for (Torneo torneo : gestorTorneos.getTorneosCreados()) {
            if (!torneo.getFases().isEmpty()) {
                torneos.add(torneo);
            }
        }
        GuiUtils.cargarTorneosEnComboBox(torneosComboBox, torneos);
    }

    private void actualizarGanadorSeleccionComboBox() {
        Partido partido = (Partido) partidosComboBox.getSelectedItem();
        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
        if (partido != null) {
            model.addElement(partido.getJugador1());
            model.addElement(partido.getJugador2());
        }
        resultadoComboBox.setModel(model);
    }

    private void clickRegistrarResultado() {
        Torneo torneo = (Torneo) torneosComboBox.getSelectedItem();
        Partido partido = (Partido) partidosComboBox.getSelectedItem();
        String ganadorSeleccionado = (String) resultadoComboBox.getSelectedItem();

        if (torneo == null) {
            GuiUtils.showMessageOnce(this, "Seleccione un torneo antes de registrar un resultado.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (partido == null) {
            GuiUtils.showMessageOnce(this, "Seleccione un partido antes de registrar un resultado.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (ganadorSeleccionado == null) {
            GuiUtils.showMessageOnce(this, "Seleccione un ganador para el partido.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            torneo.registrarResultado(partido, ganadorSeleccionado);
            cargarEnfrentamientosDisponibles();
            GuiUtils.showMessageOnce(this, "Resultado registrado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } catch (IllegalStateException | IllegalArgumentException e) {
            GuiUtils.showMessageOnce(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
