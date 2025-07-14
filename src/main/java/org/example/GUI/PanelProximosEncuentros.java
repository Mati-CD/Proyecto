package org.example.GUI;

import org.example.CodigoLogico.*;
import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * Panel que permite visualizar los próximos encuentros de los torneos disponibles.
 */
public class PanelProximosEncuentros extends JPanel implements PanelConfigurable, TorneoObserver {
    private GestorTorneos gestorTorneos;
    private PanelButton irAtrasBtn;
    private JComboBox<Torneo> torneosComboBox;
    private JList<String> encuentrosList;
    private DefaultListModel<String> encuentrosModel;
    private boolean listenersAdded = false;

    /**
     * Constructor que inicializa los componentes gráficos del panel.
     */
    public PanelProximosEncuentros() {
        super(new BorderLayout());
        setBackground(new Color(179, 85, 3));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        Font font = new Font("SansSerif", Font.BOLD, 16);
        Font titleFont = new Font("Arial", Font.BOLD, 24);

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setOpaque(false);

        irAtrasBtn = new PanelButton("Volver atrás", font);
        JPanel topLeftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topLeftPanel.setOpaque(false);
        topLeftPanel.add(irAtrasBtn);
        topPanel.add(topLeftPanel, BorderLayout.WEST);

        JLabel titleLabel = new JLabel("Próximos Encuentros", SwingConstants.CENTER);
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

        encuentrosModel = new DefaultListModel<>();
        encuentrosList = new JList<>(encuentrosModel);
        encuentrosList.setFont(font);
        encuentrosList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        centerPanel.add(new JScrollPane(encuentrosList), BorderLayout.CENTER);

        add(centerPanel, BorderLayout.CENTER);
    }

    /**
     * Inicializa el panel con las acciones asignadas y registra observadores.
     *
     * @param actionAssigner objeto que asigna acciones a los botones
     * @param gestorTorneos  gestor del sistema de torneos
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
            torneosComboBox.addActionListener(e -> actualizarProximosEncuentros());

            listenersAdded = true;
        }

        actualizarProximosEncuentros();
        this.revalidate();
        this.repaint();
    }

    /**
     * Actualiza la lista de encuentros según el torneo seleccionado.
     */
    private void actualizarProximosEncuentros() {
        Torneo torneo = (Torneo) torneosComboBox.getSelectedItem();
        encuentrosModel.clear();

        if (torneo != null && !torneo.getFases().isEmpty()) {
            FaseTorneo faseActual = torneo.getFaseActual();

            if (faseActual != null) {
                for (TorneoComponent componente : faseActual.getComponentes()) {
                    Partido partido = (Partido) componente;
                    if (!partido.tieneResultado()) {
                        encuentrosModel.addElement(partido.getJugador1() + " vs " + partido.getJugador2());
                    }
                }
            }

            if (encuentrosModel.isEmpty()) {
                encuentrosModel.addElement("No hay próximos encuentros pendientes");
                if (torneo.tieneCampeon()) {
                    encuentrosModel.addElement("El torneo ha finalizado");
                } else {
                    encuentrosModel.addElement("Esperando siguiente fase...");
                }
            }
        } else {
            encuentrosModel.addElement("Seleccione un torneo iniciado");
        }
    }

    /**
     * Método llamado al recibir notificación desde el observable.
     *
     * @param mensaje mensaje emitido por el torneo
     */
    @Override
    public void actualizar(String mensaje) {
        SwingUtilities.invokeLater(this::actualizarProximosEncuentros);
    }
}