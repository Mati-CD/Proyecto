package org.example.GUI;

import org.example.CodigoLogico.*;

import javax.swing.*;
import java.awt.*;
import java.util.List;

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

    private final Dimension size1 = new Dimension(200, 50);
    private final Dimension size2 = new Dimension(120, 30);
    private final Font componentFont1 = new Font("SansSerif", Font.BOLD, 14);
    private final Font componentFont2 = new Font("Arial", Font.BOLD, 12);
    private final Font titleFont = new Font("Arial", Font.BOLD, 24);

    /**
     * Constructor que inicializa los componentes gráficos del panel de inscripción.
     */
    public PanelInscribirParticipantes() {
        super(new BorderLayout());
        setBackground(new Color(6, 153, 153));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Inicializar Componentes
        inscribirBtn = new PanelButton("Inscribir Participante", componentFont1);
        inscribirBtn.setButtonPreferredSize(size1);
        removerBtn = new PanelButton("Eliminar participante", componentFont1);
        removerBtn.setButtonPreferredSize(size1);
        irAtrasBtn = new PanelButton("Volver atrás", componentFont2);
        irAtrasBtn.setButtonPreferredSize(size2);

        torneosComboBox = new JComboBox<>();
        torneosComboBox.setFont(componentFont1);
        torneosComboBox.setRenderer(new GuiUtils.ComboBoxRenderer<>(Torneo::getNombre));

        participantesModel = new DefaultListModel<>();
        participantesList = new JList<>(participantesModel);
        participantesList.setFont(componentFont1);
        participantesList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        panelFormularioIndividual = new PanelFormularioIndividual();
        panelFormularioEquipo = new PanelFormularioEquipo();

        panelVacio = new JPanel();
        panelVacio.setOpaque(false);

        panelFormularioInscripcion = new JPanel(new CardLayout());
        panelFormularioInscripcion.add(panelFormularioIndividual, "INDIVIDUAL");
        panelFormularioInscripcion.add(panelFormularioEquipo, "EQUIPO");
        panelFormularioInscripcion.add(panelVacio, "VACIO");

        // Panel superior
        JPanel topPanel = GuiUtils.crearPanelDeEncabezado(irAtrasBtn,
                "Inscribir Participantes",
                titleFont,
                null
        );
        add(topPanel, BorderLayout.NORTH);

        // Panel Central
        JPanel centerPanel = new JPanel(new BorderLayout(50, 0));
        centerPanel.setOpaque(false);
        centerPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));

        // Panel Central Izquierdo
        JPanel centerLeftPanel = new JPanel(new BorderLayout());
        centerLeftPanel.setOpaque(false);
        centerLeftPanel.add(panelFormularioInscripcion, BorderLayout.CENTER);

        // Panel Izquierdo Inferior
        JPanel leftButtomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        leftButtomPanel.setOpaque(false);
        leftButtomPanel.add(inscribirBtn);
        centerLeftPanel.add(leftButtomPanel, BorderLayout.SOUTH);

        centerPanel.add(centerLeftPanel, BorderLayout.WEST);

        // Panel Central Derecho
        JPanel centerRightPanel = new JPanel(new BorderLayout());
        centerRightPanel.setOpaque(false);
        centerRightPanel.setBorder(BorderFactory.createEmptyBorder(0, 50, 0, 0));

        JPanel comboPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        comboPanel.setOpaque(false);
        JLabel labelSeleccionarTorneo = new JLabel("Seleccione torneo:");
        labelSeleccionarTorneo.setFont(componentFont1);
        comboPanel.add(labelSeleccionarTorneo);
        comboPanel.add(torneosComboBox);
        centerRightPanel.add(comboPanel, BorderLayout.NORTH);

        // Lista de participantes
        JLabel labelParticipantesInscritos = new JLabel("Participantes inscritos");
        labelParticipantesInscritos.setFont(componentFont1);
        labelParticipantesInscritos.setAlignmentX(Component.CENTER_ALIGNMENT);

        contadorParticipantesLabel = new JLabel("0 de 0");
        contadorParticipantesLabel.setFont(componentFont2);
        contadorParticipantesLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JScrollPane scrollPane = new JScrollPane(participantesList);
        scrollPane.setPreferredSize(new Dimension(300, 500));
        scrollPane.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel participantesListPanel = new JPanel();
        participantesListPanel.setLayout(new BoxLayout(participantesListPanel, BoxLayout.Y_AXIS));
        participantesListPanel.setOpaque(false);

        participantesListPanel.add(labelParticipantesInscritos);
        participantesListPanel.add(Box.createVerticalStrut(5));
        participantesListPanel.add(contadorParticipantesLabel);
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
        if (mensaje.contains("ya está inscrito en el torneo")) {
            GuiUtils.showMessageOnce(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
        } else if (mensaje.contains("máximo de inscritos para este torneo")) {
            GuiUtils.showMessageOnce(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
        } else if (mensaje.contains("Participante inscrito exitosamente:")) {
            GuiUtils.showMessageOnce(this, mensaje, "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } else if (mensaje.contains("No se puede eliminar participantes de un torneo que ya ha iniciado.")) {
            GuiUtils.showMessageOnce(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
        } else if (mensaje.contains("no se encontró en el torneo")) {
            GuiUtils.showMessageOnce(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
        } else if (mensaje.contains("eliminado exitosamente del torneo")) {
            GuiUtils.showMessageOnce(this, mensaje, "Éxito", JOptionPane.INFORMATION_MESSAGE);
        }
    }

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
            if (!correo.endsWith("@gmail.com")) {
                GuiUtils.showMessageOnce(this, "Por favor ingrese un correo válido (debe terminar en @gmail.com).", "Error", JOptionPane.ERROR_MESSAGE);
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

    private void clickEliminarParticipanteBtn() {
        int selectedIndex = participantesList.getSelectedIndex();
        if (selectedIndex == -1) {
            GuiUtils.showMessageOnce(this, "Por favor, seleccione un participante de la lista para eliminar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String participanteSeleccionado = participantesModel.getElementAt(selectedIndex);
        Torneo torneoSeleccionado = (Torneo) torneosComboBox.getSelectedItem();

        if (torneoSeleccionado == null) {
            GuiUtils.showMessageOnce(this, "No hay un torneo seleccionado para eliminar el participante.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Participante buscarParticipante = null;
        List<Participante> participantesEnTorneo = gestorTorneos.getParticipantesDeTorneo(torneoSeleccionado.getNombre());
        for (Participante p : participantesEnTorneo) {
            if (p.getNombre().equals(participanteSeleccionado)) {
                buscarParticipante = p;
                break;
            }
        }

        if (buscarParticipante != null) {
            int confirmar = JOptionPane.showConfirmDialog(this,
                    "¿Está seguro de que desea eliminar a '" + participanteSeleccionado + "' del torneo '" + torneoSeleccionado.getNombre() + "'?",
                    "Confirmar Eliminación",
                    JOptionPane.YES_NO_OPTION);
            if (confirmar == JOptionPane.YES_OPTION) {
                gestorTorneos.removeParticipanteDeTorneo(torneoSeleccionado.getNombre(), buscarParticipante);
                cargarParticipantesTorneoSeleccionado();
            }
        }
        else {
            GuiUtils.showMessageOnce(this, "ERROR: No se pudo encontrar el participante seleccionado en la lista del torneo.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void cargarTorneosEnComboBox() {
        List<Torneo> torneos = gestorTorneos.getTorneosCreados();
        GuiUtils.cargarTorneosEnComboBox(torneosComboBox, torneos);
    }

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