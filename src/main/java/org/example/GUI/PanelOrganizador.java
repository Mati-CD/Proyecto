package org.example.GUI;

import javax.swing.*;
import java.awt.*;

public class PanelOrganizador extends JPanel implements PanelConfigurable {
    private OrganizadorButtons buttonsGroup;

    public PanelOrganizador() {
        super(new BorderLayout());
        setBackground(new Color(200, 255, 200));

        buttonsGroup = new OrganizadorButtons();
        this.add(buttonsGroup, BorderLayout.CENTER);
    }

    @Override
    public void inicializar(ActionAssigner actionAssigner) {
        buttonsGroup.setButtonActions(actionAssigner);
        this.revalidate();
        this.repaint();
    }
}
