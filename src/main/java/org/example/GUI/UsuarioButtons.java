package org.example.GUI;

import javax.swing.*;
import java.awt.*;

/**
 * Panel que contiene los botones principales para el usuario normal.
 *
 * Permite acceder a distintas secciones, sea para ver el estado actual del torneo, proximos encuentros,
 * estadisticas o los participantes.
 *
 */
public class UsuarioButtons extends JPanel {
    private PanelButton verEstadoActualTorneoBtn;
    private PanelButton verEstadisticasBtn;
    private PanelButton verParticipantesBtn;

    /**
     * Constructor que crea y organiza los botones en el panel.
     * Usa una fuente grande y un espaciado amplio entre los botones.
     */
    public UsuarioButtons() {
        setOpaque(false);

        Font font = new Font("SansSerif", Font.BOLD, 18);

        setLayout(new GridLayout(4, 1, 0, 100));
        setBorder(BorderFactory.createEmptyBorder(40, 150, 60, 0));

        // Crear Botones
        verEstadoActualTorneoBtn = new PanelButton("Ver estado actual del Torneo", font);
        verEstadisticasBtn = new PanelButton("Ver estadisticas del Torneo", font);
        verParticipantesBtn = new PanelButton("Ver datos de participantes", font);

        add(verEstadoActualTorneoBtn);
        add(verEstadisticasBtn);
        add(verParticipantesBtn);
    }

    /**
     * @return Botón para ir a la vista de estado actual del torneo.
     */
    public PanelButton getVerEstadoActualTorneoBtn() {
        return verEstadoActualTorneoBtn;
    }

    /**
     * @return Botón para ver estadísticas del torneo.
     */
    public PanelButton getVerEstadisticasBtn() {
        return verEstadisticasBtn;
    }

    /**
     * @return Botón para ver los datos de los participantes.
     */
    public PanelButton getVerParticipantesBtn() {
        return verParticipantesBtn;
    }
}