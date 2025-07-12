package org.example.GUI;

import org.example.CodigoLogico.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class PanelRegistrarResultados extends JPanel implements PanelConfigurable {
    private PanelButton irAtrasBtn;
    private PanelButton registrarBtn;
    private JComboBox<Torneo> torneosComboBox;
    private JComboBox<Partido> partidosComboBox;
    private JComboBox<String> resultadoComboBox;
    private JTextArea infoTorneoArea;

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
        irAtrasBtn.addActionListener(actionAssigner.getAction(ActionGUI.IR_A_ORGANIZADOR.getID()));
        registrarBtn.addActionListener(e -> registrarResultado());

        // Agregar KeyListener para Enter en los combobox
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

        cargarTorneos();
        torneosComboBox.addActionListener(e -> cargarPartidos());
    }

    private void cargarTorneos() {
        DefaultComboBoxModel<Torneo> model = new DefaultComboBoxModel<>();
        for (Torneo torneo : PanelCrearTorneo.getTorneosCreados()) {
            if (!torneo.getFases().isEmpty()) {
                model.addElement(torneo);
            }
        }
        torneosComboBox.setModel(model);
    }

    private void cargarPartidos() {
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
        actualizarInformacion();
    }

    private void registrarResultado() {
        Torneo torneo = (Torneo) torneosComboBox.getSelectedItem();
        Partido partido = (Partido) partidosComboBox.getSelectedItem();

        if (partido == null) {
            JOptionPane.showMessageDialog(this, "Seleccione un partido", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String resultado = resultadoComboBox.getSelectedItem().toString().substring(0, 1);
        torneo.registrarResultado(partido, resultado);

        cargarPartidos();
        actualizarInformacion();

        JOptionPane.showMessageDialog(this, "Resultado registrado exitosamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
    }

    private void actualizarInformacion() {
        Torneo torneo = (Torneo) torneosComboBox.getSelectedItem();
        infoTorneoArea.setText(torneo != null ? torneo.getEstructuraTexto() : "");
    }
}