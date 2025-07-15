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
        super(new BorderLayout(10, 10));
        setBackground(new Color(26, 94, 24));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        Font font = new Font("SansSerif", Font.BOLD, 16);

        irAtrasBtn = new PanelButton("Volver atrás", font);
        registrarBtn = new PanelButton("Registrar Resultado", font);

        torneosComboBox = new JComboBox<>();
        partidosComboBox = new JComboBox<>();
        resultadoComboBox = new JComboBox<>(new String[]{
                "1 - Gana primer jugador",
                "2 - Gana segundo jugador"
        });

        // Área de información más grande
        infoTorneoArea = new JTextArea(15, 35);
        infoTorneoArea.setEditable(false);
        infoTorneoArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        infoTorneoArea.setBackground(new Color(46, 124, 44));
        infoTorneoArea.setForeground(Color.WHITE);
        infoTorneoArea.setBorder(BorderFactory.createTitledBorder("Información del torneo"));

        // Panel superior con botón volver atrás
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        topPanel.setOpaque(false);
        topPanel.add(irAtrasBtn);
        add(topPanel, BorderLayout.NORTH);

        // Panel del formulario
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setOpaque(false);
        formPanel.setBorder(BorderFactory.createTitledBorder("Registro de resultado"));
        formPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        formPanel.add(createLabeledComponent("Torneo:", torneosComboBox));
        formPanel.add(Box.createVerticalStrut(10));
        formPanel.add(createLabeledComponent("Partido:", partidosComboBox));
        formPanel.add(Box.createVerticalStrut(10));
        formPanel.add(createLabeledComponent("Resultado:", resultadoComboBox));
        formPanel.add(Box.createVerticalStrut(20));
        formPanel.add(registrarBtn);

        // Panel central: formulario + área de información más ancha
        JPanel centerPanel = new JPanel(new BorderLayout(10, 10));
        centerPanel.setOpaque(false);
        centerPanel.add(formPanel, BorderLayout.CENTER);

        JScrollPane scrollPane = new JScrollPane(infoTorneoArea);
        scrollPane.setPreferredSize(new Dimension(350, 300));
        centerPanel.add(scrollPane, BorderLayout.EAST);

        add(centerPanel, BorderLayout.CENTER);

        // Tecla ENTER como atajo
        JRootPane rootPane = SwingUtilities.getRootPane(this);
        if (rootPane != null) {
            rootPane.setDefaultButton(registrarBtn);
        }

        resultadoComboBox.addActionListener(e -> registrarResultado());
    }

    /**
     * Crea un componente etiquetado alineado a la izquierda con ancho extendido.
     */
    private JPanel createLabeledComponent(String labelText, JComponent component) {
        JLabel label = new JLabel(labelText);
        label.setForeground(Color.WHITE);
        label.setFont(new Font("SansSerif", Font.BOLD, 16));
        label.setPreferredSize(new Dimension(100, 25));

        component.setPreferredSize(new Dimension(440, 25));
        component.setMaximumSize(new Dimension(440, 25));

        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        panel.setOpaque(false);
        panel.add(label);
        panel.add(component);
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);

        return panel;
    }

    /**
     * Inicializa el panel y carga torneos.
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
     * Carga partidos del torneo seleccionado que no tengan resultado.
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
     * Actualiza el área de información con los datos del torneo seleccionado.
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
     * Registra el resultado del partido seleccionado.
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
     * Muestra un mensaje modal.
     */
    private void showMessageOnce(String message, String title, int messageType) {
        SwingUtilities.invokeLater(() -> {
            JOptionPane.showMessageDialog(this, message, title, messageType);
        });
    }
}
