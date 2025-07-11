package org.example.GUI;

import javax.swing.*;
import java.awt.*;

public class UsuarioButtons extends JPanel {
    private PanelButton verEstadoActualTorneoBtn;
    private PanelButton verProxEncuentrosBtn;
    private PanelButton verEstadisticasBtn;

    public UsuarioButtons() {
        setOpaque(false);

        Font font = new Font("SansSerif", Font.BOLD, 18);

        setLayout(new GridLayout(4, 1, 0, 100));
        setBorder(BorderFactory.createEmptyBorder(40, 150, 60, 0));

        // Crear Botones
        verEstadoActualTorneoBtn = new PanelButton("Ver estado actual del Torneo", font);
        verProxEncuentrosBtn = new PanelButton("Ver próximos encuentros", font);
        verEstadisticasBtn = new PanelButton("Ver estadisticas del Torneo", font);

        add(verEstadoActualTorneoBtn);
        add(verProxEncuentrosBtn);
        add(verEstadisticasBtn);
    }

    public PanelButton getVerEstadoActualTorneoBtn() {
        return verEstadoActualTorneoBtn;
    }
    public PanelButton getVerProxEncuentrosBtn() {
        return verProxEncuentrosBtn;
    }
    public PanelButton getVerEstadisticasBtn() {
        return verEstadisticasBtn;
    }
}
