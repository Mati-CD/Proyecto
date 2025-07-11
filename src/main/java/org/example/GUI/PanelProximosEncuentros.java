package org.example.GUI;

import javax.swing.*;
import java.awt.*;

public class PanelProximosEncuentros extends JPanel implements PanelConfigurable {
private ProximosEncuentrosButtons buttonsGroup;

    public PanelProximosEncuentros() {
        super(new BorderLayout());
        setBackground(new Color(179, 85, 3));

        buttonsGroup = new ProximosEncuentrosButtons();
        this.add(buttonsGroup, BorderLayout.CENTER);

        JLabel titleLabel = new JLabel("Panel Proximos Encuentros", SwingConstants.CENTER);
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
