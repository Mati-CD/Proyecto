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
        setLayout(new GridLayout(4, 1, 0, 100));
        setBorder(BorderFactory.createEmptyBorder(40, 150, 60, 0));

        verEstadoActualTorneoBtn = crearBotonEstilizado("Ver estado actual del Torneo");
        verEstadisticasBtn = crearBotonEstilizado("Ver estadísticas del Torneo");
        verParticipantesBtn = crearBotonEstilizado("Ver datos de participantes");

        add(verEstadoActualTorneoBtn);
        add(verEstadisticasBtn);
        add(verParticipantesBtn);
    }

    private PanelButton crearBotonEstilizado(String texto) {
        PanelButton boton = new PanelButton(texto, new Font("SansSerif", Font.BOLD, 18));
        boton.setButtonPreferredSize(new Dimension(250, 50));
        boton.setButtonColor(
                new Color(70, 130, 180),  // Azul acero
                Color.WHITE,
                new Color(100, 149, 237), // Azul claro
                2
        );
        return boton;
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
