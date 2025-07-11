package org.example.GUI;

import javax.swing.*;
import java.awt.*;

public class UsuarioButtons extends JPanel implements PanelButtonsGroup{
    private PanelButton irAInicioBtn;
    private PanelButton verEstadoActualTorneoBtn;
    private PanelButton verProxEncuentrosBtn;
    private PanelButton verEstadisticasBtn;

    public UsuarioButtons() {
        setLayout(new BorderLayout());
        setOpaque(false);

        Font font = new Font("SansSerif", Font.BOLD, 18);
        Font font1 = new Font("Arial", Font.BOLD, 12);

        // Crear Botones
        irAInicioBtn = new PanelButton("Volver al Inicio", font1);
        verEstadoActualTorneoBtn = new PanelButton("Ver estado actual del Torneo", font);
        verProxEncuentrosBtn = new PanelButton("Ver próximos encuentros", font);
        verEstadisticasBtn = new PanelButton("Ver estadisticas del Torneo", font);

        // Posicionar botones
        JPanel topLeftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topLeftPanel.setOpaque(false);
        topLeftPanel.add(irAInicioBtn);
        add(topLeftPanel, BorderLayout.NORTH);

        JPanel columnaButtonsPanel = new JPanel(new GridLayout(4, 1, 0, 100));
        columnaButtonsPanel.setOpaque(false);
        columnaButtonsPanel.setBorder(BorderFactory.createEmptyBorder(40, 150, 60, 0));

        columnaButtonsPanel.add(verEstadoActualTorneoBtn);
        columnaButtonsPanel.add(verProxEncuentrosBtn);
        columnaButtonsPanel.add(verEstadisticasBtn);

        JPanel centerLeftButtonsPanel = new JPanel(new BorderLayout());
        centerLeftButtonsPanel.setOpaque(false);
        centerLeftButtonsPanel.add(columnaButtonsPanel, BorderLayout.WEST);
        add(centerLeftButtonsPanel, BorderLayout.CENTER);
    }

    @Override
    public void setButtonActions(ActionAssigner actionAssigner) {
        irAInicioBtn.addActionListener(actionAssigner.getAction(ActionGUI.IR_A_INICIO.getID()));
        verEstadoActualTorneoBtn.addActionListener(actionAssigner.getAction(ActionGUI.IR_A_ESTADO_ACTUAL_TORNEO.getID()));
    }
}
