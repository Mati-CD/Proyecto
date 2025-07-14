package org.example.GUI;

import org.example.CodigoLogico.*;
import java.util.List;

import javax.swing.*;
import java.awt.*;

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

    public PanelInscribirParticipantes() {
        super(new BorderLayout());
        setBackground(new Color(6, 153, 153));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        Font font = new Font("SansSerif", Font.BOLD, 14);
        Font titleFont = new Font("Arial", Font.BOLD, 24);

        // Componentes
        inscribirBtn = new PanelButton("Inscribir Participante", font);
        removerBtn = new PanelButton("Eliminar participante", font);
        torneosComboBox = new JComboBox<>();
        participantesModel = new DefaultListModel<>();
        participantesList = new JList<>(participantesModel);
        panelFormularioInscripcion = new PanelFormularioInscripcion();
        torneosComboBox.setFont(font);
        participantesList.setFont(font);
        participantesList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Panel Superior
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setOpaque(false);

        irAtrasBtn = new PanelButton("Volver atrás", font);
        irAtrasBtn.setButtonPreferredSize(new Dimension(120, 30));
        JPanel topLeftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        topLeftPanel.setOpaque(false);
        topLeftPanel.add(irAtrasBtn);
        topPanel.add(topLeftPanel, BorderLayout.WEST);

        JLabel titleLabel = new JLabel("Inscribir Participantes", SwingConstants.CENTER);
        titleLabel.setFont(titleFont);
        topPanel.add(titleLabel, BorderLayout.CENTER);

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
        inscribirBtn.setButtonPreferredSize(new Dimension(200, 50));
        leftButtomPanel.add(inscribirBtn);
        centerLeftPanel.add(leftButtomPanel, BorderLayout.SOUTH);

        centerPanel.add(centerLeftPanel, BorderLayout.WEST);

        // Panel Central Derecho
        JPanel centerRightPanel = new JPanel(new BorderLayout());
        centerRightPanel.setOpaque(false);
        centerRightPanel.setBorder(BorderFactory.createEmptyBorder(0, 50, 0, 0));

        JPanel topRightPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        topRightPanel.setOpaque(false);

        JLabel labelSeleccionarTorneo = new JLabel("Seleccione torneo:");
        labelSeleccionarTorneo.setFont(font);
        topRightPanel.add(labelSeleccionarTorneo);
        topRightPanel.add(torneosComboBox);
        centerRightPanel.add(topRightPanel, BorderLayout.NORTH);

        // Lista de participantes
        JLabel labelParticipantesInscritos = new JLabel("Participantes inscritos:");
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
        removerBtn.setButtonPreferredSize(new Dimension(200, 50));
        rightBottomPanel.add(removerBtn);
        centerRightPanel.add(rightBottomPanel, BorderLayout.SOUTH);

        centerPanel.add(centerRightPanel, BorderLayout.CENTER);

        add(centerPanel, BorderLayout.CENTER);
    }

    @Override
    public void inicializar(ActionAssigner actionAssigner, GestorTorneos gestorTorneos) {
        this.gestorTorneos = gestorTorneos;
        this.gestorTorneos.registrarObserver(this);

        if(!listenersActivos) {
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

    @Override
    public void actualizar(String mensaje) {
        if (mensaje.contains("ya está inscrito en el torneo")) {
            GuiUtilidades.showMessageOnce(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
        } else if (mensaje.contains("máximo de inscritos para este torneo")) {
            GuiUtilidades.showMessageOnce(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
        } else if (mensaje.contains("Participante inscrito exitosamente:")) {
            GuiUtilidades.showMessageOnce(this, mensaje, "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } else if (mensaje.contains("No se puede eliminar participantes de un torneo que ya ha iniciado.")) {
            GuiUtilidades.showMessageOnce(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
        } else if (mensaje.contains("no se encontró en el torneo")) {
            GuiUtilidades.showMessageOnce(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
        } else if (mensaje.contains("eliminado exitosamente del torneo")) {
            GuiUtilidades.showMessageOnce(this, mensaje, "Éxito", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void clickInscribirBtn() {
        String nombre = panelFormularioInscripcion.getNombre();
        String pais = panelFormularioInscripcion.getPais();
        int edad = panelFormularioInscripcion.getEdad();
        String correo = panelFormularioInscripcion.getCorreo();

        if (nombre.isEmpty() || pais.isEmpty() || correo.isEmpty()) {
            GuiUtilidades.showMessageOnce(this, "Por favor complete todos los campos", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (edad == -1) {
            GuiUtilidades.showMessageOnce(this, "La edad debe ser un número.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (edad <= 0) {
            GuiUtilidades.showMessageOnce(this, "La edad debe ser positiva.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Torneo torneoSeleccionado = (Torneo) torneosComboBox.getSelectedItem();
        if (torneoSeleccionado == null || gestorTorneos.getTorneosCreados().isEmpty()) {
            GuiUtilidades.showMessageOnce(this, "No hay torneos creados para inscribir participantes." +
                    "\nPor favor, cree un torneo primero.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Crear un participante con los datos
        ParticipanteIndividual p = new ParticipanteIndividual(nombre, edad, pais);
        gestorTorneos.addParticipanteATorneo(torneoSeleccionado.getNombre(), p);

        if (gestorTorneos.getInscritoConExito()) {
            panelFormularioInscripcion.clearFields();
            cargarParticipantesTorneoSeleccionado();
        }
    }

    private void clickEliminarBtn() {
        int selectedIndex = participantesList.getSelectedIndex();
        if (selectedIndex == -1) {
            GuiUtilidades.showMessageOnce(this, "Por favor, seleccione un participante de la lista para eliminar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String participanteSeleccionado = participantesModel.getElementAt(selectedIndex);
        Torneo torneoSeleccionado = (Torneo) torneosComboBox.getSelectedItem();

        if (torneoSeleccionado == null) {
            GuiUtilidades.showMessageOnce(this, "No hay un torneo seleccionado para eliminar el participante.", "Error", JOptionPane.ERROR_MESSAGE);
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
            GuiUtilidades.showMessageOnce(this, "ERROR: No se pudo encontrar el participante seleccionado en la lista del torneo.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void cargarTorneosEnComboBox() {
        Torneo seleccionAnterior = (Torneo) torneosComboBox.getSelectedItem();
        torneosComboBox.removeAllItems();
        List<Torneo> torneos = gestorTorneos.getTorneosCreados();

        if (torneos.isEmpty()) {
            torneosComboBox.setEnabled(false);
        }
        else {
            for (Torneo torneo : torneos) {
                torneosComboBox.addItem(torneo);
            }
            torneosComboBox.setEnabled(true);

            if (seleccionAnterior != null && torneos.contains(seleccionAnterior)) {
                torneosComboBox.setSelectedItem(seleccionAnterior);
            }
            else {
                if (torneosComboBox.getItemCount() > 0) {
                    torneosComboBox.setSelectedIndex(0);
                }
            }
        }
        // Después de cargar y seleccionar, la lista de participantes debe actualizarse
        cargarParticipantesTorneoSeleccionado();
    }

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