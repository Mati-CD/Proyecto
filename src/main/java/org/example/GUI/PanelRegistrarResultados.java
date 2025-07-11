package org.example.GUI;

import javax.swing.*;
import java.awt.*;

public class PanelRegistrarResultados extends JPanel implements PanelConfigurable {
    private RegistarResultadosButtons buttonsGroup;

    public PanelRegistrarResultados() {
        super(new BorderLayout());
        setBackground(new Color(26, 94, 24));

        buttonsGroup = new RegistarResultadosButtons();
        this.add(buttonsGroup, BorderLayout.CENTER);

        JLabel titleLabel = new JLabel("Panel Registrar Resultados", SwingConstants.CENTER);
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
