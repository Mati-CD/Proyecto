package org.example.GUI;

import org.example.CodigoLogico.GestorTorneos;

import javax.swing.*;
import java.awt.*;

public class PanelOrganizador extends JPanel implements PanelConfigurable {
    private OrganizadorButtons buttonsGroup;
    private PanelButton irAInicioBtn;

    public PanelOrganizador() {
        super(new BorderLayout());
        setBackground(new Color(200, 255, 200));

        Font font1 = new Font("Arial", Font.BOLD, 12);
        irAInicioBtn = new PanelButton("Volver al Inicio", font1);
        // Posicionar boton
        JPanel topLeftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topLeftPanel.setOpaque(false);
        topLeftPanel.add(irAInicioBtn);
        add(topLeftPanel, BorderLayout.NORTH);

        // Posicionar grupo de botones
        buttonsGroup = new OrganizadorButtons();
        JPanel centerLeftPanel = new JPanel(new BorderLayout());
        centerLeftPanel.setOpaque(false);
        centerLeftPanel.add(buttonsGroup, BorderLayout.WEST);
        add(centerLeftPanel, BorderLayout.CENTER);
    }

    @Override
    public void inicializar(ActionAssigner actionAssigner, GestorTorneos gestorTorneos) {
        irAInicioBtn.addActionListener(actionAssigner.getAction(ActionGUI.IR_A_INICIO.getID()));
        buttonsGroup.getCrearTorneoBtn().addActionListener(actionAssigner.getAction(ActionGUI.IR_A_CREAR_TORNEO.getID()));
        buttonsGroup.getInscribirParticipantesBtn().addActionListener(actionAssigner.getAction(ActionGUI.IR_A_INSCRIBIR_PARTICIPANTES.getID()));
        buttonsGroup.getIniciarTorneoBtn().addActionListener(actionAssigner.getAction(ActionGUI.IR_A_INICIAR_TORNEO.getID()));
        buttonsGroup.getActualizarRegistroDeResultadosBtn().addActionListener(actionAssigner.getAction(ActionGUI.IR_A_REGISTRAR_RESULTADOS.getID()));

        this.revalidate();
        this.repaint();
    }
}
