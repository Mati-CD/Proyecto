package org.example.GUI;

import org.example.CodigoLogico.GestorTorneos;
import javax.swing.*;
import java.awt.*;

public class PanelUsuario extends JPanel implements PanelConfigurable {
    private UsuarioButtons buttonsGroup;
    private PanelButton irAInicioBtn;

    public PanelUsuario() {
        super(new BorderLayout());
        setBackground(new Color(200, 200, 255));

        Font font1 = new Font("Arial", Font.BOLD, 12);
        irAInicioBtn = new PanelButton("Volver al Inicio", font1);
        // Posicionar boton
        JPanel topLeftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topLeftPanel.setOpaque(false);
        topLeftPanel.add(irAInicioBtn);
        add(topLeftPanel, BorderLayout.NORTH);

        // Posicionar grupo de botones
        buttonsGroup = new UsuarioButtons();
        JPanel centerLeftPanel = new JPanel(new BorderLayout());
        centerLeftPanel.setOpaque(false);
        centerLeftPanel.add(buttonsGroup, BorderLayout.WEST);
        add(centerLeftPanel, BorderLayout.CENTER);
    }

    @Override
    public void inicializar(ActionAssigner actionAssigner, GestorTorneos gestorTorneos) {
        irAInicioBtn.addActionListener(actionAssigner.getAction(ActionGUI.IR_A_INICIO.getID()));
        buttonsGroup.getVerEstadoActualTorneoBtn().addActionListener(actionAssigner.getAction(ActionGUI.IR_A_ESTADO_ACTUAL_TORNEO.getID()));
        buttonsGroup.getVerProxEncuentrosBtn().addActionListener(actionAssigner.getAction(ActionGUI.IR_A_PROXIMOS_ENCUENTROS.getID()));
        buttonsGroup.getVerEstadisticasBtn().addActionListener(actionAssigner.getAction(ActionGUI.IR_A_ESTADISTICAS_GENERALES.getID()));
        buttonsGroup.getVerParticipantesBtn().addActionListener(actionAssigner.getAction(ActionGUI.IR_A_VER_PARTICIPANTES.getID()));

        this.revalidate();
        this.repaint();
    }
}