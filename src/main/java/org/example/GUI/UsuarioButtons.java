package org.example.GUI;

import javax.swing.*;
import java.awt.*;

public class UsuarioButtons extends JPanel {
    private PanelButton verEstadoActualTorneoBtn;
    private PanelButton verProxEncuentrosBtn;
    private PanelButton verEstadisticasBtn;
    private PanelButton verParticipantesBtn;

    public UsuarioButtons() {
        setOpaque(false);

        Font font = new Font("SansSerif", Font.BOLD, 18);

        setLayout(new GridLayout(4, 1, 0, 100));
        setBorder(BorderFactory.createEmptyBorder(40, 150, 60, 0));

        // Crear Botones
        verEstadoActualTorneoBtn = new PanelButton("Ver estado actual del Torneo", font);
        verProxEncuentrosBtn = new PanelButton("Ver próximos encuentros", font);
        verEstadisticasBtn = new PanelButton("Ver estadisticas del Torneo", font);
        verParticipantesBtn = new PanelButton("Ver datos de participantes", font);

        add(verEstadoActualTorneoBtn);
        add(verProxEncuentrosBtn);
        add(verEstadisticasBtn);
        add(verParticipantesBtn);
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
    public PanelButton getVerParticipantesBtn() {
        return verParticipantesBtn;
    }
}