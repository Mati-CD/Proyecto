package org.example.GUI;

import org.example.CodigoLogico.*;
import javax.swing.*;
import java.awt.*;

/**
 * Panel que permite visualizar los participantes inscritos en un torneo seleccionado.
 * Se actualiza automáticamente cuando hay cambios en el torneo (implementa TorneoObserver).
 */
public class PanelVerParticipantes extends JPanel implements PanelConfigurable, TorneoObserver {
    private GestorTorneos gestorTorneos;
    private PanelButton irAtrasBtn;
    private JComboBox<Torneo> torneosComboBox;
    private JTextArea participantesArea;
    private boolean listenersAdded = false;

    /**
     * Constructor que arma la interfaz de selección de torneo y vista de participantes.
     */
    public PanelVerParticipantes() {
        super(new BorderLayout());
        setBackground(new Color(200, 150, 200));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        Font font = new Font("SansSerif", Font.BOLD, 16);
        Font titleFont = new Font("Arial", Font.BOLD, 24);

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setOpaque(false);

        // Botón "Volver atrás" con efecto hover suave y sin borde
        Font botonFont = new Font("Arial", Font.BOLD, 12);
        irAtrasBtn = new PanelButton("Volver atrás", botonFont);
        irAtrasBtn.setButtonColor(
                new Color(220, 220, 220),  // fondo base gris claro
                Color.BLACK,               // texto negro
                new Color(200, 200, 200),  // hover gris un poco más oscuro
                0                         // sin borde
        );

        JPanel topLeftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topLeftPanel.setOpaque(false);
        topLeftPanel.add(irAtrasBtn);
        topPanel.add(topLeftPanel, BorderLayout.WEST);

        JLabel titleLabel = new JLabel("Datos de Participantes", SwingConstants.CENTER);
        titleLabel.setFont(titleFont);
        topPanel.add(titleLabel, BorderLayout.CENTER);
        add(topPanel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new BorderLayout(10, 10));
        centerPanel.setOpaque(false);
        centerPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));

        JPanel torneoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        torneoPanel.setOpaque(false);
        torneoPanel.add(new JLabel("Seleccione torneo:"));
        torneosComboBox = new JComboBox<>();
        torneosComboBox.setFont(font);
        torneoPanel.add(torneosComboBox);
        centerPanel.add(torneoPanel, BorderLayout.NORTH);

        participantesArea = new JTextArea();
        participantesArea.setEditable(false);
        participantesArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        participantesArea.setLineWrap(true);
        participantesArea.setWrapStyleWord(true);
        participantesArea.setBackground(new Color(240, 240, 240));
        centerPanel.add(new JScrollPane(participantesArea), BorderLayout.CENTER);

        add(centerPanel, BorderLayout.CENTER);
    }

    /**
     * Inicializa el panel con los datos necesarios.
     *
     * @param actionAssigner Encargado de asignar las acciones a los botones.
     * @param gestorTorneos Objeto que maneja los torneos disponibles y sus datos.
     */
    @Override
    public void inicializar(ActionAssigner actionAssigner, GestorTorneos gestorTorneos) {
        this.gestorTorneos = gestorTorneos;

        if (!listenersAdded) {
            irAtrasBtn.addActionListener(actionAssigner.getAction(ActionGUI.IR_A_USUARIO.getID()));

            DefaultComboBoxModel<Torneo> model = new DefaultComboBoxModel<>();
            for (Torneo torneo : gestorTorneos.getTorneosCreados()) {
                model.addElement(torneo);
                torneo.registrarObserver(this);
            }
            torneosComboBox.setModel(model);
            torneosComboBox.addActionListener(e -> actualizarParticipantes());

            listenersAdded = true;
        }

        actualizarParticipantes();
        this.revalidate();
        this.repaint();
    }

    // Carga los participantes del torneo seleccionado en el área de texto
    private void actualizarParticipantes() {
        Torneo torneo = (Torneo) torneosComboBox.getSelectedItem();
        StringBuilder sb = new StringBuilder();

        if (torneo != null) {
            sb.append("=== PARTICIPANTES DEL TORNEO ===\n\n");
            sb.append("Torneo: ").append(torneo.getNombre()).append("\n");
            sb.append("Total participantes: ").append(torneo.getParticipantes().size()).append("\n\n");

            for (Participante participante : torneo.getParticipantes()) {
                sb.append(participante.getDatos()).append("\n\n");
            }
        } else {
            sb.append("No hay torneos disponibles");
        }

        participantesArea.setText(sb.toString());
    }

    /**
     * Metodo que se llama cuando se actualiza algún torneo observado.
     *
     * @param mensaje Texto recibido desde el observable.
     */
    @Override
    public void actualizar(String mensaje) {
        SwingUtilities.invokeLater(this::actualizarParticipantes);
    }
}
