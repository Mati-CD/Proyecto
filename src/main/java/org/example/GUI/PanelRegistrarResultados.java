package org.example.GUI;

import org.example.CodigoLogico.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.SimpleDateFormat;

/**
 * Panel que permite al usuario registrar el resultado de un partido en un torneo.
 * Incluye selección de torneo, partido y resultado, además de mostrar información del torneo.
 */
public class PanelRegistrarResultados extends JPanel implements PanelConfigurable {
    private GestorTorneos gestorTorneos;
    private PanelButton irAtrasBtn;
    private PanelButton registrarBtn;
    private JComboBox<Torneo> torneosComboBox;
    private JComboBox<Partido> partidosComboBox;
    private JComboBox<String> resultadoComboBox;
    private JTextArea infoTorneoArea;
    private boolean listenersAdded = false;

    /**
     * Crea e inicializa todos los componentes visuales del panel.
     */
    public PanelRegistrarResultados() {
        super(new BorderLayout());
        setBackground(new Color(26, 94, 24));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        Font font = new Font("SansSerif", Font.BOLD, 16);

        irAtrasBtn = new PanelButton("Volver atrás", font);
        registrarBtn = new PanelButton("Registrar Resultado", font);

        torneosComboBox = new JComboBox<>();
        partidosComboBox = new JComboBox<>();
        resultadoComboBox = new JComboBox<>(new String[]{"1 - Gana primer jugador", "2 - Gana segundo jugador"});

        infoTorneoArea = new JTextArea();
        infoTorneoArea.setEditable(false);
        infoTorneoArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        infoTorneoArea.setBackground(new Color(26, 94, 24));
        infoTorneoArea.setForeground(Color.WHITE);

        JPanel formPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        formPanel.setOpaque(false);

        formPanel.add(createLabel("Torneo:"));
        formPanel.add(torneosComboBox);
        formPanel.add(createLabel("Partido:"));
        formPanel.add(partidosComboBox);
        formPanel.add(createLabel("Resultado:"));
        formPanel.add(resultadoComboBox);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.setOpaque(false);
        buttonPanel.add(registrarBtn);

        add(irAtrasBtn, BorderLayout.NORTH);
        add(formPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
        add(new JScrollPane(infoTorneoArea), BorderLayout.EAST);

        JRootPane rootPane = SwingUtilities.getRootPane(this);
        if (rootPane != null) {
            rootPane.setDefaultButton(registrarBtn);
        }

        resultadoComboBox.addActionListener(e -> registrarResultado());
    }

    /**
     * Crea una etiqueta con formato estándar.
     *
     * @param text Texto a mostrar.
     * @return JLabel configurada con el texto dado.
     */
    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setForeground(Color.WHITE);
        label.setFont(new Font("SansSerif", Font.BOLD, 16));
        return label;
    }

    /**
     * Inicializa el panel asignando acciones y cargando torneos.
     *
     * @param actionAssigner Asignador de acciones para los botones.
     * @param gestorTorneos Gestor que maneja los torneos.
     */
    @Override
    public void inicializar(ActionAssigner actionAssigner, GestorTorneos gestorTorneos) {
        this.gestorTorneos = gestorTorneos;
        if (!listenersAdded) {
            irAtrasBtn.addActionListener(actionAssigner.getAction(ActionGUI.IR_A_ORGANIZADOR.getID()));
            registrarBtn.addActionListener(e -> registrarResultado());

            KeyAdapter enterKeyAdapter = new KeyAdapter() {
                @Override
                public void keyPressed(KeyEvent e) {
                    if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                        registrarResultado();
                    }
                }
            };

            torneosComboBox.addKeyListener(enterKeyAdapter);
            partidosComboBox.addKeyListener(enterKeyAdapter);
            resultadoComboBox.addKeyListener(enterKeyAdapter);

            cargarTorneosDisponibles();
            torneosComboBox.addActionListener(e -> cargarPartidosDisponibles());

            listenersAdded = true;
        }
    }

    /**
     * Carga en el comboBox los torneos que tienen al menos una fase creada.
     */
    private void cargarTorneosDisponibles() {
        DefaultComboBoxModel<Torneo> model = new DefaultComboBoxModel<>();
        for (Torneo torneo : gestorTorneos.getTorneosCreados()) {
            if (!torneo.getFases().isEmpty()) {
                model.addElement(torneo);
            }
        }
        torneosComboBox.setModel(model);
    }

    /**
     * Carga en el comboBox los partidos que aún no tienen resultados del torneo seleccionado.
     */
    private void cargarPartidosDisponibles() {
        Torneo torneo = (Torneo) torneosComboBox.getSelectedItem();
        DefaultComboBoxModel<Partido> model = new DefaultComboBoxModel<>();

        if (torneo != null && torneo.getFaseActual() != null) {
            for (Partido partido : torneo.getPartidosActuales()) {
                if (!partido.tieneResultado()) {
                    model.addElement(partido);
                }
            }
        }

        partidosComboBox.setModel(model);
        actualizarInformacionTorneo();
    }

    /**
     * Muestra en el área de texto información básica del torneo seleccionado.
     */
    private void actualizarInformacionTorneo() {
        Torneo torneo = (Torneo) torneosComboBox.getSelectedItem();
        if (torneo != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            StringBuilder info = new StringBuilder();

            info.append("=== ").append(torneo.getNombre()).append(" ===\n");
            info.append("Disciplina: ").append(torneo.getDisciplina()).append("\n");
            info.append(torneo.getEstructuraTexto());

            infoTorneoArea.setText(info.toString());
        } else {
            infoTorneoArea.setText("");
        }
    }

    /**
     * Registra el resultado seleccionado para el partido actual y muestra mensaje de éxito o error.
     */
    private void registrarResultado() {
        Torneo torneo = (Torneo) torneosComboBox.getSelectedItem();
        Partido partido = (Partido) partidosComboBox.getSelectedItem();

        if (partido == null) {
            showMessageOnce("Seleccione un partido", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String resultado = resultadoComboBox.getSelectedItem().toString().substring(0, 1);
        torneo.registrarResultado(partido, resultado);

        cargarPartidosDisponibles();
        actualizarInformacionTorneo();
        showMessageOnce("Resultado registrado exitosamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Muestra un mensaje de diálogo en el hilo de interfaz.
     *
     * @param message Mensaje a mostrar.
     * @param title Título del cuadro de diálogo.
     * @param messageType Tipo de mensaje (JOptionPane).
     */
    private void showMessageOnce(String message, String title, int messageType) {
        SwingUtilities.invokeLater(() -> {
            JOptionPane.showMessageDialog(this, message, title, messageType);
        });
    }
}