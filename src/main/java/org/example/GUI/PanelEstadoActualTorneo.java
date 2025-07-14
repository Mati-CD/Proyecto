package org.example.GUI;

import org.example.CodigoLogico.*;
import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * Panel que muestra el estado actual de un torneo, incluyendo sus fases y partidos.
 */
public class PanelEstadoActualTorneo extends JPanel implements PanelConfigurable, TorneoObserver {
    private GestorTorneos gestorTorneos;
    private PanelButton irAtrasBtn;
    private JComboBox<Torneo> torneosComboBox;
    private JTextArea estadoTorneoArea;
    private boolean listenersAdded = false;

    /**
     * Crea el panel con el diseño gráfico correspondiente al estado actual del torneo.
     */
    public PanelEstadoActualTorneo() {
        super(new BorderLayout());
        setBackground(new Color(0, 32, 142));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        Font font = new Font("SansSerif", Font.BOLD, 16);
        Font titleFont = new Font("Arial", Font.BOLD, 24);

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setOpaque(false);

        irAtrasBtn = new PanelButton("Volver atrás", font);
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
        torneoPanel.add(new JLabel("Seleccione torneo:"));
        torneosComboBox = new JComboBox<>();
        torneosComboBox.setFont(font);
        torneoPanel.add(torneosComboBox);
        centerPanel.add(torneoPanel, BorderLayout.NORTH);

        estadoTorneoArea = new JTextArea();
        estadoTorneoArea.setEditable(false);
        estadoTorneoArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        estadoTorneoArea.setLineWrap(true);
        estadoTorneoArea.setWrapStyleWord(true);
        estadoTorneoArea.setBackground(new Color(240, 240, 240));
        centerPanel.add(new JScrollPane(estadoTorneoArea), BorderLayout.CENTER);

        add(centerPanel, BorderLayout.CENTER);
    }

    /**
     * Inicializa el panel con el gestor de torneos y asigna las acciones necesarias.
     *
     * @param actionAssigner objeto que asigna las acciones de interfaz gráfica
     * @param gestorTorneos objeto que contiene la lógica del torneo
     */
    @Override
    public void inicializar(ActionAssigner actionAssigner, GestorTorneos gestorTorneos) {
        this.gestorTorneos = gestorTorneos;

        if (!listenersAdded) {
            irAtrasBtn.addActionListener(actionAssigner.getAction(ActionGUI.IR_A_USUARIO.getID()));

            DefaultComboBoxModel<Torneo> model = new DefaultComboBoxModel<>();
            for (Torneo torneo : gestorTorneos.getTorneosCreados()) {
                model.addElement(torneo);
                torneo.registrarObserver(this);
            }
            torneosComboBox.setModel(model);
            torneosComboBox.addActionListener(e -> actualizarEstadoTorneo());

            listenersAdded = true;
        }

        actualizarEstadoTorneo();
        this.revalidate();
        this.repaint();
    }

    /**
     * Actualiza el estado mostrado del torneo seleccionado, incluyendo su fase y resultados.
     */
    private void actualizarEstadoTorneo() {
        Torneo torneo = (Torneo) torneosComboBox.getSelectedItem();
        if (torneo != null) {
            StringBuilder sb = new StringBuilder();

            sb.append("=== ").append(torneo.getNombre()).append(" ===\n");
            sb.append("Disciplina: ").append(torneo.getDisciplina()).append("\n");
            sb.append("Tipo de inscripción: ").append(torneo.getTipoDeInscripcion()).append("\n");
            sb.append("Total participantes: ").append(torneo.getParticipantes().size()).append("\n");

            if (torneo.tieneCampeon()) {
                sb.append("\n¡¡¡ CAMPEÓN: ").append(torneo.getCampeon()).append(" !!!\n");
            } else if (!torneo.getFases().isEmpty()) {
                sb.append("\nEstado actual: En progreso\n");
            } else {
                sb.append("\nEstado actual: Sin iniciar\n");
            }

            for (FaseTorneo fase : torneo.getFases()) {
                sb.append("\n=== ").append(fase.getNombre().toUpperCase()).append(" ===\n");
                for (TorneoComponent componente : fase.getComponentes()) {
                    Partido partido = (Partido) componente;
                    sb.append(partido.getJugador1()).append(" vs ").append(partido.getJugador2());
                    if (partido.getGanador() != null) {
                        sb.append(" -> Ganador: ").append(partido.getGanador());
                    }
                    sb.append("\n");
                }
            }

            estadoTorneoArea.setText(sb.toString());
        } else {
            estadoTorneoArea.setText("No hay torneos disponibles");
        }
    }

    /**
     * Actualiza el contenido del panel cuando se recibe un mensaje del observable.
     *
     * @param mensaje mensaje enviado por el observable
     */
    @Override
    public void actualizar(String mensaje) {
        SwingUtilities.invokeLater(() -> {
            actualizarEstadoTorneo();
            if (mensaje.contains("ERROR") || mensaje.contains("Campeón")) {
                JOptionPane.showMessageDialog(this, mensaje);
            }
        });
    }
}