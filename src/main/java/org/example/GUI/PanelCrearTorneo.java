package org.example.GUI;

import org.example.CodigoLogico.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

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

    /**
     * Constructor que arma la interfaz gráfica de creación de torneo.
     * Incluye un formulario y botones para crear o volver atrás.
     */
    public PanelCrearTorneo() {
        super(new BorderLayout());
        setBackground(new Color(255, 255, 200));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        Font font = new Font("SansSerif", Font.BOLD, 12);
        Font font1 = new Font("Arial", Font.BOLD, 12);
        Font titleFont = new Font("Arial", Font.BOLD, 24);

        // Inicializar Componenetes
        irAtrasBtn = new PanelButton("Volver atrás", font1);
        irAtrasBtn.setButtonPreferredSize(new Dimension(120, 30));
        panelFormularioTorneo = new PanelFormularioTorneo();
        crearTorneoBtn = new PanelButton("Crear Torneo", font);
        crearTorneoBtn.setButtonPreferredSize(new Dimension(200, 50));

        // Panel superior
        JPanel topPanel = GuiUtils.crearPanelDeEncabezado(irAtrasBtn,
                "Crear nuevo torneo",
                titleFont,
                null
        );
        add(topPanel, BorderLayout.NORTH);

        // Panel central con el formulario
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setOpaque(false);

        JPanel centerLeftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        centerLeftPanel.setOpaque(false);
        centerLeftPanel.add(panelFormularioTorneo);
        centerPanel.add(centerLeftPanel, BorderLayout.WEST);

        add(centerPanel, BorderLayout.CENTER);

        // Panel inferior con botón de crear
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        bottomPanel.setOpaque(false);
        bottomPanel.add(crearTorneoBtn);

        add(bottomPanel, BorderLayout.SOUTH);
    }

    /**
     * Inicializa el panel y registra listeners para los botones.
     *
     * @param actionAssigner   Encargado de asignar acciones a los botones.
     * @param gestorTorneos    Instancia lógica que administra los torneos.
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
     * @param e                Evento de acción.
     * @param actionAssigner   Asignador de acciones para volver a la pantalla anterior si se crea con éxito.
     */
    private void clickCrearBtn(ActionEvent e, ActionAssigner actionAssigner) {
        String nombre = panelFormularioTorneo.getNombre().trim();
        String disciplina = panelFormularioTorneo.getDisciplina().trim();
        String tipoDeInscripcion = panelFormularioTorneo.getTipoInscripcion();
        int numParticipantes = panelFormularioTorneo.getNumParticipantes();

        if (nombre.isEmpty() || disciplina.isEmpty()) {
            GuiUtils.showMessageOnce(this, "Por favor complete todos los campos", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Torneo torneo = new Torneo(nombre, disciplina, tipoDeInscripcion, numParticipantes);
        gestorTorneos.addTorneo(torneo);

        if (gestorTorneos.getCreadoConExito()) {
            panelFormularioTorneo.clearFields();
            actionAssigner.getAction(ActionGUI.IR_A_ORGANIZADOR.getID()).actionPerformed(e);
        }
    }
}
