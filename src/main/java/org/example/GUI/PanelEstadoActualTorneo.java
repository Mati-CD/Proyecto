package org.example.GUI;

import org.example.CodigoLogico.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

public class PanelEstadoActualTorneo extends JPanel implements PanelConfigurable, TorneoObserver {
    private GestorTorneos gestorTorneos;
    private PanelButton irAtrasBtn;
    private JComboBox<Torneo> torneosComboBox;
    private JTextArea estadoArea;
    private boolean listenersAdded = false;
    private BufferedImage backgroundImage;
    private BufferedImage grayImage;
    private DefaultComboBoxModel<Torneo> torneosModel;

    public PanelEstadoActualTorneo() {
        super(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Cargar imagen de fondo y crear versión gris aclarada
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

    @Override
    public void inicializar(ActionAssigner actionAssigner, GestorTorneos gestorTorneos) {
        this.gestorTorneos = gestorTorneos;
        gestorTorneos.registrarObserver(this);

        if (!listenersAdded) {
            irAtrasBtn.addActionListener(actionAssigner.getAction(ActionGUI.IR_A_USUARIO.getID()));
            torneosComboBox.addActionListener(e -> mostrarEstadoTorneo());
            listenersAdded = true;
        }

        actualizarListaTorneos();
        mostrarEstadoTorneo();
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

        if (seleccionado != null && torneos.contains(seleccionado)) {
            torneosComboBox.setSelectedItem(seleccionado);
        } else if (torneosModel.getSize() > 0) {
            torneosComboBox.setSelectedIndex(0);
        }
    }

    /**
     * Muestra el estado actual del torneo seleccionado con información detallada.
     */
    private void mostrarEstadoTorneo() {
        Torneo torneo = (Torneo) torneosComboBox.getSelectedItem();
        StringBuilder sb = new StringBuilder();

        if (torneo != null) {
            sb.append("=== ESTADO DEL TORNEO ===\n\n");
            sb.append("Nombre: ").append(torneo.getNombre()).append("\n");
            sb.append("Disciplina: ").append(torneo.getDisciplina()).append("\n");
            sb.append("Participantes: ").append(torneo.getParticipantes().size())
                    .append("/").append(torneo.getNumParticipantes()).append("\n");

            if (torneo.tieneCampeon()) {
                sb.append("\n¡TORNEO FINALIZADO!\n");
                sb.append("Campeón: ").append(torneo.getCampeon()).append("\n");
            } else if (!torneo.getFases().isEmpty()) {
                sb.append("\n=== FASES DEL TORNEO ===\n");
                for (FaseTorneo fase : torneo.getFases()) {
                    sb.append("\n").append(fase.getNombre()).append(":\n");
                    for (TorneoComponent componente : fase.getComponentes()) {
                        if (componente instanceof Partido) {
                            Partido partido = (Partido) componente;
                            sb.append("- ").append(partido.getJugador1())
                                    .append(" vs ").append(partido.getJugador2())
                                    .append(": ");

                            if (partido.tieneResultado()) {
                                sb.append("Ganador: ").append(partido.getGanador());
                            } else {
                                sb.append("Pendiente");
                            }
                            sb.append("\n");
                        }
                    }
                }
            } else {
                sb.append("\nEl torneo no ha comenzado.\n");
                sb.append("Faltan ").append(torneo.getNumParticipantes() - torneo.getParticipantes().size())
                        .append(" participantes para iniciar.\n");
            }
        } else {
            sb.append("No hay torneos disponibles.\n");
        }

        estadoArea.setText(sb.toString());
        estadoArea.setCaretPosition(0);
    }

    @Override
    public void actualizar(String mensaje) {
        SwingUtilities.invokeLater(() -> {
            actualizarListaTorneos();
            mostrarEstadoTorneo();

            if (mensaje != null && (mensaje.contains("Campeón") || mensaje.contains("finalizado"))) {
                JOptionPane.showMessageDialog(this, mensaje, "Actualización de Torneo", JOptionPane.INFORMATION_MESSAGE);
            }
        });
    }
}