package org.example.GUI;

import javax.swing.*;
import java.awt.*;

public class ProximosEncuentrosButtons extends JPanel implements PanelButtonsGroup {
    private PanelButton irAtrasBtn;

    public ProximosEncuentrosButtons() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setOpaque(false);

        Font font = new Font("SansSerif", Font.BOLD, 18);

        // Crear Botones
        irAtrasBtn = new PanelButton("Volver atrás", font);

        // Posicionar Botones
        irAtrasBtn.setAlignmentX(Component.CENTER_ALIGNMENT);

        add(Box.createVerticalGlue());
        add(irAtrasBtn);
    }

    /**
     * @param actionAssigner El objeto de tipo ActionAssigner que los botones usarán para solicitar y registrar sus ActionListeners.
     */
    @Override
    public void setButtonActions(ActionAssigner actionAssigner) {
        irAtrasBtn.addActionListener(actionAssigner.getAction(ActionGUI.IR_A_USUARIO.getID()));
    }
}
