package org.example.GUI;

import javax.swing.*;
import java.awt.*;

public class PanelEstadoActualTorneo extends JPanel implements PanelConfigurable {
    private PanelButton irAtrasBtn;

    public PanelEstadoActualTorneo() {
        super(new BorderLayout());
        setBackground(new Color(0, 32, 142));

        Font font = new Font("SansSerif", Font.BOLD, 18);
        irAtrasBtn = new PanelButton("Volver atrás", font);
        // Posicionar boton
        JPanel topLeftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topLeftPanel.setOpaque(false);
        topLeftPanel.add(irAtrasBtn);
        add(topLeftPanel, BorderLayout.NORTH);

        JLabel titleLabel = new JLabel("Panel Estado Actual Torneo", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        add(titleLabel, BorderLayout.SOUTH);
    }

    @Override
    public void inicializar(ActionAssigner actionAssigner) {
        irAtrasBtn.addActionListener(actionAssigner.getAction(ActionGUI.IR_A_USUARIO.getID()));

        this.revalidate();
        this.repaint();
    }
}
