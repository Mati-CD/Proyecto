package org.example.GUI;

import org.example.CodigoLogico.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

/**
 * Panel que permite visualizar los participantes inscritos en un torneo seleccionado.
 * Se actualiza automáticamente cuando hay cambios en el torneo (implementa TorneoObserver).
 */
public class PanelVerParticipantes extends JPanel implements PanelConfigurable, TorneoObserver {
    private GestorTorneos gestorTorneos;
    private PanelButton irAtrasBtn;
    private JComboBox<Torneo> torneosComboBox;
    private JTextArea participantesArea;
    private boolean listenersAdded = false;
    private BufferedImage backgroundImage;
    private DefaultComboBoxModel<Torneo> torneosModel;

    /**
     * Constructor que arma la interfaz de selección de torneo y vista de participantes.
     */
    public PanelVerParticipantes() {
        super(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        try {
            backgroundImage = ImageIO.read(getClass().getResource("/images/image1.png"));
            if (backgroundImage == null) {
                System.err.println("No se encontró la imagen en /images/image1.png");
                setBackground(new Color(230, 230, 230));
            }
        } catch (IOException e) {
            e.printStackTrace();
            setBackground(new Color(230, 230, 230));
        }

        Font font = new Font("SansSerif", Font.BOLD, 16);
        Font titleFont = new Font("Arial", Font.BOLD, 24);

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setOpaque(false);

        // Botón "Volver atrás" con fondo negro/gris oscuro y texto blanco
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

        participantesArea = new JTextArea();
        participantesArea.setEditable(false);
        participantesArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        participantesArea.setLineWrap(true);
        participantesArea.setWrapStyleWord(true);
        participantesArea.setBackground(new Color(240, 240, 240));
        centerPanel.add(new JScrollPane(participantesArea), BorderLayout.CENTER);

        add(centerPanel, BorderLayout.CENTER);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            BufferedImage grayImage = new BufferedImage(
                    backgroundImage.getWidth(),
                    backgroundImage.getHeight(),
                    BufferedImage.TYPE_INT_ARGB
            );

            for (int y = 0; y < backgroundImage.getHeight(); y++) {
                for (int x = 0; x < backgroundImage.getWidth(); x++) {
                    int rgb = backgroundImage.getRGB(x, y);
                    int a = (rgb >> 24) & 0xff;
                    int r = (rgb >> 16) & 0xff;
                    int g1 = (rgb >> 8) & 0xff;
                    int b = rgb & 0xff;

                    int gray = (r + g1 + b) / 3;
                    int lightGray = Math.min(gray + 40, 255);

                    int grayRgb = (a << 24) | (lightGray << 16) | (lightGray << 8) | lightGray;
                    grayImage.setRGB(x, y, grayRgb);
                }
            }

            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.drawImage(grayImage, 0, 0, getWidth(), getHeight(), this);
        }
    }

    @Override
    public void inicializar(ActionAssigner actionAssigner, GestorTorneos gestorTorneos) {
        this.gestorTorneos = gestorTorneos;

        if (!listenersAdded) {
            irAtrasBtn.addActionListener(actionAssigner.getAction(ActionGUI.IR_A_USUARIO.getID()));
            torneosComboBox.addActionListener(e -> actualizarParticipantes());
            listenersAdded = true;
        }

        actualizarListaTorneos();
        actualizarParticipantes();
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
     * Actualiza el área de participantes con la información del torneo seleccionado.
     */
    private void actualizarParticipantes() {
        Torneo torneo = (Torneo) torneosComboBox.getSelectedItem();
        StringBuilder sb = new StringBuilder();

        if (torneo != null) {
            sb.append("=== PARTICIPANTES DEL TORNEO ===\n\n");
            sb.append("Torneo: ").append(torneo.getNombre()).append("\n");
            sb.append("Total participantes: ").append(torneo.getParticipantes().size()).append("\n\n");

            if (torneo.getParticipantes().isEmpty()) {
                sb.append("No hay participantes inscritos aún.\n");
            } else {
                for (Participante participante : torneo.getParticipantes()) {
                    sb.append(participante.getDatos()).append("\n\n");
                }
            }
        } else {
            sb.append("No hay torneos disponibles o seleccionados");
        }

        participantesArea.setText(sb.toString());
    }

    @Override
    public void actualizar(String mensaje) {
        SwingUtilities.invokeLater(() -> {
            actualizarListaTorneos();
            actualizarParticipantes();
        });
    }
}