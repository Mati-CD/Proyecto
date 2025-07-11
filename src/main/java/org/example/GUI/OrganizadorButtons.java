package org.example.GUI;

import javax.swing.*;
import java.awt.*;

public class OrganizadorButtons extends JPanel {
    private PanelButton crearTorneoBtn;
    private PanelButton inscribirParticipantesBtn;
    private PanelButton iniciarTorneoBtn;
    private PanelButton actualizarRegistroDeResultadosBtn;

    public OrganizadorButtons() {
        setOpaque(false);

        Font font = new Font("SansSerif", Font.BOLD, 18);

        setLayout(new GridLayout(4, 1, 0, 100));
        setBorder(BorderFactory.createEmptyBorder(40, 150, 60, 0));

        // Crear botones
        crearTorneoBtn = new PanelButton("Crear Torneo", font);
        inscribirParticipantesBtn = new PanelButton("Inscribir Participantes", font);
        iniciarTorneoBtn = new PanelButton("Iniciar Torneo", font);
        actualizarRegistroDeResultadosBtn = new PanelButton("Actualizar Registro de Resultados", font);

        add(crearTorneoBtn);
        add(inscribirParticipantesBtn);
        add(iniciarTorneoBtn);
        add(actualizarRegistroDeResultadosBtn);
    }

    public PanelButton getCrearTorneoBtn() {
        return crearTorneoBtn;
    }

    public PanelButton getInscribirParticipantesBtn() {
        return inscribirParticipantesBtn;
    }

    public PanelButton getIniciarTorneoBtn() {
        return iniciarTorneoBtn;
    }

    public PanelButton getActualizarRegistroDeResultadosBtn() {
        return actualizarRegistroDeResultadosBtn;
    }
}
