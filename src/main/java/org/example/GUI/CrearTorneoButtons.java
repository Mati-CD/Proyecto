package org.example.GUI;

import javax.swing.*;
import java.awt.*;

public class CrearTorneoButtons extends JPanel implements PanelButtonsGroup {
    private PanelButton irAtrasBtn;

    public CrearTorneoButtons() {
        setLayout(new BorderLayout());
        setOpaque(false);

        Font font = new Font("SansSerif", Font.BOLD, 18);

        // Crear Botones
        irAtrasBtn = new PanelButton("Volver atrás", font);

        // Posicionar botones
        JPanel topLeftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topLeftPanel.setOpaque(false);
        topLeftPanel.add(irAtrasBtn);
        add(topLeftPanel, BorderLayout.NORTH);
    }

    @Override
    public void setButtonActions(ActionAssigner actionAssigner) {
        irAtrasBtn.addActionListener(actionAssigner.getAction(ActionGUI.IR_A_ORGANIZADOR.getID()));
    }
}
