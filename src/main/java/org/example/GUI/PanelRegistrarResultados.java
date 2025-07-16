package org.example.GUI;

import org.example.CodigoLogico.*;

import javax.swing.*;
import java.awt.*;
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

    /**
     * Crea e inicializa todos los componentes visuales del panel.
     */
    public PanelRegistrarResultados() {
        super(new BorderLayout(10, 10));
        setBackground(new Color(26, 94, 24));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        Font labelFont = new Font("SansSerif", Font.BOLD, 16);
        Font buttonFont = new Font("Arial", Font.BOLD, 12);
        Font titleFont = new Font("Arial", Font.BOLD, 24);

        // Inicializar Componentes
        irAtrasBtn = new PanelButton("Volver atrás", buttonFont);
        irAtrasBtn.setButtonPreferredSize(new Dimension(120, 30));
        registrarGanadorBtn = new PanelButton("Registrar Resultado", buttonFont);
        registrarGanadorBtn.setButtonPreferredSize(new Dimension(200, 50));

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
        infoTorneoArea.setBackground(new Color(46, 124, 44));
        infoTorneoArea.setForeground(Color.WHITE);
        infoTorneoArea.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.GRAY), "Información del torneo", 0, 0, labelFont, Color.WHITE));

        faseActualLabel = new JLabel("Fase Actual: N/A");
        faseActualLabel.setForeground(Color.WHITE);
        faseActualLabel.setFont(labelFont);
        faseActualLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Panel Superior
        JPanel topPanel = GuiUtils.crearPanelDeEncabezado(
                irAtrasBtn,
                "Registrar Resultados",
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
            if (notificacionVisibleParaOrganizador(mensaje)) {
                String mensajeFormateado = formatearNotificacionTorneo(mensaje);
                infoTorneoArea.append(mensajeFormateado + "\n");
                infoTorneoArea.setCaretPosition(infoTorneoArea.getDocument().getLength());
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
                    String mensajeFormateado = formatearNotificacionTorneo(mensaje);
                    tempLog.append(mensajeFormateado).append("\n");
                }
            }
            infoTorneoArea.setText(tempLog.toString());
            infoTorneoArea.setCaretPosition(infoTorneoArea.getDocument().getLength());
        } else {
            infoTorneoArea.setText("Seleccione un torneo para ver sus últimas actualizaciones.");
        }
    }

    private String formatearNotificacionTorneo(String mensaje) {
        String mensajeFormateado = mensaje;

        if (mensaje.startsWith("EVENTO_PARTIDO_FINALIZADO:")) {
            return mensaje.substring("EVENTO_PARTIDO_FINALIZADO:".length());
        } else if (mensaje.startsWith("EVENTO_GANADOR_SELECCIONADO:")) {
            return mensaje.substring("EVENTO_GANADOR_SELECCIONADO:".length());
        } else if (mensaje.startsWith("EVENTO_SALTO_DE_LINEA:")) {
            return "";
        }
        return mensajeFormateado;
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
