package org.example.GUI;

import org.example.CodigoLogico.GestorTorneos;

import javax.swing.*;
import java.awt.*;

public class PanelInicio extends JPanel implements PanelConfigurable {
    private PanelButton organizadorBtn;
    private PanelButton usuarioBtn;

    public PanelInicio() {
        super(new FlowLayout(FlowLayout.CENTER, 10, 100));
        setBackground(Color.LIGHT_GRAY);

        Font font = new Font("SansSerif", Font.BOLD, 18);
        organizadorBtn = new PanelButton("Organizador", font);
        usuarioBtn = new PanelButton("Usuario", font);

        add(organizadorBtn, BorderLayout.WEST);
        add(usuarioBtn, BorderLayout.EAST);
    }

    @Override
    public void inicializar(ActionAssigner actionAssigner, GestorTorneos gestorTorneos) {
        organizadorBtn.addActionListener(actionAssigner.getAction(ActionGUI.IR_A_ORGANIZADOR.getID()));
        usuarioBtn.addActionListener(actionAssigner.getAction(ActionGUI.IR_A_USUARIO.getID()));

        this.revalidate();
        this.repaint();
    }
}
