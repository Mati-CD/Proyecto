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
    private PanelButton eliminarTorneoBtn;

    /**
     * Constructor que inicializa y configura los componentes del panel.
     */
    public PanelOrganizador() {
        super(new BorderLayout());
        setBackground(new Color(200, 255, 200));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        Font font1 = new Font("Arial", Font.BOLD, 12);

        irAInicioBtn = new PanelButton("Volver al Inicio", font1);
        irAInicioBtn.setButtonPreferredSize(new Dimension(120, 30));
        eliminarTorneoBtn = new PanelButton("Eliminar Torneo", font1);
        eliminarTorneoBtn.setButtonPreferredSize(new Dimension(130, 30));
        eliminarTorneoBtn.setBackground(new Color(255, 100, 100));
        eliminarTorneoBtn.setForeground(Color.WHITE);

        JPanel topPanel = GuiUtils.crearPanelDeEncabezado(irAInicioBtn,
                "",
                font1,
                eliminarTorneoBtn
        );
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
        eliminarTorneoBtn.addActionListener(actionAssigner.getAction(ActionGUI.IR_A_ELIMINAR_TORNEO.getID()));

        this.revalidate();
        this.repaint();
    }
}