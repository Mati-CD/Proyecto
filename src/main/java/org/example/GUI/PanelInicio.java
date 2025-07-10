package org.example.GUI;

import javax.swing.*;
import java.awt.*;

public class PanelInicio extends JPanel implements PanelConfigurable {
    private InicioButtons buttonsGroup;

    public PanelInicio() {
        super(new FlowLayout(FlowLayout.CENTER, 10, 100));
        setBackground(Color.LIGHT_GRAY);
        buttonsGroup = new InicioButtons();
        this.add(buttonsGroup);
    }

    @Override
    public void inicializar(ActionAssigner actionAssigner) {
        buttonsGroup.setButtonActions(actionAssigner);
        this.revalidate();
        this.repaint();
    }
}
