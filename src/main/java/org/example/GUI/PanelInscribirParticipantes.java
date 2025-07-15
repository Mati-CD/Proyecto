package org.example.GUI;

import org.example.CodigoLogico.*;
import javax.swing.*;
import java.awt.*;
import java.util.List;

public class PanelInscribirParticipantes extends JPanel implements PanelConfigurable, TorneoObserver {
    private GestorTorneos gestorTorneos;
    private JPanel panelFormularioContainer;
    private PanelFormularioInscripcion panelFormularioIndividual;
    private PanelFormularioEquipo panelFormularioEquipo;
    private PanelButton removerBtn;
    private PanelButton irAtrasBtn;
    private PanelButton inscribirBtn;
    private JComboBox<Torneo> torneosComboBox;
    private DefaultListModel<String> participantesModel;
    private JList<String> participantesList;
    private boolean listenersActivos = false;

    public PanelInscribirParticipantes() {
        super(new BorderLayout());
        setBackground(new Color(6, 153, 153));
        setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

        Font font = new Font("SansSerif", Font.BOLD, 14);
        Font font1 = new Font("Arial", Font.BOLD, 12);
        Font titleFont = new Font("Arial", Font.BOLD, 24);

        // Inicializar Componentes
        inscribirBtn = new PanelButton("Inscribir Participante", font);
        inscribirBtn.setButtonPreferredSize(new Dimension(200, 50));
        removerBtn = new PanelButton("Eliminar participante", font);
        removerBtn.setButtonPreferredSize(new Dimension(200, 50));
        irAtrasBtn = new PanelButton("Volver atrás", font1);
        irAtrasBtn.setButtonPreferredSize(new Dimension(120, 30));

        torneosComboBox = new JComboBox<>();
        torneosComboBox.setFont(font);
        torneosComboBox.setRenderer(new GuiUtils.TorneoComboBoxRenderer());

        participantesModel = new DefaultListModel<>();
        participantesList = new JList<>(participantesModel);
        panelFormularioIndividual = new PanelFormularioInscripcion();
        panelFormularioEquipo = new PanelFormularioEquipo(5); // Máximo 5 integrantes por defecto
        panelFormularioContainer = new JPanel(new CardLayout());
        panelFormularioContainer.add(panelFormularioIndividual, "INDIVIDUAL");
        panelFormularioContainer.add(panelFormularioEquipo, "EQUIPO");

        participantesList.setFont(font);
        participantesList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

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
        centerLeftPanel.add(panelFormularioContainer, BorderLayout.CENTER);

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
        labelSeleccionarTorneo.setFont(font);
        comboPanel.add(labelSeleccionarTorneo);
        comboPanel.add(torneosComboBox);
        centerRightPanel.add(comboPanel, BorderLayout.NORTH);

        // Lista de participantes
        JLabel labelParticipantesInscritos = new JLabel("Participantes inscritos");
        labelParticipantesInscritos.setFont(font);
        labelParticipantesInscritos.setAlignmentX(Component.CENTER_ALIGNMENT);

        JScrollPane scrollPane = new JScrollPane(participantesList);
        scrollPane.setPreferredSize(new Dimension(300, 500));
        scrollPane.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel participantesListPanel = new JPanel();
        participantesListPanel.setLayout(new BoxLayout(participantesListPanel, BoxLayout.Y_AXIS));
        participantesListPanel.setOpaque(false);

        participantesListPanel.add(labelParticipantesInscritos);
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
            inscribirBtn.addActionListener(e -> clickInscribirBtn());
            torneosComboBox.addActionListener(e -> {
                cargarParticipantesTorneoSeleccionado();
                actualizarFormularioSegunTorneo();
            });
            removerBtn.addActionListener(e -> clickEliminarBtn());
            listenersActivos = true;
        }

        cargarTorneosEnComboBox();
        cargarParticipantesTorneoSeleccionado();
        actualizarFormularioSegunTorneo();

        this.revalidate();
        this.repaint();
    }

    private void actualizarFormularioSegunTorneo() {
        Torneo torneoSeleccionado = (Torneo) torneosComboBox.getSelectedItem();
        CardLayout cl = (CardLayout) panelFormularioContainer.getLayout();

        if (torneoSeleccionado != null && torneoSeleccionado.getTipoDeInscripcion().equalsIgnoreCase("equipo")) {
            cl.show(panelFormularioContainer, "EQUIPO");
            panelFormularioEquipo.clearFields();
        } else {
            cl.show(panelFormularioContainer, "INDIVIDUAL");
            panelFormularioIndividual.clearFields();
        }
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

    private void clickInscribirBtn() {
        Torneo torneoSeleccionado = (Torneo) torneosComboBox.getSelectedItem();
        if (torneoSeleccionado == null) {
            GuiUtils.showMessageOnce(this,
                    "No hay torneos disponibles para inscribir participantes",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        Participante participante;

        if (torneoSeleccionado.getTipoDeInscripcion().equalsIgnoreCase("equipo")) {
            // Validación para equipos
            String nombreEquipo = panelFormularioEquipo.getNombreEquipo();
            List<String> integrantes = panelFormularioEquipo.getIntegrantes();
            String correo = panelFormularioEquipo.getCorreo();

            if (nombreEquipo.isEmpty()) {
                GuiUtils.showMessageOnce(this,
                        "Por favor ingrese un nombre para el equipo",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (integrantes.isEmpty()) {
                GuiUtils.showMessageOnce(this,
                        "El equipo debe tener al menos un integrante",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (correo.isEmpty()) {
                GuiUtils.showMessageOnce(this,
                        "Por favor ingrese un correo de contacto",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Crear equipo
            ParticipanteEquipo equipo = new ParticipanteEquipo(nombreEquipo, integrantes.size());
            for (String integrante : integrantes) {
                equipo.addMiembro(integrante);
            }
            equipo.setCorreo(correo); // Necesitarás agregar este método a ParticipanteEquipo
            participante = equipo;
        } else {
            // Validación para individual (mantenido igual)
            String nombre = panelFormularioIndividual.getNombre();
            int edad = panelFormularioIndividual.getEdad();
            String pais = panelFormularioIndividual.getPais();

            if (nombre.isEmpty() || pais.isEmpty()) {
                GuiUtils.showMessageOnce(this,
                        "Por favor complete todos los campos obligatorios",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (edad <= 0) {
                GuiUtils.showMessageOnce(this,
                        "Por favor ingrese una edad válida (mayor a 0)",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            participante = new ParticipanteIndividual(nombre, edad, pais);
        }

        // Inscribir participante (individual o equipo)
        gestorTorneos.addParticipanteATorneo(torneoSeleccionado.getNombre(), participante);

        // Limpiar formulario si la inscripción fue exitosa
        if (gestorTorneos.getInscritoConExito()) {
            if (torneoSeleccionado.getTipoDeInscripcion().equalsIgnoreCase("equipo")) {
                panelFormularioEquipo.clearFields();
            } else {
                panelFormularioIndividual.clearFields();
            }
            cargarParticipantesTorneoSeleccionado();
        }
    }

    /**
     * Maneja el evento de eliminación del participante seleccionado de la lista.
     * Solicita confirmación antes de proceder a eliminarlo del torneo.
     */
    private void clickEliminarBtn() {
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
        } else {
            GuiUtils.showMessageOnce(this, "ERROR: No se pudo encontrar el participante seleccionado en la lista del torneo.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Carga los torneos existentes en el comboBox.
     * Intenta mantener la selección anterior si todavía existe.
     */
    private void cargarTorneosEnComboBox() {
        Torneo seleccionAnterior = (Torneo) torneosComboBox.getSelectedItem();
        torneosComboBox.removeAllItems();
        List<Torneo> torneos = gestorTorneos.getTorneosCreados();

        if (torneos.isEmpty()) {
            torneosComboBox.setEnabled(false);
        } else {
            for (Torneo torneo : torneos) {
                torneosComboBox.addItem(torneo);
            }
            torneosComboBox.setEnabled(true);

            if (seleccionAnterior != null && torneos.contains(seleccionAnterior)) {
                torneosComboBox.setSelectedItem(seleccionAnterior);
            } else if (torneosComboBox.getItemCount() > 0) {
                torneosComboBox.setSelectedIndex(0);
            }
        }
        // Siempre llama a cargarParticipantesParaVisualizacion() después de actualizar el ComboBox
        cargarParticipantesTorneoSeleccionado();
    }

    /**
     * Carga la lista de participantes correspondientes al torneo actualmente seleccionado.
     */
    private void cargarParticipantesTorneoSeleccionado() {
        participantesModel.clear();
        Torneo torneoSeleccionado = (Torneo) torneosComboBox.getSelectedItem();

        if (torneoSeleccionado != null && !torneoSeleccionado.getNombre().equals("No hay torneos creados")) {
            List<Participante> participantes = gestorTorneos.getParticipantesDeTorneo(torneoSeleccionado.getNombre());
            for (Participante p : participantes) {
                participantesModel.addElement(p.getNombre());
            }
        }
    }
}
