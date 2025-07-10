package org.example.GUI;

import javax.swing.*;
import java.awt.*;

public class PanelInscribirParticipantes extends JPanel implements PanelConfigurable {
    private InscribirParticipantesButtons buttonsGroup;

    public PanelInscribirParticipantes() {
        super(new BorderLayout());
        setBackground(new Color(6, 153, 153));

        buttonsGroup = new InscribirParticipantesButtons();
        this.add(buttonsGroup, BorderLayout.CENTER);

        JLabel titleLabel = new JLabel("Panel Inscribir Participantes", SwingConstants.CENTER);
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
