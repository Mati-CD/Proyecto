package org.example.GUI;

import org.example.CodigoLogico.GestorTorneos;

import javax.swing.*;
import java.awt.*;

/**
 * Panel de inicio que permite al usuario elegir entre el rol de Organizador o Usuario.
 * Contiene dos botones principales que redirigen a los respectivos paneles.
 */
public class PanelInicio extends JPanel implements PanelConfigurable {
    private PanelButton organizadorBtn;
    private PanelButton usuarioBtn;

    /**
     * Constructor que inicializa y organiza los botones del panel.
     */
    public PanelInicio() {
        super(new FlowLayout(FlowLayout.CENTER, 10, 100));
        setBackground(Color.LIGHT_GRAY);

        Font font = new Font("SansSerif", Font.BOLD, 18);
        organizadorBtn = new PanelButton("Organizador", font);
        usuarioBtn = new PanelButton("Usuario", font);

        add(organizadorBtn, BorderLayout.WEST);
        add(usuarioBtn, BorderLayout.EAST);
    }

    /**
     * Inicializa el panel asignando las acciones correspondientes a los botones.
     *
     * @param actionAssigner objeto que proporciona las acciones a ejecutar al presionar cada botón
     * @param gestorTorneos instancia del gestor de torneos (no se utiliza directamente en este panel)
     */
    @Override
    public void inicializar(ActionAssigner actionAssigner, GestorTorneos gestorTorneos) {
        organizadorBtn.addActionListener(actionAssigner.getAction(ActionGUI.IR_A_ORGANIZADOR.getID()));
        usuarioBtn.addActionListener(actionAssigner.getAction(ActionGUI.IR_A_USUARIO.getID()));

        this.revalidate();
        this.repaint();
    }
}
