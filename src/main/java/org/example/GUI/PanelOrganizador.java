package org.example.GUI;

import org.example.CodigoLogico.GestorTorneos;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Panel que representa la vista principal del organizador,
 * incluyendo botones para las acciones clave como crear torneos,
 * inscribir participantes, iniciar torneos y eliminar torneos.
 */
public class PanelOrganizador extends JPanel implements PanelConfigurable {
    private OrganizadorButtons buttonsGroup;
    private PanelButton irAInicioBtn;
    private PanelButton eliminarTorneoBtn;
    private BufferedImage backgroundImage;

    private final Font componentFont1 = new Font("SansSerif", Font.BOLD, 12);

    /**
     * Constructor que inicializa y configura los componentes del panel.
     */
    public PanelOrganizador() {
        super(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        try {
            backgroundImage = ImageIO.read(getClass().getResource("/images/image1.png"));
            if (backgroundImage == null) {
                System.err.println("La imagen no se encontró en la ruta: /images/image1.png");
                setBackground(new Color(195, 0, 0));
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error al cargar la imagen de fondo: " + e.getMessage());
            setBackground(new Color(195, 0, 0));
        }

        irAInicioBtn = new PanelButton("Volver al Inicio", componentFont1);
        irAInicioBtn.setButtonPreferredSize(new Dimension(120, 30));
        irAInicioBtn.setButtonColor(new Color(50, 50, 50), Color.WHITE, Color.GRAY, 1);
        eliminarTorneoBtn = new PanelButton("Eliminar Torneo", componentFont1);
        eliminarTorneoBtn.setButtonPreferredSize(new Dimension(130, 30));
        eliminarTorneoBtn.setButtonColor(new Color(225, 4, 4),
                Color.WHITE,
                new Color(255, 100, 100),
                2
        );

        JPanel topPanel = GuiUtils.crearPanelDeEncabezado(irAInicioBtn,
                "",
                componentFont1,
                eliminarTorneoBtn
        );
        topPanel.setOpaque(false);
        add(topPanel, BorderLayout.NORTH);

        buttonsGroup = new OrganizadorButtons();
        buttonsGroup.setOpaque(false);
        JPanel centerLeftPanel = new JPanel(new BorderLayout());
        centerLeftPanel.setOpaque(false);
        centerLeftPanel.add(buttonsGroup, BorderLayout.WEST);
        add(centerLeftPanel, BorderLayout.CENTER);
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
     * Inicializa el panel, asignando acciones a los botones según el controlador recibido.
     *
     * @param actionAssigner el asignador de acciones que contiene los mapeos a ejecutar
     * @param gestorTorneos  el gestor central de torneos utilizado por la aplicación
     */
    @Override
    public void inicializar(ActionAssigner actionAssigner, GestorTorneos gestorTorneos) {
        irAInicioBtn.addActionListener(actionAssigner.getAction(ActionGUI.IR_A_INICIO.getID()));
        buttonsGroup.getCrearTorneoBtn().addActionListener(actionAssigner.getAction(ActionGUI.IR_A_CREAR_TORNEO.getID()));
        buttonsGroup.getInscribirParticipantesBtn().addActionListener(actionAssigner.getAction(ActionGUI.IR_A_INSCRIBIR_PARTICIPANTES.getID()));
        buttonsGroup.getIniciarTorneoBtn().addActionListener(actionAssigner.getAction(ActionGUI.IR_A_INICIAR_TORNEO.getID()));
        buttonsGroup.getActualizarRegistroDeResultadosBtn().addActionListener(actionAssigner.getAction(ActionGUI.IR_A_REGISTRAR_RESULTADOS.getID()));
        eliminarTorneoBtn.addActionListener(actionAssigner.getAction(ActionGUI.IR_A_ELIMINAR_TORNEO.getID()));

        this.revalidate();
        this.repaint();
    }
}