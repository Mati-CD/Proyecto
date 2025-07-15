package org.example.GUI;

import org.example.CodigoLogico.*;
import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * Panel que permite al organizador iniciar un torneo.
 * Incluye un botón para generar manualmente el bracket del torneo.
 */
public class PanelIniciarTorneo extends JPanel implements PanelConfigurable, TorneoObserver {
    private PanelButton irAtrasBtn;
    private PanelButton crearBracketBtn;
    private PanelButton iniciarTorneoBtn;
    private JComboBox<Torneo> torneosComboBox;
    private List<Participante> sorteoBracketActual;
    private MatchesDisplayPanel bracketDisplayPanel;
    private GestorTorneos gestorTorneos;
    private boolean listenersActivos = false;

    /**
     * Constructor que configura el panel gráfico.
     * Se establece el layout, los botones y el título.
     */
    public PanelIniciarTorneo() {
        super(new BorderLayout());
        setBackground(new Color(88, 150, 234));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        Font font = new Font("SansSerif", Font.BOLD, 16);
        Font titleFont = new Font("Arial", Font.BOLD, 24);

        // Panel superior con botón de volver
        irAtrasBtn = new PanelButton("Volver atrás", font);
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setOpaque(false);
        topPanel.add(irAtrasBtn, BorderLayout.WEST);

        // Título centrado
        JLabel titleLabel = new JLabel("Iniciar Torneo", SwingConstants.CENTER);
        titleLabel.setFont(titleFont);
        topPanel.add(titleLabel, BorderLayout.CENTER);
        add(topPanel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setOpaque(false);

        JPanel comboPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        comboPanel.setOpaque(false);
        JLabel selectTorneoLabel = new JLabel("Seleccione Torneo:");
        selectTorneoLabel.setFont(font);
        torneosComboBox = new JComboBox<>();
        torneosComboBox.setFont(font);
        comboPanel.add(selectTorneoLabel);
        comboPanel.add(torneosComboBox);
        centerPanel.add(comboPanel, BorderLayout.NORTH);

        bracketDisplayPanel = new MatchesDisplayPanel();
        centerPanel.add(bracketDisplayPanel, BorderLayout.CENTER);
        add(centerPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        bottomPanel.setOpaque(false);

        // Boton de generar bracket
        crearBracketBtn = new PanelButton("Generar Bracket", font);
        crearBracketBtn.setButtonPreferredSize(new Dimension(250, 50));
        bottomPanel.add(crearBracketBtn);

        iniciarTorneoBtn = new PanelButton("Iniciar Torneo", font);
        iniciarTorneoBtn.setButtonPreferredSize(new Dimension(200, 50));
        bottomPanel.add(iniciarTorneoBtn);

        add(bottomPanel, BorderLayout.SOUTH);
    }

    /**
     * Inicializa el panel asignando acciones a los botones.
     *
     * @param actionAssigner Asignador de acciones para los botones.
     * @param gestorTorneos  Gestor que contiene la lógica de torneos.
     */
    @Override
    public void inicializar(ActionAssigner actionAssigner, GestorTorneos gestorTorneos) {
        this.gestorTorneos = gestorTorneos;
        this.gestorTorneos.registrarObserver(this);

        if (!listenersActivos) {
            irAtrasBtn.addActionListener(actionAssigner.getAction(ActionGUI.IR_A_ORGANIZADOR.getID()));
            crearBracketBtn.addActionListener(e -> clickGenerarBracket());
            iniciarTorneoBtn.addActionListener(e -> clickIniciarTorneo());
            torneosComboBox.addActionListener(e -> cargarParticipantesTorneoSeleccionado());
            listenersActivos = true;
        }
        cargarTorneosEnComboBox();

        this.revalidate();
        this.repaint();
    }

    /**
     * Método invocado cuando hay una actualización desde el modelo observado.
     * (No implementado por ahora).
     *
     * @param mensaje Mensaje de actualización.
     */
    @Override
    public void actualizar(String mensaje) {
        if (mensaje.contains("No se puede generar un nuevo bracket")) {
            GuiUtilidades.showMessageOnce(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
        } else if (mensaje.contains("Para generar un bracket del torneo '") && mensaje.contains("se requieren ") && mensaje.contains("participantes.") && mensaje.contains("Actualmente hay")) {
            GuiUtilidades.showMessageOnce(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
        } else if (mensaje.contains("una cantidad incorrecta. Se esperaban")) {
            GuiUtilidades.showMessageOnce(this, mensaje, "Advertencia", JOptionPane.WARNING_MESSAGE);
        } else if (mensaje.contains("iniciado con")){
            GuiUtilidades.showMessageOnce(this, mensaje, "Éxito", JOptionPane.INFORMATION_MESSAGE);
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
        // Siempre llama a cargarParticipantesParaVisualizacion() después de actualizar el ComboBox
        cargarParticipantesTorneoSeleccionado();
    }

    private void clickGenerarBracket() {
        Torneo torneoSeleccionado = (Torneo) torneosComboBox.getSelectedItem();

        if (torneoSeleccionado == null) {
            GuiUtilidades.showMessageOnce(this, "Por favor, seleccione un torneo para generar el bracket.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        List<Participante> sortearParticipantes = torneoSeleccionado.sorteoParticipantesRandom(torneoSeleccionado.getParticipantes());

        if (sortearParticipantes != null) {
            sorteoBracketActual = sortearParticipantes;
            bracketDisplayPanel.setParticipantes(sorteoBracketActual);
        }
    }

    private void clickIniciarTorneo() {
        Torneo torneoSeleccionado = (Torneo) torneosComboBox.getSelectedItem();

        if (torneoSeleccionado == null) {
            GuiUtilidades.showMessageOnce(this, "Porfavor seleccione un torneo para iniciar.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (sorteoBracketActual == null || sorteoBracketActual.isEmpty() || sorteoBracketActual.size() != torneoSeleccionado.getNumParticipantes()) {
            sorteoBracketActual = torneoSeleccionado.getParticipantes();
        }

        int confirmar = JOptionPane.showConfirmDialog(this,
                "¿Está seguro de que desea iniciar el torneo '" + torneoSeleccionado.getNombre() + "' con el bracket actual?",
                "Confirmar Inicio de Torneo",
                JOptionPane.YES_NO_OPTION);

        if (confirmar == JOptionPane.YES_OPTION) {
            torneoSeleccionado.iniciarTorneo(sorteoBracketActual);
        }
    }

    private void cargarParticipantesTorneoSeleccionado() {
        Torneo torneoSeleccionado = (Torneo) torneosComboBox.getSelectedItem();

        if (torneoSeleccionado != null) {
            bracketDisplayPanel.setNumParticipantes(torneoSeleccionado.getNumParticipantes());
            bracketDisplayPanel.setBracketGenerado(false);
        } else {
            bracketDisplayPanel.setNumParticipantes(0);
            bracketDisplayPanel.setBracketGenerado(false);
        }
    }
}