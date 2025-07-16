package org.example.GUI;

import org.example.CodigoLogico.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;
import java.io.IOException;
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
    private Torneo actualObservedTorneo = null;

    private BufferedImage backgroundImage;

    private final Dimension size1 = new Dimension(200, 50);
    private final Dimension size2 = new Dimension(120, 30);
    private final Font componentFont1 = new Font("SansSerif", Font.BOLD, 14);
    private final Font componentFont2 = new Font("Arial", Font.BOLD, 12);
    private final Font titleFont = new Font("Arial", Font.BOLD, 24);

    /**
     * Constructor que configura el panel gráfico.
     * Se establece el layout, los botones y el título.
     */
    public PanelIniciarTorneo() {
        super(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        try {
            backgroundImage = ImageIO.read(getClass().getResource("/images/image1.png"));
            if (backgroundImage == null) {
                System.err.println("La imagen no se encontró en la ruta: /images/image1.png");
                setBackground(new Color(195, 0, 0));
            }
            else {
                float darkFactor = 0.4f;
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

        // Inicializar Componentes
        irAtrasBtn = new PanelButton("Volver atrás", componentFont2);
        irAtrasBtn.setButtonPreferredSize(new Dimension(120, 30));
        irAtrasBtn.setButtonPreferredSize(size2);
        irAtrasBtn.setButtonColor(new Color(50, 50, 50),
                Color.WHITE,
                Color.GRAY,
                1
        );

        crearBracketBtn = new PanelButton("Generar Bracket", componentFont1);
        crearBracketBtn.setButtonPreferredSize(size1);
        crearBracketBtn.setButtonColor(new Color(128, 0, 128),
                Color.WHITE,
                new Color(160, 82, 190),
                2
        );

        iniciarTorneoBtn = new PanelButton("Iniciar Torneo", componentFont1);
        iniciarTorneoBtn.setButtonPreferredSize(size1);
        iniciarTorneoBtn.setButtonColor(new Color(128, 0, 128),
                Color.WHITE,
                new Color(160, 82, 190),
                2
        );

        bracketDisplayPanel = new MatchesDisplayPanel();
        bracketDisplayPanel.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));

        torneosComboBox = new JComboBox<>();
        torneosComboBox.setFont(componentFont1);
        torneosComboBox.setForeground(Color.BLACK);
        torneosComboBox.setBackground(Color.WHITE);
        torneosComboBox.setRenderer(new GuiUtils.ComboBoxRenderer<>(Torneo::getNombre));

        // Panel supeior
        JPanel topPanel = GuiUtils.crearPanelDeEncabezado(irAtrasBtn,
                "",
                titleFont,
                null
        );
        add(topPanel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setOpaque(false);

        JPanel comboPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        comboPanel.setOpaque(false);
        JLabel labelSeleccionarTorneo = new JLabel("Seleccione torneo:");
        labelSeleccionarTorneo.setFont(componentFont1);
        GuiUtils.setLabelTextColor(labelSeleccionarTorneo, Color.WHITE);
        comboPanel.add(labelSeleccionarTorneo);
        comboPanel.add(torneosComboBox);
        centerPanel.add(comboPanel, BorderLayout.NORTH);

        JPanel bracketWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER));
        bracketWrapper.setOpaque(false);
        bracketWrapper.setBorder(BorderFactory.createEmptyBorder(30, 0, 0, 0));
        bracketWrapper.add(bracketDisplayPanel);
        centerPanel.add(bracketWrapper, BorderLayout.CENTER);

        add(centerPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        bottomPanel.setOpaque(false);
        bottomPanel.add(crearBracketBtn);
        bottomPanel.add(iniciarTorneoBtn);

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
            torneosComboBox.addActionListener(e -> actualizarObserverDelTorneo());
            listenersActivos = true;
        }
        cargarTorneosEnComboBox();

        this.revalidate();
        this.repaint();
    }

    /**
     * Metodo invocado cuando hay una actualización desde el modelo observado.
     *
     * @param mensaje Mensaje de actualización.
     */
    @Override
    public void actualizar(String mensaje) {
        if (mensaje.contains("No se puede generar un nuevo bracket")) {
            GuiUtils.showMessageOnce(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
        } else if (mensaje.contains("Para generar un bracket del torneo '") && mensaje.contains("se requieren ") && mensaje.contains("participantes.") && mensaje.contains("Actualmente hay")) {
            GuiUtils.showMessageOnce(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
        } else if (mensaje.contains("ya ha sido iniciado")) {
            GuiUtils.showMessageOnce(this, mensaje, "Advertencia", JOptionPane.WARNING_MESSAGE);
        } else if (mensaje.contains("una cantidad incorrecta. Se esperaban")) {
            GuiUtils.showMessageOnce(this, mensaje, "Advertencia", JOptionPane.WARNING_MESSAGE);
        } else if (mensaje.contains("iniciado con")){
            GuiUtils.showMessageOnce(this, mensaje, "Éxito", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void cargarTorneosEnComboBox() {
        if (actualObservedTorneo != null) {
            actualObservedTorneo.removerObserver(this);
            actualObservedTorneo = null;
        }

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
        actualizarObserverDelTorneo();
    }

    private void clickGenerarBracket() {
        Torneo torneoSeleccionado = (Torneo) torneosComboBox.getSelectedItem();

        if (torneoSeleccionado == null) {
            GuiUtils.showMessageOnce(this, "Por favor, seleccione un torneo para generar el bracket.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        List<Participante> sortearParticipantes = torneoSeleccionado.sorteoParticipantesRandom(torneoSeleccionado.getParticipantes());

        if (sortearParticipantes != null) {
            sorteoBracketActual = sortearParticipantes;
            bracketDisplayPanel.mostrarBracketSorteado(sorteoBracketActual);
        }
    }

    private void clickIniciarTorneo() {
        Torneo torneoSeleccionado = (Torneo) torneosComboBox.getSelectedItem();

        if (torneoSeleccionado == null) {
            GuiUtils.showMessageOnce(this, "Porfavor seleccione un torneo para iniciar.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!torneoSeleccionado.getFases().isEmpty()) {
            GuiUtils.showMessageOnce(this, "El torneo '" + torneoSeleccionado.getNombre() + "' ya ha sido iniciado.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (sorteoBracketActual == null || sorteoBracketActual.isEmpty() || sorteoBracketActual.size() != torneoSeleccionado.getNumParticipantes()) {
            GuiUtils.showMessageOnce(this, "Porfavor genere un bracket antes de iniciar el torneo.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirmar = JOptionPane.showConfirmDialog(this,
                "¿Está seguro de que desea iniciar el torneo '" + torneoSeleccionado.getNombre() + "' con el bracket actual?",
                "Confirmar Inicio de Torneo",
                JOptionPane.YES_NO_OPTION);

        if (confirmar == JOptionPane.YES_OPTION) {
            torneoSeleccionado.iniciarTorneo(sorteoBracketActual);
        }
    }

    private void actualizarObserverDelTorneo() {
        Torneo torneoSeleccionado = (Torneo) torneosComboBox.getSelectedItem();

        if (actualObservedTorneo != null && actualObservedTorneo != torneoSeleccionado) {
            actualObservedTorneo.removerObserver(this);
        }

        if (torneoSeleccionado != null) {
            if (actualObservedTorneo != torneoSeleccionado) {
                torneoSeleccionado.registrarObserver(this);
                actualObservedTorneo = torneoSeleccionado;
            }
            bracketDisplayPanel.setNumParticipantes(torneoSeleccionado.getNumParticipantes());
            bracketDisplayPanel.setBracketGenerado(false);
            this.sorteoBracketActual = null;
        }
        else {
            bracketDisplayPanel.setNumParticipantes(0);
            bracketDisplayPanel.setBracketGenerado(false);
            this.sorteoBracketActual = null;

            if (actualObservedTorneo != null) {
                actualObservedTorneo.removerObserver(this);
                actualObservedTorneo = null;
            }
        }
    }
}