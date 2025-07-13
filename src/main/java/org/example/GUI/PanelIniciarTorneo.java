package org.example.GUI;

import org.example.CodigoLogico.*;
import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;

public class PanelIniciarTorneo extends JPanel implements PanelConfigurable, TorneoObserver {
    private PanelButton irAtrasBtn;
    private PanelButton iniciarTorneoBtn;
    private JComboBox<Torneo> torneosComboBox;
    private JTextArea infoTorneoArea;
    private DefaultListModel<String> participantesModel;
    private JList<String> participantesList;
    private Torneo torneoSeleccionado;
    private boolean listenersAdded = false;

    public PanelIniciarTorneo() {
        super(new BorderLayout());
        setBackground(new Color(88, 150, 234));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        Font font = new Font("SansSerif", Font.BOLD, 16);
        Font titleFont = new Font("Arial", Font.BOLD, 24);

        // Panel superior con botón de volver
        irAtrasBtn = new PanelButton("Volver atrás", font);
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setOpaque(false);
        topPanel.add(irAtrasBtn, BorderLayout.WEST);

        // Título centrado
        JLabel titleLabel = new JLabel("Iniciar Torneo", SwingConstants.CENTER);
        titleLabel.setFont(titleFont);
        topPanel.add(titleLabel, BorderLayout.CENTER);
        add(topPanel, BorderLayout.NORTH);

        // Panel central con controles
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;

        // Selección de torneo
        gbc.gridx = 0;
        gbc.gridy = 0;
        mainPanel.add(new JLabel("Seleccione torneo:"), gbc);

        gbc.gridx = 1;
        torneosComboBox = new JComboBox<>();
        torneosComboBox.setFont(font);
        mainPanel.add(torneosComboBox, gbc);

        // Botón para iniciar torneo
        gbc.gridx = 2;
        iniciarTorneoBtn = new PanelButton("Iniciar Torneo", font);
        mainPanel.add(iniciarTorneoBtn, gbc);

        // Información del torneo
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 3;
        infoTorneoArea = new JTextArea(5, 40);
        infoTorneoArea.setEditable(false);
        infoTorneoArea.setFont(font);
        infoTorneoArea.setLineWrap(true);
        infoTorneoArea.setWrapStyleWord(true);
        infoTorneoArea.setBackground(new Color(240, 240, 240));
        mainPanel.add(new JScrollPane(infoTorneoArea), gbc);

        // Lista de participantes
        gbc.gridy = 2;
        participantesModel = new DefaultListModel<>();
        participantesList = new JList<>(participantesModel);
        participantesList.setFont(font);
        participantesList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        mainPanel.add(new JLabel("Participantes:"), gbc);

        gbc.gridx = 1;
        gbc.gridwidth = 2;
        mainPanel.add(new JScrollPane(participantesList), gbc);

        add(mainPanel, BorderLayout.CENTER);
    }

    @Override
    public void inicializar(ActionAssigner actionAssigner) {
        if (!listenersAdded) {
            irAtrasBtn.addActionListener(actionAssigner.getAction(ActionGUI.IR_A_ORGANIZADOR.getID()));
            iniciarTorneoBtn.addActionListener(e -> iniciarTorneo());

            // Cargar torneos disponibles
            DefaultComboBoxModel<Torneo> model = new DefaultComboBoxModel<>();
            for (Torneo torneo : PanelCrearTorneo.getTorneosCreados()) {
                model.addElement(torneo);
            }
            torneosComboBox.setModel(model);
            torneosComboBox.addActionListener(e -> actualizarInformacionTorneo());

            listenersAdded = true;
        }
        this.revalidate();
        this.repaint();
    }

    private void actualizarInformacionTorneo() {
        torneoSeleccionado = (Torneo) torneosComboBox.getSelectedItem();
        if (torneoSeleccionado != null) {
            torneoSeleccionado.registrarObserver(this);
            mostrarInformacionTorneo();
        }
    }

    private void mostrarInformacionTorneo() {
        StringBuilder info = new StringBuilder();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        // Información básica
        info.append("=== ").append(torneoSeleccionado.getNombre()).append(" ===\n");
        info.append("Disciplina: ").append(torneoSeleccionado.getDisciplina()).append("\n");
        info.append("Fecha: ").append(sdf.format(torneoSeleccionado.getFecha())).append("\n");
        info.append("Participantes: ").append(torneoSeleccionado.getParticipantes().size()).append("\n\n");

        // Mostrar todas las fases
        for (FaseTorneo fase : torneoSeleccionado.getFases()) {
            info.append("=== ").append(fase.getNombre()).append(" ===\n");
            for (TorneoComponent componente : fase.getComponentes()) {
                Partido partido = (Partido) componente;
                info.append(partido.getJugador1()).append(" vs ").append(partido.getJugador2());
                if (partido.getGanador() != null) {
                    info.append(" -> Ganador: ").append(partido.getGanador());
                }
                info.append("\n");
            }
            info.append("\n");
        }

        infoTorneoArea.setText(info.toString());
    }

    private void iniciarTorneo() {
        if (torneoSeleccionado == null) {
            showMessageOnce("Seleccione un torneo primero", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            torneoSeleccionado.iniciarTorneo();
            infoTorneoArea.setText(torneoSeleccionado.getEstructuraTexto());
            showMessageOnce("Torneo iniciado correctamente!\nSe han creado las fases iniciales.",
                    "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            showMessageOnce(ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void actualizar(String mensaje) {
        SwingUtilities.invokeLater(() -> {
            infoTorneoArea.setText(torneoSeleccionado.getEstructuraTexto());
            JOptionPane.showMessageDialog(this, mensaje);
        });
    }

    private void showMessageOnce(String message, String title, int messageType) {
        SwingUtilities.invokeLater(() -> {
            JOptionPane.showMessageDialog(this, message, title, messageType);
        });
    }
}