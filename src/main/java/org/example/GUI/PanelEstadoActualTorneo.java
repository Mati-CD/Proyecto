package org.example.GUI;

import javax.swing.*;
import java.awt.*;

public class PanelEstadoActualTorneo extends JPanel implements PanelConfigurable {
    private EstadoActualTorneoButtons buttonsGroup;

    public PanelEstadoActualTorneo() {
        super(new BorderLayout());
        setBackground(new Color(0, 32, 142));

        buttonsGroup = new EstadoActualTorneoButtons();
        this.add(buttonsGroup, BorderLayout.CENTER);

        JLabel titleLabel = new JLabel("Panel Estado Actual Torneo", SwingConstants.CENTER);
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
