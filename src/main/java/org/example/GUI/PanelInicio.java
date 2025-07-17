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
        setLayout(new BorderLayout());
        setOpaque(false);

        Font font = new Font("SansSerif", Font.BOLD, 18);

        organizadorBtn = new PanelButton("Organizador", font);
        organizadorBtn.setButtonColor(
                new Color(70, 130, 180),
                Color.WHITE,
                new Color(100, 149, 237),
                20
        );

        usuarioBtn = new PanelButton("Usuario", font);
        usuarioBtn.setButtonColor(
                new Color(70, 130, 180),
                Color.WHITE,
                new Color(100, 149, 237),
                20
        );

        JPanel centerPanel = new JPanel(new GridLayout(2, 1, 20, 20));
        centerPanel.setOpaque(false);
        centerPanel.setBorder(BorderFactory.createEmptyBorder(100, 200, 100, 200));
        centerPanel.add(organizadorBtn);
        centerPanel.add(usuarioBtn);

        add(centerPanel, BorderLayout.CENTER);
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
