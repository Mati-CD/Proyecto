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
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        Font font = new Font("SansSerif", Font.BOLD, 14);
        Font titleFont = new Font("Arial", Font.BOLD, 24);

        inscribirBtn = new PanelButton("Inscribir Participante", font);
        removerBtn = new PanelButton("Eliminar participante", font);
        torneosComboBox = new JComboBox<>();
        participantesModel = new DefaultListModel<>();
        participantesList = new JList<>(participantesModel);
        panelFormularioInscripcion = new PanelFormularioInscripcion();
        torneosComboBox.setFont(font);
        participantesList.setFont(font);
        participantesList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Construcción de layout omitida aquí por brevedad, ya está en tu código
        // Incluye topPanel, centerPanel con panel izquierdo y derecho
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

    /**
     * Maneja el evento de inscripción de participante.
     * Valida los campos ingresados y, si son correctos, intenta inscribir al participante en el torneo seleccionado.
     *
     * @throws IllegalArgumentException si los campos requeridos están vacíos.
     */
    private void clickInscribirBtn() {
        String nombre = panelFormularioInscripcion.getNombre();
        String correo = panelFormularioInscripcion.getCorreo();

        if (nombre.isEmpty() || correo.isEmpty()) {
            GuiUtilidades.showMessageOnce(this, "Por favor complete todos los campos", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Torneo torneoSeleccionado = (Torneo) torneosComboBox.getSelectedItem();
        if (torneoSeleccionado == null || gestorTorneos.getTorneosCreados().isEmpty()) {
            GuiUtilidades.showMessageOnce(this, "No hay torneos creados para inscribir participantes.\nPor favor, cree un torneo primero.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        ParticipanteIndividual p = new ParticipanteIndividual(nombre);
        gestorTorneos.addParticipanteATorneo(torneoSeleccionado.getNombre(), p);

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
