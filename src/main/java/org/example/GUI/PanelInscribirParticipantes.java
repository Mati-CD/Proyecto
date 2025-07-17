package org.example.GUI;

import org.example.CodigoLogico.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import javax.swing.border.TitledBorder;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;
import java.io.IOException;
import java.util.List;

/**
 * Panel de interfaz gráfica para la inscripción de participantes en torneos.
 * Permite seleccionar un torneo, y luego inscribir participantes (individuales o equipos)
 * o eliminar participantes ya inscritos.
 * El formulario de inscripción se adapta al tipo de inscripción del torneo seleccionado.
 */
public class PanelInscribirParticipantes extends JPanel implements PanelConfigurable, TorneoObserver {
    private GestorTorneos gestorTorneos;
    private PanelButton removerBtn;
    private PanelButton irAtrasBtn;
    private PanelButton inscribirBtn;
    private JComboBox<Torneo> torneosComboBox;
    private DefaultListModel<String> participantesModel;
    private JList<String> participantesList;
    private JLabel contadorParticipantesLabel;
    private boolean listenersActivos = false;

    private PanelFormularioIndividual panelFormularioIndividual;
    private PanelFormularioEquipo panelFormularioEquipo;
    private JPanel panelFormularioInscripcion;
    private JPanel panelVacio;
    private BufferedImage backgroundImage;

    private final Dimension size1 = new Dimension(200, 50);
    private final Dimension size2 = new Dimension(120, 30);
    private final Font componentFont1 = new Font("SansSerif", Font.BOLD, 14);
    private final Font componentFont2 = new Font("Arial", Font.BOLD, 12);
    private final Font titleFont = new Font("Arial", Font.BOLD, 24);

    /**
     * Constructor que inicializa los componentes gráficos del panel de inscripción,
     */
    public PanelInscribirParticipantes() {
        super(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        try {
            backgroundImage = ImageIO.read(getClass().getResource("/images/image1.png"));
            if (backgroundImage == null) {
                System.err.println("La imagen no se encontró en la ruta: /images/image1.png");
                setBackground(new Color(195, 0, 0));
            }
            else {
                float[] scales = {1.5f, 1.0f, 0.4f, 1.0f};
                float[] offsets = {0f, 0f, 0f, 0f};
                RescaleOp op = new RescaleOp(scales, offsets, null);
                backgroundImage = op.filter(backgroundImage, null);
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error al cargar la imagen de fondo: " + e.getMessage());
            setBackground(new Color(195, 0, 0));
        }

        // Inicializar Componentes
        inscribirBtn = new PanelButton("Inscribir Participante", componentFont1);
        inscribirBtn.setButtonPreferredSize(size1);
        inscribirBtn.setButtonColor(new Color(156, 0, 0),
                Color.WHITE,
                new Color(248, 0, 0),
                2
        );

        removerBtn = new PanelButton("Eliminar participante", componentFont1);
        removerBtn.setButtonPreferredSize(size1);
        removerBtn.setButtonColor(new Color(200, 0, 0),
                Color.WHITE,
                new Color(255, 100, 100),
                2
        );

        irAtrasBtn = new PanelButton("Volver atrás", componentFont2);
        irAtrasBtn.setButtonPreferredSize(size2);
        irAtrasBtn.setButtonColor(new Color(50, 50, 50),
                Color.WHITE,
                Color.GRAY,
                1
        );

        torneosComboBox = new JComboBox<>();
        torneosComboBox.setFont(componentFont1);
        torneosComboBox.setRenderer(new GuiUtils.ComboBoxRenderer<>(Torneo::getNombre));
        torneosComboBox.setForeground(Color.BLACK);
        torneosComboBox.setBackground(Color.WHITE);

        participantesModel = new DefaultListModel<>();
        participantesList = new JList<>(participantesModel);
        participantesList.setFont(componentFont1);
        participantesList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        participantesList.setForeground(Color.BLACK);
        participantesList.setBackground(Color.WHITE);
        participantesList.setOpaque(true);

        panelFormularioIndividual = new PanelFormularioIndividual();
        panelFormularioEquipo = new PanelFormularioEquipo();
        panelFormularioIndividual.setOpaque(false);
        panelFormularioEquipo.setOpaque(false);

        panelVacio = new JPanel();
        panelVacio.setOpaque(false);

        panelFormularioInscripcion = new JPanel(new CardLayout());
        panelFormularioInscripcion.add(panelFormularioIndividual, "INDIVIDUAL");
        panelFormularioInscripcion.add(panelFormularioEquipo, "EQUIPO");
        panelFormularioInscripcion.add(panelVacio, "VACIO");

        JPanel topPanel = GuiUtils.crearPanelDeEncabezado(irAtrasBtn,
                "",
                titleFont,
                null
        );
        topPanel.setOpaque(false);
        add(topPanel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new BorderLayout(50, 0));
        centerPanel.setOpaque(false);
        centerPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));

        JPanel centerLeftPanel = new JPanel(new BorderLayout());
        centerLeftPanel.setOpaque(false);
        TitledBorder leftPanelBorder = BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.WHITE, 1),
                "Formulario de Inscripción",
                TitledBorder.CENTER, TitledBorder.TOP,
                componentFont1, Color.WHITE
        );
        centerLeftPanel.setBorder(BorderFactory.createCompoundBorder(
                leftPanelBorder,
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        centerLeftPanel.add(panelFormularioInscripcion, BorderLayout.CENTER);

        JPanel leftButtomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        leftButtomPanel.setOpaque(false);
        leftButtomPanel.add(inscribirBtn);
        centerLeftPanel.add(leftButtomPanel, BorderLayout.SOUTH);

        centerPanel.add(centerLeftPanel, BorderLayout.WEST);

        JPanel centerRightPanel = new JPanel(new BorderLayout());
        centerRightPanel.setOpaque(false);
        TitledBorder rightPanelBorder = BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.WHITE, 1),
                "Participantes Inscritos",
                TitledBorder.CENTER, TitledBorder.TOP,
                componentFont1, Color.WHITE
        );
        centerRightPanel.setBorder(BorderFactory.createCompoundBorder(
                rightPanelBorder,
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));

        JPanel comboPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        comboPanel.setOpaque(false);
        JLabel labelSeleccionarTorneo = new JLabel("Seleccione torneo:");
        labelSeleccionarTorneo.setFont(componentFont1);
        GuiUtils.setLabelTextColor(labelSeleccionarTorneo, Color.WHITE);
        comboPanel.add(labelSeleccionarTorneo);
        comboPanel.add(torneosComboBox);
        centerRightPanel.add(comboPanel, BorderLayout.NORTH);

        // Lista de participantes
        JPanel participantesTitlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));
        participantesTitlePanel.setOpaque(false);

        JLabel labelParticipantesInscritos = new JLabel("Participantes inscritos");
        labelParticipantesInscritos.setFont(componentFont1);
        GuiUtils.setLabelTextColor(labelParticipantesInscritos, Color.WHITE);
        participantesTitlePanel.add(labelParticipantesInscritos);

        contadorParticipantesLabel = new JLabel("(0 de 0)");
        contadorParticipantesLabel.setFont(componentFont1);
        GuiUtils.setLabelTextColor(contadorParticipantesLabel, Color.WHITE);
        participantesTitlePanel.add(contadorParticipantesLabel);

        JScrollPane scrollPane = new JScrollPane(participantesList);
        scrollPane.setPreferredSize(new Dimension(300, 500));
        scrollPane.setAlignmentX(Component.CENTER_ALIGNMENT);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);

        JPanel participantesListPanel = new JPanel();
        participantesListPanel.setLayout(new BoxLayout(participantesListPanel, BoxLayout.Y_AXIS));
        participantesListPanel.setOpaque(false);

        participantesListPanel.add(participantesTitlePanel);
        participantesListPanel.add(Box.createVerticalStrut(10));
        participantesListPanel.add(scrollPane);

        JPanel wrapperParticipantesPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        wrapperParticipantesPanel.setOpaque(false);
        wrapperParticipantesPanel.setBorder(BorderFactory.createEmptyBorder(50, 0, 0, 0));
        wrapperParticipantesPanel.add(participantesListPanel);

        centerRightPanel.add(wrapperParticipantesPanel, BorderLayout.CENTER);

        JPanel rightBottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        rightBottomPanel.setOpaque(false);
        rightBottomPanel.add(removerBtn);
        centerRightPanel.add(rightBottomPanel, BorderLayout.SOUTH);

        centerPanel.add(centerRightPanel, BorderLayout.CENTER);

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
     * Inicializa el panel configurando el gestor de torneos, registrando listeners y actualizando la UI.
     *
     * @param actionAssigner    Asignador de acciones que se encarga de delegar eventos de botones.
     * @param gestorTorneos     Instancia de la lógica del sistema que gestiona los torneos.
     */
    @Override
    public void inicializar(ActionAssigner actionAssigner, GestorTorneos gestorTorneos) {
        this.gestorTorneos = gestorTorneos;
        this.gestorTorneos.registrarObserver(this);

        if (!listenersActivos) {
            irAtrasBtn.addActionListener(actionAssigner.getAction(ActionGUI.IR_A_ORGANIZADOR.getID()));
            inscribirBtn.addActionListener(e -> clickInscribirParticipanteBtn());
            removerBtn.addActionListener(e -> clickEliminarParticipanteBtn());
            torneosComboBox.addActionListener(e -> {
                cargarParticipantesTorneoSeleccionado();
                actualizarFormularioSegunTorneo();
            });
            panelFormularioEquipo.addAgregarIntegranteListener(e -> agregarIntegranteEquipo());
            panelFormularioEquipo.addEliminarIntegranteListener(e -> eliminarIntegranteEquipo());

            listenersActivos = true;
        }
        cargarTorneosEnComboBox();
        cargarParticipantesTorneoSeleccionado();
        actualizarFormularioSegunTorneo();

        this.revalidate();
        this.repaint();
    }

    @Override
    public void actualizar(String mensaje) {
        if (mensaje.startsWith("ERROR_PARTICIPANTE_DUPLICADO:") ||
                mensaje.startsWith("ERROR_TORNEO_LLENO:") ||
                mensaje.startsWith("ERROR_TORNEO_INICIADO:") ||
                mensaje.startsWith("ERROR_PARTICIPANTE_NO_ENCONTRADO:") ||
                mensaje.startsWith("ERROR_TORNEO_NO_ENCONTRADO:") ||
                mensaje.startsWith("ERROR_PARTICIPANTE_NULO:") ||
                mensaje.startsWith("ERROR_INSCRIPCION_FALLIDA:")) {
            GuiUtils.showMessageOnce(this, GuiUtils.formatearMensajeTorneo(mensaje), "Error", JOptionPane.ERROR_MESSAGE);
        } else if (mensaje.startsWith("EXITO_PARTICIPANTE_INSCRITO:") ||
                mensaje.startsWith("EXITO_PARTICIPANTE_ELIMINADO:")) {
            GuiUtils.showMessageOnce(this, GuiUtils.formatearMensajeTorneo(mensaje), "Éxito", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    /**
     * Cambia el formulario visible (individual o equipo) según el tipo de inscripción del torneo seleccionado.
     * Muestra un panel vacío si no hay un torneo válido seleccionado.
     */
    private void actualizarFormularioSegunTorneo() {
        Torneo torneoSeleccionado = (Torneo) torneosComboBox.getSelectedItem();
        CardLayout cardLayout = (CardLayout) (panelFormularioInscripcion.getLayout());

        if (torneoSeleccionado == null || (torneoSeleccionado.getNombre() != null && torneoSeleccionado.getNombre().equals("No hay torneos creados"))) {
            cardLayout.show(panelFormularioInscripcion, "VACIO");
            panelFormularioIndividual.clearFields();
            panelFormularioEquipo.setMaxIntegrantes(0);
            panelFormularioEquipo.clearFields();
            actualizarContadorParticipantes();
        }
        else {
            if (torneoSeleccionado.getTipoDeInscripcion().equalsIgnoreCase("equipo")) {
                cardLayout.show(panelFormularioInscripcion, "EQUIPO");
                panelFormularioEquipo.setMaxIntegrantes(torneoSeleccionado.getNumMiembrosEquipo());
                panelFormularioEquipo.clearFields();
            }
            else {
                cardLayout.show(panelFormularioInscripcion, "INDIVIDUAL");
                panelFormularioIndividual.clearFields();
            }
        }
    }

    /**
     * Gestiona la lógica al hacer clic en el botón "Inscribir Participante".
     * Valida los datos del formulario (individual o equipo) y llama al gestor para inscribir.
     */
    private void clickInscribirParticipanteBtn() {
        Torneo torneoSeleccionado = (Torneo) torneosComboBox.getSelectedItem();
        if (torneoSeleccionado == null || gestorTorneos.getTorneosCreados().isEmpty()) {
            GuiUtils.showMessageOnce(this, "No hay torneos creados para inscribir participantes.\nPor favor, cree un torneo primero.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Participante participante;

        if (torneoSeleccionado.getTipoDeInscripcion().equalsIgnoreCase("equipo")) {
            String nombreEquipo = panelFormularioEquipo.getNombreEquipo();
            List<String> integrantes = panelFormularioEquipo.getIntegrantes();
            String correo = panelFormularioEquipo.getCorreo();

            if (nombreEquipo.isEmpty()) {
                GuiUtils.showMessageOnce(this, "Por favor ingrese un nombre para el equipo.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (integrantes.isEmpty()) {
                GuiUtils.showMessageOnce(this, "El equipo debe tener al menos un integrante.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (integrantes.size() > torneoSeleccionado.getNumMiembrosEquipo()) {
                GuiUtils.showMessageOnce(this, "El número de integrantes (" + integrantes.size() + ") excede el máximo permitido para este torneo (" + torneoSeleccionado.getNumMiembrosEquipo() + ").", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (correo.isEmpty() || !correo.endsWith("@gmail.com")) {
                GuiUtils.showMessageOnce(this, "Por favor ingrese un correo válido para el equipo.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (gestorTorneos.nombreEquipoExiste(torneoSeleccionado.getNombre(), nombreEquipo)) {
                GuiUtils.showMessageOnce(this, "Ya existe un equipo con ese nombre en el torneo.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            ParticipanteEquipo equipo = new ParticipanteEquipo(nombreEquipo, integrantes);
            equipo.setCorreo(correo);
            participante = equipo;
        }
        else {
            String nombre = panelFormularioIndividual.getNombre();
            int edad = panelFormularioIndividual.getEdad();
            String pais = panelFormularioIndividual.getPais();
            String correo = panelFormularioIndividual.getCorreo();

            if (nombre.isEmpty() || pais.isEmpty()) {
                GuiUtils.showMessageOnce(this, "Por favor complete los campos de Nombre y País.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (edad <= 0) {
                GuiUtils.showMessageOnce(this, "Por favor ingrese una edad válida (mayor a 0).", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (correo.isEmpty() || !correo.endsWith("@gmail.com")) {
                GuiUtils.showMessageOnce(this, "Por favor ingrese un correo válido", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (gestorTorneos.nombreIndividualExiste(torneoSeleccionado.getNombre(), nombre)) {
                GuiUtils.showMessageOnce(this, "Ya existe un participante con ese nombre en el torneo.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            participante = new ParticipanteIndividual(nombre, edad, pais);
            participante.setCorreo(correo);
        }

        gestorTorneos.addParticipanteATorneo(torneoSeleccionado.getNombre(), participante);
        if (gestorTorneos.getInscritoConExito()) {
            if (torneoSeleccionado.getTipoDeInscripcion().equalsIgnoreCase("equipo")) {
                panelFormularioEquipo.clearFields();
            }
            else {
                panelFormularioIndividual.clearFields();
            }
            cargarParticipantesTorneoSeleccionado();
        }
    }

    /**
     * Gestiona la lógica al hacer clic en el botón "Eliminar participante".
     * Elimina el participante seleccionado de la lista y del torneo.
     */
    private void clickEliminarParticipanteBtn() {
        int selectedIndex = participantesList.getSelectedIndex();
        if (selectedIndex == -1) {
            GuiUtils.showMessageOnce(this, "Por favor, seleccione un participante de la lista para eliminar.",
                    "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String participanteSeleccionado = participantesModel.getElementAt(selectedIndex);
        Torneo torneoSeleccionado = (Torneo) torneosComboBox.getSelectedItem();

        if (torneoSeleccionado == null) {
            GuiUtils.showMessageOnce(this, "No hay un torneo seleccionado para eliminar el participante.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Buscar el participante por su nombre de torneo (que incluye país para individuales)
        Participante buscarParticipante = null;
        List<Participante> participantesEnTorneo = gestorTorneos.getParticipantesDeTorneo(torneoSeleccionado.getNombre());
        for (Participante p : participantesEnTorneo) {
            if (p.getNombreForTorneo().equals(participanteSeleccionado)) {
                buscarParticipante = p;
                break;
            }
        }

        if (buscarParticipante != null) {
            int confirmar = JOptionPane.showConfirmDialog(this,
                    "¿Está seguro de que desea eliminar a '" + participanteSeleccionado +
                            "' del torneo '" + torneoSeleccionado.getNombre() + "'?",
                    "Confirmar Eliminación",
                    JOptionPane.YES_NO_OPTION);
            if (confirmar == JOptionPane.YES_OPTION) {
                gestorTorneos.removeParticipanteDeTorneo(torneoSeleccionado.getNombre(), buscarParticipante);
                cargarParticipantesTorneoSeleccionado();
            }
        } else {
            GuiUtils.showMessageOnce(this, "ERROR: No se pudo encontrar el participante seleccionado en la lista del torneo.",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Carga la lista de torneos disponibles en el ComboBox.
     */
    private void cargarTorneosEnComboBox() {
        List<Torneo> torneos = gestorTorneos.getTorneosCreados();
        GuiUtils.cargarTorneosEnComboBox(torneosComboBox, torneos);
    }

    /**
     * Carga los participantes del torneo actualmente seleccionado en la lista de participantes.
     */
    private void cargarParticipantesTorneoSeleccionado() {
        participantesModel.clear();
        Torneo torneoSeleccionado = (Torneo) torneosComboBox.getSelectedItem();

        if (torneoSeleccionado != null && !torneoSeleccionado.getNombre().equals("No hay torneos creados")) {
            List<Participante> participantes = gestorTorneos.getParticipantesDeTorneo(torneoSeleccionado.getNombre());
            for (Participante p : participantes) {
                participantesModel.addElement(p.getNombreForTorneo());
            }
        }
        actualizarContadorParticipantes();
    }

    /**
     * Añade un nuevo integrante a la lista del equipo en el formulario,
     * realizando validaciones de nombre, límite y duplicados.
     */
    private void agregarIntegranteEquipo() {
        String nombre = panelFormularioEquipo.getNuevoIntegrante();
        Torneo torneoSeleccionado = (Torneo) torneosComboBox.getSelectedItem();

        if (torneoSeleccionado == null) {
            GuiUtils.showMessageOnce(this, "Seleccione un torneo primero.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (nombre.isEmpty()) {
            GuiUtils.showMessageOnce(this, "Por favor ingrese un nombre para el integrante.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (panelFormularioEquipo.getIntegrantesModel().size() >= torneoSeleccionado.getNumMiembrosEquipo()) {
            GuiUtils.showMessageOnce(this,
                    "El equipo ya tiene el máximo de " + torneoSeleccionado.getNumMiembrosEquipo() + " integrantes permitidos para este torneo.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (panelFormularioEquipo.getIntegrantesModel().contains(nombre)) {
            GuiUtils.showMessageOnce(this,
                    "Este integrante ya está en la lista del equipo.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (gestorTorneos.nombreIndividualExiste(torneoSeleccionado.getNombre(), nombre)) {
            GuiUtils.showMessageOnce(this,
                    "Este nombre ya está en uso por otro participante o integrante de equipo.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        panelFormularioEquipo.getIntegrantesModel().addElement(nombre);
        panelFormularioEquipo.clearNuevoIntegranteField();
        panelFormularioEquipo.updateIntegrantesCountLabel();
    }

    /**
     * Elimina el integrante seleccionado de la lista del equipo en el formulario.
     */
    private void eliminarIntegranteEquipo() {
        int selectedIndex = panelFormularioEquipo.getSelectedIndexIntegrantesList();
        if (selectedIndex == -1) {
            GuiUtils.showMessageOnce(this,
                    "Seleccione un integrante de la lista del equipo para eliminar.",
                    "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }
        panelFormularioEquipo.getIntegrantesModel().remove(selectedIndex);
        panelFormularioEquipo.updateIntegrantesCountLabel();
    }

    /**
     * Actualiza la etiqueta que muestra el conteo actual y máximo de participantes en el torneo seleccionado.
     */
    private void actualizarContadorParticipantes() {
        Torneo torneoSeleccionado = (Torneo) torneosComboBox.getSelectedItem();
        if (torneoSeleccionado != null) {
            int actualCount = participantesModel.size();
            int maxCount = torneoSeleccionado.getNumParticipantes();
            contadorParticipantesLabel.setText(actualCount + " de " + maxCount);
        } else {
            contadorParticipantesLabel.setText("0 de 0");
        }
    }
}