package org.example.GUI;

import javax.swing.*;
import java.awt.*;

/**
 * Panel que contiene los botones de las acciones principales para un organizador de torneos.
 * Organiza los botones verticalmente con un diseño uniforme.
 */
public class OrganizadorButtons extends JPanel {
    private PanelButton crearTorneoBtn;
    private PanelButton inscribirParticipantesBtn;
    private PanelButton iniciarTorneoBtn;
    private PanelButton actualizarRegistroDeResultadosBtn;

    /**
     * Constructor que configura el panel y añade los botones de acciones del organizador.
     */
    public OrganizadorButtons() {
        setOpaque(false);
        setLayout(new GridLayout(4, 1, 0, 100));
        setBorder(BorderFactory.createEmptyBorder(40, 150, 60, 0));

        crearTorneoBtn = crearBotonEstilizado("Crear Torneo");
        inscribirParticipantesBtn = crearBotonEstilizado("Inscribir Participantes");
        iniciarTorneoBtn = crearBotonEstilizado("Iniciar Torneo");
        actualizarRegistroDeResultadosBtn = crearBotonEstilizado("Registrar Resultados");

        add(crearTorneoBtn);
        add(inscribirParticipantesBtn);
        add(iniciarTorneoBtn);
        add(actualizarRegistroDeResultadosBtn);
    }

    /**
     * Meodo auxiliar para crear un botón con un estilo visual predefinido.
     * @param texto El texto que se mostrará en el botón.
     * @return Un botón con el estilo aplicado.
     */
    private PanelButton crearBotonEstilizado(String texto) {
        PanelButton boton = new PanelButton(texto, new Font("SansSerif", Font.BOLD, 18));
        boton.setButtonPreferredSize(new Dimension(250, 50));
        boton.setButtonColor(
                new Color(70, 130, 180),
                Color.WHITE,
                new Color(100, 149, 237),
                2
        );
        return boton;
    }


    /**
     * Obtiene el botón para crear un torneo.
     * @return El botón "Crear Torneo".
     */
    public PanelButton getCrearTorneoBtn() {
        return crearTorneoBtn;
    }

    /**
     * Obtiene el botón para inscribir participantes.
     * @return El botón "Inscribir Participantes".
     */
    public PanelButton getInscribirParticipantesBtn() {
        return inscribirParticipantesBtn;
    }

    /**
     * Obtiene el botón para iniciar un torneo.
     * @return El botón "Iniciar Torneo".
     */
    public PanelButton getIniciarTorneoBtn() {
        return iniciarTorneoBtn;
    }

    /**
     * Obtiene el botón para registrar resultados.
     * @return El botón "Registrar Resultados".
     */
    public PanelButton getActualizarRegistroDeResultadosBtn() {
        return actualizarRegistroDeResultadosBtn;
    }
}