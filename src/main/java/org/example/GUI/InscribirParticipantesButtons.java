package org.example.GUI;

import javax.swing.*;
import java.awt.*;

public class InscribirParticipantesButtons extends JPanel implements PanelButtonsGroup {
    private PanelButton irAtrasBtn;

    public InscribirParticipantesButtons() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(new Color(6, 153, 153));

        Font font = new Font("SansSerif", Font.BOLD, 18);

        // Crear Botones
        irAtrasBtn = new PanelButton("Volver atrás", font);

        irAtrasBtn.setAlignmentX(Component.CENTER_ALIGNMENT);

        add(Box.createVerticalGlue());
        add(irAtrasBtn);
    }

    @Override
    public void setButtonActions(ActionAssigner actionAssigner) {
        irAtrasBtn.addActionListener(actionAssigner.getAction(ActionGUI.IR_A_ORGANIZADOR.getID()));
    }
}