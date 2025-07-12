package org.example.GUI;

import org.example.CodigoLogico.Torneo;
import javax.swing.*;
import java.awt.*;

public class PanelInscribirParticipantes extends JPanel implements PanelConfigurable {
    private PanelButton irAtrasBtn;
    private PanelButton inscribirBtn;
    private JTextField nombreField;
    private JComboBox<Torneo> torneosComboBox;
    private DefaultListModel<String> participantesModel;
    private JList<String> participantesList;

    public PanelInscribirParticipantes() {
        super(new BorderLayout());
        setBackground(new Color(6, 153, 153));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        Font font = new Font("SansSerif", Font.BOLD, 16);
        Font titleFont = new Font("Arial", Font.BOLD, 24);

        // Panel superior con botón de volver
        irAtrasBtn = new PanelButton("Volver atrás", font);
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setOpaque(false);
        topPanel.add(irAtrasBtn, BorderLayout.WEST);

        // Título centrado
        JLabel titleLabel = new JLabel("Inscribir Participantes", SwingConstants.CENTER);
        titleLabel.setFont(titleFont);
        topPanel.add(titleLabel, BorderLayout.CENTER);
        add(topPanel, BorderLayout.NORTH);

        // Panel central con formulario
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setOpaque(false);
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;

        // Selección de torneo
        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(new JLabel("Seleccione torneo:"), gbc);

        gbc.gridx = 1;
        torneosComboBox = new JComboBox<>();
        torneosComboBox.setFont(font);
        formPanel.add(torneosComboBox, gbc);

        // Campo: Nombre participante
        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(new JLabel("Nombre participante:"), gbc);

        gbc.gridx = 1;
        nombreField = new JTextField(20);
        nombreField.setFont(font);
        formPanel.add(nombreField, gbc);

        // Lista de participantes
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        participantesModel = new DefaultListModel<>();
        participantesList = new JList<>(participantesModel);
        participantesList.setFont(font);
        formPanel.add(new JLabel("Participantes inscritos:"), gbc);

        gbc.gridy = 3;
        formPanel.add(new JScrollPane(participantesList), gbc);

        add(formPanel, BorderLayout.CENTER);

        // Panel inferior con botón de inscripción
        inscribirBtn = new PanelButton("Inscribir Participante", font);
        JPanel bottomPanel = new JPanel();
        bottomPanel.setOpaque(false);
        bottomPanel.add(inscribirBtn);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    @Override
    public void inicializar(ActionAssigner actionAssigner) {
        irAtrasBtn.addActionListener(actionAssigner.getAction(ActionGUI.IR_A_ORGANIZADOR.getID()));
        inscribirBtn.addActionListener(e -> inscribirParticipante());

        // Cargar torneos disponibles
        DefaultComboBoxModel<Torneo> model = new DefaultComboBoxModel<>();
        for (Torneo torneo : PanelCrearTorneo.getTorneosCreados()) {
            model.addElement(torneo);
        }
        torneosComboBox.setModel(model);
        torneosComboBox.addActionListener(e -> actualizarListaParticipantes());

        this.revalidate();
        this.repaint();
    }

    private void inscribirParticipante() {
        Torneo torneo = (Torneo) torneosComboBox.getSelectedItem();
        String nombre = nombreField.getText().trim();

        if (torneo == null) {
            JOptionPane.showMessageDialog(this, "Seleccione un torneo primero",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (nombre.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese un nombre válido",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        torneo.agregarParticipante(nombre);
        participantesModel.addElement(nombre);
        nombreField.setText("");
        JOptionPane.showMessageDialog(this, "Participante inscrito correctamente",
                "Éxito", JOptionPane.INFORMATION_MESSAGE);
    }

    private void actualizarListaParticipantes() {
        Torneo torneo = (Torneo) torneosComboBox.getSelectedItem();
        participantesModel.clear();

        if (torneo != null) {
            for (String participante : torneo.getParticipantes()) {
                participantesModel.addElement(participante);
            }
        }
    }
}