package org.example.GUI;

import org.example.CodigoLogico.*;
import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * Panel que muestra el estado actual de un torneo, incluyendo sus fases, partidos y próximos encuentros.
 */
public class PanelEstadoActualTorneo extends JPanel implements PanelConfigurable, TorneoObserver {
    private GestorTorneos gestorTorneos;
    private PanelButton irAtrasBtn;
    private JComboBox<Torneo> torneosComboBox;
    private JTextArea estadoTorneoArea;
    private JList<String> encuentrosList;
    private DefaultListModel<String> encuentrosModel;
    private boolean listenersAdded = false;

    /**
     * Crea el panel con el diseño gráfico correspondiente al estado actual del torneo y próximos encuentros.
     */
    public PanelEstadoActualTorneo() {
        super(new BorderLayout());
        setBackground(new Color(0, 32, 142));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        Font font = new Font("SansSerif", Font.BOLD, 16);
        Font titleFont = new Font("Arial", Font.BOLD, 24);

        // Panel superior con botón de volver y título
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

        // Panel central con selector de torneo
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

        // Panel dividido para estado y próximos encuentros
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        splitPane.setResizeWeight(0.6); // 60% para el estado, 40% para próximos encuentros
        splitPane.setDividerLocation(400);
        splitPane.setOpaque(false);

        // Área de texto para el estado del torneo
        estadoTorneoArea = new JTextArea();
        estadoTorneoArea.setEditable(false);
        estadoTorneoArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        estadoTorneoArea.setLineWrap(true);
        estadoTorneoArea.setWrapStyleWord(true);
        estadoTorneoArea.setBackground(new Color(240, 240, 240));
        JScrollPane estadoScroll = new JScrollPane(estadoTorneoArea);
        estadoScroll.setBorder(BorderFactory.createTitledBorder("Estado del Torneo"));

        // Lista para próximos encuentros
        encuentrosModel = new DefaultListModel<>();
        encuentrosList = new JList<>(encuentrosModel);
        encuentrosList.setFont(font);
        encuentrosList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane encuentrosScroll = new JScrollPane(encuentrosList);
        encuentrosScroll.setBorder(BorderFactory.createTitledBorder("Próximos Encuentros"));

        splitPane.setTopComponent(estadoScroll);
        splitPane.setBottomComponent(encuentrosScroll);
        centerPanel.add(splitPane, BorderLayout.CENTER);

        add(centerPanel, BorderLayout.CENTER);
    }

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
            torneosComboBox.addActionListener(e -> {
                actualizarEstadoTorneo();
                actualizarProximosEncuentros();
            });

            listenersAdded = true;
        }

        actualizarEstadoTorneo();
        actualizarProximosEncuentros();
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
     * Actualiza la lista de próximos encuentros según el torneo seleccionado.
     */
    private void actualizarProximosEncuentros() {
        Torneo torneo = (Torneo) torneosComboBox.getSelectedItem();
        encuentrosModel.clear();

        if (torneo != null && !torneo.getFases().isEmpty()) {
            FaseTorneo faseActual = torneo.getFaseActual();

            if (faseActual != null) {
                for (TorneoComponent componente : faseActual.getComponentes()) {
                    Partido partido = (Partido) componente;
                    if (!partido.tieneResultado()) {
                        encuentrosModel.addElement(partido.getJugador1() + " vs " + partido.getJugador2());
                    }
                }
            }

            if (encuentrosModel.isEmpty()) {
                encuentrosModel.addElement("No hay próximos encuentros pendientes");
                if (torneo.tieneCampeon()) {
                    encuentrosModel.addElement("El torneo ha finalizado");
                } else {
                    encuentrosModel.addElement("Esperando siguiente fase...");
                }
            }
        } else {
            encuentrosModel.addElement("Seleccione un torneo iniciado");
        }
    }

    @Override
    public void actualizar(String mensaje) {
        SwingUtilities.invokeLater(() -> {
            actualizarEstadoTorneo();
            actualizarProximosEncuentros();
            if (mensaje.contains("ERROR") || mensaje.contains("Campeón")) {
                JOptionPane.showMessageDialog(this, mensaje);
            }
        });
    }
}