package org.example.GUI;

import org.example.CodigoLogico.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Panel que muestra estadísticas generales de un torneo seleccionado desde un comboBox.
 */
public class PanelEstadisticasGenerales extends JPanel implements PanelConfigurable, TorneoObserver {
    private GestorTorneos gestorTorneos;
    private PanelButton irAtrasBtn;
    private JComboBox<Torneo> torneosComboBox;
    private JTextArea estadisticasArea;
    private boolean listenersAdded = false;
    private BufferedImage backgroundImage;
    private BufferedImage grayImage;
    private DefaultComboBoxModel<Torneo> torneosModel;

    /**
     * Construye el panel de estadísticas generales con su interfaz gráfica.
     */
    public PanelEstadisticasGenerales() {
        super(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        try {
            backgroundImage = ImageIO.read(getClass().getResource("/images/image1.png"));
            if (backgroundImage == null) {
                System.err.println("No se encontró la imagen en /images/image1.png");
                setBackground(new Color(230, 230, 230));
            } else {
                grayImage = new BufferedImage(
                        backgroundImage.getWidth(),
                        backgroundImage.getHeight(),
                        BufferedImage.TYPE_INT_ARGB
                );
                for (int y = 0; y < backgroundImage.getHeight(); y++) {
                    for (int x = 0; x < backgroundImage.getWidth(); x++) {
                        int rgb = backgroundImage.getRGB(x, y);
                        int a = (rgb >> 24) & 0xff;
                        int r = (rgb >> 16) & 0xff;
                        int g = (rgb >> 8) & 0xff;
                        int b = rgb & 0xff;

                        int gray = (r + g + b) / 3;
                        int lightGray = Math.min(gray + 40, 255);

                        int grayRgb = (a << 24) | (lightGray << 16) | (lightGray << 8) | lightGray;
                        grayImage.setRGB(x, y, grayRgb);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            setBackground(new Color(230, 230, 230));
        }

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

        JLabel titleLabel = new JLabel("", SwingConstants.CENTER);
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
        torneosModel = new DefaultComboBoxModel<>();
        torneosComboBox.setModel(torneosModel);
        torneosComboBox.setRenderer(new GuiUtils.ComboBoxRenderer<>(Torneo::getNombre));
        torneosComboBox.setFont(font);
        torneoPanel.add(torneosComboBox);
        centerPanel.add(torneoPanel, BorderLayout.NORTH);

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
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (grayImage != null) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.drawImage(grayImage, 0, 0, getWidth(), getHeight(), this);
        } else {
            g.setColor(new Color(230, 230, 230));
            g.fillRect(0, 0, getWidth(), getHeight());
        }
    }

    /**
     * Inicializa el panel cargando los torneos y asignando acciones a los botones.
     *
     * @param actionAssigner objeto que permite obtener acciones asociadas a eventos
     * @param gestorTorneos gestor que contiene la lógica de los torneos
     */
    @Override
    public void inicializar(ActionAssigner actionAssigner, GestorTorneos gestorTorneos) {
        this.gestorTorneos = gestorTorneos;

        if (!listenersAdded) {
            irAtrasBtn.addActionListener(actionAssigner.getAction(ActionGUI.IR_A_USUARIO.getID()));
            torneosComboBox.addActionListener(e -> actualizarEstadisticas());
            listenersAdded = true;
        }

        actualizarListaTorneos();
        actualizarEstadisticas();
        this.revalidate();
        this.repaint();
    }

    /**
     * Actualiza la lista de torneos disponibles en el ComboBox.
     * Mantiene la selección actual si el torneo sigue disponible.
     */
    private void actualizarListaTorneos() {
        Torneo seleccionado = (Torneo) torneosComboBox.getSelectedItem();
        torneosModel.removeAllElements();

        List<Torneo> torneos = gestorTorneos.getTorneosCreados();
        for (Torneo torneo : torneos) {
            torneosModel.addElement(torneo);
            torneo.registrarObserver(this);
        }

        // Restaurar la selección anterior si todavía existe
        if (seleccionado != null && torneos.contains(seleccionado)) {
            torneosComboBox.setSelectedItem(seleccionado);
        } else if (torneosModel.getSize() > 0) {
            torneosComboBox.setSelectedIndex(0);
        }
    }

    /**
     * Actualiza el área de estadísticas con la información del torneo seleccionado.
     */
    private void actualizarEstadisticas() {
        Torneo torneo = (Torneo) torneosComboBox.getSelectedItem();
        StringBuilder sb = new StringBuilder();

        if (torneo != null) {
            sb.append("=== ESTADÍSTICAS GENERALES ===\n\n");
            sb.append("Torneo: ").append(torneo.getNombre()).append("\n");
            sb.append("Disciplina: ").append(torneo.getDisciplina()).append("\n");
            sb.append("Tipo de inscripción: ").append(torneo.getTipoDeInscripcion()).append("\n");
            sb.append("Total participantes: ").append(torneo.getParticipantes().size()).append("\n");

            if (torneo.tieneCampeon()) {
                sb.append("\nEstado: Finalizado\n");
                sb.append("Campeón: ").append(torneo.getCampeon()).append("\n");
            } else if (!torneo.getFases().isEmpty()) {
                sb.append("\nEstado: En progreso\n");
                sb.append("Fase actual: ").append(torneo.getFaseActual().getNombre()).append("\n");
            } else {
                sb.append("\nEstado: Sin iniciar\n");
            }

            int partidosJugados = 0;
            int partidosPendientes = 0;
            Map<String, Integer> victoriasPorParticipante = new HashMap<>();

            for (FaseTorneo fase : torneo.getFases()) {
                for (TorneoComponent componente : fase.getComponentes()) {
                    if (componente instanceof Partido) {
                        Partido partido = (Partido) componente;
                        if (partido.tieneResultado()) {
                            partidosJugados++;
                            String ganador = partido.getGanador();
                            if (ganador != null) {
                                victoriasPorParticipante.put(ganador, victoriasPorParticipante.getOrDefault(ganador, 0) + 1);
                            }
                        } else {
                            partidosPendientes++;
                        }
                    }
                }
            }

            sb.append("\n=== PARTIDOS ===\n");
            sb.append("Jugados: ").append(partidosJugados).append("\n");
            sb.append("Pendientes: ").append(partidosPendientes).append("\n");
            sb.append("Total: ").append(partidosJugados + partidosPendientes).append("\n");

            if (!victoriasPorParticipante.isEmpty()) {
                sb.append("\n=== PARTICIPANTES DESTACADOS ===\n");
                victoriasPorParticipante.entrySet().stream()
                        .sorted((e1, e2) -> e2.getValue().compareTo(e1.getValue()))
                        .limit(5)
                        .forEach(entry -> sb.append("- ").append(entry.getKey()).append(": ")
                                .append(entry.getValue()).append(" victoria(s)\n"));
            }

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
            actualizarListaTorneos();
            actualizarEstadisticas();
            if (mensaje != null && (mensaje.contains("ERROR") || mensaje.contains("Campeón"))) {
                JOptionPane.showMessageDialog(this, mensaje);
            }
        });
    }
}