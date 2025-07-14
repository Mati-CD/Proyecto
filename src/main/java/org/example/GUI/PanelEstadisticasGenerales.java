package org.example.GUI;

import org.example.CodigoLogico.*;
import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PanelEstadisticasGenerales extends JPanel implements PanelConfigurable, TorneoObserver {
    private GestorTorneos gestorTorneos;
    private PanelButton irAtrasBtn;
    private JComboBox<Torneo> torneosComboBox;
    private JTextArea estadisticasArea;
    private boolean listenersAdded = false;

    public PanelEstadisticasGenerales() {
        super(new BorderLayout());
        setBackground(new Color(104, 120, 1));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        Font font = new Font("SansSerif", Font.BOLD, 16);
        Font titleFont = new Font("Arial", Font.BOLD, 24);

        // Panel superior
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setOpaque(false);

        // Botón de volver
        irAtrasBtn = new PanelButton("Volver atrás", font);
        JPanel topLeftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topLeftPanel.setOpaque(false);
        topLeftPanel.add(irAtrasBtn);
        topPanel.add(topLeftPanel, BorderLayout.WEST);

        // Título
        JLabel titleLabel = new JLabel("Estadísticas Generales", SwingConstants.CENTER);
        titleLabel.setFont(titleFont);
        topPanel.add(titleLabel, BorderLayout.CENTER);
        add(topPanel, BorderLayout.NORTH);

        // Panel central
        JPanel centerPanel = new JPanel(new BorderLayout(10, 10));
        centerPanel.setOpaque(false);
        centerPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));

        // Selección de torneo
        JPanel torneoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        torneoPanel.setOpaque(false);
        torneoPanel.add(new JLabel("Seleccione torneo:"));
        torneosComboBox = new JComboBox<>();
        torneosComboBox.setFont(font);
        torneoPanel.add(torneosComboBox);
        centerPanel.add(torneoPanel, BorderLayout.NORTH);

        // Área de texto para estadísticas
        estadisticasArea = new JTextArea();
        estadisticasArea.setEditable(false);
        estadisticasArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        estadisticasArea.setLineWrap(true);
        estadisticasArea.setWrapStyleWord(true);
        estadisticasArea.setBackground(new Color(240, 240, 240));
        centerPanel.add(new JScrollPane(estadisticasArea), BorderLayout.CENTER);

        add(centerPanel, BorderLayout.CENTER);
    }

    @Override
    public void inicializar(ActionAssigner actionAssigner, GestorTorneos gestorTorneos) {
        this.gestorTorneos = gestorTorneos;

        if (!listenersAdded) {
            irAtrasBtn.addActionListener(actionAssigner.getAction(ActionGUI.IR_A_USUARIO.getID()));

            // Cargar torneos disponibles
            DefaultComboBoxModel<Torneo> model = new DefaultComboBoxModel<>();
            for (Torneo torneo : gestorTorneos.getTorneosCreados()) {
                model.addElement(torneo);
                torneo.registrarObserver(this);
            }
            torneosComboBox.setModel(model);
            torneosComboBox.addActionListener(e -> actualizarEstadisticas());

            listenersAdded = true;
        }

        actualizarEstadisticas();
        this.revalidate();
        this.repaint();
    }

    private void actualizarEstadisticas() {
        Torneo torneo = (Torneo) torneosComboBox.getSelectedItem();
        StringBuilder sb = new StringBuilder();

        if (torneo != null) {
            // Estadísticas básicas del torneo
            sb.append("=== ESTADÍSTICAS GENERALES ===\n\n");
            sb.append("Torneo: ").append(torneo.getNombre()).append("\n");
            sb.append("Disciplina: ").append(torneo.getDisciplina()).append("\n");
            sb.append("Tipo de inscripción: ").append(torneo.getTipoDeInscripcion()).append("\n");
            sb.append("Total participantes: ").append(torneo.getParticipantes().size()).append("\n");

            // Estado del torneo
            if (torneo.tieneCampeon()) {
                sb.append("\nEstado: Finalizado\n");
                sb.append("Campeón: ").append(torneo.getCampeon()).append("\n");
            } else if (!torneo.getFases().isEmpty()) {
                sb.append("\nEstado: En progreso\n");
                sb.append("Fase actual: ").append(torneo.getFaseActual().getNombre()).append("\n");
            } else {
                sb.append("\nEstado: Sin iniciar\n");
            }

            // Estadísticas de partidos
            int partidosJugados = 0;
            int partidosPendientes = 0;
            Map<String, Integer> victoriasPorParticipante = new HashMap<>();

            for (FaseTorneo fase : torneo.getFases()) {
                for (TorneoComponent componente : fase.getComponentes()) {
                    Partido partido = (Partido) componente;
                    if (partido.tieneResultado()) {
                        partidosJugados++;
                        String ganador = partido.getGanador();
                        victoriasPorParticipante.put(ganador, victoriasPorParticipante.getOrDefault(ganador, 0) + 1);
                    } else {
                        partidosPendientes++;
                    }
                }
            }

            sb.append("\n=== PARTIDOS ===\n");
            sb.append("Jugados: ").append(partidosJugados).append("\n");
            sb.append("Pendientes: ").append(partidosPendientes).append("\n");
            sb.append("Total: ").append(partidosJugados + partidosPendientes).append("\n");

            // Estadísticas de participantes
            if (!victoriasPorParticipante.isEmpty()) {
                sb.append("\n=== PARTICIPANTES DESTACADOS ===\n");
                victoriasPorParticipante.entrySet().stream()
                        .sorted((e1, e2) -> e2.getValue().compareTo(e1.getValue()))
                        .limit(5)
                        .forEach(entry -> sb.append("- ").append(entry.getKey()).append(": ")
                                .append(entry.getValue()).append(" victoria(s)\n"));
            }

            // Porcentaje de avance del torneo
            if (partidosJugados + partidosPendientes > 0) {
                double porcentaje = (double) partidosJugados / (partidosJugados + partidosPendientes) * 100;
                sb.append("\nAvance del torneo: ").append(String.format("%.1f", porcentaje)).append("%\n");
            }
        } else {
            sb.append("No hay torneos disponibles para mostrar estadísticas");
        }

        estadisticasArea.setText(sb.toString());
    }

    @Override
    public void actualizar(String mensaje) {
        SwingUtilities.invokeLater(() -> {
            actualizarEstadisticas();
            if (mensaje.contains("ERROR") || mensaje.contains("Campeón")) {
                JOptionPane.showMessageDialog(this, mensaje);
            }
        });
    }
}