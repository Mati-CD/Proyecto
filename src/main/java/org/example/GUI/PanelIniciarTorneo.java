package org.example.GUI;

import javax.swing.*;
import java.awt.*;

public class PanelIniciarTorneo extends JPanel implements PanelConfigurable {
    private IniciarTorneoButtons buttonsGroup;

    public PanelIniciarTorneo() {
        super(new BorderLayout());
        setBackground(new Color(88, 150, 234));

        buttonsGroup = new IniciarTorneoButtons();
        this.add(buttonsGroup, BorderLayout.CENTER);

        JLabel titleLabel = new JLabel("Panel Iniciar Torneo", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        this.add(titleLabel, BorderLayout.NORTH);
    }

    @Override
    public void inicializar(ActionAssigner actionAssigner) {
        buttonsGroup.setButtonActions(actionAssigner);
        this.revalidate();
        this.repaint();
    }
}
