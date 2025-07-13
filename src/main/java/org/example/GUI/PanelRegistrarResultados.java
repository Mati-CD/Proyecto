package org.example.GUI;

import org.example.CodigoLogico.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.SimpleDateFormat;

public class PanelRegistrarResultados extends JPanel implements PanelConfigurable {
    private PanelButton irAtrasBtn;
    private PanelButton registrarBtn;
    private JComboBox<Torneo> torneosComboBox;
    private JComboBox<Partido> partidosComboBox;
    private JComboBox<String> resultadoComboBox;
    private JTextArea infoTorneoArea;
    private boolean listenersAdded = false;

    public PanelRegistrarResultados() {
        super(new BorderLayout());
        setBackground(new Color(26, 94, 24));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        Font font = new Font("SansSerif", Font.BOLD, 16);

        // Configuración de componentes
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

        // Organización de la interfaz
        JPanel formPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        formPanel.setOpaque(false);

        formPanel.add(createLabel("Torneo:"));
        formPanel.add(torneosComboBox);
        formPanel.add(createLabel("Partido:"));
        formPanel.add(partidosComboBox);
        formPanel.add(createLabel("Resultado:"));
        formPanel.add(resultadoComboBox);

        // Panel para el botón de registrar
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.setOpaque(false);
        buttonPanel.add(registrarBtn);

        // Agregar componentes al panel principal
        add(irAtrasBtn, BorderLayout.NORTH);
        add(formPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
        add(new JScrollPane(infoTorneoArea), BorderLayout.EAST);

        // Configurar el botón de registrar como botón por defecto
        JRootPane rootPane = SwingUtilities.getRootPane(this);
        if (rootPane != null) {
            rootPane.setDefaultButton(registrarBtn);
        }

        // Permitir confirmar con Enter en el comboBox de resultados
        resultadoComboBox.addActionListener(e -> registrarResultado());
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setForeground(Color.WHITE);
        label.setFont(new Font("SansSerif", Font.BOLD, 16));
        return label;
    }

    @Override
    public void inicializar(ActionAssigner actionAssigner) {
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

    private void cargarTorneosDisponibles() {
        DefaultComboBoxModel<Torneo> model = new DefaultComboBoxModel<>();
        for (Torneo torneo : PanelCrearTorneo.getTorneosCreados()) {
            if (!torneo.getFases().isEmpty()) {
                model.addElement(torneo);
            }
        }
        torneosComboBox.setModel(model);
    }

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

    private void actualizarInformacionTorneo() {
        Torneo torneo = (Torneo) torneosComboBox.getSelectedItem();
        if (torneo != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            StringBuilder info = new StringBuilder();

            info.append("=== ").append(torneo.getNombre()).append(" ===\n");
            info.append("Disciplina: ").append(torneo.getDisciplina()).append("\n");
            info.append("Fecha: ").append(sdf.format(torneo.getFecha())).append("\n\n");
            info.append(torneo.getEstructuraTexto());

            infoTorneoArea.setText(info.toString());
        } else {
            infoTorneoArea.setText("");
        }
    }

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

    private void showMessageOnce(String message, String title, int messageType) {
        SwingUtilities.invokeLater(() -> {
            JOptionPane.showMessageDialog(this, message, title, messageType);
        });
    }
}