package org.example.GUI;

import javax.swing.*;
import java.awt.*;

public class PanelUsuario extends JPanel implements PanelConfigurable {
    private UsuarioButtons buttonsGroup;

    public PanelUsuario() {
        super(new BorderLayout());
        setBackground(new Color(200, 200, 255));

        buttonsGroup = new UsuarioButtons();
        this.add(buttonsGroup, BorderLayout.CENTER);
    }

    @Override
    public void inicializar(ActionAssigner actionAssigner) {
        buttonsGroup.setButtonActions(actionAssigner);
        this.revalidate();
        this.repaint();
    }
}