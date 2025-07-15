package org.example.GUI;

import org.example.CodigoLogico.GestorTorneos;

import javax.swing.*;
import java.awt.*;

/**
 * Panel que representa la vista principal del organizador,
 * incluyendo botones para las acciones clave como crear torneos,
 * inscribir participantes, iniciar torneos y eliminar torneos.
 */
public class PanelOrganizador extends JPanel implements PanelConfigurable {
    private OrganizadorButtons buttonsGroup;
    private PanelButton irAInicioBtn;
    private PanelButton eliminarTorneoBtn;  // Nuevo botón para eliminar torneos

    /**
     * Constructor que inicializa y configura los componentes del panel.
     */
    public PanelOrganizador() {
        super(new BorderLayout());
        setBackground(new Color(200, 255, 200));

        Font font1 = new Font("Arial", Font.BOLD, 12);
        irAInicioBtn = new PanelButton("Volver al Inicio", font1);
        eliminarTorneoBtn = new PanelButton("Eliminar Torneo", font1);
        eliminarTorneoBtn.setBackground(new Color(255, 100, 100)); // Color rojo para indicar acción importante
        eliminarTorneoBtn.setForeground(Color.WHITE);

        // Panel superior con botones de navegación
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setOpaque(false);

        // Panel izquierdo con botón de volver
        JPanel topLeftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topLeftPanel.setOpaque(false);
        topLeftPanel.add(irAInicioBtn);
        topPanel.add(topLeftPanel, BorderLayout.WEST);

        // Panel derecho con botón de eliminar
        JPanel topRightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        topRightPanel.setOpaque(false);
        topRightPanel.add(eliminarTorneoBtn);
        topPanel.add(topRightPanel, BorderLayout.EAST);

        add(topPanel, BorderLayout.NORTH);

        // Posicionar grupo de botones principales
        buttonsGroup = new OrganizadorButtons();
        JPanel centerLeftPanel = new JPanel(new BorderLayout());
        centerLeftPanel.setOpaque(false);
        centerLeftPanel.add(buttonsGroup, BorderLayout.WEST);
        add(centerLeftPanel, BorderLayout.CENTER);
    }

    /**
     * Inicializa el panel, asignando acciones a los botones según el controlador recibido.
     *
     * @param actionAssigner el asignador de acciones que contiene los mapeos a ejecutar
     * @param gestorTorneos  el gestor central de torneos utilizado por la aplicación
     */
    @Override
    public void inicializar(ActionAssigner actionAssigner, GestorTorneos gestorTorneos) {
        irAInicioBtn.addActionListener(actionAssigner.getAction(ActionGUI.IR_A_INICIO.getID()));
        buttonsGroup.getCrearTorneoBtn().addActionListener(actionAssigner.getAction(ActionGUI.IR_A_CREAR_TORNEO.getID()));
        buttonsGroup.getInscribirParticipantesBtn().addActionListener(actionAssigner.getAction(ActionGUI.IR_A_INSCRIBIR_PARTICIPANTES.getID()));
        buttonsGroup.getIniciarTorneoBtn().addActionListener(actionAssigner.getAction(ActionGUI.IR_A_INICIAR_TORNEO.getID()));
        buttonsGroup.getActualizarRegistroDeResultadosBtn().addActionListener(actionAssigner.getAction(ActionGUI.IR_A_REGISTRAR_RESULTADOS.getID()));
        eliminarTorneoBtn.addActionListener(actionAssigner.getAction(ActionGUI.IR_A_ELIMINAR_TORNEO.getID()));  // Nueva acción para eliminar torneos

        this.revalidate();
        this.repaint();
    }
}