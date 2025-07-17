package org.example.GUI;

import org.example.CodigoLogico.GestorTorneos;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Panel de inicio que permite al usuario elegir entre el rol de Organizador o Usuario.
 * Contiene dos botones principales que redirigen a los respectivos paneles.
 */
public class PanelInicio extends JPanel implements PanelConfigurable {
    private PanelButton organizadorBtn;
    private PanelButton usuarioBtn;
    private BufferedImage backgroundImage;

    /**
     * Constructor que inicializa y organiza los botones del panel.
     */
    public PanelInicio() {
        setLayout(new BorderLayout());

        try {
            backgroundImage = ImageIO.read(getClass().getResource("/images/image3.png"));
            if (backgroundImage == null) {
                System.err.println("La imagen no se encontró en la ruta: /images/image3.png");
                setBackground(new Color(195, 0, 0));
            }

        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error al cargar la imagen de fondo: " + e.getMessage());
            setBackground(new Color(195, 0, 0));
        }

        Font font = new Font("SansSerif", Font.BOLD, 18);

        organizadorBtn = new PanelButton("Organizador", font);
        organizadorBtn.setButtonColor(
                new Color(70, 130, 180),
                Color.WHITE,
                new Color(100, 149, 237),
                20
        );

        usuarioBtn = new PanelButton("Usuario", font);
        usuarioBtn.setButtonColor(
                new Color(70, 130, 180),
                Color.WHITE,
                new Color(100, 149, 237),
                20
        );

        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setOpaque(false);

        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 100, 0));
        buttonPanel.setOpaque(false);
        buttonPanel.add(organizadorBtn);
        buttonPanel.add(usuarioBtn);
        centerPanel.add(buttonPanel);

        add(centerPanel, BorderLayout.CENTER);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            Graphics2D g2d = (Graphics2D) g;

            g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }


    /**
     * Inicializa el panel asignando las acciones correspondientes a los botones.
     *
     * @param actionAssigner objeto que proporciona las acciones a ejecutar al presionar cada botón
     * @param gestorTorneos instancia del gestor de torneos (no se utiliza directamente en este panel)
     */
    @Override
    public void inicializar(ActionAssigner actionAssigner, GestorTorneos gestorTorneos) {
        organizadorBtn.addActionListener(actionAssigner.getAction(ActionGUI.IR_A_ORGANIZADOR.getID()));
        usuarioBtn.addActionListener(actionAssigner.getAction(ActionGUI.IR_A_USUARIO.getID()));

        this.revalidate();
        this.repaint();
    }
}
