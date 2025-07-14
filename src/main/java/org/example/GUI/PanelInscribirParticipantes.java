package org.example.GUI;

import org.example.CodigoLogico.*;
import java.util.List;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class PanelInscribirParticipantes extends JPanel implements PanelConfigurable, TorneoObserver {
    private GestorTorneos gestorTorneos;
    private PanelFormularioInscripcion panelFormularioInscripcion;
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

            listenersActivos = true;
        }
        cargarTorneosEnComboBox();
        cargarParticipantesTorneoSeleccionado();

        this.revalidate();
        this.repaint();
    }

    @Override
    public void actualizar(String mensaje) {
        if (mensaje.contains("ERROR")) {
            GuiUtilidades.showMessageOnce(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
        } else if (mensaje.contains("Participante inscrito exitosamente:")) {
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

        Torneo torneoSeleccionado = (Torneo) torneosComboBox.getSelectedItem();
        if (torneoSeleccionado == null) {
            GuiUtilidades.showMessageOnce(this, "Por favor, seleccione un torneo antes de inscribir un participante.", "Error", JOptionPane.ERROR_MESSAGE);
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

    private void cargarTorneosEnComboBox() {
        Torneo seleccionAnteior = (Torneo) torneosComboBox.getSelectedItem();

        torneosComboBox.removeAllItems();

        List<Torneo> torneos = gestorTorneos.getTorneosCreados();

        if (torneos.isEmpty()) {
            torneosComboBox.setEnabled(false);
            inscribirBtn.setEnabled(false);
        }
        else {
            for (Torneo torneo : torneos) {
                torneosComboBox.addItem(torneo);
            }
            torneosComboBox.setEnabled(true);
            inscribirBtn.setEnabled(true);

            if (seleccionAnteior != null && torneos.contains(seleccionAnteior)) {
                torneosComboBox.setSelectedItem(seleccionAnteior);
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