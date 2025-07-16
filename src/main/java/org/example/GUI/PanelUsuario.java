package org.example.GUI;

import org.example.CodigoLogico.GestorTorneos;
import javax.swing.*;
import java.awt.*;

/**
 * Panel principal que visualiza las opciones disponibles para el usuario.
 * Ofrece botones para acceder a distintos paneles informativos del torneo.
 */
public class PanelUsuario extends JPanel implements PanelConfigurable {
    private UsuarioButtons buttonsGroup;
    private PanelButton irAInicioBtn;

    /**
     * Constructor que arma la interfaz del panel de usuario.
     */
    public PanelUsuario() {
        super(new BorderLayout());
        setBackground(new Color(200, 200, 255));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        Font font1 = new Font("Arial", Font.BOLD, 12);
        irAInicioBtn = new PanelButton("Volver al Inicio", font1);

        // Panel para posicionar el botón de volver
        JPanel topLeftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topLeftPanel.setOpaque(false);
        topLeftPanel.add(irAInicioBtn);
        add(topLeftPanel, BorderLayout.NORTH);

        // Panel que contiene los botones de acción del usuario
        buttonsGroup = new UsuarioButtons();
        JPanel centerLeftPanel = new JPanel(new BorderLayout());
        centerLeftPanel.setOpaque(false);
        centerLeftPanel.add(buttonsGroup, BorderLayout.WEST);
        add(centerLeftPanel, BorderLayout.CENTER);
    }

    /**
     * Inicializa el panel y asigna las acciones a cada botón.
     *
     * @param actionAssigner Objeto que proporciona las acciones según la interfaz.
     * @param gestorTorneos  No se utiliza directamente en este panel.
     */
    @Override
    public void inicializar(ActionAssigner actionAssigner, GestorTorneos gestorTorneos) {
        irAInicioBtn.addActionListener(actionAssigner.getAction(ActionGUI.IR_A_INICIO.getID()));
        buttonsGroup.getVerEstadoActualTorneoBtn().addActionListener(actionAssigner.getAction(ActionGUI.IR_A_ESTADO_ACTUAL_TORNEO.getID()));
        buttonsGroup.getVerEstadisticasBtn().addActionListener(actionAssigner.getAction(ActionGUI.IR_A_ESTADISTICAS_GENERALES.getID()));
        buttonsGroup.getVerParticipantesBtn().addActionListener(actionAssigner.getAction(ActionGUI.IR_A_VER_PARTICIPANTES.getID()));

        this.revalidate();
        this.repaint();
    }
}