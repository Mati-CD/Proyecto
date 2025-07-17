package org.example.GUI;

import org.example.CodigoLogico.GestorTorneos;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Panel principal que visualiza las opciones disponibles para el usuario.
 * Ofrece botones para acceder a distintos paneles informativos del torneo.
 */
public class PanelUsuario extends JPanel implements PanelConfigurable {
    private UsuarioButtons buttonsGroup;
    private PanelButton irAInicioBtn;
    private BufferedImage backgroundImage;

    /**
     * Constructor que arma la interfaz del panel de usuario.
     */
    public PanelUsuario() {
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

        Font font = new Font("SansSerif", Font.BOLD, 12);

        irAInicioBtn = new PanelButton("Volver al Inicio", font);
        irAInicioBtn.setButtonPreferredSize(new Dimension(130, 30));
        irAInicioBtn.setButtonColor(
                new Color(40, 40, 40),
                Color.WHITE,
                new Color(70, 70, 70),
                0
        );


        JPanel topLeftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topLeftPanel.setOpaque(false);
        topLeftPanel.add(irAInicioBtn);
        add(topLeftPanel, BorderLayout.NORTH);

        buttonsGroup = new UsuarioButtons();
        buttonsGroup.setOpaque(false);
        JPanel centerLeftPanel = new JPanel(new BorderLayout());
        centerLeftPanel.setOpaque(false);
        centerLeftPanel.add(buttonsGroup, BorderLayout.WEST);
        add(centerLeftPanel, BorderLayout.CENTER);
    }

    /**
     * Dibuja el fondo con la imagen si está disponible.
     */
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
                    // Aclarar el gris (máx 255)
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



    /**
     * Inicializa el panel y asigna las acciones a cada botón.
     */
    @Override
    public void inicializar(ActionAssigner actionAssigner, GestorTorneos gestorTorneos) {
        irAInicioBtn.addActionListener(actionAssigner.getAction(ActionGUI.IR_A_INICIO.getID()));
        buttonsGroup.getVerEstadoActualTorneoBtn().addActionListener(actionAssigner.getAction(ActionGUI.IR_A_ESTADO_ACTUAL_TORNEO.getID()));
        buttonsGroup.getVerEstadisticasBtn().addActionListener(actionAssigner.getAction(ActionGUI.IR_A_ESTADISTICAS_GENERALES.getID()));
        buttonsGroup.getVerParticipantesBtn().addActionListener(actionAssigner.getAction(ActionGUI.IR_A_VER_PARTICIPANTES.getID()));

        this.revalidate();
        this.repaint();
    }
}
