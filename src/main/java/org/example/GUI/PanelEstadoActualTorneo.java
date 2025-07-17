package org.example.GUI;

import org.example.CodigoLogico.*;
import javax.swing.*;
import java.awt.*;

public class PanelEstadoActualTorneo extends JPanel implements PanelConfigurable, TorneoObserver {
    private GestorTorneos gestorTorneos;
    private PanelButton irAtrasBtn;
    private JComboBox<Torneo> torneosComboBox;
    private JTextArea estadoArea;
    private boolean listenersAdded = false;

    public PanelEstadoActualTorneo() {
        super(new BorderLayout());
        setBackground(new Color(200, 180, 220));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        Font font = new Font("SansSerif", Font.BOLD, 16);
        Font titleFont = new Font("Arial", Font.BOLD, 24);

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setOpaque(false);

        Font botonFont = new Font("Arial", Font.BOLD, 12);
        irAtrasBtn = new PanelButton("Volver atrás", botonFont);
        irAtrasBtn.setButtonColor(
                new Color(220, 220, 220),
                Color.BLACK,
                new Color(200, 200, 200),
                0
        );

        JPanel topLeftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topLeftPanel.setOpaque(false);
        topLeftPanel.add(irAtrasBtn);
        topPanel.add(topLeftPanel, BorderLayout.WEST);

        JLabel titleLabel = new JLabel("Estado Actual del Torneo", SwingConstants.CENTER);
        titleLabel.setFont(titleFont);
        topPanel.add(titleLabel, BorderLayout.CENTER);

        add(topPanel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new BorderLayout(10, 10));
        centerPanel.setOpaque(false);
        centerPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));

        JPanel torneoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        torneoPanel.setOpaque(false);
        JLabel torneoLabel = new JLabel("Seleccione torneo:");
        torneoLabel.setForeground(Color.WHITE);
        torneoPanel.add(torneoLabel);

        torneosComboBox = new JComboBox<>();
        torneosComboBox.setFont(font);
        torneoPanel.add(torneosComboBox);
        centerPanel.add(torneoPanel, BorderLayout.NORTH);

        estadoArea = new JTextArea();
        estadoArea.setEditable(false);
        estadoArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        estadoArea.setLineWrap(true);
        estadoArea.setWrapStyleWord(true);
        estadoArea.setBackground(new Color(240, 240, 240));
        centerPanel.add(new JScrollPane(estadoArea), BorderLayout.CENTER);

        add(centerPanel, BorderLayout.CENTER);
    }

    @Override
    public void inicializar(ActionAssigner actionAssigner, GestorTorneos gestorTorneos) {
        this.gestorTorneos = gestorTorneos;
        gestorTorneos.registrarObserver(this); // Registrar este panel como observer del gestor

        if (!listenersAdded) {
            irAtrasBtn.addActionListener(actionAssigner.getAction(ActionGUI.IR_A_USUARIO.getID()));
            actualizarListaTorneos();
            torneosComboBox.addActionListener(e -> mostrarEstadoTorneo());
            listenersAdded = true;
        }

        mostrarEstadoTorneo();
    }

    private void actualizarListaTorneos() {
        SwingUtilities.invokeLater(() -> {
            Torneo seleccionado = (Torneo) torneosComboBox.getSelectedItem();
            DefaultComboBoxModel<Torneo> model = new DefaultComboBoxModel<>();

            for (Torneo torneo : gestorTorneos.getTorneosCreados()) {
                model.addElement(torneo);
                torneo.registrarObserver(this); // Registrar como observer de cada torneo
            }

            torneosComboBox.setModel(model);

            // Restaurar selección si el torneo sigue existiendo
            if (seleccionado != null) {
                for (int i = 0; i < model.getSize(); i++) {
                    if (model.getElementAt(i).getNombre().equals(seleccionado.getNombre())) {
                        torneosComboBox.setSelectedIndex(i);
                        break;
                    }
                }
            }
        });
    }

    private void mostrarEstadoTorneo() {
        Torneo torneo = (Torneo) torneosComboBox.getSelectedItem();
        StringBuilder sb = new StringBuilder();

        if (torneo != null) {
            sb.append("=== ESTADO DEL TORNEO ===\n\n");
            sb.append("Nombre: ").append(torneo.getNombre()).append("\n");
            sb.append("Disciplina: ").append(torneo.getDisciplina()).append("\n");
            sb.append("Participantes: ").append(torneo.getParticipantes().size())
                    .append("/").append(torneo.getNumParticipantes()).append("\n");

            if (!torneo.getFases().isEmpty()) {
                sb.append("\n=== FASES ===\n");
                for (FaseTorneo fase : torneo.getFases()) {
                    sb.append("\n").append(fase.getNombre()).append(":\n");
                    for (TorneoComponent componente : fase.getComponentes()) {
                        Partido partido = (Partido) componente;
                        sb.append("- ").append(partido.getJugador1())
                                .append(" vs ").append(partido.getJugador2())
                                .append(": ").append(partido.getGanador() != null ?
                                        "Ganador: " + partido.getGanador() : "En juego")
                                .append("\n");
                    }
                }

                if (torneo.tieneCampeon()) {
                    sb.append("\n¡CAMPEÓN: ").append(torneo.getCampeon()).append("!\n");
                }
            } else {
                sb.append("\nEl torneo no ha comenzado.\n");
            }
        } else {
            sb.append("No hay torneos disponibles.\n");
        }

        estadoArea.setText(sb.toString());
        estadoArea.setCaretPosition(0); // Mover scroll al inicio
    }

    @Override
    public void actualizar(String mensaje) {
        // Reaccionar a cambios relevantes
        if (mensaje.contains("Torneo creado") || mensaje.contains("Torneo eliminado") ||
                mensaje.contains("iniciado") || mensaje.contains("finalizado") ||
                mensaje.contains("Campeón declarado")) {
            actualizarListaTorneos();
        }
        mostrarEstadoTorneo();
    }
}