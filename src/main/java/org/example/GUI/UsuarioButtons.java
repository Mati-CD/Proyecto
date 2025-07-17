package org.example.GUI;

import javax.swing.*;
import java.awt.*;

/**
 * Panel que contiene los botones principales para el usuario normal.
 *
 * Permite acceder a distintas secciones, sea para ver el estado actual del torneo, próximos encuentros,
 * estadísticas o los participantes.
 *
 */
public class UsuarioButtons extends JPanel {
    private PanelButton verEstadoActualTorneoBtn;
    private PanelButton verEstadisticasBtn;
    private PanelButton verParticipantesBtn;

    public UsuarioButtons() {
        setOpaque(false);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder(40, 150, 60, 0));

        Font font1 = new Font("SansSerif", Font.BOLD, 18);
        Dimension size1 = new Dimension(350, 200);

        verEstadoActualTorneoBtn = new PanelButton("Ver estado actual de Torneo", font1);
        verEstadoActualTorneoBtn.setButtonPreferredSize(size1);
        verEstadoActualTorneoBtn.setButtonColor(
                new Color(70, 130, 180),
                Color.WHITE,
                new Color(100, 149, 237),
                2
        );

        verEstadisticasBtn = new PanelButton("Ver estadísticas de Torneo", font1);
        verEstadisticasBtn.setButtonPreferredSize(size1);
        verEstadisticasBtn.setButtonColor(
                new Color(70, 130, 180),
                Color.WHITE,
                new Color(100, 149, 237),
                2
        );

        verParticipantesBtn = new PanelButton("Ver datos de participantes", font1);
        verParticipantesBtn.setButtonPreferredSize(size1);
        verParticipantesBtn.setButtonColor(
                new Color(70, 130, 180),
                Color.WHITE,
                new Color(100, 149, 237),
                2
        );

        add(verEstadoActualTorneoBtn);
        add(Box.createVerticalStrut(30));
        add(verEstadisticasBtn);
        add(Box.createVerticalStrut(30));
        add(verParticipantesBtn);
    }

    public PanelButton getVerEstadoActualTorneoBtn() {
        return verEstadoActualTorneoBtn;
    }

    public PanelButton getVerEstadisticasBtn() {
        return verEstadisticasBtn;
    }

    public PanelButton getVerParticipantesBtn() {
        return verParticipantesBtn;
    }
}