package org.example.GUI;

import org.example.CodigoLogico.*;
import java.util.List;

import javax.swing.*;
import java.awt.*;

/**
 * Panel encargado de permitir la inscripción y eliminación de participantes en torneos existentes.
 * Proporciona un formulario para ingresar los datos del participante, una lista de torneos y una lista
 * de los participantes inscritos en el torneo seleccionado.
 */
public class PanelInscribirParticipantes extends JPanel implements PanelConfigurable, TorneoObserver {
    private GestorTorneos gestorTorneos;
    private PanelFormularioInscripcion panelFormularioInscripcion;
    private PanelButton removerBtn;
    private PanelButton irAtrasBtn;
    private PanelButton inscribirBtn;
    private JComboBox<Torneo> torneosComboBox;
    private DefaultListModel<String> participantesModel;
    private JList<String> participantesList;
    private boolean listenersActivos = false;

    /**
     * Constructor que inicializa los componentes gráficos del panel de inscripción.
     */
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
        panelFormularioInscripcion = new PanelFormularioInscripcion();
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
            inscribirBtn.addActionListener(e -> clickInscribirBtn());
            torneosComboBox.addActionListener(e -> cargarParticipantesTorneoSeleccionado());
            removerBtn.addActionListener(e -> clickEliminarBtn());
            listenersActivos = true;
        }

        cargarTorneosEnComboBox();
        cargarParticipantesTorneoSeleccionado();

        this.revalidate();
        this.repaint();
    }

    /**
     * Recibe notificaciones del gestor de torneos y muestra mensajes según el contenido recibido.
     *
     * @param mensaje Mensaje emitido por el gestor de torneos.
     */
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

    /**
     * Maneja el evento de inscripción de participante.
     * Valida los campos ingresados y, si son correctos, inscribe al participante.
     */
    private void clickInscribirBtn() {
        // Obtener datos del formulario
        String nombre = panelFormularioInscripcion.getNombre();
        int edad = panelFormularioInscripcion.getEdad();
        String pais = panelFormularioInscripcion.getPais();
        String correoUsuario = panelFormularioInscripcion.getCorreo().replace("@gmail.com", "");

        // Validar campos obligatorios
        if (nombre.isEmpty() || pais.isEmpty() || correoUsuario.isEmpty()) {
            GuiUtils.showMessageOnce(this,
                    "Por favor complete todos los campos obligatorios",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Validar edad
        if (edad <= 0) {
            GuiUtils.showMessageOnce(this,
                    "Por favor ingrese una edad válida (mayor a 0)",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Verificar torneo seleccionado
        Torneo torneoSeleccionado = (Torneo) torneosComboBox.getSelectedItem();
        if (torneoSeleccionado == null) {
            GuiUtils.showMessageOnce(this,
                    "No hay torneos disponibles para inscribir participantes",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Crear e inscribir participante
        ParticipanteIndividual participante = new ParticipanteIndividual(nombre, edad, pais);
        gestorTorneos.addParticipanteATorneo(torneoSeleccionado.getNombre(), participante);

        // Limpiar formulario si la inscripción fue exitosa
        if (gestorTorneos.getInscritoConExito()) {
            panelFormularioInscripcion.clearFields();
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
