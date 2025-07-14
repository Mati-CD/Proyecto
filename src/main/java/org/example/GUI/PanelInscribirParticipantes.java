package org.example.GUI;

import org.example.CodigoLogico.*;
import javax.swing.*;
import java.awt.*;

public class PanelInscribirParticipantes extends JPanel implements PanelConfigurable {
    private GestorTorneos gestorTorneos;
    private PanelParticipanteInput panelParticipanteInput;
    private PanelButton irAtrasBtn;
    private PanelButton inscribirBtn;
    private JComboBox<Torneo> torneosComboBox;
    private DefaultListModel<String> participantesModel;
    private JList<String> participantesList;
    private boolean initialized = false;

    public PanelInscribirParticipantes() {
        super(new BorderLayout());
        setBackground(new Color(6, 153, 153));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        Font font = new Font("SansSerif", Font.BOLD, 14);
        Font titleFont = new Font("Arial", Font.BOLD, 24);

        inscribirBtn = new PanelButton("Inscribir Participante", font);
        torneosComboBox = new JComboBox<>();
        participantesModel = new DefaultListModel<>();
        participantesList = new JList<>(participantesModel);
        panelParticipanteInput = new PanelParticipanteInput();

        torneosComboBox.setFont(font);
        participantesList.setFont(font);
        participantesList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setOpaque(false);

        irAtrasBtn = new PanelButton("Volver atrás", font);
        irAtrasBtn.setButtonPreferredSize(new Dimension(120, 30));
        JPanel topLeftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        topLeftPanel.setOpaque(false);
        topLeftPanel.add(irAtrasBtn);
        topPanel.add(topLeftPanel, BorderLayout.WEST);

        JLabel titleLabel = new JLabel("Inscribir Participantes", SwingConstants.CENTER);
        titleLabel.setFont(titleFont);
        topPanel.add(titleLabel, BorderLayout.CENTER);

        add(topPanel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new BorderLayout(50, 0));
        centerPanel.setOpaque(false);
        centerPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));

        JPanel centerLeftPanel = new JPanel(new BorderLayout());
        centerLeftPanel.setOpaque(false);

        JPanel torneoSelectionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        torneoSelectionPanel.setOpaque(false);
        JLabel labelSeleccionarTorneo = new JLabel("Seleccione torneo:");
        labelSeleccionarTorneo.setFont(font);
        torneoSelectionPanel.add(labelSeleccionarTorneo);
        torneoSelectionPanel.add(torneosComboBox);
        centerLeftPanel.add(torneoSelectionPanel, BorderLayout.NORTH);

        centerLeftPanel.add(panelParticipanteInput, BorderLayout.CENTER);
        centerPanel.add(centerLeftPanel, BorderLayout.WEST);

        JPanel centerRightPanel = new JPanel(new BorderLayout());
        centerRightPanel.setOpaque(false);
        centerRightPanel.setBorder(BorderFactory.createEmptyBorder(0, 50, 0, 0));

        JLabel labelParticipantesInscritos = new JLabel("Participantes inscritos:");
        labelParticipantesInscritos.setFont(font);
        centerRightPanel.add(labelParticipantesInscritos, BorderLayout.NORTH);

        JScrollPane scrollPane = new JScrollPane(participantesList);
        scrollPane.setPreferredSize(new Dimension(280, 250));
        centerRightPanel.add(scrollPane, BorderLayout.CENTER);

        centerPanel.add(centerRightPanel, BorderLayout.CENTER);

        add(centerPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        bottomPanel.setOpaque(false);
        inscribirBtn.setButtonPreferredSize(new Dimension(200, 50));
        bottomPanel.add(inscribirBtn);
        add(bottomPanel, BorderLayout.SOUTH);

        initialized = true;
    }

    @Override
    public void inicializar(ActionAssigner actionAssigner, GestorTorneos gestorTorneos) {
        this.gestorTorneos = gestorTorneos;
        if (!initialized) {
            throw new IllegalStateException("Panel no ha sido inicializado correctamente");
        }

        for (var listener : irAtrasBtn.getActionListeners()) {
            irAtrasBtn.removeActionListener(listener);
        }
        for (var listener : inscribirBtn.getActionListeners()) {
            inscribirBtn.removeActionListener(listener);
        }
        for (var listener : torneosComboBox.getActionListeners()) {
            torneosComboBox.removeActionListener(listener);
        }

        irAtrasBtn.addActionListener(actionAssigner.getAction(ActionGUI.IR_A_ORGANIZADOR.getID()));
        inscribirBtn.addActionListener(e -> inscribirParticipante());

        DefaultComboBoxModel<Torneo> model = new DefaultComboBoxModel<>();
        for (Torneo torneo : gestorTorneos.getTorneosCreados()) {
            model.addElement(torneo);
        }
        torneosComboBox.setModel(model);
        torneosComboBox.addActionListener(e -> actualizarListaParticipantes());

        this.revalidate();
        this.repaint();
    }

    private void inscribirParticipante() {
        Torneo torneo = (Torneo) torneosComboBox.getSelectedItem();
        String nombre = panelParticipanteInput.getNombreParticipante().trim();
        String edadStr = panelParticipanteInput.getEdadParticipante().trim();
        String pais = panelParticipanteInput.getPaisParticipante().trim();

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

        try {
            int edad = edadStr.isEmpty() ? 0 : Integer.parseInt(edadStr);
            ParticipanteIndividual p = new ParticipanteIndividual(nombre, edad, pais.isEmpty() ? null : pais);

            torneo.inscribirParticipante(p);
            participantesModel.addElement(p.getNombre());
            panelParticipanteInput.clearFields();
            JOptionPane.showMessageDialog(this, "Participante inscrito correctamente",
                    "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "La edad debe ser un número válido",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void actualizarListaParticipantes() {
        Torneo torneo = (Torneo) torneosComboBox.getSelectedItem();
        participantesModel.clear();

        if (torneo != null) {
            for (Participante participante : torneo.getParticipantes()) {
                participantesModel.addElement(participante.getNombre());
            }
        }
    }
}