package org.example.GUI;

import javax.swing.*;
import java.awt.*;

public class OrganizadorButtons extends JPanel implements PanelButtonsGroup {
    private PanelButton irAInicioBtn;
    private PanelButton crearTorneoBtn;
    private PanelButton inscribirParticipantesBtn;
    private PanelButton iniciarTorneoBtn;
    private PanelButton actualizarRegistroDeResultadosBtn;

    public OrganizadorButtons() {
        setLayout(new BorderLayout());
        setOpaque(false);

        Font font = new Font("SansSerif", Font.BOLD, 18);
        Font font1 = new Font("Arial", Font.BOLD, 12);

        // Crear botones
        irAInicioBtn = new PanelButton("Volver al Inicio", font1);
        crearTorneoBtn = new PanelButton("Crear Torneo", font);
        inscribirParticipantesBtn = new PanelButton("Inscribir Participantes", font);
        iniciarTorneoBtn = new PanelButton("Iniciar Torneo", font);
        actualizarRegistroDeResultadosBtn = new PanelButton("Actualizar Registro de Resultados", font);

        // Posicionar botones
        JPanel topLeftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topLeftPanel.setOpaque(false);
        topLeftPanel.add(irAInicioBtn);
        add(topLeftPanel, BorderLayout.NORTH);

        JPanel columnaButtonsPanel = new JPanel(new GridLayout(4, 1, 0, 100));
        columnaButtonsPanel.setOpaque(false);
        columnaButtonsPanel.setBorder(BorderFactory.createEmptyBorder(40, 150, 60, 0));

        columnaButtonsPanel.add(crearTorneoBtn);
        columnaButtonsPanel.add(inscribirParticipantesBtn);
        columnaButtonsPanel.add(iniciarTorneoBtn);
        columnaButtonsPanel.add(actualizarRegistroDeResultadosBtn);

        JPanel centerLeftButtonsPanel = new JPanel(new BorderLayout());
        centerLeftButtonsPanel.setOpaque(false);
        centerLeftButtonsPanel.add(columnaButtonsPanel, BorderLayout.WEST);
        add(centerLeftButtonsPanel, BorderLayout.CENTER);
    }

    @Override
    public void setButtonActions(ActionAssigner actionAssigner) {
        irAInicioBtn.addActionListener(actionAssigner.getAction(ActionGUI.IR_A_INICIO.getID()));
        crearTorneoBtn.addActionListener(actionAssigner.getAction(ActionGUI.CREAR_TORNEO.getID()));
        inscribirParticipantesBtn.addActionListener(actionAssigner.getAction(ActionGUI.INSCRIBIR_PARTICIPANTES.getID()));
        iniciarTorneoBtn.addActionListener(actionAssigner.getAction(ActionGUI.INICIAR_TORNEO.getID()));
    }
}
