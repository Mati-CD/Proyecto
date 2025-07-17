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
        setLayout(new GridLayout(4, 1, 0, 100));
        setBorder(BorderFactory.createEmptyBorder(40, 150, 60, 0));

        // Todos los botones principales con estilo primario uniforme
        crearTorneoBtn = crearBotonEstilizado("Crear Torneo");
        inscribirParticipantesBtn = crearBotonEstilizado("Inscribir Participantes");
        iniciarTorneoBtn = crearBotonEstilizado("Iniciar Torneo");
        actualizarRegistroDeResultadosBtn = crearBotonEstilizado("Registrar Resultados");

        add(crearTorneoBtn);
        add(inscribirParticipantesBtn);
        add(iniciarTorneoBtn);
        add(actualizarRegistroDeResultadosBtn);
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