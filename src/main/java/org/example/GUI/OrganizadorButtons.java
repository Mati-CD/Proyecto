package org.example.GUI;

import org.example.GUI.PanelButton;

import javax.swing.*;
import java.awt.*;

/**
 * Panel que contiene los botones del menú del organizador del torneo.
 */
public class OrganizadorButtons extends JPanel {
    private PanelButton crearTorneoBtn;
    private PanelButton inscribirParticipantesBtn;
    private PanelButton iniciarTorneoBtn;
    private PanelButton actualizarRegistroDeResultadosBtn;

    /**
     * Constructor que inicializa los botones y configura el diseño del panel.
     */
    public OrganizadorButtons() {
        setOpaque(false);

        Font font = new Font("SansSerif", Font.BOLD, 18);

        setLayout(new GridLayout(4, 1, 0, 100));
        setBorder(BorderFactory.createEmptyBorder(40, 150, 60, 0));

        crearTorneoBtn = new PanelButton("Crear Torneo", font);
        inscribirParticipantesBtn = new PanelButton("Inscribir Participantes", font);
        iniciarTorneoBtn = new PanelButton("Iniciar Torneo", font);
        actualizarRegistroDeResultadosBtn = new PanelButton("Actualizar Registro de Resultados", font);

        add(crearTorneoBtn);
        add(inscribirParticipantesBtn);
        add(iniciarTorneoBtn);
        add(actualizarRegistroDeResultadosBtn);
    }

    /**
     * @return el botón para crear un torneo
     */
    public PanelButton getCrearTorneoBtn() {
        return crearTorneoBtn;
    }

    /**
     * @return el botón para inscribir participantes
     */
    public PanelButton getInscribirParticipantesBtn() {
        return inscribirParticipantesBtn;
    }

    /**
     * @return el botón para iniciar un torneo
     */
    public PanelButton getIniciarTorneoBtn() {
        return iniciarTorneoBtn;
    }

    /**
     * @return el botón para actualizar el registro de resultados
     */
    public PanelButton getActualizarRegistroDeResultadosBtn() {
        return actualizarRegistroDeResultadosBtn;
    }
}