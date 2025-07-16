package org.example.GUI;

import org.example.CodigoLogico.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;
import java.io.IOException;

/**
 * Panel de interfaz gráfica encargado de la creación de un nuevo torneo.
 * Permite ingresar datos como nombre, disciplina, tipo de inscripción y número de participantes,
 * y luego enviarlos al gestor de torneos para registrar el torneo.
 */
public class PanelCrearTorneo extends JPanel implements PanelConfigurable, TorneoObserver {
    private GestorTorneos gestorTorneos;
    private PanelButton irAtrasBtn;
    private PanelButton crearTorneoBtn;
    private PanelFormularioTorneo panelFormularioTorneo;
    private boolean listenersActivos = false;
    private BufferedImage backgroundImage;

    private final Dimension size1 = new Dimension(120, 30);
    private final Dimension size2 = new Dimension(200, 50);
    private final Font componentFont1 = new Font("Arial", Font.BOLD, 12);
    private final Font componentFont2 = new Font("SansSerif", Font.BOLD, 18);
    private final Font titleFont = new Font("SansSerif", Font.BOLD, 24);

    /**
     * Constructor que arma la interfaz gráfica de creación de torneo.
     * Incluye un formulario y botones para crear o volver atrás.
     */
    public PanelCrearTorneo() {
        super(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        try {
            backgroundImage = ImageIO.read(getClass().getResource("/images/image1.png"));
            if (backgroundImage == null) {
                System.err.println("La imagen no se encontró en la ruta: /images/image1.png");
                setBackground(new Color(195, 0, 0));
            }
            else {
                float darkFactor = 0.5f;
                float[] scales = {darkFactor, darkFactor, darkFactor, 1.0f};
                float[] offsets = {0f, 0f, 0f, 0f};

                RescaleOp op = new RescaleOp(scales, offsets, null);
                backgroundImage = op.filter(backgroundImage, null);
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error al cargar la imagen de fondo: " + e.getMessage());
            setBackground(new Color(195, 0, 0));
        }

        // Inicializar Componenetes
        irAtrasBtn = new PanelButton("Volver atrás", componentFont1);
        irAtrasBtn.setButtonPreferredSize(size1);
        irAtrasBtn.setButtonColor(new Color(50, 50, 50), Color.WHITE, Color.GRAY, 1);

        panelFormularioTorneo = new PanelFormularioTorneo();
        crearTorneoBtn = new PanelButton("Crear Torneo", componentFont2);
        crearTorneoBtn.setButtonPreferredSize(size2);
        crearTorneoBtn.setButtonColor(new Color(200, 0, 0),
                Color.WHITE,
                new Color(255, 100, 100),
                2
        );

        // Panel superior
        JPanel topPanel = GuiUtils.crearPanelDeEncabezado(irAtrasBtn,
                "",
                titleFont,
                null

        );
        topPanel.setOpaque(false);
        add(topPanel, BorderLayout.NORTH);

        // Panel central con el formulario
        JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(30, 0, 0, 0));
        centerPanel.setOpaque(false);

        centerPanel.add(panelFormularioTorneo);
        add(centerPanel, BorderLayout.CENTER);

        // Panel inferior con botón de crear
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        bottomPanel.setOpaque(false);
        bottomPanel.add(crearTorneoBtn);
        add(bottomPanel, BorderLayout.SOUTH);
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
     * Inicializa el panel y registra listeners para los botones.
     *
     * @param actionAssigner Encargado de asignar acciones a los botones.
     * @param gestorTorneos Instancia lógica que administra los torneos.
     */
    @Override
    public void inicializar(ActionAssigner actionAssigner, GestorTorneos gestorTorneos) {
        this.gestorTorneos = gestorTorneos;
        this.gestorTorneos.registrarObserver(this);

        if (!listenersActivos) {
            irAtrasBtn.addActionListener(actionAssigner.getAction(ActionGUI.IR_A_ORGANIZADOR.getID()));
            crearTorneoBtn.addActionListener(e -> clickCrearBtn(e, actionAssigner));
            listenersActivos = true;

        }
        this.revalidate();
        this.repaint();
    }

    /**
     * Recibe mensajes desde el gestor de torneos y los muestra como mensajes emergentes.
     *
     * @param mensaje Mensaje enviado desde el GestorTorneos.
     */
    @Override
    public void actualizar(String mensaje) {
        if (mensaje.contains("Ya existe un torneo con el nombre")) {
            GuiUtils.showMessageOnce(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
        } else if (mensaje.contains("Torneo creado exitosamente:")) {
            GuiUtils.showMessageOnce(this, mensaje, "Éxito", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    /**
     * Lógica que se ejecuta cuando se hace clic en el botón "Crear Torneo".
     * Valida los datos del formulario y crea el torneo si son válidos.
     *
     * @param e Evento de acción.
     * @param actionAssigner Asignador de acciones para volver a la pantalla anterior si se crea con éxito.
     */
    private void clickCrearBtn(ActionEvent e, ActionAssigner actionAssigner) {
        String nombre = panelFormularioTorneo.getNombre().trim();
        String disciplina = panelFormularioTorneo.getDisciplina().trim();
        String tipoDeInscripcion = panelFormularioTorneo.getTipoInscripcion();
        int numParticipantes = panelFormularioTorneo.getNumParticipantes();
        int numMiembrosEquipo = panelFormularioTorneo.getNumMiembrosEquipo();

        if (nombre.isEmpty() || disciplina.isEmpty()) {
            GuiUtils.showMessageOnce(this, "Por favor complete todos los campos", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if ("Equipo".equals(tipoDeInscripcion) && numMiembrosEquipo <= 0) {
            GuiUtils.showMessageOnce(this, "El número de miembros por equipo debe ser mayor a 0.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        Torneo torneo = new Torneo(nombre, disciplina, tipoDeInscripcion, numParticipantes, numMiembrosEquipo);
        gestorTorneos.addTorneo(torneo);

        if (gestorTorneos.getCreadoConExito()) {
            panelFormularioTorneo.clearFields();
            actionAssigner.getAction(ActionGUI.IR_A_ORGANIZADOR.getID()).actionPerformed(e);
        }
    }
}
