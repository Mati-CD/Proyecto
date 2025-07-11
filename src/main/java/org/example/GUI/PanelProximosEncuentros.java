package org.example.GUI;

import javax.swing.*;
import java.awt.*;

public class PanelProximosEncuentros extends JPanel implements PanelConfigurable {
private PanelButton irAtrasBtn;

    public PanelProximosEncuentros() {
        super(new BorderLayout());
        setBackground(new Color(179, 85, 3));

        Font font = new Font("SansSerif", Font.BOLD, 18);
        irAtrasBtn = new PanelButton("Volver atrás", font);
        // Posicionar boton
        JPanel topLeftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topLeftPanel.setOpaque(false);
        topLeftPanel.add(irAtrasBtn);
        add(topLeftPanel, BorderLayout.NORTH);

        JLabel titleLabel = new JLabel("Panel Proximos Encuentros", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        add(titleLabel, BorderLayout.SOUTH);
    }

    /**
     * @param actionAssigner El objeto de tipo ActionAssigner que el panel usará para obtener y configurar sus ActionListeners.
     */
    @Override
    public void inicializar(ActionAssigner actionAssigner) {
        irAtrasBtn.addActionListener(actionAssigner.getAction(ActionGUI.IR_A_USUARIO.getID()));

        this.revalidate();
        this.repaint();
    }
}
