package org.example.GUI;

import javax.swing.*;
import java.awt.*;

public class PanelEstadisticasGenerales extends JPanel implements PanelConfigurable {
    private EstadisticasGeneralesButtons buttonsGroup;

    public PanelEstadisticasGenerales() {
        super(new BorderLayout());
        setBackground(new Color(104, 120, 1));

        buttonsGroup = new EstadisticasGeneralesButtons();
        this.add(buttonsGroup, BorderLayout.CENTER);

        JLabel titleLabel = new JLabel("Panel Estadisticas Generales", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        this.add(titleLabel, BorderLayout.NORTH);
    }

    /**
     * @param actionAssigner El objeto de tipo ActionAssigner que el panel usará para obtener y configurar sus ActionListeners.
     */
    @Override
    public void inicializar(ActionAssigner actionAssigner) {
        buttonsGroup.setButtonActions(actionAssigner);
        this.revalidate();
        this.repaint();
    }
}
