package org.example.GUI;

import javax.swing.*;
import java.awt.*;

public class PanelCrearTorneo extends JPanel implements PanelConfigurable {
    private PanelButton irAtrasBtn;

    public PanelCrearTorneo() {
        super(new BorderLayout());
        setBackground(new Color(255, 255, 200));

        Font font = new Font("SansSerif", Font.BOLD, 18);
        irAtrasBtn = new PanelButton("Volver atrás", font);
        // Posicionar boton
        JPanel topLeftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topLeftPanel.setOpaque(false);
        topLeftPanel.add(irAtrasBtn);
        add(topLeftPanel, BorderLayout.NORTH);

        JLabel titleLabel = new JLabel("Panel Crear Torneo", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        add(titleLabel, BorderLayout.SOUTH);
    }

    @Override
    public void inicializar(ActionAssigner actionAssigner) {
        irAtrasBtn.addActionListener(actionAssigner.getAction(ActionGUI.IR_A_ORGANIZADOR.getID()));

        this.revalidate();
        this.repaint();
    }
}